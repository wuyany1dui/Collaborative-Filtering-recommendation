package com.ahr.film.mysql;


import com.ahr.film.annotation.SkipInit;
import com.ahr.film.exception.NullFieldException;
import com.ahr.film.exception.NullPrimaryKeyException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUtils extends MySQLBaseUtils{
    private static SqlFactory sqlFactory = new SqlFactory();



    public static <T> int doInsert(Connection conn, T t) throws NullFieldException, SQLException {
        String insertsql = sqlFactory.getInsertSql(t);
        int count = insert(conn, insertsql);
        closeConnection(conn);
        return count;
    }

    public static <T> int doInsert(Connection conn, List<T> tList) throws NullFieldException, SQLException {
        String[] insertSqls = new String[tList.size()];
        for(int i = 0; i < tList.size(); i++){
            T t = tList.get(i);
            insertSqls[i] = sqlFactory.getInsertSql(t);
        }

        int count = 0;

        for(String insertSql : insertSqls) {
            count += insert(conn, insertSql);
            count++;
        }


        closeConnection(conn);

        return count;
    }

    public static <T> List<T> doQuery(Connection conn, T t)  {
        List<T> tList = new ArrayList<>();

        ResultSet rs = null;
        try {
            String sql = sqlFactory.getSelectSql(t);
            rs = select(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSetMetaData metaData = null;
        try {
            metaData = rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int columnCount = 0;
        try {
            columnCount = metaData.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Class tClass = t.getClass();

        while(true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            T t1 = null;
            try {
                t1 = (T) tClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < columnCount; i++){
                Object value = null;
                try {
                    value = rs.getObject(i + 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String valueName = null;
                try {
                    valueName = metaData.getColumnName(i + 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Field field = null;
                try {
                    field = tClass.getDeclaredField(valueName);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                field.setAccessible(true);
                try {
                    if(field.getType().toString().equals("class java.lang.Integer")){
                        field.set(t1, Integer.parseInt(value.toString()));
                    }else field.set(t1, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tList.add(t1);
        }

        closeConnection(conn);
        closeResultSet(rs);
        return tList;
    }

    public static <T> List<T> doQuery(Connection conn, Class clazz){
        List<T> tList = new ArrayList<>();

        try{
            ResultSet rs = select(conn, sqlFactory.getSelectSql(clazz));

            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();

            while(rs.next()){
                T t = (T) clazz.getDeclaredConstructor().newInstance();
                for(int i = 0; i < columnCount; i++){
                    Object value = rs.getObject(i + 1);
                    String valueName = metaData.getColumnName(i + 1);
                    Field field = clazz.getDeclaredField(valueName);
                    field.setAccessible(true);
                    field.set(t, value);
                }

                tList.add(t);
            }

            closeConnection(conn);
            closeResultSet(rs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tList;
    }

    public static <T> int doDelete(Connection conn, T t){
        String sql = null;
        try {
            sql = sqlFactory.getDeleteSql(t);
        } catch (NullFieldException e) {
            e.printStackTrace();
        }
        int i = 0;
        try {
            i = delete(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection(conn);
        return i;
    }

    public static <T> int doDelete(Connection conn, List<T> t) throws NullFieldException, SQLException {
        String[] sqls = new String[t.size()];

        for(int i = 0; i < t.size(); i++){
            T t1 = t.get(i);
            sqls[i] =sqlFactory.getDeleteSql(t1);
        }

        int count = 0;

        for(String s : sqls){
            count += delete(conn, s);
        }

        return count;
    }

    public static <T> int doUpdate(Connection conn, T t) throws NullPrimaryKeyException, NullFieldException, SQLException {
        String sql = sqlFactory.getUpdateSql(t);
        int i = update(conn, sql);

        closeConnection(conn);

        return i;
    }

    public static <T> int doUpdate(Connection conn, List<T> tList) throws NullPrimaryKeyException, NullFieldException, SQLException {
        String[] sqls = new String[tList.size()];
        for(int i = 0; i < tList.size(); i++) {
            T t = tList.get(i);
            sqls[i] = sqlFactory.getUpdateSql(t);
        }

        int count = 0;

        for(String s : sqls){
            count += update(conn, s);
        }

        closeConnection(conn);

        return count;
    }



}
