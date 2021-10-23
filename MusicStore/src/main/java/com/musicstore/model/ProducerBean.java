package com.musicstore.model;

import java.util.Objects;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.SqlResultSetMapping;

import com.musicstore.pojos.BestProducts;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.musicstore.pojos.BestProducers;

@Entity
@SqlResultSetMapping(name="firstProducerProdMapping", classes= {
		@ConstructorResult(targetClass = BestProducers.class,
						   columns = {
								   @ColumnResult(name="mail", type = String.class),
								   @ColumnResult(name="name", type = String.class),
								   @ColumnResult(name="address", type = String.class)
						   })
})
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name="producerFirstProc", procedureName="BestProducers", resultSetMappings = {"firstProducerProdMapping"})})
public class ProducerBean {

	@Id
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
		result = prime * result + Objects.hash(address, mail, name);
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
		return Objects.equals(address, other.address) && Objects.equals(mail, other.mail)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ProducerBean [mail=" + mail + ", name=" + name + ", address=" + address + "]";
	}

	
}
