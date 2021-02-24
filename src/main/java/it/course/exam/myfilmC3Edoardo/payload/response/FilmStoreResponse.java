package it.course.exam.myfilmC3Edoardo.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmStoreResponse {

	private String film_id;
	
	private String store_name;
}
