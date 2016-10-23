package com.sap.hana.cloud.samples.library.odata;

import static com.sap.hana.cloud.samples.library.odata.cfg.FunctionImportNames.RESET_DB;
import static com.sap.hana.cloud.samples.library.odata.cfg.FunctionImportNames.UI_CONFIG;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.HttpMethod;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hana.cloud.samples.library.csv.dataimport.BooksDataImporter;
import com.sap.hana.cloud.samples.library.csv.dataimport.LocationDataImporter;
import com.sap.hana.cloud.samples.library.csv.dataimport.UsersDataImporter;
import com.sap.hana.cloud.samples.library.odata.beans.UIConfig;
import com.sap.hana.cloud.samples.library.odata.cfg.BenefitsODataServiceFactory;
import com.sap.hana.cloud.samples.library.persistence.BookInfoDAO;
import com.sap.hana.cloud.samples.library.persistence.LocationDAO;
import com.sap.hana.cloud.samples.library.persistence.RequestDAO;

public class AdministrationService extends ODataService {
	private static final String TRUE_STR = "true"; //$NON-NLS-1$
	private static final String BENEFITS_CSV_PATH = "/books.csv"; //$NON-NLS-1$
	private static final String LOCATION_CSV_PATH = "/locations.csv"; //$NON-NLS-1$
	private static final String USERS_CSV_PATH = "/users.csv"; //$NON-NLS-1$
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EdmFunctionImport(name = RESET_DB, returnType = @ReturnType(type = Type.SIMPLE, isCollection = false), httpMethod = HttpMethod.POST)
	public boolean resetDatabase() {
		cleanDB();
		forceSubsequentInitialization();

		final BooksDataImporter benefitImporter = new BooksDataImporter();
		final LocationDataImporter locationImporter = new LocationDataImporter();
		final UsersDataImporter usersImporter = new UsersDataImporter();
		try {
			usersImporter.importDataFromCSV(USERS_CSV_PATH);
			locationImporter.importDataFromCSV(LOCATION_CSV_PATH);
			benefitImporter.importDataFromCSV(BENEFITS_CSV_PATH);	
		} catch (IOException e) {
			logger.error("Could not insert beneits data into DB", e); //$NON-NLS-1$
			return false;
		}

		return true;
	}

	private void cleanDB() {
		final RequestDAO requestDAO = new RequestDAO();
		final BookInfoDAO bookInfoDAO = new BookInfoDAO();
		final LocationDAO locationDAO = new LocationDAO();
//		final RequestDetailDAO requestDetailDAO = new RequestDetailDAO();

		userDAO.deleteAll();
		requestDAO.deleteAll();
		bookInfoDAO.deleteAll();
		locationDAO.deleteAll();
//		requestDetailDAO.deleteAll();
	}

	private void forceSubsequentInitialization() {
		ODataContext ctx = BenefitsODataServiceFactory.getContextInThreadLocal();
		HttpServletRequest httpServlReq = (HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);
	}

	@EdmFunctionImport(name = UI_CONFIG, returnType = @ReturnType(type = Type.COMPLEX, isCollection = false), httpMethod = HttpMethod.GET)
	public UIConfig getUIConfigurationData() {
		final UIConfig config = new UIConfig();
//		if (UserManager.getIsUserAdmin()) {
//			config.initAdminConfiguration();
//		} else {
			config.initEmployeeConfiguration();
//		}

		return config;
	}
}
