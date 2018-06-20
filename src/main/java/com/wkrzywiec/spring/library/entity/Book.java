package com.wkrzywiec.spring.library.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name="book")
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="google_id")
	private String googleId;
	
	@Column(name="title")
	private String title;
	
	@ManyToMany(fetch=FetchType.EAGER,
				cascade= {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_author",
			joinColumns=@JoinColumn(name="book_id"),
			inverseJoinColumns=@JoinColumn(name="author_id"))
	private Set<Author> authors;
	
	@Column(name="publisher")
	private String publisher;
	
	@Column(name="published_date")
	private String publishedDate;
	
	@OneToOne(fetch=FetchType.EAGER,
			cascade= {CascadeType.DETACH, CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="isbn_id")
	private Isbn isbn;
	
	@Column(name="page_count")
	private int pageCount;
	
	@ManyToMany(fetch=FetchType.EAGER,
				cascade= {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_bookcategory",
			joinColumns=@JoinColumn(name="book_id"),
			inverseJoinColumns=@JoinColumn(name="bookcategory_id"))
	private Set<BookCategory> categories;
	
	@Column(name="rating")
	private double rating;
	
	@Column(name="image_link")
	private String imageLink;
	
	@Column(name="description")
	@Type(type="text")
	private String description;
	
}
