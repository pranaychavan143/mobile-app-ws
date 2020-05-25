package com.tst.mobileappws.io.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -9060447717593224201L;

	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private String userId;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false, length = 100,unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String encryptedPassword;
	private String emailVerificationTocken;

	@Column(nullable = false)
	private Boolean emailVerificationStatus = false;

	@OneToMany(mappedBy = "userDetails", cascade =CascadeType.ALL)
	private List<AddressEntity> address;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationTocken() {
		return emailVerificationTocken;
	}

	public void setEmailVerificationTocken(String emailVerificationTocken) {
		this.emailVerificationTocken = emailVerificationTocken;
	}

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {

		emailVerificationStatus = false;
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public List<AddressEntity> getAddress() {
		return address;
	}

	public void setAddress(List<AddressEntity> address) {
		this.address = address;
	}
}
