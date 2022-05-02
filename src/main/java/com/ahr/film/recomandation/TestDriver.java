package com.ahr.film.recomandation;

import com.ahr.film.entity.Film;
import com.ahr.film.entity.FilmScore;
import com.ahr.film.entity.User;
import com.ahr.film.exception.NullFieldException;
import com.ahr.film.exception.NullPrimaryKeyException;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static java.lang.System.out;

public class TestDriver {
    public static void main(String[] args) {
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        List<Film> filmList = MySQLUtils.doQuery(conn, Film.class);
    }


}
