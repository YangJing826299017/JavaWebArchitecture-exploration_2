package org.smart4j.chapter2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;

public class CustomerServiceTest {
    
    private CustomerService customerService;
    
    @Before
    public void init(){
        customerService=new CustomerService();
    }
    
    @Test
    public void createCustomerTest(){
        System.out.println("------创建用户-----");
        Map<String,Object> map=new HashMap<>();
        map.put("name","customer7");
        map.put("contact","Zoo");
        map.put("telphone", "XXXX");
        map.put("email", "xxxxxx@qq.com");
        map.put("remark", "无备注");
        boolean result=this.customerService.createCustomer(map);
        System.out.println("----用户创建"+result+"------");
        System.out.println();
    }
    
    
    public void listCustomerTest(){
        List<Customer> listCoustomer=customerService.listCustomer();
        for (Customer customer : listCoustomer) {
            System.out.println(customer.toString());
        }
    }
    
    
    public void getCustomerTest(){
        Customer customer=customerService.getCustomer(1);
        System.out.println("QueryResult:"+customer);
    }
    
    
    public void deleteCustomerTest(){
       boolean result= customerService.deleteCustomer(6);
       System.out.println(result);
    }
    
    
    public void updateCustomerTest(){
        Map<String,Object> map=new HashMap<>();
        map.put("name","customer6");
        map.put("contact","Black");
        map.put("telphone", "XXXX");
        map.put("email", "xxxxxx@qq.com");
        map.put("remark", "无备注");
        boolean result=this.customerService.updateCustomer(map,2);
        System.out.println(result);
    }

}
