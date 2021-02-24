package it.course.exam.myfilmC3Edoardo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import it.course.exam.myfilmC3Edoardo.entity.Film;
import it.course.exam.myfilmC3Edoardo.repository.FilmRepository;

@Service
public class FilmService {
	
	@Autowired 	FilmRepository filmRepository;


	public List<Film> findAllOrderByTitleAscPaged(int pageNo, int pageSize, String direction, String sortBy){
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Direction.valueOf(direction.toUpperCase()), sortBy));
		
		Page<Film> pageResult = filmRepository.findAll(paging);
		
		if(pageResult.hasContent()) {
			return pageResult.getContent();
		}else {
			return new ArrayList<Film>();
		}

	}
}
