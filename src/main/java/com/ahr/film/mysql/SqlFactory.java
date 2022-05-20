package com.ahr.film.mysql;



import com.ahr.film.annotation.PrimaryKey;
import com.ahr.film.annotation.SkipInit;
import com.ahr.film.annotation.SkipStorage;
import com.ahr.film.exception.NullFieldException;
import com.ahr.film.exception.NullPrimaryKeyException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class SqlFactory {


    /**
     * @param t pass a variable which is about to provide conditions of the selection sql
     * @return selection sql
     * @author wws
     * @createtime 2022-04-06 00:30
     * */
    public <T> String getSelectSql(T t) throws NullPointerException{
        String sql = "select * from ";

        if(t==null){
            throw new NullPointerException();
        }

        Class tClass = t.getClass();
        sql = sql + this.getTableName(tClass) + ' ';

        List<Field> fields = this.getFileds(t);


        try{
            if(fields.size() >= 0){
                sql = sql + "where 1 = 1";
                for(int j = 0; j < fields.size(); j++){
                    Field f = fields.get(j);
                    String name = f.getName();
                    SkipInit si = f.getAnnotation(SkipInit.class);
                    if(si != null){
                        continue;
                    }
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    String type = f.getGenericType().toString();
                    Method m = tClass.getMethod("get" + name);

                    if("class java.lang.String".equals(type)){
                        String value = (String) m.invoke(t);
                        if(null != value && !"".equals(value)){
                            sql=sql+" and "+f.getName()+"='"+value+"'";
                        }
                    }else if("class java.util.Date".equals(type)){
                        Date value = (Date) m.invoke(t);
                        if(null != value && !"".equals(value)){
                            sql=sql+" and "+f.getName()+"='"+value+"'";
                        }

                    }else if("class java.lang.Integer".equals(type) || "int".equals(type)){
                        Integer value = (Integer) m.invoke(t);
                        if(null != value && !"".equals(value)){
                            sql=sql+" and "+f.getName()+"="+value+"";
                        }
                    }else if("class java.lang.Boolean".equals(type) || "boolean".equals(type)){
                        Boolean value = (Boolean) m.invoke(t);
                        if(null != value && !"".equals(value)){
                            sql=sql+" and "+f.getName()+"="+value+"";
                        }
                    }else if("class java.lang.Double".equals(type) || "double".equals(type)){
                        Double value = (Double) m.invoke(t);
                        if(null != value && !"".equals(value)){
                            sql=sql+" and "+f.getName()+"="+value+"";
                        }
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return sql;
    }

    /**
     * @param clazz pass a variable which is about to provide which table to select
     * @return selection sql
     * @author wws
     * @createtime 2022-04-06 00:30
     * */
    public String getSelectSql(Class clazz) throws NullPointerException{
        String sql = "select * from ";
        if(clazz == null){
            throw new NullPointerException();
        }
        sql = sql + this.getTableName(clazz) + ';';
        return sql;

    }

    /**
     * @param t pass a variable which is about to provide conditions of update sql
     * @return update sql
     * @author wws
     * @createtime 2022-04-06 00:30
     * */
    public <T> String getUpdateSql(@NotNull T t) throws NullPointerException, NullFieldException, NullPrimaryKeyException {
        String sql = "update ";
        Class tClass = t.getClass();

        if(t == null){
            throw new NullPointerException();
        }
        sql = sql + this.getTableName(tClass);
        String parameter = " set ";
        String terms = " where 1=1";
        try{

            List<Field> fields = this.getFileds(t);


            for(int j = 0; j < fields.size(); j++){
                Field f = fields.get(j);
                String name = f.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = f.getGenericType().toString();
                PrimaryKey pk = f.getAnnotation(PrimaryKey.class);
                if(pk != null){

                    if("class java.lang.String".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        String value = (String) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            terms = terms + " and " + f.getName() + "='" + value+"'";
                        }
                    }else if("class java.lang.Integer".equals(type) || "int".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        Integer value = (Integer) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            terms = terms + " and " + f.getName() + "='" + value+"'";
                        }
                    }else if("class java.lang.Date".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        Date value = (Date) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            terms = terms + " and " + f.getName() + "='" + value+"'";
                        }
                    }else if("class java.lang.Double".equals(type) || "double".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        Double value = (Double) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            terms = terms + " and " + f.getName() + "='" + value+"'";
                        }
                    }else if("class java.lang.Boolean".equals(type) || "boolean".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        Boolean value = (Boolean) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            terms = terms + " and " + f.getName() + "='" + value+"'";
                        }
                    }
                }else{
                    if("class java.lang.String".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        String value = (String) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            if(" set ".equals(parameter)){
                                parameter = parameter + f.getName() + "='" + value + "'";
                            }else {
                                parameter = parameter + "," + f.getName() + "='" + value+"'";
                            }
                        }
                    }else if("class java.lang.Integer".equals(type) || "int".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        Integer value = (Integer) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            if(" set ".equals(parameter)){
                                parameter = parameter + f.getName() + "='" + value + "'";
                            }else {
                                parameter = parameter + "," + f.getName() + "='" + value+"'";
                            }
                        }
                    }else if("class java.lang.Date".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        Date value = (Date) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            if(" set ".equals(parameter)){
                                parameter = parameter + f.getName() + "='" + value + "'";
                            }else {
                                parameter = parameter + "," + f.getName() + "='" + value+"'";
                            }
                        }
                    }else if("class java.lang.Double".equals(type) || "double".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        Double value = (Double) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            if(" set ".equals(parameter)){
                                parameter = parameter + f.getName() + "='" + value + "'";
                            }else {
                                parameter = parameter + "," + f.getName() + "='" + value+"'";
                            }
                        }
                    }else if("class java.lang.Boolean".equals(type) || "boolean".equals(type)){
                        Method m = tClass.getMethod("get" + name);
                        Boolean value = (Boolean) m.invoke(t);
                        if(value != null && !"".equals(value)){
                            if(" set ".equals(parameter)){
                                parameter = parameter + f.getName() + "='" + value + "'";
                            }else {
                                parameter = parameter + "," + f.getName() + "='" + value+"'";
                            }
                        }
                    }
                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(" set ".equals(parameter)){
            throw new NullFieldException("Illegal parameters");
        }else if(" where 1=1".equals(terms)){
            throw new NullPrimaryKeyException();
        }else{
            sql = sql + parameter + terms;
        }

        return sql;
    }

    /**
     * @param t pass a variable which is about to provide conditions of insert sql
     * @return update sql
     * @author wws
     * @createtime 2022-04-06 20:30
     * */
    public <T> String getInsertSql(T t) throws NullFieldException {
        String sql = "insert into ";
        Class tClass = t.getClass();
        String argument = "(";
        String values = "(";
        List<Field> fields = this.getFileds(t);
        if(t == null)
            throw new NullPointerException("Null Object");


        try{
            String tableName = this.getTableName(tClass);
            sql = sql + tableName;

            for(int j = 0; j < fields.size(); j++){
                Field f = fields.get(j);
                String name = f.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = f.getGenericType().toString();

                SkipStorage ss = f.getAnnotation(SkipStorage.class);
                if(ss != null)
                    continue;

                if("class java.lang.String".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    String value = (String) m.invoke(t);
                    if(value != null && !"".equals(value)){
                        if("(".equals(argument)){
                            argument = argument + f.getName().toUpperCase();
                        }else{
                            argument = argument + "," + f.getName().toUpperCase();
                        }

                        if("(".equals(values)){
                            values = values + "'" + value + "'";
                        }else{
                            values = values + ",'" +value + "'";
                        }
                    }
                }else if("class java.lang.Integer".equals(type) || "int".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    Integer value = (Integer) m.invoke(t);
                    if(value != null && !"".equals(value)){
                        if("(".equals(argument)){
                            argument = argument + f.getName().toUpperCase();
                        }else{
                            argument = argument + "," + f.getName().toUpperCase();
                        }
                        if("(".equals(values)){
                            values = values + "" + value + "";
                        }else{
                            values = values + "," +value + "";
                        }
                    }
                }else if("class java.lang.Boolean".equals(type) || "boolean".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(t);
                    if(value != null && !"".equals(value)){
                        if("(".equals(argument)){
                            argument = argument + f.getName().toUpperCase();
                        }else{
                            argument = argument + "," + f.getName().toUpperCase();
                        }
                        if("(".equals(values)){
                            values = values + "" + value + "";
                        }else{
                            values = values + "," +value + "";
                        }
                    }
                }else if("class java.lang.Date".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    Date value = (Date) m.invoke(t);
                    if(value != null && !"".equals(value)){
                        if("(".equals(argument)){
                            argument = argument + f.getName().toUpperCase();
                        }else{
                            argument = argument + "," + f.getName().toUpperCase();
                        }
                        if("(".equals(values)){
                            values = values + "'" + value + "'";
                        }else{
                            values = values + ",'" +value + "'";
                        }
                    }
                }else if("class java.lang.Double".equals(type) || "double".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    Double value = (Double) m.invoke(t);
                    if(value != null && !"".equals(value)){
                        if("(".equals(argument)){
                            argument = argument + f.getName().toUpperCase();
                        }else{
                            argument = argument + "," + f.getName().toUpperCase();
                        }
                        if("(".equals(values)){
                            values = values + "" + value + "";
                        }else{
                            values = values + "," +value + "";
                        }
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if ("(".equals(argument))
            throw new NullFieldException("There aren't any argument in this object");
        else if("(".equals(values))
            throw new NullFieldException("There aren't any values in this object");
        else sql = sql + argument + ") values " + values + ")";
        return sql;
    }

    public <T> String getDeleteSql(T t) throws NullFieldException {
        String sql = "delete from ";
        Class tClass = t.getClass();
        String terms = " where 1=1";

        List<Field> fields = this.getFileds(t);
        if(t == null)
            throw new NullPointerException("Null Object");

        try{
            String tableName = this.getTableName(tClass);
            sql = sql + tableName;

            for(Field f : fields){
                String name = f.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = f.getGenericType().toString();

                if("class java.lang.String".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    String value = (String) m.invoke(t);
                    if(null != value && !"".equals(value)){
                        terms = terms + " and " + f.getName().toLowerCase() + "='" + value + "'";
                    }
                }else if("class java.lang.Integer".equals(type) || "int".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    Integer value = (Integer) m.invoke(t);
                    if(null != value && !"".equals(value)){
                        terms = terms + " and " + f.getName().toLowerCase() + "=" + value + "";
                    }
                }else if("class java.lang.Boolean".equals(type) || "boolean".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(t);
                    if(null != value && !"".equals(value)){
                        terms = terms + " and " + f.getName().toLowerCase() + "=" + value + "";
                    }
                }else if("class java.lang.Double".equals(type) || "double".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    Double value = (Double) m.invoke(t);
                    if(null != value && !"".equals(value)){
                        terms = terms + " and " + f.getName().toLowerCase() + "=" + value + "";
                    }
                }else if("class java.lang.Date".equals(type)){
                    Method m = tClass.getMethod("get" + name);
                    Date value = (Date) m.invoke(t);
                    if(null != value && !"".equals(value)){
                        terms = terms + " and " + f.getName().toLowerCase() + "='" + value + "'";
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        if(" where 1=1 ".equals(terms))
            throw new NullFieldException("");
        else sql = sql + terms;
        return sql;

    }


    protected String getTableName(@NotNull Class clazz){
        String className = clazz.getName();
        return className.substring(className.lastIndexOf(".") + 1).toLowerCase();
    }

    public <T> List<Field> getFileds(@NotNull T t){
        Class tempClass = t.getClass();
        List<Field> fields = new ArrayList<>();
        while(tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object")){
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        return fields;

    }


    public SqlFactory(){

    }


}
