package com.sap.hana.cloud.samples.library.csv.dataimport;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.sap.hana.cloud.samples.library.persistence.BookInfoDAO;
import com.sap.hana.cloud.samples.library.persistence.model.BookInfo;

import au.com.bytecode.opencsv.CSVReader;

public class BooksDataImporter {

	public void importDataFromCSV(String filepath) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		if (classLoader == null) {
			throw new IllegalStateException("Cannot import data - null classloader"); //$NON-NLS-1$
		}
		Reader fileReader = new InputStreamReader(classLoader.getResourceAsStream(filepath), Charset.forName("UTF-8")); //$NON-NLS-1$

		try (CSVReader csvFile = new CSVReader(fileReader)) {
			final List<BookInfo> benefitsInfo = readBooksInfo(csvFile);
			persistBooksInfo(benefitsInfo);
		}

	}

	private List<BookInfo> readBooksInfo(CSVReader reader) throws NumberFormatException, IOException {
		final List<BookInfo> benefitsInfo = new ArrayList<>();
		String[] nextLine;
		reader.readNext();

		while ((nextLine = reader.readNext()) != null) {

			if (nextLine.length != 3) {
				throw new IllegalArgumentException("data is not a valid benefit record"); //$NON-NLS-1$
			}

			final BookInfo bookInfo = new BookInfo();
			bookInfo.setName(nextLine[0]);
			bookInfo.setDescription(nextLine[1]);
			//TODO set proper location
			//first put locations than books
			bookInfo.setLocationId(Long.parseLong(nextLine[2]));
			benefitsInfo.add(bookInfo);
		}

		return benefitsInfo;
	}

	private void persistBooksInfo(List<BookInfo> bookInfos) {
		final BookInfoDAO dao = new BookInfoDAO();
		dao.deleteAll();

		for (BookInfo bookInfo : bookInfos) {
			dao.save(bookInfo);
		}
	}
}
