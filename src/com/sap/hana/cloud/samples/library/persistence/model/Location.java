package com.sap.hana.cloud.samples.library.persistence.model;

import static com.sap.hana.cloud.samples.library.persistence.model.DBQueries.GET_LOCATION_ALL_BOOKS;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "LOCATION", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@NamedQueries({ @NamedQuery(name = GET_LOCATION_ALL_BOOKS, query = "select o from Location o where o.id = :id") })
public class Location implements IDBEntity {

	@Id
	@GeneratedValue
	@Column(name = "LOCATION_ID")
	private Long id;

	@Basic
	private String name;

	@Basic
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "location", fetch = FetchType.EAGER, targetEntity = BookInfo.class)
	private List<BookInfo> bookInfos;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BookInfo> getBookInfos() {
		if (this.bookInfos == null) {
			this.bookInfos = new ArrayList<>();
		}
		return bookInfos;
	}

	public void setBookInfos(List<BookInfo> bookInfos) {
		this.bookInfos = bookInfos;
	}

}
