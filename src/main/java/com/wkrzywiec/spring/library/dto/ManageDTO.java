package com.wkrzywiec.spring.library.dto;

import java.util.Date;

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
public class ManageDTO {

	private String userFirstName;
	
	private String userLastName;
	
	private int userId;
	
	private String bookTitle;
	
	private int bookId;
	
	private String bookStatus;
	
	private Date dueDate;
	
}
