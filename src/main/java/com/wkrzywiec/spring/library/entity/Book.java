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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

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
@Indexed
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="google_id")
	private String googleId;
	
	@Column(name="title")
	@Field
	private String title;
	
	@ManyToMany(fetch=FetchType.EAGER,
				cascade= {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="book_author",
			joinColumns=@JoinColumn(name="book_id"),
			inverseJoinColumns=@JoinColumn(name="author_id"))
	@IndexedEmbedded(depth=1)
	private Set<Author> authors;
	
	@Column(name="publisher")
	@Field
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
	@IndexedEmbedded(depth=1)
	private Set<BookCategory> categories;
	
	@Column(name="rating")
	private double rating;
	
	@Column(name="image_link")
	private String imageLink;
	
	@Column(name="description")
	@Type(type="text")
	@Field
	private String description;
	
	@OneToOne(mappedBy="book",
			cascade= {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH},
			fetch=FetchType.LAZY)
	private Reserved reserved;
	
}
