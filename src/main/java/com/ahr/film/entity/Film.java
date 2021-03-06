package com.ahr.film.entity;

import com.ahr.film.annotation.SkipInit;
import com.ahr.film.annotation.SkipStorage;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class Film {

    private Integer filmId;
    private String filmName;
    private String filmType;
    private String filmFeature;
    private String filmPoster;
    private Date filmPublished;
    private String filmPath;
    @SkipInit
    @SkipStorage
    private Double w;

    @Override
    public String toString(){
        JSONObject obj = new JSONObject();
        obj.put("filmId", filmId);
        obj.put("filmType", filmType);
        obj.put("filmFeature", filmFeature);
        obj.put("filmPoster", filmPoster);
        obj.put("filmPublished", filmPublished.toString());
        obj.put("path", filmPath);
        obj.put("filmName", filmName);
        return obj.toString();
    }
}