package com.domain.myprojects.moviesservice.controller;

import com.domain.myprojects.moviesservice.resource.Rating;
import com.domain.myprojects.moviesservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Component
public class RatingController {
	static final Integer defaultRating = 0;

	@Autowired
	private RatingService ratingService;

	@ResponseBody
	@RequestMapping(value = {"/movies/{id}/rating","/tvSeries/{id}/rating"}, method = RequestMethod.GET)
	public ResponseEntity<Rating> getMovieRating(@PathVariable String id) {
		Rating rating = ratingService.getMovieRatings(id);
		return rating.getRating() != null ?
				ResponseEntity.ok()
						.eTag(String.valueOf(rating.getRating().hashCode())).body(rating) :
				ResponseEntity.ok()
						.eTag(String.valueOf(defaultRating.hashCode())).body(rating);
	}

	@RequestMapping(value = "/tvSeries/{id}/rating", method = RequestMethod.PUT)
	public ResponseEntity<Rating> updateRating(@RequestBody Rating ratingBody, @PathVariable String id, HttpServletRequest request) {
		Rating rating = ratingService.getMovieRatings(id);

		String etagServer = rating.getRating() != null ? String.valueOf(rating.getRating().hashCode())
				: String.valueOf(defaultRating.hashCode());

		String etagRequest = request.getHeader("If-Match");

		if (etagRequest == null)
			return ResponseEntity.noContent().eTag(etagServer).build();

		if (etagServer.equals(etagRequest)) {
			if (rating != null) {
				rating.setRating(ratingBody.getRating());
				rating.setMovieId(ratingBody.getMovieId());
				rating.setVotes(ratingBody.getVotes());
				ratingService.saveRating(rating);
				return getMovieRating(id);
			} else {
				Rating rating1 = new Rating();
				rating1.setMovieId(id);
				ratingService.saveRating(rating1);
				return getMovieRating(id);
			}
		} else {
			return ResponseEntity.status(412).build();
		}
	}
}
