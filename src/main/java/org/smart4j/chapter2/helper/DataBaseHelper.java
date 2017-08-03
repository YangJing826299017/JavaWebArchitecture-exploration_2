package org.smart4j.chapter2.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropsUtil;

/**
 * 数据库操作类
 * @author 杨景
 *
 */
public class DataBaseHelper {
    private static final Logger LOGGER=LoggerFactory.getLogger(DataBaseHelper.class);
    private static final ThreadLocal<Connection> CONNECTION_HOLDER=new ThreadLocal<>();
    private static final QueryRunner QUERY_RUNNER=new QueryRunner();
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    
    static{
        Properties properties=PropsUtil.loadProps("config.properties");
        DRIVER=PropsUtil.getString(properties,"jdbc.driverClassName");
        URL=PropsUtil.getString(properties, "jdbc.url");
        USERNAME=PropsUtil.getString(properties,"jdbc.username");
        PASSWORD=PropsUtil.getString(properties,"jdbc.password");
        
        try{
            Class.forName(DRIVER);
        }catch(ClassNotFoundException e){
            LOGGER.error("无法加载driver",e);
        }
    }
    
    public static Connection getConnection() {
        Connection connection=CONNECTION_HOLDER.get();
        if(connection!=null)
            return connection;
        
        //connection为空
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error("获取连接失败",e);
            e.printStackTrace();
        }finally {
            CONNECTION_HOLDER.set(connection);
        }
        return connection;
    }
    
    public static void closeConnection(){
        Connection connection=CONNECTION_HOLDER.get();
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                LOGGER.error("关闭连接失败!",e);
                e.printStackTrace();
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
       
    }
    
    public static <T>List<T> listEntity(Class<T>entityClass,String sql,Object... params){
        Connection conn=getConnection();
        List<T> listEntity=null;
        try {
            listEntity=QUERY_RUNNER.query(conn, sql, new BeanListHandler<>(entityClass), params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error("sql语句:"+sql+"\t抛出异常",e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return listEntity;
    }
    
    public static <T>T getEntity(Class<T> entityClass,String sql,Object... params){
        Connection conn=getConnection();
        T entity=null;
        try {
            entity=QUERY_RUNNER.query(conn,sql, new BeanHandler<>(entityClass), params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error("sql语句:"+sql+"\t查询抛出异常",e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        
        return entity;
    }

}
