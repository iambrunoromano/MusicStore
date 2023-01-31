package com.musicstore.entity;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.SqlResultSetMapping;

import javax.persistence.Id;

import com.musicstore.pojos.BestProducers;

import lombok.Data;
import lombok.NoArgsConstructor;

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
public @Data @NoArgsConstructor class Producer {

	@Id
	private String mail;
	
	private String name;
	private String address;
	
}
