package com.imooc.miaosha.util;

import com.imooc.miaosha.domain.MiaoshaUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBUtil {

    //count：生成的用户数量
    private static void createUser(int count) throws Exception {

        //生成用户，用List存储
        List<MiaoshaUser> users = new ArrayList<MiaoshaUser>(count);
        for (int i = 0; i < count; i++) {
            MiaoshaUser user = new MiaoshaUser();
            user.setId(13000000000L + i);
            user.setLoginCount(1);
            user.setNickname("user" + i);
            user.setRegisterDate(new Date());
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSalt()));

            users.add(user);
        }
        System.out.println("create user");



    }




}
