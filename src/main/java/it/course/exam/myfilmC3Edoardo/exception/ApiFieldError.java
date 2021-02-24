package it.course.exam.myfilmC3Edoardo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ApiFieldError {

	private String field;
	private String error;
	private String defaultMessage;
	private Object rejectValue;
	
	
}
