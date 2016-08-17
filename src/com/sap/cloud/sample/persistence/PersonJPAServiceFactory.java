package com.sap.cloud.sample.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.eclipse.persistence.config.PersistenceUnitProperties;

public class PersonJPAServiceFactory extends ODataJPAServiceFactory {

	private static final String PUNIT_NAME = "persistence-with-jpa";

	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");

			Map<String, DataSource> properties = new HashMap<String, DataSource>();
			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
			ODataJPAContext oDataJPAContext = getODataJPAContext();
			oDataJPAContext.setEntityManagerFactory(Persistence.createEntityManagerFactory(PUNIT_NAME, properties));
			oDataJPAContext.setPersistenceUnitName(PUNIT_NAME);
			return oDataJPAContext;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
