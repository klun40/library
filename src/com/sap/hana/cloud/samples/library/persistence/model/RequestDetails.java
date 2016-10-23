package com.sap.hana.cloud.samples.library.persistence.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
//@Table(name = "REQUEST_DETAILS")
//public class RequestDetails implements IDBEntity {

//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "REQUEST_ID", referencedColumnName = "REQUEST_ID")
//    private Request request;
//    
//    @Column(name = "REQUEST_ID", insertable = false, updatable = false) 
//    private Long requestId;
//
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "BOOK_INFO_ID", referencedColumnName = "BOOK_INFO_ID")
//    private BookInfo bookInfo;
//    
//    @Column(name = "BOOK_INFO_ID", insertable = false, updatable = false) 
//    private Long bookId;
//    
//    @Basic
//    @Column(name = "REQUEST_TYPE")
//	private String requestType;
//
//    @Override
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public BookInfo getBookInfo() {
//        return bookInfo;
//    }
//
//    public void setBookInfo(BookInfo bookInfo) {
//        this.bookInfo = bookInfo;
//    }
//
//    public Request getRequest() {
//        return request;
//    }
//
//    public void setRequest(Request request) {
//        this.request = request;
//        if (!request.getRequestDetails().contains(this)) {
//        	request.addRequestDetails(this);
//        }
//    }
//    
//    public Long getOrderId() {
//		return requestId;
//	}
//
//	public void setRequestId(Long orderId) {
//		this.requestId = orderId;
//	}
//	
//	public Long getBookId() {
//		return bookId;
//	}
//
//	public void setBookId(Long bookId) {
//		this.bookId = bookId;
//	}
//
//	public String requestType() {
//		return requestType;
//	}
//
//	public void setRequestType(String requestType) {
//		this.requestType = requestType;
//	}

//}
