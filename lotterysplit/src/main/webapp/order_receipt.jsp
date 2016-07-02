<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Map" %>
<%@ page import="net.authorize.*" %>
<%@ page import="java.net.URLDecoder" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<%@ include file="/common/include.jsp" %>

<body>
	<div data-role="page" data-theme="b">

		<div data-role="header" data-position="fixed">
			<h1>Lottery Split</h1>
			<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/list.htm?start=1">Back</a>
		</div>

		<div data-role="content">

<%
  // Show the confirmation data
  Map<String, String[]> requestParameterMap = request.getParameterMap();

  if(requestParameterMap != null &&
    requestParameterMap.containsKey(ResponseField.RESPONSE_CODE.getFieldName())) {


    String transactionId = "";
    if(requestParameterMap.containsKey(ResponseField.TRANSACTION_ID.getFieldName())) {
      transactionId = requestParameterMap.get(ResponseField.TRANSACTION_ID.getFieldName())[0];
    }

    // 1 means we have a successful transaction
    if("1".equals(requestParameterMap.get(ResponseField.RESPONSE_CODE.getFieldName())[0])) {
%>

    <h2>Success!</h2>
    <div>Your transaction ID:  <%=net.authorize.util.StringUtils.sanitizeString(transactionId)%></div> <br>
    <div><fmt:setLocale value="en_US"/><fmt:formatNumber value="<%=requestParameterMap.get(ResponseField.AMOUNT.getFieldName())[0]%>" type="currency"/></div> <br>
    <div><%=requestParameterMap.get(ResponseField.EMAIL_ADDRESS.getFieldName())[0]%></div> <br>
    <div><%=requestParameterMap.get(ResponseField.INVOICE_NUMBER.getFieldName())[0]%></div>    
    
<%  } else { %>

    <h2>Error!</h2>
    <h3>Credit card not accepted.</h3>
    <p>Please try again</p>
<%
    }
  }
%>
    </div>

		<%@ include file="/common/footer.jsp" %>
  
    
</div>
  </body>
</html>