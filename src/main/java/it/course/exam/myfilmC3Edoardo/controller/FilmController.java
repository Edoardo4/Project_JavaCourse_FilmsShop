package it.course.exam.myfilmC3Edoardo.controller;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.course.exam.myfilmC3Edoardo.entity.Actor;
import it.course.exam.myfilmC3Edoardo.entity.Country;
import it.course.exam.myfilmC3Edoardo.entity.Film;
import it.course.exam.myfilmC3Edoardo.entity.Language;
import it.course.exam.myfilmC3Edoardo.payload.request.FilmByActorsRequest;
import it.course.exam.myfilmC3Edoardo.payload.request.FilmRequest;
import it.course.exam.myfilmC3Edoardo.payload.response.ApiResponseCustom;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmNrRentResponse;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmRentCustomerResponse;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmResponse;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmResponsePage;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmStoreResponse;
import it.course.exam.myfilmC3Edoardo.repository.ActorRepository;
import it.course.exam.myfilmC3Edoardo.repository.CountryRepository;
import it.course.exam.myfilmC3Edoardo.repository.CustomerRepository;
import it.course.exam.myfilmC3Edoardo.repository.FilmRepository;
import it.course.exam.myfilmC3Edoardo.repository.LanguageRepository;
import it.course.exam.myfilmC3Edoardo.service.FilmService;

@RestController
public class FilmController {

	@Autowired LanguageRepository languageRepsitory;
	@Autowired CountryRepository countryRepository;
	@Autowired ActorRepository actorRepository;
	@Autowired FilmRepository filmRepostory;
	@Autowired FilmService filmService;
	@Autowired CustomerRepository customerRepository;
	
	//add-update-film
	@PostMapping("/add-update-film")
	public ResponseEntity<ApiResponseCustom> addUpdateFilm(@Valid @RequestBody FilmRequest filmRequest, HttpServletRequest request){
		
		Optional<Language> l = languageRepsitory.findById(filmRequest.getLanguageId());
		if(!l.isPresent()) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Languauge not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
	
		Optional<Country> c = countryRepository.findById(filmRequest.getCountryId());
		if(!c.isPresent()) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Country not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		Set<Actor> actors = new HashSet<Actor>();	
		actors= actorRepository.findByActorIdIn(filmRequest.getActors());
		if(actors.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Actors not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		String msg = "Film whit id: "+filmRequest.getFilmId();
		if(filmRepostory.existsById(filmRequest.getFilmId())) 
			msg +=" updated";
		else
			msg +=" added";
		
		Film film = new Film(
				filmRequest.getFilmId(),
				filmRequest.getDescription(),
				filmRequest.getReleaseYear(),
				filmRequest.getTitle(),
				l.get(),c.get(),actors
				);
		filmRepostory.save(film);
	
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",msg,request.getRequestURI()
				), HttpStatus.OK);
	}
	
	//get-film-by-filmId
	@GetMapping("/get-film/{filmId}")
	public ResponseEntity<ApiResponseCustom> getFilmById(@Valid @PathVariable String filmId, HttpServletRequest request){
	
		Optional<FilmResponse> film = filmRepostory.getFilmResponseFilmId(filmId);
		
		if(!film.isPresent()) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Film whit id: "+filmId+" not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",film.get(),request.getRequestURI()
				), HttpStatus.OK);
	}
	

	//get-films-paged-by-title-asc
	@GetMapping("/get-films-paged-by-title-asc")
	@Transactional
	public ResponseEntity<ApiResponseCustom> getFilmByTitleAscPaged(
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="ASC") String direction,
			@RequestParam(defaultValue="title") String sortBy,
			HttpServletRequest request) {

		List<Film> films = filmService.findAllOrderByTitleAscPaged(pageNo, pageSize, direction, sortBy);
		
		if(films.isEmpty()) 
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","page not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		Long totFilm =  filmRepostory.count();
		
		FilmResponsePage filmResponse =  new FilmResponsePage(
				films.stream().map(FilmResponse::createFromEntity).collect(Collectors.toList()),
				 ((totFilm / pageSize) + (totFilm % pageSize > 0 ? 1 : 0 ))			
				);
	
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",filmResponse,request.getRequestURI()
				), HttpStatus.OK);
		
	}
	
	//get-list-film-in-store
	@GetMapping("/find-film-in-store/{filmId}") 
	public ResponseEntity<ApiResponseCustom> getFilmInStore(@Valid @PathVariable String filmId, HttpServletRequest request){
		
		Set<String> FilmId = new HashSet<String>(Arrays.asList(filmId));
		
		List<FilmStoreResponse> fsr = filmRepostory.getFilmStoreResponse(FilmId);
		
		if(fsr.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Film in store whit id: "+filmId+" not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",fsr,request.getRequestURI()
				), HttpStatus.OK);
	}
	
	//get-films-by-actors
	@GetMapping("/find-films-by-actors")
	public ResponseEntity<ApiResponseCustom> getFilmsByActors(@Valid @RequestBody FilmByActorsRequest filmByActorsRequest , HttpServletRequest request){
		
		List<FilmResponse> filmResponses = filmRepostory.getFilmResponseByActors(filmByActorsRequest.getActorsId());
		if(filmResponses.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Films not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",filmResponses,request.getRequestURI()
				), HttpStatus.OK);
	}
		
	//get-films-by-countryId 
	@GetMapping("/find-films-by-country/{countryId}")
	public ResponseEntity<ApiResponseCustom> getFilmsByCountryId(@Valid @PathVariable String countryId, HttpServletRequest request){
		
		List<FilmResponse> filmResponses = filmRepostory.getFilmResponseByCountryId(countryId);
		if(filmResponses.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Films whit country Id: "+countryId+" not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",filmResponses,request.getRequestURI()
				), HttpStatus.OK);
	}
	
	//get-films-by-languageId 
	@GetMapping("/find-films-by-language/{languageId}")
	public ResponseEntity<ApiResponseCustom> getFilmsByLanguageId(@Valid @PathVariable String languageId, HttpServletRequest request){
		
		List<FilmResponse> filmResponses = filmRepostory.getFilmResponseByLanguageId(languageId);
		if(filmResponses.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Films whit language Id: "+languageId+" not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",filmResponses,request.getRequestURI()
				), HttpStatus.OK);
	}
	

	//find-all-films-rent-by-one-customer
	@GetMapping("/find-all-films-rent-by-one-customer/{customerId}")
	public ResponseEntity<ApiResponseCustom> getFilmsByRentCustomer(@Valid @PathVariable String customerId, HttpServletRequest request){
		
		if(!customerRepository.existsById(customerId))
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"OK","Customer: "+customerId+" not found ",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		List<FilmRentCustomerResponse> films = filmRepostory.getFilmsRentByOneCustomer(customerId);
		if(films.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),403,"FORBIDDEN","Customer: "+customerId+" hasn't rented any films ",request.getRequestURI()
					), HttpStatus.FORBIDDEN);
				
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",films,request.getRequestURI()
				), HttpStatus.OK);
	}
	
	//find-film-with-max-number-of-rent
	@GetMapping("/find-film-with-max-number-of-rent")
	public ResponseEntity<ApiResponseCustom> getFilmsWhitMaxNumberOfRent(HttpServletRequest request){
		
		FilmNrRentResponse film = filmRepostory.getFilmWhitMaxNumberOfRent().get(0);
		
		if(film==null)
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Film not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",film,request.getRequestURI()
				), HttpStatus.OK);
	}
	
	
	
	
}
