package it.course.exam.myfilmC3Edoardo.payload.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RentalRequest {

	
	private LocalDate rentalDate;
	
	@NotBlank @NotEmpty
	@Size(max=50)
	private String customerEmail;
	
	@NotBlank @NotEmpty
	@Size(max=10)
	private String filmId;

	@NotBlank @NotEmpty
	@Size(max=10)
	private String storeId;
	

	

}
