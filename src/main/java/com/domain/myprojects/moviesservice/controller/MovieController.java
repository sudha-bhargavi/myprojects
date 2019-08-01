package com.domain.myprojects.moviesservice.controller;

import com.domain.myprojects.moviesservice.common.ResourceNotFoundException;
import com.domain.myprojects.moviesservice.resource.Episode;
import com.domain.myprojects.moviesservice.resource.Movie;
import com.domain.myprojects.moviesservice.resource.Principals;
import com.domain.myprojects.moviesservice.resource.Rating;
import com.domain.myprojects.moviesservice.service.CrewAndCastService;
import com.domain.myprojects.moviesservice.service.EpisodeService;
import com.domain.myprojects.moviesservice.service.MovieService;
import com.domain.myprojects.moviesservice.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Component
public class MovieController {
	public static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	private MovieService movieService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private CrewAndCastService crewAndCastService;

	@Autowired
	private EpisodeService episodeService;

	/**
	 * Get a movie by its Id
	 * @param id
	 * @return Movie
	 */
	@RequestMapping(value = {"/movies/{id}", "/tvSeries/{id}"})
	@ResponseBody
	public ResponseEntity<Movie> getMovie(@PathVariable String id) {
		Optional<Movie> movie = movieService.getMovie(id);
		if (!movie.isPresent()) {
			logger.error("Movie with id {} not found.", id);
			throw new ResourceNotFoundException("Invalid employee id : " + id);
		}
		addRatingLink(movie.get());
		addCastAdnCrew(movie.get());

		return new ResponseEntity(movie.get(), HttpStatus.OK);
	}

	private void addRatingLink(Movie m) {
		logger.info("Adding rating link for movie {}", m);
		Link selfLink;
		if(m.getTitleType().equalsIgnoreCase("tvSeries"))
			selfLink = linkTo(MovieController.class).slash("tvSeries/" + m.getMovieId()).withSelfRel();
		else
			selfLink = linkTo(MovieController.class).slash("movies/" + m.getMovieId()).withSelfRel();
		m.add(selfLink);
		Rating rating = ratingService.getMovieRatings(m.getMovieId());
		if (rating.getRating() != null) {
			m.setMovieRating(rating);
			Link ratingsLink = linkTo(methodOn(RatingController.class).getMovieRating(m.getMovieId())).withRel(m.getTitleType()+" Rating");
			m.add(ratingsLink);
		}
	}

	private void addCastAdnCrew(Movie m) {
		List<Principals> principals = getPeopleIdsForTheMovie(m.getMovieId());

		if (!principals.isEmpty()) {
			for (Principals principals1 : principals) {
				String nameId = principals1.getNameId();
				Link castInfoLink = linkTo(methodOn(CrewAndCastController.class).getCrewAndCastInfo(nameId)).withRel("movie cast info");
				m.add(castInfoLink);
			}
		}
	}

	private void addSeasonRatingLink(Movie m, HttpServletRequest request) {
		Map<Integer, List<Integer>> seasonEpisodeMap = new HashMap<>();
		if (m.getTitleType().equalsIgnoreCase("tvSeries")) {
			List<Episode> episodes = episodeService.getAllEpisodes(m.getMovieId());

			for (Episode ep : episodes) {
				if (seasonEpisodeMap.get(ep.getSeasonNum()) != null)
					seasonEpisodeMap.get(ep.getSeasonNum()).add(ep.getEpisodeNum());
				else {
					List l = new ArrayList<>();
					l.add(ep.getEpisodeNum());
					seasonEpisodeMap.put(ep.getSeasonNum(), l);
				}
			}
			seasonEpisodeMap.entrySet().stream().forEach(e -> {
				m.add(linkTo(methodOn(EpisodeController.class).getSeasonRating(m.getMovieId(), e.getKey(), request)).withRel("season Rating"));
			});
		}
	}

	/**
	 * Get all the TVSeries for 2017 , including its cast , ratings and seasons ratings
	 * @param pageable
	 * @param assembler
	 * @param request
	 * @return Page of TVSeries
	 */
	@RequestMapping(value = "/tvSeries", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PagedResources<Movie>> getAllTvSeries(Pageable pageable, PagedResourcesAssembler assembler, HttpServletRequest request) {
		PagedResources<Movie> pr;
		Page<Movie> series = movieService.getAllMovies(pageable, "2017", "tvSeries");
		for (Movie m : series) {
			addRatingLink(m);
			addSeasonRatingLink(m, request);
		}
		pr = assembler.toResource(series, linkTo(MovieController.class).slash("/tvSeries").withSelfRel());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Link", createLinkMetadata(pr));
		responseHeaders.add("Cache-Control", "max-age=3600");
		logger.info("get tvSseries request success");
		return new ResponseEntity<>(assembler.toResource(series, linkTo(MovieController.class).slash("/tvSeries").withSelfRel()), responseHeaders, HttpStatus.OK);
	}

	/**
	 * Get all the Movies for 2017 , including its cast and ratings
	 * @param pageable
	 * @param assembler
	 * @param request
	 * @return Page of TVSeries
	 */
	@RequestMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<PagedResources<Movie>> getAllMovies(Pageable pageable, PagedResourcesAssembler assembler, HttpServletRequest request) {
		PagedResources<Movie> pr;
		Page<Movie> movies = movieService.getAllMovies(pageable, "2017", "movie");
		for (Movie m : movies) {
			addRatingLink(m);
			addCastAdnCrew(m);
		}

		pr = assembler.toResource(movies, linkTo(MovieController.class).slash("/movies").withSelfRel());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Link", createLinkMetadata(pr));
		responseHeaders.add("Cache-Control", "max-age=3600");
		logger.info("get movies request success");
		return new ResponseEntity<>(assembler.toResource(movies, linkTo(MovieController.class).slash("/movies").withSelfRel()), responseHeaders, HttpStatus.OK);
	}

	private String createLinkMetadata(PagedResources<Movie> pr) {
		final StringBuilder linkHeader = new StringBuilder();
		if (!pr.getLinks().isEmpty()) {
			if (!pr.getLinks("first").isEmpty())
				linkHeader.append(buildLinkMeta(pr.getLinks("first").get(0).getHref(), "first"));
			linkHeader.append(", ");
			if (!pr.getLinks("next").isEmpty())
				linkHeader.append(buildLinkMeta(pr.getLinks("next").get(0).getHref(), "next"));
		}
		return linkHeader.toString();
	}

	public static String buildLinkMeta(final String uri, final String rel) {
		return "<" + uri + ">; rel=\"" + rel + "\"";
	}

	private List<Principals> getPeopleIdsForTheMovie(String movieId) {
		return crewAndCastService.getPeopleInTheMovie(movieId);
	}
}