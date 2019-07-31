package com.domain.myprojects.moviesservice.service;

import com.domain.myprojects.moviesservice.repository.RatingRepository;
import com.domain.myprojects.moviesservice.resource.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {
	@Autowired
	private RatingRepository ratingRepository;

	public Rating getMovieRatings(String id) {
		Optional<Rating> rating= ratingRepository.findById(id);
		 return  rating.isPresent() ? rating.get() : new Rating();
	}

	public Rating saveRating(Rating rating) {
		return ratingRepository.save(rating);
	}
}
