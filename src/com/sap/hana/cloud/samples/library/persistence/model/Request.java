package com.sap.hana.cloud.samples.library.persistence.model;

import static com.sap.hana.cloud.samples.library.persistence.model.DBQueries.GET_USER_ALL_REQUESTS;

import javax.persistence.Basic;
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

@Entity
@Table(name = "REQUEST")
@NamedQueries({ @NamedQuery(name = GET_USER_ALL_REQUESTS, query = "select o from Request o where o.user = :user") })
public class Request implements IDBEntity {

	@Id
	@GeneratedValue
	@Column(name = "REQUEST_ID")
	private Long id;

	@Basic
	private String type;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	@Column(name = "USER_ID", insertable = false, updatable = false)
	private Long userId;

	// @OneToMany(cascade = CascadeType.ALL, mappedBy = "request", fetch =
	// FetchType.EAGER, targetEntity = RequestDetails.class)
	// private List<RequestDetails> orderDetails;

	@OneToOne(optional = false)
	private BookInfo bookInfo;

	@Column(name = "BOOK_INFO_ID", insertable = false, updatable = false)
	private Long bookId;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if (!user.getRequests().contains(this)) {
			user.addOrder(this);
		}
	}

	// public List<RequestDetails> getRequestDetails() {
	// if (this.orderDetails == null) {
	// this.orderDetails = new ArrayList<>();
	// }
	// return orderDetails;
	// }
	//
	// public void addRequestDetails(RequestDetails details) {
	// getRequestDetails().add(details);
	//
	// if (details.getRequest() != this) {
	// details.setRequest(this);
	// }
	// }
	//
	// public void removeOrderDetails(RequestDetails details) {
	// getRequestDetails().remove(details);
	// }
	//
	// public void setOrderDetails(List<RequestDetails> orderDetails) {
	// this.orderDetails = orderDetails;
	// }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BookInfo getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

}
