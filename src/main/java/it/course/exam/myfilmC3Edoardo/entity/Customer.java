package it.course.exam.myfilmC3Edoardo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CUSTOMER")
@Data @AllArgsConstructor @NoArgsConstructor
public class Customer {

	@Id
	@Column(name="EMAIL", length=50)
	private String email;
	
	@Column(name="FIRST_NAME", nullable=true, length=45)
	private String firstName;
	
	@Column(name="LAST_NAME", nullable=true, length=45)
	private String lastName;
	
}
