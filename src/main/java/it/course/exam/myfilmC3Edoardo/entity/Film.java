package it.course.exam.myfilmC3Edoardo.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="FILM")
@Data @AllArgsConstructor @NoArgsConstructor
public class Film {

	@Id
	@Column(name="FILM_ID", columnDefinition="VARCHAR(10)")
	private String filmId;
	
	@Column(name="DESCRIPTION", columnDefinition="TEXT NOT NULL", nullable = false)
	private String description;
	
	@Column(name="RELEASE_YEAR", columnDefinition = "INT(10)", nullable = false)
	private int releaseYear;
	

	@Column(name="TITLE", columnDefinition="VARCHAR(128)", nullable = false)
	private String title;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LANGUAGE_ID", nullable=false)
	private Language language;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COUNTRY_ID", nullable=false)
	private Country country;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="FILM_ACTOR",
		joinColumns = {@JoinColumn(name="FILM_ID", referencedColumnName="FILM_ID")},
		inverseJoinColumns = {@JoinColumn(name="ACTOR_ID", referencedColumnName="ACTOR_ID")}
			)
	private Set<Actor> actors;
	
	
}
