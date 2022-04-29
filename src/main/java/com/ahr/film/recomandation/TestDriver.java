package com.ahr.film.recomandation;

import com.ahr.film.entity.Film;
import com.ahr.film.entity.User;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;

public class TestDriver {
    public static void main(String[] args) {
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        List<Film> filmList = MySQLUtils.doQuery(conn, Film.class);
        JSONArray arr = new JSONArray();
        if(Objects.nonNull(filmList)){
            for(Film f : filmList){
                arr.add(JSONObject.parseObject(f.toString()));
            }
        }

        out.println(arr.toString());
    }
}
