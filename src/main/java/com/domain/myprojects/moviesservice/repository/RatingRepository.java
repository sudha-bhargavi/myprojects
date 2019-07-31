package com.domain.myprojects.moviesservice.repository;

import com.domain.myprojects.moviesservice.resource.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {

}
