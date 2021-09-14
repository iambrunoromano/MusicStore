package com.musicstore.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdminBean {

	@Id
	private String mail;
	
	private String name;
	private String surname;
	private int phoneNumber;
	
	public AdminBean() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(mail, name, phoneNumber, surname);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminBean other = (AdminBean) obj;
		return Objects.equals(mail, other.mail) && Objects.equals(name, other.name) && phoneNumber == other.phoneNumber
				&& Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "AdminBean [mail=" + mail + ", name=" + name + ", surname=" + surname + ", phoneNumber=" + phoneNumber
				+ "]";
	}

	
}
