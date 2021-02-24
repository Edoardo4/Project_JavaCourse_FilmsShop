package it.course.exam.myfilmC3Edoardo.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.course.exam.myfilmC3Edoardo.entity.Customer;
import it.course.exam.myfilmC3Edoardo.entity.Film;
import it.course.exam.myfilmC3Edoardo.entity.Rental;
import it.course.exam.myfilmC3Edoardo.entity.RentalId;
import it.course.exam.myfilmC3Edoardo.entity.Store;
import it.course.exam.myfilmC3Edoardo.payload.request.RentalRequest;
import it.course.exam.myfilmC3Edoardo.payload.request.RentalsNumberInDateRangeRequest;
import it.course.exam.myfilmC3Edoardo.payload.response.ApiResponseCustom;
import it.course.exam.myfilmC3Edoardo.repository.ActorRepository;
import it.course.exam.myfilmC3Edoardo.repository.CountryRepository;
import it.course.exam.myfilmC3Edoardo.repository.CustomerRepository;
import it.course.exam.myfilmC3Edoardo.repository.FilmRepository;
import it.course.exam.myfilmC3Edoardo.repository.LanguageRepository;
import it.course.exam.myfilmC3Edoardo.repository.RentalRepostory;
import it.course.exam.myfilmC3Edoardo.repository.StoreRepository;
import it.course.exam.myfilmC3Edoardo.service.FilmService;

@RestController
public class RentalController {
	
	@Autowired LanguageRepository languageRepsitory;
	@Autowired CountryRepository countryRepository;
	@Autowired ActorRepository actorRepository;
	@Autowired FilmRepository filmRepository;
	@Autowired FilmService filmService;
	@Autowired StoreRepository storeRepository;
	@Autowired RentalRepostory rentalRepostory;
	@Autowired CustomerRepository customerRepository;
	
	//update -inser-rental
	//per fare l'insert in postman lasciare rentalDate:""
	@PostMapping("/add-update-rental")
	@Transactional
	public ResponseEntity<ApiResponseCustom> addUpdateRental(@Valid @RequestBody RentalRequest rentalRequest, HttpServletRequest request){
		
		Optional<Customer> customer = customerRepository.findById(rentalRequest.getCustomerEmail());
		if(!customer.isPresent()) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Customer not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		Optional<Film> film = filmRepository.findById(rentalRequest.getFilmId());
		if(!customer.isPresent()) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Film not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		Optional<Store> store = storeRepository.findById(rentalRequest.getStoreId());
		if(!customer.isPresent()) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Store not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		Optional<Rental> rentalExist = rentalRepostory.findById(		
				new RentalId(
						rentalRequest.getRentalDate(),
						customer.get(),film.get(),store.get())
				);
		
		//se è presente rentalExist e il date return è 9999/12/30 set il returnDate
		if(rentalExist.isPresent() && rentalExist.get().getRentalReturn().isEqual(LocalDate.of(9999,12,30))) {
			
			rentalExist.get().setRentalReturn(LocalDate.now());
			
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),200,"OK","rental updated",request.getRequestURI()
					), HttpStatus.OK);
			
		//se è presente rentalExist e il date return è diverso da 9999/12/30 il film è già stato restituito
		}else if(rentalExist.isPresent() && !rentalExist.get().getRentalReturn().isEqual(LocalDate.of(9999,12,30))) {
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),403,"FORBIDDEN","film returned on: "+rentalExist.get().getRentalReturn(),request.getRequestURI()
					), HttpStatus.FORBIDDEN);
		}
		
		/*if(!store.get().getFilms().contains(film.get()))
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),400,"OK","Film: "+film.get().getFilmId()+" is not present in store: "+store.get().getStoreId(),request.getRequestURI()
				), HttpStatus.NOT_FOUND);*/
		
		//se è presente un rental con storeId,filmId, customerId e date return = 999/12/30 vuol dire che è già noleggiato e non ancora restituito
		if(rentalRepostory.getRentalOpen(rentalRequest.getStoreId(), rentalRequest.getFilmId(), rentalRequest.getCustomerEmail()).isPresent()) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),403,"FORBIDDEN","this film: "+ rentalRequest.getFilmId()+" is already present and has not yet been restored" ,request.getRequestURI()
					), HttpStatus.FORBIDDEN);
		
		Rental rental = new Rental(
				new RentalId(
						LocalDate.now(),customer.get(),film.get(),store.get()
						),
				(LocalDate.of(9999,12,30))
				);
		rentalRepostory.save(rental);
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK","rental added",request.getRequestURI()
				), HttpStatus.OK);	
	}
	
	//get-rentals-number-in-date-range
	@GetMapping("/get-rentals-number-in-date-range")
	public ResponseEntity<ApiResponseCustom> getRentalsNumberInDateRange(@Valid @RequestBody RentalsNumberInDateRangeRequest rentalsNumberInDateRangeRequest, HttpServletRequest request){
		
		int rentalsNumber = rentalRepostory.getRentalNumberInDateRange(rentalsNumberInDateRangeRequest.getStartDate(),  rentalsNumberInDateRangeRequest.getEndDate());
		if(rentalsNumber ==0)
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Rentals in date range: "
							+rentalsNumberInDateRangeRequest.getStartDate()+" and "+ rentalsNumberInDateRangeRequest.getEndDate() +" not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
			
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",rentalsNumber,request.getRequestURI()
				), HttpStatus.OK);
	}
}
