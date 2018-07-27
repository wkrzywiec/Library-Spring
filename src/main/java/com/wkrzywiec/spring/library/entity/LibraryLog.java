package com.wkrzywiec.spring.library.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="library_logs")
public class LibraryLog {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="level")
	private String level;
	
	@Column(name="message")
	private String message;
	
	@ManyToOne (fetch=FetchType.EAGER)
	@JoinColumn(name="book_id", insertable=false, updatable=false)
	private Book book;
	
	@ManyToOne (fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	@Column(name="dated")
	private Timestamp dated;

	@Column(name="changed_by_username")
	private String changedByUsername;
}
