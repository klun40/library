package com.sap.hana.cloud.samples.library.persistence.model;

import static com.sap.hana.cloud.samples.library.persistence.model.DBQueries.GET_USER_BY_EMAIL;
import static com.sap.hana.cloud.samples.library.persistence.model.DBQueries.GET_USER_BY_USER_ID;

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
@Table(name = "USERS", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_ID" }) })
@NamedQueries({ @NamedQuery(name = GET_USER_BY_USER_ID, query = "select u from User u where u.userId = :userId"),
		@NamedQuery(name = GET_USER_BY_EMAIL, query = "select u from User u where u.email = :email") })
public class User implements IDBEntity {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Basic
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Basic
	@Column(name = "LAST_NAME")
	private String lastName;

	@Basic
	@Column(name = "USER_ID")
	private String userId;

	@Basic
	private String email;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, targetEntity = Request.class)
	private List<Request> requests;

	public User() {

	}

	public User(String userId) {
		this.userId = userId;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return firstName + lastName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Request> getRequests() {
		if (requests == null) {
			requests = new ArrayList<>();
		}
		return requests;
	}

	public void setOrders(List<Request> orders) {
		this.requests = orders;
	}

	public void addOrder(Request request) {
		getRequests().add(request);
		if (request.getUser() != this) {
			request.setUser(this);
		}
	}

}
