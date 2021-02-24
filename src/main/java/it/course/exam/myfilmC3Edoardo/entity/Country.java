package it.course.exam.myfilmC3Edoardo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="COUNTRY")
@Data @AllArgsConstructor @NoArgsConstructor
public class Country {

	@Id
	@Column(name="COUNTRY_ID", columnDefinition="VARCHAR(2)")
	private String countryId;
	
	@Column(name="COUNTRY_NAME", nullable=false, length=40)
	private String countryName;
}
