package com.sap.hana.cloud.samples.library.csv.dataimport;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.sap.hana.cloud.samples.library.persistence.UserDAO;
import com.sap.hana.cloud.samples.library.persistence.model.User;

import au.com.bytecode.opencsv.CSVReader;

public class UsersDataImporter {

	public void importDataFromCSV(String filepath) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		if (classLoader == null) {
			throw new IllegalStateException("Cannot import data - null classloader"); //$NON-NLS-1$
		}
		Reader fileReader = new InputStreamReader(classLoader.getResourceAsStream(filepath), Charset.forName("UTF-8")); //$NON-NLS-1$

		try (CSVReader csvFile = new CSVReader(fileReader)) {
			final List<User> locations = readUsers(csvFile);
			persistUsers(locations);
		}

	}

	private List<User> readUsers(CSVReader reader) throws NumberFormatException, IOException {
		final List<User> users = new ArrayList<>();
		String[] nextLine;
		reader.readNext();

		while ((nextLine = reader.readNext()) != null) {

			if (nextLine.length != 3) {
				throw new IllegalArgumentException("data is not a valid location record"); //$NON-NLS-1$
			}

			final User user = new User();
			user.setId(Long.parseLong(nextLine[0]));
			user.setUserId(nextLine[0]);
			user.setFirstName(nextLine[1]);
			user.setLastName(nextLine[2]);
			users.add(user);
		}

		return users;
	}

	private void persistUsers(List<User> users) {
		final UserDAO dao = new UserDAO();
		dao.deleteAll();

		for (User user : users) {
			dao.save(user);
		}
	}
}
