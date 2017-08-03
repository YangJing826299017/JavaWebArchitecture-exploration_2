package org.smart4j.chapter2.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载属性文件工具包
 * @author 杨景
 * @since 2017-8-3
 */
public class PropsUtil {
    private static final Logger LOGGER=LoggerFactory.getLogger(PropsUtil.class);
    
    /**
     * 加载属性文件
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName){
        Properties properties=null;
        InputStream is=null;
        try{
            //1.获取properties文件流
            is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is==null){
                throw new FileNotFoundException(fileName+":文件找不到!");
            }
            //2.加载properties文件
            properties=new Properties();
            try {
                properties.load(is);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                LOGGER.error(fileName+"加载properties文件异常",e);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
            LOGGER.error("文件:"+fileName+"\t找不到",e);
        }finally {
          //3.关闭properties文件流
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    LOGGER.error("关闭properties文件流异常",e);
                }
            }
        }
        
        return properties;
    }
    
    public static String getString(Properties properties,String key){
        return getString(properties, key, "");
    }
    
    public static String getString(Properties properties,String key,String defaultValue){
        String strValue=defaultValue;
        if(properties.containsKey(key)){
            strValue=properties.getProperty(key);
        }
        return strValue;
    }
    
    public static int getInt(Properties properties,String key){
        return getInt(properties, key,0);
    }
    
    public static int getInt(Properties properties,String key,int defaultValue){
        int intValue=defaultValue;
        if(properties.containsKey(key)){
           Object object= properties.get(key);
           intValue=CastUtil.castInt(object,defaultValue);
        }
        return intValue;
    }
    
    public static boolean getBoolean(Properties properties,String key){
        return getBoolean(properties, key, false);
    }
    
    public static boolean getBoolean(Properties properties,String key,boolean defaultValue){
        boolean booleanValue=defaultValue;
        if(properties.contains(key)){
            Object object=properties.get(key);
            booleanValue=CastUtil.castBoolean(object);
        }
        return booleanValue;
    }

}
