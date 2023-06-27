package com.example.ContactManager.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="name should not be blank")
	@Size(min=2,max=30,message="character should be in range of 2 to 30 !!")
	private String name;
	
	private String nickname;
	private String role;
	@Column(unique=true)
	private String email;
	private String password;
	private boolean enable;
	@Column(length=500)
	private String about;
	private String image;
	
	@OneToMany(cascade= CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="user")
	private List<Contact> contacts = new ArrayList<>();
	
	
	public int getId() {
		return id;
	}
	public User() {
		super();
	}
	public User(int id, String name, String nickname, String role, String email, String password, boolean enable,
			String about) {
		super();
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.role = role;
		this.email = email;
		this.password = password;
		this.enable = enable;
		this.about = about;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public boolean getEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", nickname=" + nickname + ", role=" + role + ", email=" + email
				+ ", password=" + password + ", enable=" + enable + ", about=" + about + ", image=" + image
				+ ", contacts=" + contacts + "]";
	}
	
}
