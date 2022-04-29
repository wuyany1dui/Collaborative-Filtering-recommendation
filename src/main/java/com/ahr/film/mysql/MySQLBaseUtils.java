package com.ahr.film.mysql;

import java.sql.*;

public abstract class MySQLBaseUtils {

    public static Connection getConnection(String url, String password, String userName, String driverName){
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    protected static int insert(Connection conn, String sql) throws SQLException {
        PreparedStatement st = null;
        int count = 0;
        st = conn.prepareStatement(sql);
        count = st.executeUpdate();

        closeStatment(st);
        return count;
    }

    protected static int delete(Connection conn, String sql) throws SQLException {
        PreparedStatement statement = null;

        statement = conn.prepareStatement(sql);

        int i = statement.executeUpdate();

        closeStatment(statement);

        return i;
    }

    protected static int update(Connection conn, String sql) throws SQLException {
        PreparedStatement statement = null;

        statement = conn.prepareStatement(sql);
        int i = statement.executeUpdate();
        closeStatment(statement);
        return i;
    }

    protected static ResultSet select(Connection conn, String sql) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        return rs;
    }


    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatment(Statement st) {
        try {
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
