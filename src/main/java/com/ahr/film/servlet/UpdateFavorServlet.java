package com.ahr.film.servlet;

import com.ahr.film.entity.FilmScore;
import com.ahr.film.exception.NullFieldException;
import com.ahr.film.exception.NullPrimaryKeyException;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
        String action = request.getParameter("action");
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        if(action.equals("insert")){
            filmScore.setUserId(Integer.parseInt(request.getParameter("userId")));
            filmScore.setFilmId(Integer.parseInt(request.getParameter("filmId")));
            filmScore.setFilmScore(Integer.parseInt(request.getParameter("filmScore")));
            try {
                MySQLUtils.doInsert(conn, filmScore);
            } catch (NullFieldException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(action.equals("update")){
            filmScore.setFilmScoreId(Integer.parseInt(request.getParameter("filmScoreId")));
            filmScore.setUserId(Integer.parseInt(request.getParameter("userId")));
            filmScore.setFilmId(Integer.parseInt(request.getParameter("filmId")));
            filmScore.setFilmScore(Integer.parseInt(request.getParameter("filmScore")));
            try {
                MySQLUtils.doUpdate(conn, filmScore);
            } catch (NullPrimaryKeyException e) {
                e.printStackTrace();
            } catch (NullFieldException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
