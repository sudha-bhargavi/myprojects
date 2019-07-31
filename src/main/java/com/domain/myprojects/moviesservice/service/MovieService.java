package com.domain.myprojects.moviesservice.service;

import com.domain.myprojects.moviesservice.repository.MovieRepository;
import com.domain.myprojects.moviesservice.repository.PrincipalsRepository;
import com.domain.myprojects.moviesservice.resource.Movie;
import com.domain.myprojects.moviesservice.resource.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;

	public Page<Movie> getAllMovies(Pageable pageable, String year, String type) {
		return movieRepository.findAllByStartYearAndTitleType(year,type, pageable);
	}

	public Optional<Movie> getMovie(String id) {
		return movieRepository.findById(id);
	}

}
