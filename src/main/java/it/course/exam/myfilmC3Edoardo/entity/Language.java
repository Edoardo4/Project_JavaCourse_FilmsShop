package it.course.exam.myfilmC3Edoardo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="LANGUAGE")
@Data @AllArgsConstructor @NoArgsConstructor
public class Language {

	@Id
	@Column(name="LANGUAGE_ID", columnDefinition="VARCHAR(2)")
	private String langugeId;
	
	@Column(name="LANGUAGE_NAME",columnDefinition="VARCHAR(40)",  nullable=false, unique=true)
	private String languageName;
	
	
}
