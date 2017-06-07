package io.vp.projects.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.vp.projects.Repository.CustomerRepository;
import io.vp.projects.model.Customer;
import io.vp.projects.service.CustomerService;

@RestController
public class CustromerRestController {

	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(path="/customers", method=RequestMethod.GET)
	public List<Customer> getAllCustomers(){
		return customerService.getCustomers();
	}
	
	@RequestMapping(path="/customers", method=RequestMethod.POST)
	public Customer saveCustomer(@RequestBody Customer customer){
		return customerService.saveCustomer(customer);
	}
	
	@RequestMapping(path="/customers/{id}", method=RequestMethod.GET)
	public Customer getCustomer(@PathVariable Long id){
		return customerService.findCustomer(id);
	}

	@RequestMapping(path="/customers/{id}", method=RequestMethod.PUT)
	public Customer getCustomer(@PathVariable Long id,@RequestBody Customer customer){
		return customerService.updateCustomer(customer);
	}
	
	@RequestMapping(path="/customers/{id}", method=RequestMethod.DELETE)
	public void removeCustomer(@PathVariable Long id){
		    customerService.deleteCustomer(id);
	}
}
