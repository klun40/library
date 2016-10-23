package com.sap.hana.cloud.samples.library.persistence;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sap.hana.cloud.samples.library.persistence.manager.EntityManagerProvider;
import com.sap.hana.cloud.samples.library.persistence.model.DBQueries;
import com.sap.hana.cloud.samples.library.persistence.model.Request;
import com.sap.hana.cloud.samples.library.persistence.model.User;

public class RequestDAO extends BasicDAO<Request> {

	public RequestDAO() {
		super(EntityManagerProvider.getInstance());
	}

	public Request createRequestForUser(User user) {
		final Request request = new Request();
		request.setUser(user);
		saveNew(request);
		return request;
	}

	public Collection<Request> getRequestsForUser(User user) {
		final EntityManager em = emProvider.get();
		final TypedQuery<Request> query = em.createNamedQuery(DBQueries.GET_USER_ALL_REQUESTS, Request.class);
		query.setParameter("user", user); //$NON-NLS-1$

		return query.getResultList();
	}

}
