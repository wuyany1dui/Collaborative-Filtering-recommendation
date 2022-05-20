package com.ahr.film.recomandation;

import com.ahr.film.entity.Film;
import com.ahr.film.entity.FilmScore;
import com.ahr.film.entity.User;
import com.ahr.film.exception.NullFieldException;
import com.ahr.film.exception.NullPrimaryKeyException;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.ahr.film.servlet.ServletUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;
import okhttp3.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

public class TestDriver {
    public static void main(String[] args) throws ParseException, IllegalAccessException {
        Film f = new Film();
        Class clazz = f.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field f1 : fields){
            if(f1.getName().equals("filmId")){
                f1.setAccessible(true);
                f1.set(f, 1);
                out.println(f1.get(f));
            }
        }
    }


}
