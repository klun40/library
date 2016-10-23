package com.sap.hana.cloud.samples.library.odata;

import static com.sap.hana.cloud.samples.library.odata.cfg.FunctionImportParameters.BOOK_DESCRIPTION;
import static com.sap.hana.cloud.samples.library.odata.cfg.FunctionImportParameters.BOOK_NAME;
import static com.sap.hana.cloud.samples.library.odata.cfg.FunctionImportParameters.USER_ID;
import static org.apache.olingo.odata2.api.annotation.edm.EdmType.STRING;

import java.util.Collection;

import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.HttpMethod;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImportParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hana.cloud.samples.library.odata.cfg.FunctionImportNames;
import com.sap.hana.cloud.samples.library.persistence.BookInfoDAO;
import com.sap.hana.cloud.samples.library.persistence.RequestDAO;
import com.sap.hana.cloud.samples.library.persistence.model.BookInfo;
import com.sap.hana.cloud.samples.library.persistence.model.Request;
import com.sap.hana.cloud.samples.library.persistence.model.User;

public class RequestService extends ODataService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@EdmFunctionImport(name = FunctionImportNames.ADD_REQUEST, returnType = @ReturnType(type = Type.SIMPLE, isCollection = false), httpMethod = HttpMethod.POST)
	public boolean addRequest(@EdmFunctionImportParameter(name = USER_ID, type = STRING) String userId,
			@EdmFunctionImportParameter(name = BOOK_NAME, type = STRING) String bookName,
			@EdmFunctionImportParameter(name = BOOK_DESCRIPTION, type = STRING) String bookDescription)
			throws AppODataException {
		final User user = userDAO.getByUserId(userId);

		final RequestDAO requestDAO = new RequestDAO();
		final Request request = requestDAO.createRequestForUser(user);
		final BookInfo bookInfo = createBookInfo(bookName, bookDescription, 3l);
		new BookInfoDAO().save(bookInfo);
		request.setType("add");
		request.setBookInfo(bookInfo);
		request.setBookId(bookInfo.getId());
		requestDAO.save(request);
		return true;
	}

//	@EdmFunctionImport(name = FunctionImportNames.DELETE_REQUEST, returnType = @ReturnType(type = Type.SIMPLE, isCollection = false), httpMethod = HttpMethod.DELETE)
//	public boolean deleteRequest(@EdmFunctionImportParameter(name = USER_ID, type = STRING) String userId,)
//			throws AppODataException {
//		final OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//		final Order order = orderDetailDAO.getOrderByOrderDetailsId(orderId);
//		final OrderDetails details = orderDetailDAO.getById(orderId);
//		final UserPoints userPoints = getUserPoints(order);
//		try {
//			order.removeOrderDetails(details);
//			userPoints.addPoints(calcPointsToAdd(details));
//			userPointsDAO.save(userPoints);
//			orderDetailDAO.delete(orderId);
//			return true;
//		} catch (IllegalArgumentException ex) {
//			logger.error("Error occur while deleting order with id:{}", orderId, ex);
//			// $NON-NLS-1$
//			throw new AppODataException("Error occur while deleting order", ex);
//			// $NON-NLS-1$
//		}
//	}

	private Request getOrCreateUserRequest(final User user, final RequestDAO requestDAO) {
		Request request = null;
		final Collection<Request> userRequest = requestDAO.getRequestsForUser(user);
		if (userRequest.isEmpty()) {
			request = requestDAO.createRequestForUser(user);
		} else {
			request = userRequest.iterator().next();
		}
		return request;
	}

	private BookInfo createBookInfo(String name, String description, long locationId) {
		BookInfo bookInfo = new BookInfo();
		bookInfo.setName(name);
		bookInfo.setDescription(description);
		bookInfo.setLocationId(locationId);
		return bookInfo;
	}

	// private RequestDetails createRequestDetails(Request request, BookInfo
	// bookInfo, RequestType requestType) {
	// final RequestDetails requestDetails = new RequestDetails();
	// requestDetails.setBookId(bookInfo.getId());
	// requestDetails.setRequestId((long) request.getId());
	// return requestDetails;
	// }
}
