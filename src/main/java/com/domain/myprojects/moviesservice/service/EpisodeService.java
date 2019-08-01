package com.domain.myprojects.moviesservice.service;

import com.domain.myprojects.moviesservice.repository.EpisodeRepository;
import com.domain.myprojects.moviesservice.resource.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {
	@Autowired
	private EpisodeRepository episodeRepository;

	 public List<Episode> getAllEpisodes(String seriesId) {
	 	return episodeRepository.findAllEpisodes(seriesId);
	 }
	public List<Episode> getEpisodesBySeason(String seriesId, Integer num) {
		return episodeRepository.findAllEpisodesBySeason(seriesId, num);
	}

	public Optional<Episode> findByID(String episodeId) {
	 	return episodeRepository.findById(episodeId);
	}
}
