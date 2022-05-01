package com.ahr.film.entity;

import com.ahr.film.annotation.PrimaryKey;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {
    @PrimaryKey
    private Integer userId;
    private String userName;
    private String userPassword;
    private Integer userGender;
    private String userTelephone;
    private Integer userLevel;
    private String userAccount;

    @Override
    public String toString(){
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("userName", userName);
        obj.put("userPassword", userPassword);
        obj.put("userGender", userGender);
        obj.put("userTelephone", userTelephone);
        obj.put("userLevel", userLevel);
        obj.put("userAccount", userAccount);
        return obj.toString();
    }

}
