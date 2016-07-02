package com.paypal.paymenthost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import urn.ebay.api.PayPalAPI.BMCreateButtonReq;
import urn.ebay.api.PayPalAPI.BMCreateButtonRequestType;
import urn.ebay.api.PayPalAPI.BMCreateButtonResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.AckCodeType;
import urn.ebay.apis.eBLBaseComponents.ButtonCodeType;
import urn.ebay.apis.eBLBaseComponents.ButtonTypeType;

import com.paypal.core.credential.ICredential;
import com.paypal.core.credential.SignatureCredential;

public class PaymentProServlet extends HttpServlet {
	private static final long serialVersionUId = 123123123123123L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if (req.getRequestURI().contains("hss")) {
			getServletConfig().getServletContext()
					.getRequestDispatcher("/hss.jsp").forward(req, res);
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {

			PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(
					this.getClass().getResourceAsStream(
							"/sdk_config.properties"));

			BMCreateButtonReq request = new BMCreateButtonReq();
			BMCreateButtonRequestType reqType = new BMCreateButtonRequestType();
			reqType.setButtonType(ButtonTypeType.fromValue(req
					.getParameter("buttontype")));
			reqType.setButtonCode(ButtonCodeType.fromValue(req
					.getParameter("buttoncode")));
			List<String> buttonVarList = new ArrayList<String>();
			if (req.getParameter("subtotal") != null
					&& req.getParameter("subtotal").length() > 0) {
				buttonVarList.add("subtotal=" + req.getParameter("subtotal"));
			}
			if (req.getParameter("tax") != null
					&& req.getParameter("tax").length() > 0) {
				buttonVarList.add("tax=" + req.getParameter("tax"));
			}
			if (req.getParameter("handling") != null
					&& req.getParameter("handling").length() > 0) {
				buttonVarList.add("handling=" + req.getParameter("handling"));
			}
			if (req.getParameter("shipping") != null
					&& req.getParameter("shipping").length() > 0) {
				buttonVarList.add("shipping=" + req.getParameter("shipping"));
			}
			if (req.getParameter("street1") != null
					&& req.getParameter("street1").length() > 0) {
				buttonVarList.add("address1=" + req.getParameter("street1"));
			}
			if (req.getParameter("street2") != null
					&& req.getParameter("street2").length() > 0) {
				buttonVarList.add("address2=" + req.getParameter("street2"));
			}
			if (req.getParameter("city") != null
					&& req.getParameter("city").length() > 0) {
				buttonVarList.add("city=" + req.getParameter("city"));
			}
			if (req.getParameter("state") != null
					&& req.getParameter("state").length() > 0) {
				buttonVarList.add("state=" + req.getParameter("state"));
			}
			if (req.getParameter("zip") != null
					&& req.getParameter("zip").length() > 0) {
				buttonVarList.add("zip=" + req.getParameter("zip"));
			}
			if (req.getParameter("country") != null
					&& req.getParameter("country").length() > 0) {
				buttonVarList.add("country=" + req.getParameter("country"));
			}
			if (req.getParameter("currencyCode") != null
					&& req.getParameter("currencyCode").length() > 0) {
				buttonVarList.add("currency_code="
						+ req.getParameter("currencyCode"));
			}

			if (req.getParameter("returnURL") != null
					&& !req.getParameter("returnURL").equalsIgnoreCase("")) {
				buttonVarList.add("return=" + req.getParameter("returnURL"));
			}

			if (req.getParameter("checkboxTemplate") != null
					&& !req.getParameter("checkboxTemplate").equalsIgnoreCase(
							"")) {
				buttonVarList.add("template="
						+ req.getParameter("checkboxTemplate"));

			}

			reqType.setButtonVar(buttonVarList);
			request.setBMCreateButtonRequest(reqType);
			BMCreateButtonResponseType resp;
			ICredential credential = null;
			if (req.getParameter("userName") != null
					&& req.getParameter("userName").length() > 0
					&& req.getParameter("password") != null
					&& req.getParameter("password").length() > 0
					&& req.getParameter("signature") != null
					&& req.getParameter("signature").length() > 0) {
				credential = new SignatureCredential(
						req.getParameter("userName"),
						req.getParameter("password"),
						req.getParameter("signature"));

			}
			resp = service.bMCreateButton(request, credential);
			res.setContentType("application/json");
			StringBuilder returnData = new StringBuilder();
			if (resp.getAck().equals(AckCodeType.SUCCESS)) {
				returnData.append("{\"email\":\"");
				returnData.append(resp.getEmail());
				String website = resp.getWebsite();
				website = website.replace("\"", "\\\"");
				website = website.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");

				returnData.append("\",\"website\":\"");
				returnData.append(website).append("\"");
				if (req.getParameter("checkboxTemplate") != null
						&& !req.getParameter("checkboxTemplate").equalsIgnoreCase(
								"")) {
				returnData.append(",\"displayIframe\":true");
				}
				returnData.append("}");

			} else {
				returnData.append("{\"errorCode\":\"");
				returnData.append(resp.getErrors().get(0).getErrorCode());
				returnData.append("\",\"shortMsg\":\"");
				returnData.append(resp.getErrors().get(0).getShortMessage());
				returnData.append("\",\"longMsg\":\"");
				returnData.append(resp.getErrors().get(0).getLongMessage());
				returnData.append("\"}");
				req.setAttribute("Error", resp.getErrors());
			}
			res.getWriter().println(returnData.toString());
		} catch (Exception e) {
			if (e.getMessage().equalsIgnoreCase(
					"Credential is null in MerchantAPICallPreHandler")) {
				req.setAttribute("credential", "false");
				getServletConfig().getServletContext()
						.getRequestDispatcher("/hss.jsp").forward(req, res);

			}
		}

	}
}
