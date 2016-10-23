package com.sap.hana.cloud.samples.library.csv.dataimport;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.sap.hana.cloud.samples.library.persistence.LocationDAO;
import com.sap.hana.cloud.samples.library.persistence.model.Location;

import au.com.bytecode.opencsv.CSVReader;

public class LocationDataImporter {

	public void importDataFromCSV(String filepath) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		if (classLoader == null) {
			throw new IllegalStateException("Cannot import data - null classloader"); //$NON-NLS-1$
		}
		Reader fileReader = new InputStreamReader(classLoader.getResourceAsStream(filepath), Charset.forName("UTF-8")); //$NON-NLS-1$

		try (CSVReader csvFile = new CSVReader(fileReader)) {
			final List<Location> locations = readLocations(csvFile);
			persistLocations(locations);
		}

	}

	private List<Location> readLocations(CSVReader reader) throws NumberFormatException, IOException {
		final List<Location> locations = new ArrayList<>();
		String[] nextLine;
		reader.readNext();

		while ((nextLine = reader.readNext()) != null) {

			if (nextLine.length != 3) {
				throw new IllegalArgumentException("data is not a valid location record"); //$NON-NLS-1$
			}

			final Location location = new Location();
			location.setId(Long.parseLong(nextLine[0]));
			location.setName(nextLine[1]);
			location.setDescription(nextLine[2]);
			locations.add(location);
		}

		return locations;
	}

	private void persistLocations(List<Location> locations) {
		final LocationDAO dao = new LocationDAO();
		dao.deleteAll();

		for (Location location : locations) {
			dao.save(location);
		}
	}
}
