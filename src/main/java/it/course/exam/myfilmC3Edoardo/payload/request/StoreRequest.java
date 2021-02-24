package it.course.exam.myfilmC3Edoardo.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class StoreRequest {

	@NotBlank @NotEmpty
	@Size(max=10)
	private String storeId;
	
	@NotBlank @NotEmpty
	@Size(max=50)
	private String storeName;
	
	Set<String>filmId;
}
