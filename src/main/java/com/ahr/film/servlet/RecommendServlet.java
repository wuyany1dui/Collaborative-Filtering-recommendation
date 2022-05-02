package com.ahr.film.servlet;

import com.ahr.film.entity.Film;
import com.ahr.film.entity.User;
import com.ahr.film.recomandation.Recommendation;
import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecommenServlet", value = "/RecommenServlet")
public class RecommendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setCharSet(request, response);
        User u = new User();
        u.setUserId(Integer.parseInt(request.getParameter("userId")));
        List<Film> filmList = new Recommendation().getRecommendFilmList(u);
        JSONArray arr = new JSONArray();
        for(Film f : filmList){
            arr.add(f.toString());
        }
        response.getWriter().println(arr);
    }
}
