package com.imooc.miaosha.rabbitmq;


import com.imooc.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//队列消息发送者
@Service
public class MQSender {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;//操作队列的帮助类

    //用direct模式发送秒杀消息给队列
    public void sendMiaoshaMessage(MiaoshaMessage miaoshaMessage) {
        String msg = RedisService.beanToString(miaoshaMessage);
        logger.info("send direct message:" + msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);//给队列发送一个消息
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    //Direct模式，发送消息
//    public void send(Object message){
//        String msg = RedisService.beanToString(message);
//        logger.info("send message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);//给队列发送一个消息
//    }
//
//    //Topic模式，发送消息。（消息、交换机、RoutingKey），不需要知道是要传给哪个队列，因为已经在MQConfig类中通过RoutingKey绑定了交换机和队列
//    public void sendTopic(Object message){
//        String msg = RedisService.beanToString(message);
//        logger.info("send topic message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", message + "消息1");//消息：--->交换机--->RoutingKey1--->队列1和队列2
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", message + "消息2");//消息：--->交换机--->RoutingKey3--->队列2
//    }
//
//    //Fanout模式，发送消息。（消息、交换机）
//    public void sendFanout(Object message){
//        String msg = RedisService.beanToString(message);
//        logger.info("send fanout message:" + msg);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", message + "消息1");//消息：--->交换机--->队列1和队列2
//    }
//
//
//    //Headers模式，发送消息。（消息、交换机、队列、）
//    public void sendHeaders(Object message){
//        String msg = RedisService.beanToString(message);
//        logger.info("send headers message:" + msg);
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("header1", "value1");
//        properties.setHeader("header2", "value2");
//        Message obj = new Message(msg.getBytes(),properties);
//
//        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);//消息：--->交换机--->headers的key——value
//    }


}














