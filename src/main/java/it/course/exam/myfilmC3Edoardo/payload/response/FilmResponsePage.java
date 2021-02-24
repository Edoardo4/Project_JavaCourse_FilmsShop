package it.course.exam.myfilmC3Edoardo.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmResponsePage {

	List<FilmResponse> pageResponsePaged;
	
	private Long totPages;
}
