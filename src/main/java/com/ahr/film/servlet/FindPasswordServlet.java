package com.ahr.film.servlet;

import com.ahr.film.entity.Film;
import com.ahr.film.entity.FilmScore;
import com.ahr.film.entity.User;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class FindPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCharSet(request, response);
        String account = request.getParameter("account");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        User u = new User();
        u.setUserAccount(account);
        PrintWriter out = response.getWriter();
        List<User> userList = MySQLUtils.doQuery(conn, u);
        if(userList.size() == 1){
            User targetUser = userList.get(0);
            if(targetUser.getUserName().equals(name) && targetUser.getUserTelephone().equals(phone)){
                out.println(targetUser.getUserPassword());
                out.flush();
            }
        }else {
            out.println("can not find user");
        }

        return;
    }
}
