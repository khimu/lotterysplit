<%@ include file="/common/include.jsp" %>

<head>
    <title><fmt:message key="errorPage.title"/></title>
</head>
	<body id="error">
		<div data-role="page" id="loginPage" data-theme="d" >
		
			 <%@ include file="/common/menu.jsp" %>
			 
	         <div data-role="header" data-position="fixed" data-theme="d"> 
	         		<a href="#nav-panel" data-theme="d" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
	               <h1>Online Lottery Pool</h1> 
	               <fmt:message key="login.signup"><fmt:param><c:url value="/lottery/signup.htm"/></fmt:param></fmt:message>
	         </div> 

	        <div data-role="content"> 

		    <div class="container">
		        <h1><fmt:message key="errorPage.heading"/></h1>
		        <%@ include file="/common/messages.jsp" %>
		
		        <p><fmt:message key="errorPage.message"/></p>
		    </div>
    	</div>
    </div>
</body>
</html>
