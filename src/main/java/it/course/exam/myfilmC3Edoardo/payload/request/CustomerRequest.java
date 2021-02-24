package it.course.exam.myfilmC3Edoardo.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerRequest {

	@NotBlank @NotEmpty
	@Size(max=50)
	private String email;
	
	@NotBlank @NotEmpty
	@Size(max=45)
	private String firstName;
	
	@NotBlank @NotEmpty
	@Size(max=45)
	private String lastName;
}
