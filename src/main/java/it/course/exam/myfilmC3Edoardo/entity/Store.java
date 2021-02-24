package it.course.exam.myfilmC3Edoardo.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="STORE")
@Data @AllArgsConstructor @NoArgsConstructor
public class Store {

	@Id
	@Column(name="STORE_ID", columnDefinition="VARCHAR(10)")
	private String storeId;
	
	@Column(name="STORE_NAME", columnDefinition="VARCHAR(50)",nullable=false,unique=true)
	private String storeName;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="INVENTORY",
		joinColumns = {@JoinColumn(name="STORE_ID", referencedColumnName="STORE_ID")},
		inverseJoinColumns = {@JoinColumn(name="FILM_ID", referencedColumnName="FILM_ID")}
			)
	private Set<Film> films;
}
