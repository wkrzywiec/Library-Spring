package com.wkrzywiec.spring.library.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.wkrzywiec.spring.library.validation.EmailValid;
import com.wkrzywiec.spring.library.validation.FieldMatch;
import com.wkrzywiec.spring.library.validation.PasswordValid;
import com.wkrzywiec.spring.library.validation.PhoneNumber;
import com.wkrzywiec.spring.library.validation.PostalCode;


@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match!")
})
public class UserDTO {

	//@UniqueUsername
	@NotEmpty
	@Size(min=5,max=45)
	private String username;
	
	@NotEmpty
	@Size(min=2,max=60)
	private String firstName;
	
	@NotEmpty
	@Size(min=2,max=60)
	private String lastName;
	
	@PasswordValid
	@NotEmpty
	private String password;
	
	@PasswordValid
	@NotEmpty
	private String confirmPassword;
	
	//@UniqueEmail
	@NotEmpty
	@EmailValid
	private String email;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Past
	@NotNull
	private Date birthday;
	
	@NotEmpty
	@PhoneNumber
	private String phone;
	
	@NotEmpty
	@Size(min=2,max=60)
	private String address;
	
	@NotEmpty
	@PostalCode
	private String postalCode;

	@NotEmpty
	@Size(min=2,max=60)
	private String city;
	
	public UserDTO(){
		
	}
	
	public UserDTO(	String username, String firstName, String lastName,
					String password, String confirmPassword, String email) {
		
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", confirmPassword=" + confirmPassword + ", email=" + email + ", birthday=" + birthday
				+ ", phone=" + phone + ", address=" + address + ", postalCode=" + postalCode + ", city=" + city + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 13;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
