package com.wkrzywiec.spring.library.dto;

import java.math.BigDecimal;
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
public class PenaltyDTO {

	private String bookTitle;
	
	private int bookId;
	
	private Date dueDate;
	
	private Date returnDate;
	
	private int days;
	
	private BigDecimal penalty;
}
