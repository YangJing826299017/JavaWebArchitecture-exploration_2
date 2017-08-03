package org.smart4j.chapter2.service;

import java.sql.Connection;
import java.util.List;

import org.smart4j.chapter2.helper.DataBaseHelper;
import org.smart4j.chapter2.model.Customer;

public class CustomerService {
    

    public List<Customer> listCustomer(){
        Connection conn=null;
        try{
            conn=DataBaseHelper.getConnection();
            return DataBaseHelper.listEntity(Customer.class, conn,"select * from customer");
        }finally{
            DataBaseHelper.closeConnection(conn);
        }
     }
    
    

}
