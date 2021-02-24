package it.course.exam.myfilmC3Edoardo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmRentCustomerResponse {

	private String filmId;
	
	private String titleFilm;
}
