package com.domain.myprojects.moviesservice.controller;

import com.domain.myprojects.moviesservice.resource.Episode;
import com.domain.myprojects.moviesservice.resource.Movie;
import com.domain.myprojects.moviesservice.resource.Rating;
import com.domain.myprojects.moviesservice.service.EpisodeService;
import com.domain.myprojects.moviesservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.OptionalDouble;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Component
public class EpisodeController {
	@Autowired
	private RatingService ratingService;

	@Autowired
	private EpisodeService episodeService;

	@ResponseBody
	@RequestMapping(value = "/tvSeries/{id}/season/{seasonnum}/rating", method = RequestMethod.GET)
	public ResponseEntity<Double> getSeasonRating(@PathVariable String id, @PathVariable Integer seasonnum, HttpServletRequest request) {
		List<Episode> episodes = episodeService.getEpisodesBySeason(id, seasonnum);

		String etagServer = calulateETag(episodes);
		String etagRequest = request.getHeader("If-Match");

		if(etagRequest == null)
			return ResponseEntity.noContent().eTag(etagServer).build();

		if (etagServer.equals(etagRequest)) {
			OptionalDouble d = calcRating(episodes);
			Double rating = d.isPresent() ? d.getAsDouble() : 0;
			return ResponseEntity.ok().eTag(etagServer).body(rating);
		}

		return ResponseEntity.status(412).eTag(etagServer).build();
	}

	@ResponseBody
	@RequestMapping(value = "/tvSeries/{id}/season/{num}/episodes", method = RequestMethod.GET)
	public ResponseEntity<List<Episode>> getEpisodesForSeason(@PathVariable String id, @PathVariable Integer num) {
		List<Episode> episodeList = episodeService.getEpisodesBySeason(id, num);
		episodeList
				.stream()
				.filter(episode -> episode != null)
				.forEach(episode -> {
					Rating r = ratingService.getMovieRatings(episode.getEpisodeId());
					episode.setRating(r);
				});
		return ResponseEntity.ok().body(episodeList);
	}

	private String calulateETag(List<Episode> episodes) {
		return String.valueOf(String.valueOf(episodes.stream().map(episode -> ratingService.getMovieRatings(episode.getEpisodeId()))
				.filter(rating -> rating.getRating()!= null)
				.mapToDouble(rating -> rating.getRating())
				.sum()).hashCode());
	}

	private OptionalDouble calcRating(List<Episode> episodes) {
		return episodes.stream().map(episode -> ratingService.getMovieRatings(episode.getEpisodeId()))
				.filter(rating -> rating.getRating() != null)
				.mapToDouble(rating -> rating.getRating())
				.average();
	}

	private void addRatingLink(Movie m) {
		Link selfLink = linkTo(MovieController.class).slash("tvSeries/" + m.getMovieId()).withSelfRel();
		m.add(selfLink);
		Rating rating = ratingService.getMovieRatings(m.getMovieId());
		if (rating.getRating() != null) {
			m.setMovieRating(rating);
			Link ratingsLink = linkTo(methodOn(RatingController.class).getMovieRating(m.getMovieId())).withRel("tvSeries rating");
			m.add(ratingsLink);
		}
	}
}
