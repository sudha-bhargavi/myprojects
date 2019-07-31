package com.domain.myprojects.moviesservice.repository;

import com.domain.myprojects.moviesservice.resource.Movie;
import com.domain.myprojects.moviesservice.resource.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

	Page<Movie> findAllByStartYearAndTitleType(@Param("startYear") String startYear, @Param("titleType") String titletype, Pageable pageable);

	@Query("SELECT rating FROM Rating rating WHERE rating.Id LIKE (:movieId)")
	Rating getMovieRating(@Param("movieId") String movieId);

}
