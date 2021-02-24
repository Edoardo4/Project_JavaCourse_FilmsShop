package it.course.exam.myfilmC3Edoardo.payload.response;

import it.course.exam.myfilmC3Edoardo.entity.Film;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmResponse {

	//film_id, title, description, release_year, language_name, country_name
	
	private String filmId;
	private String title;
	private String description;
	private int releaseYear;
	private String languageName;
	private String countryName;
		
	public static FilmResponse createFromEntity(Film fl) {
			
			return new FilmResponse(
				fl.getFilmId(),
				fl.getTitle(),
				fl.getDescription(),
				fl.getReleaseYear(),
				fl.getLanguage().getLanguageName(),
				fl.getCountry().getCountryName()
			);
	}

	public FilmResponse(String filmId, String title, String description, int releaseYear) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
	}
	
	
}
