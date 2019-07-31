package com.domain.myprojects.moviesservice.repository;

import com.domain.myprojects.moviesservice.resource.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, String> {
	@Query("SELECT new Episode(e.tvSeriesId, e.episodeId, e.episodeNum, e.seasonNum)  FROM Episode e WHERE e.tvSeriesId LIKE (:movieId)")
	List<Episode> findAllEpisodes(@Param("movieId") String movieId);

	@Query("SELECT new Episode(e.tvSeriesId, e.episodeId, e.episodeNum, e.seasonNum)  FROM Episode e WHERE e.tvSeriesId LIKE (:movieId) AND e.seasonNum = :num")
	List<Episode> findAllEpisodesBySeason(@Param("movieId") String movieId, @Param("num") Integer num);
}
