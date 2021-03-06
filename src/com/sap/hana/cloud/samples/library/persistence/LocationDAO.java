package com.sap.hana.cloud.samples.library.persistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hana.cloud.samples.library.persistence.manager.EntityManagerProvider;
import com.sap.hana.cloud.samples.library.persistence.model.DBQueries;
import com.sap.hana.cloud.samples.library.persistence.model.Location;
import com.sap.hana.cloud.samples.library.persistence.model.User;

public class LocationDAO extends BasicDAO<Location> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public LocationDAO() {
		super(EntityManagerProvider.getInstance());
	}

	public Location getLocationById(String locationId) {
		final EntityManager em = emProvider.get();
		try {
			final TypedQuery<Location> query = em.createNamedQuery(DBQueries.GET_LOCATION_ALL_BOOKS, Location.class);
			query.setParameter("locationId", locationId); //$NON-NLS-1$
			Location location = query.getSingleResult();
			return location;
		} catch (NoResultException x) {
			logger.warn("Could not retrieve entity for locationId {} from table {}. Maybe the user doesn't exist yet.", locationId, "Location"); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (NonUniqueResultException ex) {
			throw new IllegalStateException(String.format("More than one entity for locationId %s from table Location.", locationId), ex); //$NON-NLS-1$
		}

		return null;
	}

	public User findByEmail(String email) {
		final EntityManager em = emProvider.get();
		try {
			final TypedQuery<User> query = em.createNamedQuery(DBQueries.GET_USER_BY_EMAIL, User.class);
			query.setParameter("email", email); //$NON-NLS-1$
			User user = query.getSingleResult();
			return user;
		} catch (NoResultException x) {
			logger.warn("Could not retrieve user with emial {} from table {}. Maybe the user doesn't exist yet.", email, "User"); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (NonUniqueResultException ex) {
			throw new IllegalStateException(String.format("More than one users with email %s from table User.", email), ex); //$NON-NLS-1$
		}

		return null;
	}
}
