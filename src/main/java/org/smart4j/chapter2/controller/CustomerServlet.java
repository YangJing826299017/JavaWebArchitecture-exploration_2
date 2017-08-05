package org.smart4j.chapter2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet{
    
    private CustomerService customerService;

  
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init() throws ServletException {
        customerService=new CustomerService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> listCustomer=customerService.listCustomer();
        request.setAttribute("listCustomer",listCustomer);
        request.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(request, response);
    }
    
    
    

}
