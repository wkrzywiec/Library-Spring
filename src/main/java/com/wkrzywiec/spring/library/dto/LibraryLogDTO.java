package com.wkrzywiec.spring.library.dto;

import java.sql.Timestamp;

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
public class LibraryLogDTO {

	private int id;
	
	private int bookId;
	
	private String bookTitle;
	
	private int userId;
	
	private String userFirstName;
	
	private String userLastName;
	
	private String username;
	
	private String message;
	
	private Timestamp dated;
	
	private String changedByUsername;
}
