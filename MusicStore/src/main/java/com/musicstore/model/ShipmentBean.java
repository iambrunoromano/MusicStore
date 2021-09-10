package com.musicstore.model;

import java.sql.Timestamp;
import java.util.Objects;

public class ShipmentBean {
	
	private int id;
	private Timestamp shipmentDate;
	private Timestamp arriveDate;
	private String shipAddress;
	private double total;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(Timestamp shipmentDate) {
		this.shipmentDate = shipmentDate;
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
	@Override
	public int hashCode() {
		return Objects.hash(arriveDate, id, shipAddress, shipmentDate, total);
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
		return Objects.equals(arriveDate, other.arriveDate) && id == other.id
				&& Objects.equals(shipAddress, other.shipAddress) && Objects.equals(shipmentDate, other.shipmentDate)
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
	}
	@Override
	public String toString() {
		return "ShipmentBean [id=" + id + ", shipmentDate=" + shipmentDate + ", arriveDate=" + arriveDate
				+ ", shipAddress=" + shipAddress + ", total=" + total + "]";
	}	

}
