package it.course.exam.myfilmC3Edoardo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.exam.myfilmC3Edoardo.entity.Actor;


@Repository
public interface ActorRepository extends JpaRepository<Actor, String>{

	Set<Actor> findByActorIdIn(Set<String> actorId);
}
