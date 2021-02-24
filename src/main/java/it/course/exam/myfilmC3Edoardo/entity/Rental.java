package it.course.exam.myfilmC3Edoardo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RENTAL")
@Data @AllArgsConstructor @NoArgsConstructor
public class Rental {

	@EmbeddedId
	private RentalId rentalId;
	
	@Column(name="RENTAL_RETURN", nullable=true)
	private LocalDate rentalReturn;

	public Rental(RentalId rentalId) {
		super();
		this.rentalId = rentalId;
	}
	
	
}

