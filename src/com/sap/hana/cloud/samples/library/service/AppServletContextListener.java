package com.sap.hana.cloud.samples.library.service;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hana.cloud.samples.library.csv.dataimport.BooksDataImporter;
import com.sap.hana.cloud.samples.library.csv.dataimport.LocationDataImporter;
import com.sap.hana.cloud.samples.library.csv.dataimport.UsersDataImporter;
import com.sap.hana.cloud.samples.library.persistence.BookInfoDAO;
import com.sap.hana.cloud.samples.library.persistence.LocationDAO;
import com.sap.hana.cloud.samples.library.persistence.UserDAO;
import com.sap.hana.cloud.samples.library.persistence.manager.EntityManagerFactoryProvider;
import com.sap.hana.cloud.samples.library.persistence.manager.EntityManagerProvider;

public class AppServletContextListener implements ServletContextListener {

	private static final String BOOKS_CSV_PATH = "/books.csv"; //$NON-NLS-1$
	private static final String LOCATION_CSV_PATH = "/locations.csv"; //$NON-NLS-1$
	private static final String USERS_CSV_PATH = "/users.csv"; //$NON-NLS-1$
	private static Logger logger = LoggerFactory.getLogger(AppServletContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		EntityManagerFactoryProvider.getInstance().close();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		try {
			EntityManagerProvider.getInstance().initEntityManagerProvider();
			initUsers();
			initLocations();
			initBooks();
		} finally {
			EntityManagerProvider.getInstance().closeEntityManager();
		}
	}

	private void initUsers() {
		final UserDAO userDAO = new UserDAO();
		if (userDAO.getAll().size() == 0) {
			final UsersDataImporter usersImporter = new UsersDataImporter();
			try {
				usersImporter.importDataFromCSV(USERS_CSV_PATH);
			} catch (IOException e) {
				logger.error("Could not insert users data into DB", e); //$NON-NLS-1$
			}
		}

	}
	
	private void initLocations() {
		final LocationDAO locationDAO = new LocationDAO();
		if (locationDAO.getAll().size() == 0) {
			final LocationDataImporter locationImporter = new LocationDataImporter();
			try {
				locationImporter.importDataFromCSV(LOCATION_CSV_PATH);
			} catch (IOException e) {
				logger.error("Could not insert locations data into DB", e); //$NON-NLS-1$
			}
		}

	}

	private void initBooks() {
		final BookInfoDAO benefitDAO = new BookInfoDAO();
		if (benefitDAO.getAll().size() == 0) {
			final BooksDataImporter benefitImporter = new BooksDataImporter();
			try {
				benefitImporter.importDataFromCSV(BOOKS_CSV_PATH);
			} catch (IOException e) {
				logger.error("Could not insert benefits data into DB", e); //$NON-NLS-1$
			}
		}

	}

}
