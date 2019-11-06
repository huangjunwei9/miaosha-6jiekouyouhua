package com.imooc.miaosha.rabbitmq;



import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//队列消息接收者
@Service
public class MQReceiver {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;
    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    // Direct模式接收队列消息后：请求出队，生成订单，减少库存
    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE) //监听某个Queue
    public void receiveMiaosha(String message){
        logger.info("receiver message:" + message);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);

        MiaoshaUser miaoshaUser = miaoshaMessage.getMiaoshaUser();
        long goodsId = miaoshaMessage.getGoodsId();

        /*
         * 第一步.判断数据库中的库存：按照商品id获取商品的库存，判断库存是否符合用户需求
         */
        System.out.println("goodsId = "+goodsId);
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stockCount = goodsVo.getStockCount();
        if(stockCount <= 0){
            return ;
        }

         /* 第二步.是否重复秒杀
         * 判断是否已经秒杀到了，用于防止一个人秒杀多个商品，即：一个用户对于一种商品只能秒杀一次
         * 1.根据用户ID和商品Id，获取秒杀订单
         * 2.如果秒杀订单不是空，说明用户已经秒杀过了，返回无
         * 3.如果商品有库存，而且用户并没有秒杀过，则可执行商品秒杀
         * */
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(), goodsId);
        if(miaoshaOrder != null){ //如果秒杀订单不是空，说明用户已经秒杀过了，返回错误信息“重复秒杀”
            System.out.println("receiveMiaosha函数，提示重复秒杀");
            return ;
        }

        //生成秒杀订单
        miaoshaService.miaosha(miaoshaUser, goodsVo);
    }





///////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    /*
//     *  Direct模式，一个队列名，一个消息
//     * */
//    @RabbitListener(queues = MQConfig.QUEUE) //监听某个Queue
//    public void receive(String message){
//        logger.info("receiver message:" + message);
//    }
//
//    /*
//     *  Topic模式，监听Queue1
//     * */
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1) //监听Queue1队列，队列名为MQConfig.TOPIC_QUEUE1
//    public void receiveTopic1(String message){
//        logger.info("receiver topic queue1 message:" + message);
//    }
//
//    /*
//     *  Topic模式，监听Queue2
//     * */
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2) //监听Queue2队列，队列名为MQConfig.TOPIC_QUEUE2
//    public void receiveTopic2(String message){
//        logger.info("receiver topic queue2 message:" + message);
//    }
//
//
//    /*
//     *  Headers模式，监听Queue2
//     * */
//    @RabbitListener(queues = MQConfig.HEADERS_QUEUE1) //监听Queue2队列，队列名为MQConfig.TOPIC_QUEUE2
//    public void receiveHeaders1(byte[] message){
//        logger.info("receiver Headers queue message:" + new String(message));
//    }

}












