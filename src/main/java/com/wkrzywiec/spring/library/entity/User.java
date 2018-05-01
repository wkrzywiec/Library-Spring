package com.wkrzywiec.spring.library.entity;

import java.util.HashSet;
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

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Table(name="user")
@Indexed
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="username", unique=true)
	@Field
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email", unique=true)
	@Field
	private String email;
	
	@Column(name="enable")
	private boolean enable;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="user_detail_id")
	@IndexedEmbedded(depth=1)
	private UserDetail userDetail;

	@ManyToMany(fetch=FetchType.EAGER,
				cascade= {CascadeType.DETACH, CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
				name="user_role",
				joinColumns=@JoinColumn(name="user_id"),
				inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	public User(){
		
	}
	
	public User(String username, String password, String email, boolean enable, UserDetail userDetail) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.enable = enable;
		this.userDetail = userDetail;
	}
	
	public void addRole(Role role){
		
		if (roles == null){
			roles = new HashSet<Role>();
		}
		
		roles.add(role);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", enable=" + enable + ", userDetail="
				+ userDetail + ", roles=" + roles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enable ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((userDetail == null) ? 0 : userDetail.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enable != other.enable)
			return false;
		if (id != other.id)
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (userDetail == null) {
			if (other.userDetail != null)
				return false;
		} else if (!userDetail.equals(other.userDetail))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
