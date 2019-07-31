package com.domain.myprojects.moviesservice.resource;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CREWANDCAST")
public class CrewAndCast extends ResourceSupport {
	public String getNameId() {
		return Id;
	}

	public void setNameId(String id) {
		Id = id;
	}

	public String getPrimayName() {
		return primayName;
	}

	public void setPrimayName(String primayName) {
		this.primayName = primayName;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getDeathYear() {
		return deathYear;
	}

	public void setDeathYear(String deathYear) {
		this.deathYear = deathYear;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getKnownFor() {
		return knownFor;
	}

	public void setKnownFor(String knownFor) {
		this.knownFor = knownFor;
	}

	@javax.persistence.Id
	@Column(name = "NAMEID")
	private String Id;

	@Column(name = "PRIMARY_NAME")
	private String primayName;


	@Column(name = "BIRTHYEAR")
	private String birthYear;

	@Column(name = "DEATHYEAR")
	private String deathYear;

	@Column(name = "PROFESSION")
	private String profession;

	@Column(name = "TITLES")
	private String knownFor;
}
