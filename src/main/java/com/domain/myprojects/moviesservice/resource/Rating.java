package com.domain.myprojects.moviesservice.resource;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RATINGS")
public class Rating extends ResourceSupport {

	@javax.persistence.Id
	@Column(name = "TITLEID")
	private String Id;

	@Column(name = "AVERAGERATING")
	private Double rating;

	public String getMovieId() {
		return Id;
	}

	public Rating setMovieId(String id) {
		this.Id = id;
		return this;
	}

	public Double getRating() {
		return rating;
	}

	public Rating setRating(Double rating) {
		this.rating = rating;
		return this;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	@Column(name = "NUMVOTES")
	private Integer votes;
}
