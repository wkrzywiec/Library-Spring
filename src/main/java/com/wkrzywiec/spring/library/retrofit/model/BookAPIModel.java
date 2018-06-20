package com.wkrzywiec.spring.library.retrofit.model;

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
public class BookAPIModel {

	private String id;
	
	private VolumeInfoModel volume;
}
