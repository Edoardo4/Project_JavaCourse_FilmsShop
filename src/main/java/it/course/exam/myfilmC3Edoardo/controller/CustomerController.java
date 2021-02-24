package it.course.exam.myfilmC3Edoardo.controller;

import java.time.Instant;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import it.course.exam.myfilmC3Edoardo.entity.Customer;

import it.course.exam.myfilmC3Edoardo.payload.request.CustomerRequest;
import it.course.exam.myfilmC3Edoardo.payload.response.ApiResponseCustom;
import it.course.exam.myfilmC3Edoardo.repository.ActorRepository;
import it.course.exam.myfilmC3Edoardo.repository.CountryRepository;
import it.course.exam.myfilmC3Edoardo.repository.CustomerRepository;
import it.course.exam.myfilmC3Edoardo.repository.FilmRepository;
import it.course.exam.myfilmC3Edoardo.repository.LanguageRepository;
import it.course.exam.myfilmC3Edoardo.repository.StoreRepository;
import it.course.exam.myfilmC3Edoardo.service.FilmService;

@RestController
public class CustomerController {
	
	@Autowired LanguageRepository languageRepsitory;
	@Autowired CountryRepository countryRepository;
	@Autowired ActorRepository actorRepository;
	@Autowired FilmRepository filmRepostory;
	@Autowired FilmService filmService;
	@Autowired StoreRepository storeRepository;
	@Autowired CustomerRepository customerRepository;
	
	//add-update-customer
	@PostMapping("/add-update-customer")
	public ResponseEntity<ApiResponseCustom> addUpdateCustomer(@Valid @RequestBody CustomerRequest customerRequest, HttpServletRequest request){	
		
		String msg = "Customer whit email: "+(customerRequest.getEmail());
		if(customerRepository.existsById(customerRequest.getEmail())) 
			msg +=" updated";
		else
			msg +=" added";
		
		Customer c = new Customer(
				customerRequest.getEmail(),
				customerRequest.getFirstName().toUpperCase(),
				customerRequest.getLastName().toUpperCase()
				);
		customerRepository.save(c);
	
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",msg,request.getRequestURI()
				), HttpStatus.OK);
	}
	
	
	
	//get-all-customers-by-storeId
	@GetMapping("/get-all-customers-by-store/{storeId}")
	public ResponseEntity<ApiResponseCustom> getAllCustomersByStore(@Valid @PathVariable String storeId, HttpServletRequest request){
		
		if(!storeRepository.existsById(storeId)) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Store whit id: "+storeId+" not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		List<Customer> c = customerRepository.getCustomerByStore(storeId);
		
		if(c.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),200,"400","In this store: "+storeId+" has not purchased any customers ",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",c,request.getRequestURI()
				), HttpStatus.OK);
	}
	
	//get-customers-who-rent-films-by-language-film/{languageId}
	@GetMapping("/get-customers-who-rent-films-by-language-film/{languageId}")
	public ResponseEntity<ApiResponseCustom> getAllCustomersByLnagFilmRent(@Valid @PathVariable String languageId, HttpServletRequest request){
		
		if(!languageRepsitory.existsById(languageId))
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"400","Language: "+languageId+" not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		List<Customer> customers = customerRepository.getCustomerByLanguageFilmRent(languageId);
		if(customers.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"400","This customer has not rented films in this language: "+languageId,request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",customers,request.getRequestURI()
				), HttpStatus.OK);
	
		
	}
	
}
