package com.ahr.film.servlet;

import com.ahr.film.entity.Film;
import com.ahr.film.entity.FilmScore;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetFavorFilmServlet", value = "/GetFavorFilmServlet")
public class GetFavorFilmServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCharSet(request, response);
        Integer serchUserId = null;
        if(StringUtils.isNotBlank(request.getParameter("serachUserId"))){
            serchUserId = Integer.parseInt(request.getParameter("serachUserId"));
        }
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        FilmScore filmScore = new FilmScore();
        filmScore.setUserId(serchUserId);
        List<FilmScore> filmScoreList = MySQLUtils.doQuery(conn, filmScore);
        List<Film> filmList = new ArrayList<>();
        JSONArray arr = new JSONArray();
        for(FilmScore f : filmScoreList){
            conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
            Film film = new Film();
            film.setFilmId(f.getFilmId());
            filmList.addAll(MySQLUtils.doQuery(conn, film));
        }

        for(Film f : filmList){
            arr.add(f.toString());
        }

        PrintWriter out = response.getWriter();
        out.println(arr.toString());
        return;
    }
}
