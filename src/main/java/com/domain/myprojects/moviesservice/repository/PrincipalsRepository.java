package com.domain.myprojects.moviesservice.repository;

import com.domain.myprojects.moviesservice.resource.Principals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrincipalsRepository extends JpaRepository<Principals, String> {

	@Query("SELECT new Principals(p.nameId) FROM Principals p WHERE p.titleId LIKE (:movieId)")
	List<Principals> findAllNameIdForThisMovie(@Param("movieId") String movieId);
}
