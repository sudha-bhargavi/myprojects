package com.domain.myprojects.moviesservice.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EPISODE")
public class Episode {
	public Episode setRating(Rating rating) {
		this.rating = rating;
		return this;
	}

	public Rating getRating() {
		return rating;
	}

	@Transient
	private Rating rating;

	public Episode(String tvSeriesId, String episodeId, Integer episodeNum, Integer seasonNum) {
		this.episodeId = episodeId;
		this.tvSeriesId = tvSeriesId;
		this.episodeNum = episodeNum;
		this.seasonNum = seasonNum;
	}

	public String getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	public String getTvSeriesId() {
		return tvSeriesId;
	}

	public void setTvSeriesId(String tvSeriesId) {
		this.tvSeriesId = tvSeriesId;
	}

	public Integer getSeasonNum() {
		return seasonNum;
	}

	public void setSeasonNum(Integer seasonNum) {
		this.seasonNum = seasonNum;
	}

	public Integer getEpisodeNum() {
		return episodeNum;
	}

	public void setEpisodeNum(Integer episodeNum) {
		this.episodeNum = episodeNum;
	}

	@Id
	@Column(name = "EPISODEID")
	private String episodeId;

	@Column(name = "SERIESID")
	private String tvSeriesId;

	@Column(name = "SEASONNUM")
	private Integer seasonNum;

	@Column(name = "EPISODENUM")
	private Integer episodeNum;

}
