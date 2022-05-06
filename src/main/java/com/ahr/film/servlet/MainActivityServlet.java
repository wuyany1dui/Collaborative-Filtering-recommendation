package com.ahr.film.servlet;

import com.ahr.film.entity.Film;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "MainActivityServlet", value = "/MainActivityServlet")
public class MainActivityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCharSet(request, response);
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        List<Film> filmList = MySQLUtils.doQuery(conn, Film.class);
        JSONArray arr = new JSONArray();
        if(Objects.nonNull(filmList)){
            for(Film f : filmList){
                arr.add(JSONObject.parseObject(f.toString()));
            }
        }
        PrintWriter out = response.getWriter();

        out.println(arr.toString());
        out.flush();
        out.close();
        return;
    }
}
