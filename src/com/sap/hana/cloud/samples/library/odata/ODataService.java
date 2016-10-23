package com.sap.hana.cloud.samples.library.odata;

import com.sap.hana.cloud.samples.library.persistence.UserDAO;
import com.sap.hana.cloud.samples.library.persistence.model.User;

public abstract class ODataService {

	protected UserDAO userDAO;

	public ODataService() {
		this.userDAO = new UserDAO();
	}

	protected User getLoggedInSfUser() {
		return null;
	}

}
