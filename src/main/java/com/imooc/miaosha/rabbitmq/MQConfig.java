package com.imooc.miaosha.rabbitmq;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


//队列消息配置类
@Configuration
public class MQConfig {

    //队列名
    public static final String MIAOSHA_QUEUE = "miaosha.queue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String HEADERS_QUEUE1 = "headers.queue1";

    //交换机名
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String HEADERS_EXCHANGE = "headersExchange";

    //路由名RoutingKey
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.#";//*代表一个单词，#代表0个或者多个单词。所以key2包含了所有topic.开头的单词

     /**
     *  Direct模式，
     * */
    @Bean
    public Queue miaoshaQueue(){
        //生成一个名称为"miaosha.queue"的队列
        return new Queue(MIAOSHA_QUEUE,true);
    }

//    @Bean
//    public Queue queue(){
//        //生成一个名称为"queue"的队列
//        return new Queue(QUEUE,true);
//    }
//
//
//    /**
//     *  Topic模式。
//     *  生成两个队列，一个名称为"topic.queue1"，一个名称为"topic.queue2"
//     *  生成一个交换机TopicExchange
//     * */
//    @Bean
//    public Queue topicQueue1(){ //队列1，名字为TOPIC_QUEUE1
//        return new Queue(TOPIC_QUEUE1,true);
//    }
//    @Bean
//    public Queue topicQueue2(){ //队列2，名字为TOPIC_QUEUE2
//        return new Queue(TOPIC_QUEUE2,true);
//    }
//    @Bean
//    public TopicExchange topicExchange(){ //交换机，名字为TOPIC_EXCHANGE
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//    @Bean
//    public Binding topicBinding1(){ //绑定1：队列1--交换机（通过ROUTING_KEY1字符串来绑定，路由匹配时找到交换机--队列）
//        System.out.println("队列1绑定KEY1");
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
//    }
//    @Bean
//    public Binding topicBinding2(){ //绑定2：队列2--交换机（通过ROUTING_KEY2="topic.#"字符串来绑定，路由匹配时 交换机--队列）
//        System.out.println("队列2绑定KEY2");
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");//任何topic.形式的key都能发给队列2
//    }
//
//
//    /**
//     *  Fanout模式。
//     *  生成一个交换机FanoutExchange，又名广播交换机。
//     *  广播交换机，不需要用key，会发送给所有与交换机绑定的队列s
//     * */
//    @Bean
//    public FanoutExchange fanoutExchange(){ //fanout交换机，名字为FANOUT_EXCHANGE
//        return new FanoutExchange(FANOUT_EXCHANGE);
//    }
//    @Bean
//    public Binding fanoutBinding1(){ //fanout绑定1：队列 --交换机
//        System.out.println("队列1绑定Fanout交换机");
//        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
//    }
//    @Bean
//    public Binding fanoutBinding2(){ //fanout绑定2：队列 --交换机
//        System.out.println("队列1绑定Fanout交换机");
//        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
//    }
//
//
//
//    /**
//     *  Headers模式。
//     *  只有满足key--value时，才能放消息
//     * */
//    @Bean
//    public HeadersExchange headersExchange(){ //fanout交换机，名字为FANOUT_EXCHANGE
//        return new HeadersExchange(HEADERS_EXCHANGE);
//    }
//    @Bean
//    public Queue headersQueue1(){ //队列1，名字为HEADERS_QUEUE1
//        return new Queue(HEADERS_QUEUE1,true);
//    }
//    @Bean
//    public Binding headersBinding1(){ //fanout绑定1：队列 --交换机
//        System.out.println("队列1绑定Headers交换机");
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("header1", "value1");
//        map.put("header2", "value2");
//        return BindingBuilder.bind(headersQueue1()).to(headersExchange()).whereAll(map).match();
//
//    }



}
