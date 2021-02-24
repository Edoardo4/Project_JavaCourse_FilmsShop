package it.course.exam.myfilmC3Edoardo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmC3Edoardo.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, String>{

	boolean existsByStoreName(String stroeName);
}
