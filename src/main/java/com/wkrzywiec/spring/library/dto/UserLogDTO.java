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
public class UserLogDTO {

	private int id;
	
	private String username;
	
	private Timestamp dated;
	
	private String field;
	
	private String fromValue;
	
	private String toValue;
	
	private String message;
	
	private String changedByUsername;
}
