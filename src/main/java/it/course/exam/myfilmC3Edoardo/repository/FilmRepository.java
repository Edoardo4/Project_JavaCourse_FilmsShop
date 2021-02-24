package it.course.exam.myfilmC3Edoardo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmC3Edoardo.entity.Film;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmNrRentResponse;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmRentCustomerResponse;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmResponse;
import it.course.exam.myfilmC3Edoardo.payload.response.FilmStoreResponse;


@Repository
public interface FilmRepository extends JpaRepository<Film, String>{

	//get-film-by-filmId
	@Query(value="select new it.course.exam.myfilmC3Edoardo.payload.response.FilmResponse("
			+ "f.filmId, "
			+ "f.title, "
			+ "f.description, "
			+ "f.releaseYear, "	
			+ "f.language.languageName, "
			+ "f.country.countryName"
			+ ") "
			+ "FROM Film f "
			+ "WHERE f.id = :filmId "
			+ " "
			)
	Optional<FilmResponse> getFilmResponseFilmId(@Param("filmId") String filmId);
	
	
	Page<Film> findAll(Pageable pageable);
	
	//get-list-film-in-store
	@Query(value="select new it.course.exam.myfilmC3Edoardo.payload.response.FilmStoreResponse("
			+ "sf.filmId, "
			+ "s.storeName "	
			+ ") "
			+ "FROM Store s "
			+ "LEFT JOIN s.films sf "
			+ "WHERE sf.filmId IN (:filmId) "
			+ " "
			)
	List<FilmStoreResponse> getFilmStoreResponse(@Param("filmId") Set<String> filmId);
	
	//get-film-by-actors
	@Query(value="select new it.course.exam.myfilmC3Edoardo.payload.response.FilmResponse("
			+ "f.filmId, "
			+ "f.title, "
			+ "f.description, "
			+ "f.releaseYear, "	
			+ "f.language.languageName, "
			+ "f.country.countryName"
			+ ") "
			+ "FROM Film f "
			+ "LEFT JOIN f.actors fa "
			+ "WHERE fa.actorId IN (:actorsId) "
			+ " "
			)
	List<FilmResponse> getFilmResponseByActors(@Param("actorsId") Set<String> actorsId);

	//get-film-by-countryId
	@Query(value="select new it.course.exam.myfilmC3Edoardo.payload.response.FilmResponse("
			+ "f.filmId, "
			+ "f.title, "
			+ "f.description, "
			+ "f.releaseYear, "	
			+ "f.language.languageName, "
			+ "f.country.countryName"
			+ ") "
			+ "FROM Film f "
			+ "WHERE f.country.countryId = :countryId "
			+ " "
			)
	List<FilmResponse> getFilmResponseByCountryId(@Param("countryId") String countryId);
	
	//get-film-by-countryId
	@Query(value="select new it.course.exam.myfilmC3Edoardo.payload.response.FilmResponse("
			+ "f.filmId, "
			+ "f.title, "
			+ "f.description, "
			+ "f.releaseYear, "	
			+ "f.language.languageName, "
			+ "f.country.countryName"
			+ ") "
			+ "FROM Film f "
			+ "WHERE f.language.langugeId = :languageId "
			+ " "
			)
	List<FilmResponse> getFilmResponseByLanguageId(@Param("languageId") String languageId);
		
	
	@Query(value="SELECT * FROM film f where f.film_id IN (:filmId)", nativeQuery = true)
	Set<Film> getFilmByIdIn(Set<String> filmId);
	
	//find-all-films-rent-by-one-customer
	@Query(value="select new it.course.exam.myfilmC3Edoardo.payload.response.FilmRentCustomerResponse("
			+ "f.filmId, "
			+ "f.title "
			+ ") "
			+ "FROM Film f "
			+ "LEFT JOIN Rental r ON r.rentalId.customer.email = :customerId "
			+ "WHERE r.rentalId.film.filmId = f.filmId "
			+ "GROUP BY f.filmId"
			+ " "
			)
	List<FilmRentCustomerResponse> getFilmsRentByOneCustomer(@Param("customerId") String customerId);
		
		
	//find-all-film-whit-max-number-rents
	@Query(value="select new it.course.exam.myfilmC3Edoardo.payload.response.FilmNrRentResponse("				
			+ "r.rentalId.film.filmId, "
			+ "f.title, "
			+ "COUNT(*) AS Frequency"
			+ ") "
			+ "FROM Rental r "
			+ "LEFT JOIN Film f ON f.filmId = r.rentalId.film.filmId "
			+ "GROUP BY f.filmId "
			+ "ORDER BY COUNT(*) DESC"
			+ " "
			)
	List<FilmNrRentResponse> getFilmWhitMaxNumberOfRent();
		
		

}
