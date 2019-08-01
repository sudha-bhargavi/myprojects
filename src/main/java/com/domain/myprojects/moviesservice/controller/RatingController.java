package com.domain.myprojects.moviesservice.controller;

import com.domain.myprojects.moviesservice.common.ResourceNotFoundException;
import com.domain.myprojects.moviesservice.resource.Rating;
import com.domain.myprojects.moviesservice.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	public static final Logger logger = LoggerFactory.getLogger(MovieController.class);
	static final Integer defaultRating = 0;

	@Autowired
	private RatingService ratingService;

	/**
	 * Get a movies rating
	 * @param id
	 * @return Rating
	 */
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

	/**
	 * Update a rating for a movie
	 * @param ratingBody
	 * @param id
	 * @param request
	 * @return update Rating
	 */
	@RequestMapping(value = "/tvSeries/{id}/rating", method = RequestMethod.PUT)
	public ResponseEntity<Rating> updateRating(@RequestBody Rating ratingBody, @PathVariable String id, HttpServletRequest request) {
		logger.info("updating rating for episode {}", id);
		Rating rating = ratingService.getMovieRatings(id);

		String etagServer = rating.getRating() != null ? String.valueOf(rating.getRating().hashCode())
				: String.valueOf(defaultRating.hashCode());

		String etagRequest = request.getHeader("If-Match");

		if (etagRequest == null) {
			logger.debug("ETag missing in the request", request);
			return ResponseEntity.noContent().eTag(etagServer).build();
		}

		if (etagServer.equals(etagRequest)) {
			logger.debug("ETag are matching hence updating the resource", request);
			if (rating != null) {
				logger.debug("Found resource , uptaing it {} ", id);
				rating.setRating(ratingBody.getRating());
				rating.setMovieId(ratingBody.getMovieId());
				rating.setVotes(ratingBody.getVotes());
				ratingService.saveRating(rating);
				return getMovieRating(id);
			} else {
				logger.debug("Resource not found , creating it ", id);
				throw new ResourceNotFoundException(id);
			}
		} else {
			return ResponseEntity.status(412).build();
		}
	}
}