package com.musicstore.utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Map;

import com.musicstore.model.AdminBean;
import com.musicstore.model.CartBean;
import com.musicstore.model.CategoryBean;
import com.musicstore.model.CustomerBean;
import com.musicstore.model.OrderBean;
import com.musicstore.model.ProducerBean;
import com.musicstore.model.ProductBean;
import com.musicstore.model.ShipmentBean;
import com.musicstore.model.WebUserBean;

import java.time.OffsetDateTime;

public abstract class Utility {
	
	private static Timestamp stringtoDate(String s) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date date = dateFormat.parse(s);
			return new Timestamp(date.getTime());
			}
		catch (ParseException e) {
			System.out.println("Exception :" + e);
	        return null;
	      }
	    
	}

	public static AdminBean adminDeMap(Map<String, String> map){
		AdminBean b = new AdminBean();
		b.setMail(map.get("mail"));
		b.setName(map.get("name"));
		b.setSurname(map.get("surname"));
		b.setPhoneNumber(Integer.parseInt(map.get("phoneNumber")));
		return b; 
	}
	
	public static CartBean cartDeMap(Map<String, String> map){
		CartBean b = new CartBean();
		b.setId(Integer.parseInt(map.get("id")));
		b.setDate(stringtoDate(map.get("date")));
		b.setMail(map.get("mail"));
		b.setProductId(Integer.parseInt(map.get("productId")));
		return b; 
	}
	
	public static CategoryBean categoryDeMap(Map<String, String> map){
		CategoryBean b = new CategoryBean();
		b.setId(Integer.parseInt(map.get("id")));
		b.setName(map.get("name"));
		b.setParent(Integer.parseInt(map.get("parent")));
		return b; 
	}
	
	public static CustomerBean customerDeMap(Map<String, String> map){
		CustomerBean b = new CustomerBean();
		b.setMail(map.get("mail"));
		b.setName(map.get("name"));
		b.setSurname(map.get("surname"));
		b.setPaymentCard(map.get("paymentCard"));
		b.setBillingAddress(map.get("billingAddress"));
		return b; 
	}
	
	public static OrderBean orderDeMap(Map<String, String> map){
		OrderBean b = new OrderBean();
		b.setId(Integer.parseInt(map.get("id")));
		b.setMail(map.get("mail"));
		b.setDate(stringtoDate(map.get("date")));
		b.setTotal(Double.parseDouble(map.get("total")));
		return b; 
	}
	
	public static ProducerBean producerDeMap(Map<String, String> map){
		ProducerBean b = new ProducerBean();
		b.setMail(map.get("mail"));
		b.setName(map.get("name"));
		b.setAddress(map.get("address"));
		return b; 
	}
	
	public static ProductBean productDeMap(Map<String, String> map){
		ProductBean b = new ProductBean();
		b.setId(Integer.parseInt(map.get("id")));
		b.setName(map.get("name"));
		b.setPrice(Double.parseDouble(map.get("price")));
		b.setQuantity(Integer.parseInt(map.get("quantity")));
		b.setProducer(map.get("producer"));
		b.setCategory(Integer.parseInt(map.get("category")));
		return b; 
	}
	
	public static ShipmentBean shipmentDeMap(Map<String, String> map){
		ShipmentBean b = new ShipmentBean();
		b.setId(Integer.parseInt(map.get("id")));
		b.setShipDate(stringtoDate(map.get("shipDate")));
		b.setArriveDate(stringtoDate(map.get("arriveDate")));
		b.setShipAddress(map.get("shipAddress"));
		b.setTotal(Double.parseDouble(map.get("total")));
		b.setIdOrder(Integer.parseInt(map.get("idOrder")));
		return b; 
	}
	
	public static WebUserBean webuserDeMap(Map<String, String> map){
		WebUserBean b = new WebUserBean();
		b.setMail(map.get("mail"));
		b.setPassword(map.get("password"));
		return b; 
	}
}
