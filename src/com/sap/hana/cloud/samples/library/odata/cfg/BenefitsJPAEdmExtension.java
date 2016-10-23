package com.sap.hana.cloud.samples.library.odata.cfg;

import java.io.InputStream;

import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;

import com.sap.hana.cloud.samples.library.odata.AdministrationService;
import com.sap.hana.cloud.samples.library.odata.RequestService;

public class BenefitsJPAEdmExtension implements JPAEdmExtension {

	private static final Class<?>[] SERVICES = { AdministrationService.class, RequestService.class };

	@Override
	public void extendWithOperation(final JPAEdmSchemaView view) {
		for (Class<?> service : SERVICES) {
			view.registerOperations(service, null);
		}
	}

	@Override
	public void extendJPAEdmSchema(final JPAEdmSchemaView view) {
		Schema edmSchema = view.getEdmSchema();
		edmSchema.setComplexTypes(ComplexTypesDescriber.getInstance().getEdmComplexTypes());
	}

	@Override
	public InputStream getJPAEdmMappingModelStream() {
		return null;
	}

}
