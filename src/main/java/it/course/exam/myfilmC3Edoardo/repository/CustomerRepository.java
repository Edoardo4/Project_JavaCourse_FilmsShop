package it.course.exam.myfilmC3Edoardo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmC3Edoardo.entity.Customer;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, String>{

	//get-customer-by-store
	@Query(value="SELECT * FROM customer c "
			+ "JOIN rental r ON r.customer_email = c.email "
			+ "WHERE r.store_id = :storeId "
			+ "GROUP BY c.email", nativeQuery = true)
	List<Customer> getCustomerByStore(@Param("storeId") String storeId); 
	
	
	//get-customer-by-language-film-rent
	@Query(value="SELECT  c.email , c.first_name, c.last_name  FROM customer c "
			+ "inner JOIN  rental r ON r.customer_email = c.email "
			+ "inner JOIN  film f ON f.film_id = r.film_id "
			+ "WHERE f.language_id = :languageId "
			+ "GROUP BY r.customer_email", nativeQuery = true)
	List<Customer> getCustomerByLanguageFilmRent(@Param("languageId") String languageId); 
}
