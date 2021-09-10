package com.musicstore.model;

import java.util.Objects;

import com.musicstore.model.WebUserBean;

public class AdminBean extends WebUserBean{

	private String name;
	private String surname;
	private int phoneNumber;
	private String mail;
	
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
		result = prime * result + Objects.hash(name, phoneNumber, surname);
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
		return Objects.equals(name, other.name) && phoneNumber == other.phoneNumber
				&& Objects.equals(surname, other.surname);
	}
	
	@Override
	public String toString() {
		return "AdminBean [name=" + name + ", surname=" + surname + ", phoneNumber=" + phoneNumber + "]";
	}
}
