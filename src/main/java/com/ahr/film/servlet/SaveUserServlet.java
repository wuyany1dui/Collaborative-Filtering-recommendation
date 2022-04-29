package com.ahr.film.servlet;

import com.ahr.film.entity.User;
import com.ahr.film.exception.NullFieldException;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class SaveUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setCharSet(req, resp);
        User u = new User();

        u.setUserAccount(req.getParameter("userAccount"));
        u.setUserPassword(req.getParameter("userPassword"));
        u.setUserName(req.getParameter("userName"));
        u.setUserTelephone(req.getParameter("userTelephone"));
        u.setUserGender(Integer.parseInt(req.getParameter("userGender")));

        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        PrintWriter out = resp.getWriter();
        try {
            if(MySQLUtils.doInsert(conn, u) > 0){
                out.println(new JSONObject().put("status", "success"));
            }else{
                out.println(new JSONObject().put("status", "failed"));
            }
        } catch (NullFieldException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
