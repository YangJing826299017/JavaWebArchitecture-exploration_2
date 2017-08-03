package org.smart4j.chapter2.service;

import java.util.List;

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
    public void listCustomerTest(){
        List<Customer> listCoustomer=customerService.listCustomer();
        for (Customer customer : listCoustomer) {
            System.out.println(customer.toString());
        }
    }

}
