package it.course.exam.myfilmC3Edoardo.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class RentalId implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="RENTAL_DATE", nullable=true)
	private LocalDate rentalDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_EMAIL", nullable=false)
	private Customer customer;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FILM_ID", nullable=false)
	private Film film;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="STORE_ID", nullable=false)
	private Store store;
	
}
