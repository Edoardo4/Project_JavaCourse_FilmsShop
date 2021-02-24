package it.course.exam.myfilmC3Edoardo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmC3Edoardo.entity.Rental;
import it.course.exam.myfilmC3Edoardo.entity.RentalId;

@Repository
public interface RentalRepostory extends JpaRepository<Rental, RentalId>{

	Optional<Rental> findByRentalIdAndRentalReturnNull(RentalId rentalId);
	
	@Query(value="select ("
			+ "r "
			+ ") "
			+ "FROM Rental r "
			+ "WHERE r.rentalId.customer.email = :customerEmail AND r.rentalId.film.filmId=:filmId "
			+ "AND r.rentalId.store.storeId = :storeId AND r.rentalReturn = '9999/12/30'"
			+ " "
			)
	Optional<Rental> getRentalOpen(@Param("storeId") String storeId,@Param("filmId") String filmId,@Param("customerEmail") String customerEmail );
	
	
	@Query(value="select count("
			+ "r "
			+ ") "
			+ "FROM Rental r "
			+ "WHERE r.rentalId.rentalDate between :startDate and :endDate"
			+ " "
			)
	int getRentalNumberInDateRange(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
}
