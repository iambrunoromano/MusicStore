package com.musicstore.model;

import java.util.Objects;

import com.musicstore.model.WebUserBean;

public class ProducerBean extends WebUserBean{

	private String mail;
	private String name;
	private String address;
	
	public ProducerBean() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
		result = prime * result + Objects.hash(address, name);
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
		ProducerBean other = (ProducerBean) obj;
		return Objects.equals(address, other.address) && Objects.equals(name, other.name);
	}
	@Override
	public String toString() {
		return "ProducerBean [name=" + name + ", address=" + address + "]";
	}
	
}
