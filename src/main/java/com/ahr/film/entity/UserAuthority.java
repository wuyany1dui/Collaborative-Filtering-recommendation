package com.ahr.film.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserAuthority {
    private Integer authorityId;
    private Integer authorityLevel;
    private String RMKS;

    @Override
    public String toString(){
        JSONObject obj = new JSONObject();
        obj.put("authorityId", authorityId);
        obj.put("authortiyLevel", authorityLevel);
        obj.put("RMKS", RMKS);
        return obj.toString();
    }

}