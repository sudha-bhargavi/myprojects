package com.domain.myprojects.moviesservice.controller;

import com.domain.myprojects.moviesservice.AbstractTestSetup;
import com.domain.myprojects.moviesservice.resource.Rating;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class EpisodecontrollerTest extends AbstractTestSetup {
	/**
	 * Test all episodes for a season in a TvSeries
	 */
	@Test   ///tvSeries/{id}/season/{num}/episodes
	public void getAllEpisodesForATvSeriesSeason() throws Exception {
		String uri = "/tvSeries/tt10004066/season/1/episodes";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status1 = mvcResult.getResponse().getStatus();
		assertEquals(200, status1);
		String content = mvcResult.getResponse().getContentAsString();

		assertTrue(content.contains("rating\":{"));
		assertTrue(content.contains("episodeId"));
		assertTrue(content.contains("seasonNum"));
	}

	/**
	 * This test is to get ratings of a season.
	 * Season ratings are calculated based on the episode ratings.
	 * Episode ratings can be updated.So season ratings will change accordingly.
	 * We expect an ETag header If-Match so that we can be make sure latest
	 * episode ratings are used in calculation of season rating
	 * <p>
	 * For tvSeries tt10004066 there is season 1 which has some ratings for the episodes.
	 * Hence we expect the season rating to be average of all episode ratings and is not empty.
	 * To note: Zero rating is different from empty rating
	 *
	 * @throws Exception
	 */
	@Test // /tvSeries/{id}/season/{seasonnum}/rating
	public void getSeasonRatingForATvSeries() throws Exception {
		String uri = "/tvSeries/tt10004066/season/1/rating";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		//If the ETag header is missing we get No content response from server with latest Etag in response.
		int status1 = mvcResult.getResponse().getStatus();
		String etag = mvcResult.getResponse().getHeader("ETag");
		assertEquals(204, status1);
		assertNotNull(etag);

		//Re perform the request including new Etag received from server.
		MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.get(uri)
				.header("If-Match", etag.replace("\"", "").trim())
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status2 = mvcResult1.getResponse().getStatus();
		String etag2 = mvcResult1.getResponse().getHeader("ETag");
		assertEquals(200, status2);
		assertNotNull(etag2);


		String content = mvcResult1.getResponse().getContentAsString();
		assertFalse(content.isEmpty());
	}

	/** TestCase for Scenario 2
	 * This test will update a season`s episode rating .
	 * Hence when we request for Season rating it should now give the updated rating.
	 * When updating a resource we use optimistic way of determining the updates using ETag.
	 *
	 * Test steps:
	 * STEP 1: Get the current value fo the season rating.
	 *         Here we assume the tvSeries Id as tt10004066 and season number as 1
	 *
	 * STEP2: Get the existing rating for this season. Will also give current Server ETag
	 *
	 * STEP3: Update an episode for this season using  Etag
	 *
	 * STEP4: Redo the Get request for the seasons rating.
	 *
	 * Result:
	 *        Rating in STEP2  will be different from rating received in STEP4
	 *        This is because updated episode rating will recalculate seasons rating syncronously.
	 *
	 * User 1 tries to update the resource for first time.
	 * First request to update the resource will return current resources Etag
	 * Then we add the ETag header to the request and update the resource.
	 * ETag can be used to determine optimistic way of achieving concurrency
	 */
	@Test
	public void updateEpisodeRatingsAndReturnLatestSeasonRating() throws Exception {
		// STEP 1 & 2 : Get the existing rating for this movie No Etag provided
		String uri = "/tvSeries/tt10004066/season/1/rating";
		MvcResult mvcResult0 = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		//If the ETag header is missing we get No content response from server with latest Etag in response.
		String etag = mvcResult0.getResponse().getHeader("ETag");

		//Making request with latest Etag
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.header("If-Match", etag.replace("\"", "").trim())
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		String s = mvcResult.getResponse().getContentAsString();
		int status1 = mvcResult.getResponse().getStatus();
		assertEquals(200, status1);
		assertNotNull(s);
		Double existingSeasonRating  = Double.parseDouble(s);

		// STEP 3:
		// Re perform the PUT request to update an episode`s tt10014818 rating, including new Etag received from server.
		// You will receive an updated ETag with updated Rating value for this movie
		String uri2 = "/tvSeries/tt10014818/rating";
		MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get(uri2)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		String tag = mvcResult2.getResponse().getHeader("ETag");
		Double r = getObject(mvcResult2.getResponse().getContentAsString(), Rating.class).getRating();

		MvcResult mvcResult1 = mvc.perform(put(uri2, "tt10014818")
				.content(getJson(new Rating().setRating(r+20.0).setMovieId("tt10014818")))
				.header("If-Match", tag.replace("\"", "").trim())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status2 = mvcResult1.getResponse().getStatus();
		String etag2 = mvcResult1.getResponse().getHeader("ETag");
		assertEquals(200, status2);
		assertNotNull(etag2);

		Rating updateRating = getObject(mvcResult1.getResponse().getContentAsString(), Rating.class);

		assertTrue(updateRating.getRating().equals(r+20));

		assertTrue(updateRating.getMovieId().equalsIgnoreCase("tt10014818"));


		//STEP 4:
		//Now get the updated season rating
		MvcResult mvcResult3 = mvc.perform(MockMvcRequestBuilders.get(uri)
				.header("If-Match", etag.replace("\"", "").trim())
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		//If the ETag header is missing we get No content response from server with latest Etag in response.
		int status3 = mvcResult3.getResponse().getStatus();
		String etag3 = mvcResult3.getResponse().getHeader("ETag");
		assertEquals(412, status3);

		MvcResult mvcResult4 = mvc.perform(MockMvcRequestBuilders.get(uri)
				.header("If-Match", etag3.replace("\"", "").trim())
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		String s3 = mvcResult4.getResponse().getContentAsString();
		assertNotNull(s3);
		Double existingSeasonRating2  = Double.parseDouble(s3);

		//Result :
		assertTrue(existingSeasonRating2 != existingSeasonRating);	
	}

}
