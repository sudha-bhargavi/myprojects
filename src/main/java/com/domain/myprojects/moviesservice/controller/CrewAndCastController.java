package com.domain.myprojects.moviesservice.controller;

import com.domain.myprojects.moviesservice.resource.CrewAndCast;
import com.domain.myprojects.moviesservice.service.CrewAndCastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Component
public class CrewAndCastController {
	@Autowired
	private CrewAndCastService crewAndCastService;

	@ResponseBody
	@RequestMapping("/movies/{id}/cast")
	public List<CrewAndCast> getCrewAndCastInfo(@PathVariable String id) {
		return crewAndCastService.getCastInfo(id);
	}
}
