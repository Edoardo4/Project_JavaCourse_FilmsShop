package it.course.exam.myfilmC3Edoardo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmC3Edoardo.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String>{

	@Query(value="SELECT c.country_name FROM country c "
			+ "	JOIN film f ON c.country_id = f.country_id "
			+ "	where f.film_id = :filmId", nativeQuery = true)
	String countryNameByFilmId(@Param("filmId") String filmId); 
}
