package com.ahr.film.servlet;

import com.ahr.film.entity.FilmScore;
import com.ahr.film.exception.NullFieldException;
import com.ahr.film.exception.NullPrimaryKeyException;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.lang.System.out;

@WebServlet(name = "UpdateFavorServlet", value = "/UpdateFavorServlet")
public class UpdateFavorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCharSet(request, response);
        FilmScore filmScore = new FilmScore();
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);

            filmScore.setUserId(Integer.parseInt(request.getParameter("userId")));
            filmScore.setFilmId(Integer.parseInt(request.getParameter("filmId")));


            List<FilmScore> filmScoreList = MySQLUtils.doQuery(conn, filmScore);
            int updateCount = 0;

            if(filmScoreList.size() > 0){
                FilmScore fs1 = filmScoreList.get(0);
                fs1.setUserId(Integer.parseInt(request.getParameter("userId")));
                fs1.setFilmId(Integer.parseInt(request.getParameter("filmId")));
                fs1.setFilmScore(Integer.parseInt(request.getParameter("filmScore")));
                conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
                try {
                    updateCount = MySQLUtils.doUpdate(conn, fs1);
                } catch (NullPrimaryKeyException e) {
                    e.printStackTrace();
                } catch (NullFieldException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                FilmScore fs1 = new FilmScore();
                fs1.setUserId(Integer.parseInt(request.getParameter("userId")));
                fs1.setFilmId(Integer.parseInt(request.getParameter("filmId")));
                fs1.setFilmScore(Integer.parseInt(request.getParameter("filmScore")));
                conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
                try {
                    updateCount = MySQLUtils.doInsert(conn, fs1);
                } catch (NullFieldException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            PrintWriter out = response.getWriter();
            JSONObject obj = new JSONObject();
            obj.put("count", updateCount);
            out.println(obj.toString());
            out.flush();
            out.close();
            return;
    }
}
