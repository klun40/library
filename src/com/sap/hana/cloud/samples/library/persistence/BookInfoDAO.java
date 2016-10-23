package com.sap.hana.cloud.samples.library.persistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hana.cloud.samples.library.persistence.manager.EntityManagerProvider;
import com.sap.hana.cloud.samples.library.persistence.model.BookInfo;

@SuppressWarnings("nls")
public class BookInfoDAO extends BasicDAO<BookInfo> {

	private final Logger logger = LoggerFactory.getLogger(BookInfoDAO.class);

	public BookInfoDAO() {
		super(EntityManagerProvider.getInstance());
	}

	@Override
	public BookInfo save(BookInfo bookInfo) {
		BookInfo existingBenefit = getByName(bookInfo.getName());
		if (existingBenefit == null) {
			saveNew(bookInfo);
			return bookInfo;
		}

		return existingBenefit;
	}

	private BookInfo getByName(String name) {
		BookInfo bookInfo = null;
		final EntityManager em = emProvider.get();
		try {
			final Query query = em.createQuery("select b from BookInfo b where b.name = :name");
			query.setParameter("name", name);

			bookInfo = (BookInfo) query.getSingleResult();
		} catch (NoResultException e) {
			logger.warn("Could not retrieve entity {} from table {}.  Maybe the benefit info doesn't exist yet.", name,
					"Benefits info");
		} catch (NonUniqueResultException e) {
			throw new IllegalStateException(String.format("More than one entity %s from table Benefits info.", name)); //$NON-NLS-1$
		}

		return bookInfo;
	}

}
