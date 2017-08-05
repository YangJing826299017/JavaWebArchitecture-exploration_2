package org.smart4j.chapter2.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.CollectionUtil;
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
    private static final BasicDataSource DATA_SOURCE=new BasicDataSource();
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
        
        //采用DBCP加载数据源
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setPassword(PASSWORD);
        DATA_SOURCE.setUsername(USERNAME);
        
        /*
        try{
            Class.forName(DRIVER);
        }catch(ClassNotFoundException e){
            LOGGER.error("无法加载driver",e);
        }
        */
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
    
    //查询语句 返回列与值的映射(Map)
    public static List<Map<String,Object>> executeQuery(String sql,Object...params){
        List<Map<String,Object>> result=null;
        Connection conn=getConnection();
        try {
            result=QUERY_RUNNER.query(conn, sql,new MapListHandler(), params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error("查询语句发生异常:sql\t"+sql,e);
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return result;
    }
    
    //更新语句(update\insert\delete)
    public static int executeUpdate(String sql,Object... params){
        int result=0;
        Connection conn=getConnection();
        try {
            result=QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error("查询语句发生异常:sql\t"+sql,e);
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return result;
    }
    
    //插入实体
    /**
     * 插入数据
     * @param classEntity 实体类
     * @param filedMap 列名与值的映射列表
     * @return
     */
    public static boolean insertEntity(Class<?> classEntity,Map<String,Object> filedMap){
        //1.判断进入fileMap 映射是否为空
        boolean isFiledMapNull=CollectionUtil.isEmpty(filedMap);
        if(isFiledMapNull){
            LOGGER.error("列名与值的映射表为空");
            return false;
        }
        //2.拼接Sql
        //insert into table (id,name) values (?,?)
        String sql="insert into "+getTableName(classEntity);
        StringBuilder colums=new StringBuilder("(");
        StringBuilder values=new StringBuilder("(");
        int filedMapSize=filedMap.size();
        Set<String> keySet=filedMap.keySet();
        Iterator<String> it=keySet.iterator();
        int count=1;
        while(it.hasNext()){
              String oneColum=it.next();
              boolean isLastElement=filedMapSize==count;
              if(isLastElement){
                  colums.append(oneColum+")");
                  values.append("?)");
              }
              if(!isLastElement){
                  colums.append(oneColum+",");
                  values.append("?,");
              }
              count++;
        }
        String finalSql=sql+ colums.toString()+ " values "+values.toString();
        //3.插入
        Object[] params=filedMap.values().toArray();
        int result=executeUpdate(finalSql, params);
        return result==1;
    }
    
    //删除实体
    public static boolean deleteEntity(Class<?> classEntity,Long id){
        String tableName=getTableName(classEntity);
        StringBuilder sb=new StringBuilder("delete from "+tableName+" where id=?");
        int result=executeUpdate(sb.toString(),id);
        return result==1;
    }
    
    //更新实体
    public static boolean updateEntity(Class<?> classEntity,Map<String,Object> filedMap,long id){
        //1.判断进入fileMap 映射是否为空
        boolean isFiledMapNull=CollectionUtil.isEmpty(filedMap);
        if(isFiledMapNull){
            LOGGER.error("列名与值的映射表为空");
            return false;
        }
        //2.拼接Sql
        //update customer set name=?,sex=? where id=?
        String sql="update "+getTableName(classEntity)+" set ";
        StringBuilder columsAndValue=new StringBuilder(" ");
        int filedMapSize=filedMap.size();
        Set<String> keySet=filedMap.keySet();
        Iterator<String> it=keySet.iterator();
        int count=1;
        while(it.hasNext()){
              String oneColum=it.next();
              boolean isLastElement=filedMapSize==count;
              if(isLastElement){
                  columsAndValue.append(oneColum+"=? ");
              }
              if(!isLastElement){
                  columsAndValue.append(oneColum+"=?,");
              }
              count++;
        }
        StringBuilder whereSql=new StringBuilder(" where id="+id);
        String finalSql=sql+columsAndValue.toString()+whereSql.toString();
        //3.更新
        Object[] params=filedMap.values().toArray();
        int result=executeUpdate(finalSql, params);
        return result==1;
    }
    
    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<>();
        map.put("name","杨景");
        map.put("sex","男");
        DataBaseHelper.updateEntity(Customer.class, map, 15240069l);
    }
    
    
    private static String getTableName(Class<?> classEntity){
        return classEntity.getSimpleName();
    }
    

}
