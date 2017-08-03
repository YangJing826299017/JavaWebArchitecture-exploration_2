package org.smart4j.chapter2.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @author 杨景
 * @since 2017-8-3
 */
public class StringUtil {
    
    public static boolean isEmpty(String checkString){
        if(checkString!=null){
            checkString=checkString.trim();
        }
        return StringUtils.isEmpty(checkString);
    }
    
    public static boolean isNotEmpty(String checkString){
        return !isEmpty(checkString);
    }
}
