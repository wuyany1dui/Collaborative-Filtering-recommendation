package com.ahr.film.servlet;

import com.ahr.film.entity.User;
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


public class BanUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCharSet(request, response);
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        String action = request.getParameter("action");
        Integer userLevel = null;
        if(action.equals("ban")){
           userLevel = Integer.valueOf(-1);
        }else{
            userLevel = Integer.valueOf(1);
        }

        User u = new User();
        u.setUserId(userId);
        u.setUserLevel(userLevel);

        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);

        int updateCount = 0;
        try {
            updateCount = MySQLUtils.doUpdate(conn, u);
        } catch (NullPrimaryKeyException e) {
            e.printStackTrace();
        } catch (NullFieldException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject();
        obj.put("count", updateCount);
        PrintWriter out = response.getWriter();
        out.println(obj.toString());
    }
}
