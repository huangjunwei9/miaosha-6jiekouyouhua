package com.imooc.miaosha.redis;

public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        //继承父类的构造函数，传递参数（过期时间=expireSeconds，前缀=prefix）
        super(expireSeconds, prefix);
    }

    private GoodsKey(String prefix) {
        //继承父类的构造函数，传递参数（过期时间=0，前缀=prefix）
        super(prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");  //缓存60秒过期
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");  //缓存60秒过期
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");  //秒杀商品的数量
}
