package com.sap.hana.cloud.samples.library.persistence.model;

import static com.sap.hana.cloud.samples.library.persistence.model.DBQueries.GET_USER_ALL_REQUESTS;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "BOOK_INFO", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@NamedQueries({ @NamedQuery(name = GET_USER_ALL_REQUESTS, query = "select o from Request o where o.user = :user") })
public class BookInfo implements IDBEntity {
	@Id
	@GeneratedValue
	@Column(name = "BOOK_INFO_ID")
	private Long id;

	@Basic
	private String name;

	@Basic
	private String description;

	@Basic
	private String status;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID")
	private Location location;

	@Column(name = "LOCATION_ID", insertable = false, updatable = false)
	private Long locationId;
	
	@OneToOne(optional = false)
	private User owner;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
