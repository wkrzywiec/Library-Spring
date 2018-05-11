package com.wkrzywiec.spring.library.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;

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
@Table(name="user_detail")
public class UserDetail {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	@Field
	private String firstName;
	
	@Column(name="last_name")
	@Field
	private String lastName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="birthday")
	private Date birthday;
	
	@Column(name="address")
	private String address;

	@Column(name="postal")
	private String postalCode;
	
	@Column(name="city")
	private String city;
	
	@OneToOne(mappedBy="userDetail",
			cascade=CascadeType.ALL)
	@ContainedIn
	private User user;
	
	public UserDetail(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
