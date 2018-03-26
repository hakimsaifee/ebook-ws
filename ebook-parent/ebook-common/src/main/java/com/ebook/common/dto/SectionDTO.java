package com.ebook.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class SectionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	private String sectionHeading;

	private BigDecimal sectionNumber;

	@JsonBackReference
	private ChapterDTO chapter;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSectionHeading(String sectionHeading) {
		this.sectionHeading = sectionHeading;
	}

	public String getSectionHeading() {
		return sectionHeading;
	}

	public BigDecimal getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(BigDecimal sectionNumber) {
		this.sectionNumber = sectionNumber;
	}


	public ChapterDTO getChapter() {
		return chapter;
	}

	public void setPart(ChapterDTO chapter) {
		this.chapter = chapter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
