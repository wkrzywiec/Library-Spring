package com.wkrzywiec.spring.library.dto;

import java.util.List;

import javax.persistence.Column;

import org.hibernate.annotations.Type;

import com.wkrzywiec.spring.library.entity.Author;

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
public class BookDTO {
	
	private int id;

	private String googleId;
	
	private String title;
	
	private List<String> authors;
	
	private String publisher;
	
	private String publishedDate;
	
	private String isbn_10;
	
	private String isbn_13;
	
	private int pageCount;
	
	private List<String> bookCategories;
	
	private double rating;
	
	private String imageLink;
	
	private String description;
	
	private String status;
}
