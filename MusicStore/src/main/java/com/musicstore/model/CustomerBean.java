package com.musicstore.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public @Data @NoArgsConstructor class CustomerBean {

	@Id
	private String mail;
	
	private String name;
	private String surname;
	private String paymentCard;
	private String billingAddress;

}
