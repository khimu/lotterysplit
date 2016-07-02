<%@ include file="/common/include.jsp" %>

<head>
    <title><fmt:message key="signup.title"/></title>
    
</head>

<body class="signup"/>

    <div data-role="page" id="register" data-theme="d" data-add-back-btn="true" > 
    
    	<%@ include file="/common/publicmenu.jsp" %>
    
         <div data-role="header" data-position="fixed" data-theme="d" > 
               <h1>Register</h1> 
         </div> 
         
         <div data-role="content">     
         
<a data-ajax="false" href="javascript: referralFBLogin('${param.referredBy}');" data-role="button" >FB Login</a>         

		    <h2><fmt:message key="signup.heading"/></h2>
		    <p><fmt:message key="signup.message"/></p>
		    <p>Must be 18 to participate</p>

		    <spring:bind path="user.*">
		        <c:if test="${not empty status.errorMessages}">
		            <div class="alert alert-danger alert-dismissable">
		                <a href="#" data-dismiss="alert" class="close">&times;</a>
		                <c:forEach var="error" items="${status.errorMessages}">
		                    <c:out value="${error}" escapeXml="false"/><br/>
		                </c:forEach>
		            </div>
		        </c:if>
		    </spring:bind>
    
    <div class="col-sm-7">
    <form:form commandName="user" method="post" action="/lottery/signup.htm" id="signupForm" autocomplete="off"
               cssClass="well" onsubmit="return validateSignup(this)">
               
        <div class="form-group">
            <label for="email" class="control-label"><fmt:message key="user.email"/><span class="required">*</span></label>
            <form:input cssClass="form-control" path="email" id="email"/>
            <form:errors path="email" cssClass="help-block"/>
        </div>
       	<div class="form-group">
            <label for="password" class="control-label"><fmt:message key="user.password"/><span class="required">*</span></label>
            <form:password cssClass="form-control" path="password" id="password" showPassword="true"/>
            <form:errors path="password" cssClass="help-block"/>
        </div>
		<div class="ui-grid-a">
               <div class="ui-block-a">
		        	<div class="form-group">
			            <label for="firstName" class="control-label"><fmt:message key="user.firstName"/><span class="required">*</span></label>
		            	<form:input cssClass="form-control" path="firstName" id="firstName" maxlength="50"/>
		            	<form:errors path="firstName" cssClass="help-block"/>
		            </div>
		        </div>
		        <div class="ui-block-b">
		       		<div class="form-group">
		                <label for="lastName" class="control-label"><fmt:message key="user.lastName"/><span class="required">*</span></label>
		                <form:input cssClass="form-control" path="lastName" id="lastName" maxlength="50"/>
		                <form:errors path="lastName" cssClass="help-block"/>
		        	</div>
		        </div>
        </div>
        
        <div class="form-group">
			<input type="checkbox" name="business" id="business" />
			<div style="padding-left:50px;">For Business Use</div>		
		</div>	 
        
        <!--
       	 <div class="form-group">
               <label for="phoneNumber" class="control-label"><fmt:message key="user.phoneNumber"/></label>
               <form:input cssClass="form-control" path="phoneNumber" id="phoneNumber"/>
       	</div>

        <div data-role="collapsible" data-mini="true" data-collapsed="false" >
        	<h3>Address</h3>
			<div class="ui-grid-a">
               <div class="ui-block-a">
		        	<div class="form-group">
	                    <label for="address.address" class="control-label"><fmt:message key="user.address.address"/></label>
	                    <form:input path="address.address" id="address.address"/>
	                </div>
	           </div>
	     		<div class="ui-block-b">
	     			<div class="form-group">
                		<label for="address.city" class="control-label"><fmt:message key="user.address.city"/></label>
                        <form:input path="address.city" id="address.city"/>	    
                    </div> 		
	     		</div>
		     </div>
			<div class="ui-grid-b">
               <div class="ui-block-a">
		        	<div class="form-group">
                        <label for="address.province" class="control-label"><fmt:message key="user.address.province"/></label>
                        <form:input path="address.province" id="address.province"/>
                    </div>
                </div>
	     		<div class="ui-block-b">
	     			<div class="form-group">
                        <label for="address.postalCode" class="control-label"><fmt:message key="user.address.postalCode"/></label>
                        <form:input path="address.postalCode" id="address.postalCode"/>
                	</div>
                </div>
                <div class="ui-block-c">
                	<div class="form-group">
                    	<label for="address.country" class="control-label"><fmt:message key="user.address.country"/></label>
                    	<form:input path="address.country" id="address.country" />
                	</div>
                </div>
        	</div>
        </div>	
        -->
		<div class="form-group">
			<input name="agreeToTermsOfService" type="checkbox" id="user_terms_of_service" />
			<div style="padding-left:50px;">By checking this box, I agree to the terms of use and privacy policy.</div>	
			<div style="color:red" id="user_terms_of_service_error"></div>		
		</div>	        
		
        <div class="form-group">
			<button onclick="checkAgree();" class="btn btn-primary" name="save"><fmt:message key='button.register'/></button>	            
            <button type="button" name="cancel" class="btn btn-default" onclick="window.location.href='/lottery/login.htm'">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </button>
        </div>
	     
    </form:form>
    </div>
</div>

</div>

</body>
</html>

