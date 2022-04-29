package com.ahr.film.servlet;

import com.ahr.film.entity.User;
import com.ahr.film.mysql.MySQLUtils;
import com.ahr.film.mysql.StringValues;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setCharSet(req, resp);
        PrintWriter out = resp.getWriter();
        Integer userId = Integer.parseInt(req.getParameter("userId"));
        String userPassword = req.getParameter("userPassword");
        JSONObject obj = new JSONObject();
        User user = new User();
        user.setUserId(userId);
        Connection conn = MySQLUtils.getConnection(StringValues.MYSQL_URL, StringValues.PASSWORD, StringValues.USERNAME, StringValues.DRIVER_NAME);
        List<User> loginUser = MySQLUtils.doQuery(conn, user);
        if(loginUser.size() != 1){
            obj.put("status", 201);

            out.println(obj.toString());
            return;
        }
        User u = loginUser.get(0);
        if(StringUtils.isNotBlank(userPassword) && userPassword.equals(u.getUserPassword())){
            obj.put("status", 200);
            obj.put("longinUser", u.toString());
        }else{
            obj.put("status", 201);
        }

        out.println(obj.toString());


        return;
    }
}
