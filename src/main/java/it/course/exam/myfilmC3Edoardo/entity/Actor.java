package it.course.exam.myfilmC3Edoardo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ACTOR")
@Data @AllArgsConstructor @NoArgsConstructor
public class Actor {

	@Id
	@Column(name="ACTOR_ID", columnDefinition="VARCHAR(10)")
	private String actorId;
	
	@Column(name="FIRST_NAME", nullable=false, length=45)
	private String firstName;
	
	@Column(name="LAST_NAME", nullable=false, length=45)
	private String lastName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COUNTRY_ID", nullable=false)
	private Country country;
}
