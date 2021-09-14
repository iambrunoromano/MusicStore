package com.musicstore.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
public class WebUserBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String mail;
	
	private String password;
	
	public WebUserBean() {}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		return Objects.hash(mail, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebUserBean other = (WebUserBean) obj;
		return Objects.equals(mail, other.mail) && Objects.equals(password, other.password);
	}
	@Override
	public String toString() {
		return "WebUserBean [mail=" + mail + ", password=" + password + "]";
	}
	
}
