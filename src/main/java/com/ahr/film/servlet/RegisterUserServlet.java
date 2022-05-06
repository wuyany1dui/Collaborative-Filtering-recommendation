package com.ahr.film.servlet;

import com.ahr.film.entity.User;
import com.ahr.film.exception.NullFieldException;
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

@WebServlet(name = "RegisterUserServlet", value = "/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setCharSet(request, response);
        User u = new User();

        u.setUserAccount(request.getParameter("userAccount"));
        u.setUserPassword(request.getParameter("userPassword"));
        u.setUserName(request.getParameter("userName"));
        u.setUserTelephone(request.getParameter("userTelephone"));
        u.setUserGender(Integer.parseInt(request.getParameter("userGender")));

        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        PrintWriter out = response.getWriter();
        try {
            if(MySQLUtils.doInsert(conn, u) > 0){
                JSONObject obj = new JSONObject();
                obj.put("status", "success");
                out.println(obj.toString());
                out.flush();
            }else{
                JSONObject obj = new JSONObject();
                obj.put("status", "failed");
                out.println(obj.toString());
                out.flush();
            }
        } catch (NullFieldException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
