package it.course.exam.myfilmC3Edoardo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmNrRentResponse {

	private String filmId;
	
	private String  title;
	
	private long  numberOfRents;
}
