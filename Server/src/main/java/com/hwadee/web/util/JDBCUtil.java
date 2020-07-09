package com.hwadee.web.util;

import com.hwadee.web.conf.Config;
import com.hwadee.web.entity.Word;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * JDBC - MySQL 操作工具类
 * <p>
 * 1、注册驱动
 * 2、创建连接
 * 3、预定义处理
 * 4、填充占位元素
 * 5、执行处理
 * 6、后续数据处理
 * 7、关闭连接
 */
public class JDBCUtil {

    private Connection connection;
    private PreparedStatement statement;

    public JDBCUtil() throws ClassNotFoundException {
        //注册驱动
        Class.forName("com.mysql.jdbc.Driver");
    }

    private Connection getConnection() throws SQLException {
        //创建连接
        return DriverManager.getConnection(Config.MYSQL_URL, Config.MYSQL_USER, Config.MYSQL_PASS);
    }

    private void close() throws SQLException {
        if (statement != null && !statement.isClosed()) {
            statement.close();
        }

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * 增删改 操作
     *
     * @param sql 预处理SQL语句
     */
    public void execute(String sql, Object... objects) {
        try {
            connection = getConnection();

            //预定义操作
            //"select * from tmovies where id > ?"
            statement = connection.prepareStatement(sql);


            if (objects != null && objects.length > 0) {
                for (int i = 0; i < objects.length; i++) {
                    statement.setObject(i + 1, objects[i]);
                }
            }

            statement.execute();
            //int line = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询方式：
     * 1、查询单条数据
     * 2、查询多条数据
     * 3、利用 SQL 函数，查询对应结果
     * <p>
     * 可使用反射优化，自动映射入实体，返回
     *
     * @param sql
     * @param objects
     */


    /**
     * 通过反射注入的方式，查询单条数据
     */
    public <T> T selectOne(Class<T> clazz, String sql, Object... objects) {
        try {
            connection = getConnection();

            //预定义操作
            statement = connection.prepareStatement(sql);


            if (objects != null && objects.length > 0) {
                for (int i = 0; i < objects.length; i++) {
                    statement.setObject(i + 1, objects[i]);
                }
            }

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return getT(clazz, set);
            }

            return null;
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 通过反射的方式，将 ResultSet 中的每行数据映射到实体中
     *
     * @param clazz 实体的Class
     * @param set   ResultSet
     * @param <T>   注入的实体
     * @return
     */
    private <T> T getT(Class<T> clazz, ResultSet set) throws IllegalAccessException, InstantiationException, SQLException, NoSuchFieldException {
        //取到泛型的对象，所以必须要求获取的实体类至少要有无参的构造函数
        T t = clazz.newInstance();

        //获取 ResultSet 查找到的数据字段有哪些
        //元数据
        ResultSetMetaData metaData = set.getMetaData();
        //"select mid,mname,maliasname from tmovies where id > ?"
        int count = metaData.getColumnCount();


        //获取到所有的列名（对应到实体对象的属性名）、列所对应的的数据
        //通过 ResultSet 中的数据结构对照实体 T 的属性，调用 Setter 函数，设置值
        for (int i = 1; i <= count; i++) {
            //获取对应索引的列名
            String name = metaData.getColumnName(i);
            //获取列对应的数据
            Object value = set.getObject(name);

            if (value == null)
                value = "";

            //使用反射的方式获取实体 T 的成员变量（属于类）并设置值（属于对象）
            Field field = clazz.getDeclaredField(name);

            //强行执行
            field.setAccessible(true);
            field.set(t, value);
        }

        return t;
    }

    public Word[] selectMore(String sql, Object... objects) {
        try {
            connection = getConnection();
            Word t[] = new Word[1000];

            //预定义操作
            statement = connection.prepareStatement(sql);

            if (objects != null && objects.length > 0) {
                for (int i = 0; i < objects.length; i++) {
                    statement.setObject(i + 1, objects[i]);
                }
            }

            ResultSet set = statement.executeQuery();
            int num = 0;
            while (set.next()) {
                t[num] = getT(Word.class, set);
                num++;
            }

            return t;
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
