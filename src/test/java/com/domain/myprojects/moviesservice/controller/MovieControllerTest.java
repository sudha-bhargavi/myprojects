package com.domain.myprojects.moviesservice.controller;

import com.domain.myprojects.moviesservice.AbstractTestSetup;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Test {@link com.domain.myprojects.moviesservice.controller.MovieController} methods */
public class MovieControllerTest extends AbstractTestSetup {
	/** Test GET ALL 2017 movies along with movie titles, year, cast info */
	@Test
	public void getMoviesList() throws Exception {
		String uri = "/movies";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();

		assertTrue(content.contains("movieList"));
		assertTrue(content.contains("cast info"));
		assertTrue(content.contains("movie Rating"));
	}

	/** Test GET a particular movie with an ID*/
	@Test
	public void getMovie() throws Exception {
		String uri = "/movies/tt0100275";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.ALL)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();

		assertTrue(content.contains("movieId\":\"tt0100275"));
		assertTrue(content.contains("titleType\":\"movie\""));
	}

	/** Test GET ALL TVSeries along with ratings, year, and season ratings info */
	@Test
	public void getTvSeriesList() throws Exception {
		String uri = "/tvSeries";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();

		assertTrue(content.contains("movieList"));
		assertTrue(content.contains("\"titleType\":\"tvSeries\""));
		assertTrue(content.contains("season Rating\""));
	}
}
