package it.course.exam.myfilmC3Edoardo.payload.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmByActorsRequest {

	Set<String>actorsId;
}
