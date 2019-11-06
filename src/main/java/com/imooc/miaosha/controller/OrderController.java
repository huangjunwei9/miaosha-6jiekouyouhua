package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import com.imooc.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;


    //用户某订单id的订单详情
    //    @NeedLogin //@NeedLogin：调用自定义的拦截器，校验session的用户
    @RequestMapping(value="/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser miaoshaUser, @RequestParam("orderId") long orderId){
        System.out.println("准备获取用户某订单id的订单详情");

        //校验用户是否为空
        if(miaoshaUser == null){
            System.out.println("用户sessIon丢失");
            Result<OrderDetailVo> resultNull =  new Result(CodeMsg.SESSION_ERROR);
            resultNull.setState(1); //状态：出错
            return resultNull;
        }

        //根据订单id获取订单详情，以及订单对应的商品详情
        OrderInfo orderInfo = orderService.getOrderById(orderId);//根据订单id 获取订单
        if(orderInfo == null){
            System.out.println("没有此订单");
            Result<OrderDetailVo> resultNull =  new Result(CodeMsg.ORDER_NOT_EXIST);
            resultNull.setState(1); //状态：出错
            return resultNull;
        }
        long goodsId = orderInfo.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(orderInfo);
        vo.setGoods(goods);
        vo.setMiaoshaUser(miaoshaUser);
        System.out.println("goodsId = " + goodsId);
        return new Result(vo);
    }

}


















