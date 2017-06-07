package io.vp.projects;



import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


import java.util.List;

import io.vp.projects.Repository.CustomerRepository;
import io.vp.projects.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KenzanTestApplicationTests {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
	
	@Autowired
	EntityManager em;
	
	private static final String DEFAULT_NAME = "john";
    private static final String UPDATED_NAME = "joy";

    private static final String DEFAULT_EMAIL = "j@gmail.com";
    private static final String UPDATED_EMAIL = "joy@gmail.com";

    private static final String DEFAULT_TELEPHONE = "123456";
    private static final String UPDATED_TELEPHONE = "987654";
    
    private static final String DEFAULT_STREET = "abcd";
    private static final String UPDATED_STREET = "xyz";
    
    private static final String DEFAULT_STATE = "abcd";
    private static final String UPDATED_STATE = "xyz";
    
    private static final String DEFAULT_ZIP = "abcd";
    private static final String UPDATED_ZIP = "xyz";
    
   
    
   
    private Customer customer;
    
    Client client = ClientBuilder.newClient();
    
    WebTarget baseTarget = client.target("http://localhost:8880/");
    WebTarget customersTarget = baseTarget.path("customers");
    WebTarget singlecustomerTarget = customersTarget.path("{customerId}");
 

    
    public static Customer createCustomerEntity(EntityManager em) {
        Customer customer = new Customer();
        customer.setName(DEFAULT_NAME);
        customer.setEmail(DEFAULT_EMAIL);
        customer.setTelephone(DEFAULT_TELEPHONE);
        return customer;
    }
	
    @Before
    public void initTest() {
        customer = createCustomerEntity(em);
    }
	
    
    
	@Test
	@Transactional
	public void getCustomer() {
		
		customerRepository.saveAndFlush(customer);
		System.out.println("get"+customer.getId());
		//Making GET Request from RestAPI
		Customer customer1 = singlecustomerTarget
				.resolveTemplate("customerId",customer.getId())
				.request(MediaType.APPLICATION_JSON)
				.get(Customer.class);
		
		Customer customer2 = customerRepository.findOne(customer.getId());
		assert(customer1).equals(customer2);		
		assert(customer1.getName()).equals(DEFAULT_NAME);
		assert(customer1.getEmail()).equals(DEFAULT_EMAIL);
		assert(customer1.getTelephone()).equals(DEFAULT_TELEPHONE);
		
	}
	
	@Test
	public void getAllCustomers() {
		
		@SuppressWarnings("unchecked")
		List<Customer> customers = customersTarget
				.request(MediaType.APPLICATION_JSON)
				.get(List.class);
		Integer size = customerRepository.findAll().size();
		assert(size).equals(customers.size());
	}
	
	@Test
	@Transactional
	public void AddCustomer() {
		
		Integer sizeBeforenewCustomer = customerRepository.findAll().size();
		
		Response response = customersTarget
				.request()
				.post(Entity.json(customer));
		//System.out.println("post"+customer.getId());
		Integer sizeAfterAddCustomer = customerRepository.findAll().size();
		assert(sizeAfterAddCustomer).equals(sizeBeforenewCustomer+1);
		Customer newCustomer = response.readEntity(Customer.class);
		assert(newCustomer.getName()).equals(DEFAULT_NAME);
		assert(newCustomer.getEmail()).equals(DEFAULT_EMAIL);
		assert(newCustomer.getTelephone()).equals(DEFAULT_TELEPHONE);
	
	}
	
	@Test
	@Transactional
	public void UpdateCustomer() {
		
		customerRepository.saveAndFlush(customer);
		System.out.println("update"+customer.getId());
		Customer updateC = new Customer();
		updateC.setName(UPDATED_NAME);
		updateC.setEmail(UPDATED_EMAIL);
		updateC.setTelephone(UPDATED_TELEPHONE);
		
		Response response = singlecustomerTarget
				.resolveTemplate("customerId",customer.getId())
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.json(updateC));
		
		//System.out.println(customerRepository.findAll().size());
		Customer updatedCustomer = response.readEntity(Customer.class);
		assert(updatedCustomer.getName()).equals(UPDATED_NAME);
		assert(updatedCustomer.getEmail()).equals(UPDATED_EMAIL);
		assert(updatedCustomer.getTelephone()).equals(UPDATED_TELEPHONE);
	
	}
	
	@Test
	@Transactional
	public void deleteCustomer() {
		
		customerRepository.saveAndFlush(customer);
        int databaseSizeBeforeDelete = customerRepository.findAll().size();
        
        Response response = singlecustomerTarget
				.resolveTemplate("customerId",customer.getId()	)
				.request()
				.delete();
        
        List<Customer> customers = customerRepository.findAll();
        assert(customers).equals(databaseSizeBeforeDelete - 1);
		
		
	}

}
