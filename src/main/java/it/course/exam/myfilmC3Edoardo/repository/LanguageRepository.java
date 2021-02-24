package it.course.exam.myfilmC3Edoardo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmC3Edoardo.entity.Language;


@Repository
public interface LanguageRepository extends JpaRepository<Language, String>{

	@Query(value="SELECT l.language_name FROM `language` l "
			+ "JOIN film f ON l.language_id = f.language_id "
			+ "where f.film_id = :filmId", nativeQuery = true)
	String languaugeNameByFilmId(@Param("filmId") String filmId); 
}
