package com.domain.myprojects.moviesservice.repository;

import com.domain.myprojects.moviesservice.resource.CrewAndCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewAndCastRepository extends JpaRepository<CrewAndCast, String> {

}
