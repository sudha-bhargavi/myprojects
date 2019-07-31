package com.domain.myprojects.moviesservice.resource;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MOVIE")
public class Movie extends ResourceSupport implements IHasMedia {
	@Id
	@Column(name = "TITLEID")
	private String movieId;


	@Column(name = "TITLETYPE")
	private String titleType;

	@Column(name = "ORIGINALTITLE")
	private String originalTitlel;

	@Column(name = "PRIMARYTITLE")
	private String primaryTitle;


	@Column(name = "STARTYEAR")
	private String startYear;


	@Column(name = "ENDYEAR")
	private String endYear;


	@Column(name = "GENERES")
	private String genres;


	@Column(name = "ISADULT")
	private boolean isAdult;


	@Column(name = "RUNTIME_MINUTES")
	private int runtimeMinutes;

	public Rating getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(Rating movieRating) {
		this.movieRating = movieRating;
	}

	@Transient
	private Rating movieRating;

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getTitleType() {
		return titleType;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	public String getOriginalTitlel() {
		return originalTitlel;
	}

	public void setOriginalTitlel(String originalTitlel) {
		this.originalTitlel = originalTitlel;
	}

	public String getPrimaryTitle() {
		return primaryTitle;
	}

	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public boolean isAdult() {
		return isAdult;
	}

	public void setAdult(boolean adult) {
		isAdult = adult;
	}

	public int getRuntimeMinutes() {
		return runtimeMinutes;
	}

	public void setRuntimeMinutes(int runtimeMinutes) {
		this.runtimeMinutes = runtimeMinutes;
	}

	@Override
	public boolean isMedia() {
		return true;
	}
}
