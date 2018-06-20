package com.wkrzywiec.spring.library.retrofit.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString()
@NoArgsConstructor
public class VolumeInfoModel {

	private String title;
	
	private List<String> authors;
	
	private String publisher;
	
	private String publishedDate;
	
	private String description;
	
	private List<IsbnAPIModel> industryIdentifiers;
	
	private int pageCount;
	
	private List<String> categories;
	
	private double averageRating;
	
	private ImageLinksAPIModel imageLinks;
}
