package org.smart4j.chapter2.service;

import java.util.List;
import java.util.Map;

import org.smart4j.chapter2.helper.DataBaseHelper;
import org.smart4j.chapter2.model.Customer;

public class CustomerService {
    

    public List<Customer> listCustomer(){
        return DataBaseHelper.listEntity(Customer.class,"select * from customer");
     }
    
    public Customer getCustomer(Integer id){
        return DataBaseHelper.getEntity(Customer.class, "select * from customer where id=?",id);
    }
    
    //创建客户
    public boolean createCustomer(Map<String,Object> filedMap){
        return DataBaseHelper.insertEntity(Customer.class, filedMap);
    }
    
    //删除客户
    public boolean deleteCustomer(long id){
        return DataBaseHelper.deleteEntity(Customer.class, id);
    }
    
    //更新用户
    public boolean updateCustomer(Map<String,Object> filedMap,long id){
        return DataBaseHelper.updateEntity(Customer.class, filedMap, id);
    }
    
    
    
    

}
