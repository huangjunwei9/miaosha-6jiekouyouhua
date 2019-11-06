package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.MiaoshaUser;

//返回商品详情对象
public class GoodsDetailVo {
    private int miaoshaStatus = 0;//秒杀状态，0未开始，1进行中，2已结束
    private int remainSeconds = 0;//还剩 remainSeconds秒 开始秒杀
    private GoodsVo goodsVo;
    private MiaoshaUser miaoshaUser;

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public MiaoshaUser getMiaoshaUser() {
        return miaoshaUser;
    }

    public void setMiaoshaUser(MiaoshaUser miaoshaUser) {
        this.miaoshaUser = miaoshaUser;
    }
}
