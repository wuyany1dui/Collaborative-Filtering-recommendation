package com.ahr.film.entity;

import com.alibaba.fastjson.JSONObject;
import com.sun.jdi.IntegerType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class FilmScore{
    private Integer filmScoreId;
    private Integer filmId;
    private Integer userId;
    private Integer filmScore;

    @Override
    public String toString(){
        JSONObject obj = new JSONObject();
        obj.put("filmScoreId", filmScoreId);
        obj.put("filmId", filmId);
        obj.put("userId", userId);
        obj.put("filmScore", filmScore);
        return obj.toString();
    }
}
