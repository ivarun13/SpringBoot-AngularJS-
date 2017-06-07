package io.vp.projects.service;

import java.util.List;

import io.vp.projects.model.Customer;

public interface CustomerService {
	
	public List<Customer> getCustomers();
	
	public Customer findCustomer(Long id);
	
	public Customer saveCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer);
	
	public void deleteCustomer(Long id);

}
