package com.musicstore.model;

import java.util.Objects;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;

import com.musicstore.pojos.Categories;

@Entity
@SqlResultSetMapping(name="firstCategoryProcMapping", classes= {
		@ConstructorResult(targetClass = Categories.class,
						   columns = {
								   @ColumnResult(name="id", type = Integer.class),
								   @ColumnResult(name="name", type = String.class),
								   @ColumnResult(name="parent", type = Integer.class),
								   @ColumnResult(name="imgurl", type = String.class)
						   })
})
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
			name="categoryFirstProc", 
			procedureName="CategoriesByProducer", 
			parameters= {@StoredProcedureParameter(mode=ParameterMode.IN, name="producerMail", type=String.class)}, 
			resultSetMappings = {"firstCategoryProcMapping"})
	})
public class CategoryBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private int parent;
	private String imgurl;
	
	public CategoryBean() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, imgurl, name, parent);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryBean other = (CategoryBean) obj;
		return id == other.id && Objects.equals(imgurl, other.imgurl) && Objects.equals(name, other.name)
				&& parent == other.parent;
	}

	@Override
	public String toString() {
		return "CategoryBean [id=" + id + ", name=" + name + ", parent=" + parent + ", imgurl=" + imgurl + "]";
	}
	
}
