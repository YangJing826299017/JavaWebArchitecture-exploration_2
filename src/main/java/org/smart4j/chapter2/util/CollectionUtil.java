package org.smart4j.chapter2.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

/**
 * 集合工具类
 * @author 杨景
 * @since 2017-8-3
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }
    
    public static boolean isNotEmpty(Collection<?> collection){
        return CollectionUtils.isNotEmpty(collection);
    }
    
    public static boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }
    
    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }
}
