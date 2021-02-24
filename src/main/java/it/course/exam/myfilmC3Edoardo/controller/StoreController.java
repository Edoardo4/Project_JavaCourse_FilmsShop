package it.course.exam.myfilmC3Edoardo.controller;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import it.course.exam.myfilmC3Edoardo.entity.Film;
import it.course.exam.myfilmC3Edoardo.entity.Store;
import it.course.exam.myfilmC3Edoardo.payload.request.StoreRequest;
import it.course.exam.myfilmC3Edoardo.payload.response.ApiResponseCustom;
import it.course.exam.myfilmC3Edoardo.repository.ActorRepository;
import it.course.exam.myfilmC3Edoardo.repository.CountryRepository;
import it.course.exam.myfilmC3Edoardo.repository.FilmRepository;
import it.course.exam.myfilmC3Edoardo.repository.LanguageRepository;
import it.course.exam.myfilmC3Edoardo.repository.StoreRepository;
import it.course.exam.myfilmC3Edoardo.service.FilmService;

@RestController
public class StoreController {
	
	@Autowired LanguageRepository languageRepsitory;
	@Autowired CountryRepository countryRepository;
	@Autowired ActorRepository actorRepository;
	@Autowired FilmRepository filmRepostory;
	@Autowired FilmService filmService;
	@Autowired StoreRepository storeRepository;
	
	//@PostMapping("/add-update-store")  ---> @RequestBody StoreRequest
	
	@PostMapping("/add-update-store")
	@Transactional
	public ResponseEntity<ApiResponseCustom> addUpdateStore(@Valid @RequestBody StoreRequest storeRequest, HttpServletRequest request){
		
		Set<Film> films = new HashSet<Film>();	
				
		films= filmRepostory.getFilmByIdIn(storeRequest.getFilmId());	
	
		if(films.isEmpty())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Films not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		
		String msg = "Store whit id: "+storeRequest.getStoreId();
		if(storeRepository.existsById(storeRequest.getStoreId())) 
			msg +=" updated";
		else {
			if(storeRepository.existsByStoreName(storeRequest.getStoreName()))
				return new ResponseEntity<ApiResponseCustom>(
						new ApiResponseCustom(Instant.now(),403,"FORBIDDEN","Store name already in use",request.getRequestURI()
						), HttpStatus.FORBIDDEN);
			msg +=" added";
		}
		
		
		Store store = new Store(
				storeRequest.getStoreId(),storeRequest.getStoreName(),films
				);
		storeRepository.save(store);
		
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"OK",msg,request.getRequestURI()
				), HttpStatus.OK);	
	}
	
	//@PostMapping("/add-film-to-store/{storeId}/{filmId}")
	@PostMapping("/add-film-to-store/{storeId}/{filmId}")
	@Transactional
	public ResponseEntity<ApiResponseCustom> addFilmToStore(@Valid @PathVariable String storeId,@Valid @PathVariable String filmId, HttpServletRequest request){
		
		Optional<Store> store = storeRepository.findById(storeId);
		if(!store.isPresent())
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Store whit id: "+storeId+" not found",request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		Optional<Film> film = filmRepostory.findById(filmId);
		
		if(store.get().getFilms().contains(film.get()))
			return new ResponseEntity<ApiResponseCustom>(
					new ApiResponseCustom(Instant.now(),400,"NOT_FOUND","Film: "+film.get().getFilmId()+" is aleady present in store: "+store.get().getStoreId(),request.getRequestURI()
					), HttpStatus.NOT_FOUND);
		
		store.get().getFilms().add(film.get());
		return new ResponseEntity<ApiResponseCustom>(
				new ApiResponseCustom(Instant.now(),200,"NOT_FOUND","Film: "+film.get().getFilmId()+" added in store: "+store.get().getStoreId(),request.getRequestURI()
				), HttpStatus.OK);
	}
	
	
}
