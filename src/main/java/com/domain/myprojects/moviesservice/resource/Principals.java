package com.domain.myprojects.moviesservice.resource;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRINCIPALS")
public class Principals extends ResourceSupport implements IHasMedia {
@Id
	@Column(name = "TITLEID")
	private String titleId;

	@Override
	public boolean isMedia() {
		return false;
	}

	public String getTitleId() {
		return titleId;
	}

	public String getNameId() {
		return nameId;
	}

	public String getCategory() {
		return Category;
	}

	@Column(name = "NAMEID")
	private String nameId;

	@Column(name = "CATEGORY")
	private String Category;

	public Principals(String nameId) {
		this.nameId = nameId;
	}
}
