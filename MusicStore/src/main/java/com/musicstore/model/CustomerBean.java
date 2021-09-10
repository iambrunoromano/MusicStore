package com.musicstore.model;

import java.util.Objects;

import com.musicstore.model.WebUserBean;

public class CustomerBean extends WebUserBean{

	private String name;
	private String surname;
	private String address;
	private String paymentCard;
	private String billingAddress;
	
	public CustomerBean() {}
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPaymentCard() {
		return paymentCard;
	}
	public void setPaymentCard(String paymentCard) {
		this.paymentCard = paymentCard;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(address, billingAddress, name, paymentCard, surname);
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
		CustomerBean other = (CustomerBean) obj;
		return Objects.equals(address, other.address) && Objects.equals(billingAddress, other.billingAddress)
				&& Objects.equals(name, other.name) && Objects.equals(paymentCard, other.paymentCard)
				&& Objects.equals(surname, other.surname);
	}
	@Override
	public String toString() {
		return "CustomerBean [name=" + name + ", surname=" + surname + ", address=" + address + ", paymentCard="
				+ paymentCard + ", billingAddress=" + billingAddress + "]";
	}
	
}
