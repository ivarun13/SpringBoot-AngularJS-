package io.vp.projects.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.vp.projects.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
