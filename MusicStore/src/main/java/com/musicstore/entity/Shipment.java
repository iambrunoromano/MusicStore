package com.musicstore.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public @Data @NoArgsConstructor class ShipmentBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Timestamp shipDate;
	private Timestamp arriveDate;
	private String shipAddress;
	private double total;
	private int idOrder;

}
