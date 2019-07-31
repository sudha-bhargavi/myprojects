package com.domain.myprojects.moviesservice.service;

import com.domain.myprojects.moviesservice.repository.CrewAndCastRepository;
import com.domain.myprojects.moviesservice.repository.PrincipalsRepository;
import com.domain.myprojects.moviesservice.resource.CrewAndCast;
import com.domain.myprojects.moviesservice.resource.Principals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CrewAndCastService {
	@Autowired
	private CrewAndCastRepository crewAndCastRepository;

	@Autowired
	private PrincipalsRepository principalsRepository;

	public List<CrewAndCast> getCastInfo(String nameId) {
		List<String> nameIDs = new ArrayList<>();
		nameIDs.add(nameId);
		return crewAndCastRepository.findAllById(nameIDs);
	}

	public List<Principals> getPeopleInTheMovie(String movieId) {
		try {
		return principalsRepository.findAllNameIdForThisMovie(movieId);
		} catch (Exception e) {

		}
		return Collections.EMPTY_LIST;
	}
}

