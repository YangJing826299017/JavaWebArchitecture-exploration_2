package org.smart4j.chapter2.util;
/**
 * 转型操作工具类
 * @author 杨景
 * @since 2017-8-3
 */
public class CastUtil {
    
    public static String castString(Object object){
        return castString(object,"");
    }
    
    public static String castString(Object object,String defaultValue){
        return object!=null?String.valueOf(object):defaultValue;
    }
    
    public static double castDouble(Object object){
        return castDouble(object, 0);
    }
    
    public static double castDouble(Object object,double defaultValue){
        double doubleValue=defaultValue;
        if(object!=null){
            String strValue=castString(object);
            if(StringUtil.isNotEmpty(strValue)){
               try{
                   doubleValue= Double.parseDouble(strValue);
               }catch(Exception e){
                   e.printStackTrace();
                   doubleValue=defaultValue;
               }
            }
        }
        return doubleValue;
    }
    
    public static long castLong(Object object){
        return castLong(object,0);
    }

    public static long castLong(Object object,long defaultValue){
        long longValue=defaultValue;
        if(object!=null){
            String strValue=castString(object);
            boolean isEmpty=StringUtil.isEmpty(strValue);
            if(!isEmpty){
                try{
                    longValue=Long.parseLong(strValue);
                }catch(Exception e){
                    e.printStackTrace();
                    longValue=defaultValue;
                }
            }
        }
        return longValue;
    }
    
    public static int castInt(Object object){
        return castInt(object, 0);
    }
    
    public static int castInt(Object object,int defaultValue){
        int intValue=defaultValue;
        if(object!=null){
            String strValue=castString(object);
            boolean isEmpty=StringUtil.isEmpty(strValue);
            if(!isEmpty){
                try{
                    intValue=Integer.parseInt(strValue);
                }catch(Exception e){
                    intValue=defaultValue;
                    e.printStackTrace();
                }
            }
        }
        return intValue;
    }
    
    public static boolean castBoolean(Object object){
        return castBoolean(object,false);
    }
    
    public static boolean castBoolean(Object object,boolean defaultValue){
        boolean booleanValue=defaultValue;
        if(object!=null){
            String strValue=castString(object);
            boolean isEmpty=StringUtil.isEmpty(strValue);
            if(!isEmpty){
                try{
                    booleanValue=Boolean.parseBoolean(strValue);
                }catch(Exception e){
                    booleanValue=defaultValue;
                    e.printStackTrace();
                }
            }
        }
        return booleanValue;
    }
}
