package com.ahr.film.recomandation;

import com.ahr.film.entity.Film;
import com.ahr.film.entity.FilmScore;
import com.ahr.film.entity.User;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;

import static java.lang.System.out;

public class TestDriver {
    public static void main(String[] args) {
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        FilmScore filmScore = new FilmScore();
        filmScore.setUserId(2);
        List<FilmScore> filmScoreList = MySQLUtils.doQuery(conn, filmScore);
        conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);

        List<Film> filmList = MySQLUtils.doQuery(conn, Film.class);
        Set<Film> filmSet = new HashSet<>();
        JSONArray arr = new JSONArray();
        for(FilmScore f : filmScoreList){
            Integer filmId = f.getFilmId();
            for(Film f1 : filmList){
                out.println(f1.getFilmId() + " " + f.getFilmId());
                if(f1.getFilmId().equals(filmId)){
                    filmSet.add(f1);
                }
            }
        }

        out.println(filmSet.size());

        for(Film f : filmSet){
            arr.add(f.toString());
        }

        out.println(arr);

    }
}
