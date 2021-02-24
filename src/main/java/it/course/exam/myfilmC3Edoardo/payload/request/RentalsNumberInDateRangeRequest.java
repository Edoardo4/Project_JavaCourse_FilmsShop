package it.course.exam.myfilmC3Edoardo.payload.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RentalsNumberInDateRangeRequest {

	@NotNull
	private LocalDate startDate;
	
	@NotNull
	private LocalDate endDate;
}
