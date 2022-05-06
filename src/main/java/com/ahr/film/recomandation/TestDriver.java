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
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

public class TestDriver {
    public static void main(String[] args) throws ParseException {
        OkHttpClient client = new OkHttpClient();
        RequestBody rb = new FormBody.Builder().add("userAccount", "duwei7088").
                add("userPassword", "tel2229@").add("userTelephone", "15106982229").
                add("userName", "王文硕").add("userGender", "1").build();
        Request r = new Request.Builder().post(rb).url("http://43.138.54.99:8080/WebTestProject1-1.0-SNAPSHOT/registerUserServlet").build();
        Call call = client.newCall(r);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = null;
        try {
            s = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(s);
        JSONObject obj = JSONObject.parseObject(s);
        String status = obj.get("status").toString();
        if ("success".equals(status)) {
            out.println("success");
        } else {
            out.println("failed");
        }
    }


}
