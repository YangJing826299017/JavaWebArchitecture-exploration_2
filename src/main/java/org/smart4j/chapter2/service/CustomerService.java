package org.smart4j.chapter2.service;

import java.util.List;

import org.smart4j.chapter2.helper.DataBaseHelper;
import org.smart4j.chapter2.model.Customer;

public class CustomerService {
    

    public List<Customer> listCustomer(){
        return DataBaseHelper.listEntity(Customer.class,"select * from customer");
     }
    
    public Customer getCustomer(Integer id){
        return DataBaseHelper.getEntity(Customer.class, "select * from customer where id=?",id);
    }
    
    

}
