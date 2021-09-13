package com.musicstore.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ShipmentBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Timestamp shipDate;
	private Timestamp arriveDate;
	private String shipAddress;
	private double total;
	private int idOrder;
	
	public ShipmentBean() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getShipDate() {
		return shipDate;
	}

	public void setShipDate(Timestamp shipDate) {
		this.shipDate = shipDate;
	}

	public Timestamp getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Timestamp arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arriveDate, id, idOrder, shipAddress, shipDate, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShipmentBean other = (ShipmentBean) obj;
		return Objects.equals(arriveDate, other.arriveDate) && id == other.id && idOrder == other.idOrder
				&& Objects.equals(shipAddress, other.shipAddress) && Objects.equals(shipDate, other.shipDate)
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
	}

	@Override
	public String toString() {
		return "ShipmentBean [id=" + id + ", shipDate=" + shipDate + ", arriveDate=" + arriveDate + ", shipAddress="
				+ shipAddress + ", total=" + total + ", idOrder=" + idOrder + "]";
	}
	
	

}
