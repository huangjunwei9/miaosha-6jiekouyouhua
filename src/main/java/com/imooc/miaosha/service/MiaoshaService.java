package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;


    /* 执行商品秒杀
     * 1.减少库存
     * 2.下订单
     * 3.将订单写入秒杀订单miaosha_order，
     * 4.秒杀成功则返回秒杀订单
     * 注意：这应该放入一个事务中进行，任何一步失败都不能执行商品秒杀
     * */
    @Transactional
    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goodsVo) {
        //减库存，对goods表中的商品id 进行减1操作
        System.out.println("正在减库存");
        goodsService.reduceStock(goodsVo);

        //下订单，并写入到秒杀订单miaosha_order 和 redis缓存中
        System.out.println("正在下订单");
        return orderService.createOrder(miaoshaUser, goodsVo);

    }

    //获取秒杀订单，返回秒杀结果。0表示没有订单，其他表示订单id
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);

        if(miaoshaOrder != null){
            System.out.println("miaosha函数，提示：已经从缓存中查询到订单"+ miaoshaOrder);
            return miaoshaOrder.getOrderId();
        } else{ //秒杀失败， 失败原因：还没轮到消息队列出队
            System.out.println("miaosha函数，提示：还没有从缓存中查询到订单"+ miaoshaOrder);
            return 0;
        }

    }




}












