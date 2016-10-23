//package com.sap.hana.cloud.samples.library.persistence;
//
//import javax.persistence.EntityManager;
//
//import com.sap.hana.cloud.samples.library.persistence.manager.EntityManagerProvider;
//import com.sap.hana.cloud.samples.library.persistence.model.Request;
//import com.sap.hana.cloud.samples.library.persistence.model.RequestDetails;
//
//public class RequestDetailDAO extends BasicDAO<RequestDetails> {
//
//	public RequestDetailDAO() {
//		super(EntityManagerProvider.getInstance());
//	}
//
//	public void delete(long id) {
//		final EntityManager em = emProvider.get();
//		final RequestDetails orderDetail = em.find(RequestDetails.class, id);
//		em.getTransaction().begin();
//		if (orderDetail != null) {
//			orderDetail.getRequest().getRequestDetails().remove(orderDetail);
//			em.remove(orderDetail);
//		}
//		em.getTransaction().commit();
//	}
//
//	public Request getOrderByOrderDetailsId(long id) {
//		RequestDetails orderDetails = this.getById(id);
//		if (orderDetails != null) {
//			return orderDetails.getRequest();
//		}
//		return null;
//	}
//}
