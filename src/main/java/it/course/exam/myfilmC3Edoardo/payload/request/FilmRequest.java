package it.course.exam.myfilmC3Edoardo.payload.request;

import java.util.HashSet;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @AllArgsConstructor @NoArgsConstructor
public class FilmRequest {

	@NotBlank @NotEmpty
	@Size(max=10)
	private String filmId;
	
	@NotBlank @NotEmpty
	private String description;
	
	@NotNull
	private int releaseYear;
	
	@NotBlank @NotEmpty
	@Size(max=128)
	private String title;
	
	@NotBlank @NotEmpty
	@Size(max=2)
	private String languageId;

	@NotBlank @NotEmpty
	@Size(max=2)
	private String countryId;

	Set<String> actors = new HashSet<String>();

}
