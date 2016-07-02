<%@ page import="javax.servlet.ServletContext"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<%@ include file="/common/include.jsp"%>


    <div data-role="page" id="payment" data-theme="d"> 
    
    	<%@ include file="/common/menu.jsp" %>
    	
		<div data-role="header" data-position="fixed" data-theme="d">
			<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
			<h1>Add Funds</h1>
			<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/split.htm" data-role="button" data-icon="split" data-iconpos="right"><fmt:message key="list.lottery"/></a>
		</div>

		<div data-role="content">		
		
			<div class="ui-grid-a">
                <div class="ui-block-a">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/payment/form.htm" data-role="button">Add Cash</a>
                </div>
                <div class="ui-block-b">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/report/payments.htm?start=1" data-role="button">Payment History</a>                   
                </div>
			</div>   
			
			<p class="info">Balance:  <fmt:setLocale value="en_US"/><fmt:formatNumber value="${balance}" type="currency"/></p>	
		
		<%@ page import="net.authorize.sim.*"%>

<%

String apiLoginId = (String)request.getAttribute("apiLoginId");
/*
HttpSession fundsSession = request.getSession();       
ServletContext sc = fundsSession.getServletContext();
ServletContext servletContext =this.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
*/
String authorizeNetUrl = "https://secure.authorize.net/gateway/transact.dll";

%>		

			<div class="col-sm-7">
	
			<br/>
			<ul data-role="listview" style="padding:10px;">
			<li><a data-ajax="false" href="javascript: fundAccount(5.00);" class="btn btn-primary" style="color:white;">Pay Using Paypal - $5.00</a></li>
<!-- 
			<li><a data-ajax="false" href="javascript: fundAccount(10.00);" class="btn btn-primary" style="color:white;">Pay Using Paypal - $10.00</a></li>
			<li><a data-ajax="false" href="javascript: fundAccount(15.00);" class="btn btn-primary" style="color:white;">Pay Using Paypal - $15.00</a></li>
			<li><a data-ajax="false" href="javascript: fundAccount(20.00);" class="btn btn-primary" style="color:white;">Pay Using Paypal - $20.00</a></li>
 -->
			</ul>
			<div id="div1"></div>

<%--
			<div data-role="collapsible" data-mini="true" data-collapsed="true" >
			<h3>For Credit Card</h3>
			
			<FORM class="well" data-ajax="false" NAME='secure_redirect_form' ID='secure_redirect_form_id' ACTION='<%=authorizeNetUrl%>' METHOD='POST'>
	
				<div class="form-group">
        		<label for="cardNum" class="control-label">Credit Card Number</label>
					<input type='text' class='required cc-number' name='x_card_num' id="cardNum" size='15' /> 
				</div>
				
				<div class="form-group">
					<label for="expDate" class="control-label">Card Exp. Date</label>
					<input type="text" class='required cc-exp' style='color:black;' name='x_exp_date' id="expDate" size='4' data-role="datebox" data-options='{"mode":"datebox", "useNewStyle":true}' /> 
				</div>

				<div class="form-group">
					<label for="cvv" class="control-label">CVV</label>
					<input type="text" class="required cc-cvc" name="x_card_code" id="cvv" size="9" />
				</div>

				<div class="form-group">
					<label for="amount" class="control-label">Amount</label>
					<input type='text' class='required numbers' name='x_amount' id="amount" size='9' value=''/>
				</div>			

				<div class="form-group">
					<label for="firstName" class="control-label">First Name</label>
					<INPUT TYPE='text' NAME='x_first_name' id="firstName" class=' required' VALUE=''/>	
				</div>			
				
				<div class="form-group">
					<label for="lastName" class="control-label">Last Name</label>
					<INPUT TYPE='text' NAME='x_last_name' class=' required' id="lastName" VALUE=''/>	
				</div>	
				
				<div class="form-group">
					<label for="address" class="control-label">Address</label>
					<INPUT TYPE='text' NAME='x_address' id="address" class=' required' VALUE=''/>	
				</div>		
				
				<div class="form-group">
					<label for="city" class="control-label">City</label>
					<INPUT TYPE='text' NAME='x_city' id="city" class=' required' VALUE=''/>		
				</div>
				
				<div class="form-group">
					<label for="state" class="control-label">State</label>
					<INPUT TYPE='text' NAME='x_state' id="state" class=' required' VALUE=''/>	
				</div>
				
				<div class="form-group">
					<label for="zipcode" class="control-label">Zip Code</label>
					<INPUT TYPE='text' NAME='x_zip' id="zipcode" class=' required numbers' VALUE=''/>			
				</div>
				
				<input type="hidden" name="x_country" id="country" value="United States" />
				
				<INPUT TYPE='HIDDEN' NAME='x_invoice_num' VALUE='<%=System.currentTimeMillis()%>'/>
				<INPUT TYPE='HIDDEN' NAME='x_relay_url' VALUE='http://www.splitlottery.com/lottery/${relayResponseUrl}'/> 
				<INPUT TYPE='HIDDEN' NAME='x_login' VALUE='${apiLoginId}'/> 
				
				<div id="fingerprint"></div>
				
				<INPUT TYPE='HIDDEN' NAME='x_version' VALUE='3.1'/> 
				<INPUT TYPE='HIDDEN' NAME='x_method' VALUE='CC'/> 
				<INPUT TYPE='HIDDEN' NAME='x_type' VALUE='AUTH_CAPTURE'/> 
				<INPUT TYPE='HIDDEN' NAME='x_test_request' VALUE='FALSE'/> 
				<INPUT TYPE='hidden' NAME='x_email' class='text' id="email" VALUE='${email}' />
				

				<div class="form-group">
					<input name="agreeToTermsOfService" type="checkbox" id="user_terms_of_service" />
					<div style="padding-left:50px;">By checking this box, I agree to the terms of use and privacy policy.	</div>			
				</div>				
				
				<div class="form-group">
					<div class="error_msg" id="user_terms_of_service_error"></div>
				</div>				
				
				<INPUT TYPE='button' class="btn btn-primary" NAME='buy_button' VALUE='Add Funds' onclick="calculateFingerprint();"/>

			</FORM>
			</div>
 --%>

		</div>

				
		
  <script>
  
  function validate_form() {
	    var valid = true;
	    
	    $('#secure_redirect_form_id .required').each(function(){
	    	var $spanVal = $(this).next();
	    	$spanVal.remove();
	    });

	    var cardType = $.payment.cardType($('.cc-number').val());
	    
	    $('#secure_redirect_form_id .required').each(function(){
	    	var $spanVal = $("#" + $(this).attr('id')).next();

	    	if($(this).hasClass('cc-number') == true){
	    		if(!$.payment.validateCardNumber($('.cc-number').val())){
	 	    	   if(!$spanVal.is("div.error_msg")){ //Create a validation message if it doesn't exist
	 	    		  $("#" + $(this).attr('id')).parent().append('<div class="error_msg">* Invalid Card Number</div>');
		               //$('<div class="error_msg">* Invalid Card Number</div>').insertAfter(this);
		            }
	 	    	  	valid = false;
		 	    	return false;
	    		}	    		
	    	}

	    	if($(this).hasClass('cc-exp') == true){
	    		if(!$.payment.validateCardExpiry($('.cc-exp').payment('cardExpiryVal'))){
		 	    	   if(!$spanVal.is("div.error_msg")){ //Create a validation message if it doesn't exist
			               $('<div class="error_msg">* Invalid Expiration Date</div>').insertAfter(this);
			            }
			 	    	valid = false;
				 	    return false;
	    		}
	    	}
	    	
	    	if($(this).hasClass('cc-cvc') == true){
	    		if(!$.payment.validateCardCVC($('.cc-cvc').val(), cardType)){
		 	    	   if(!$spanVal.is("div.error_msg")){ //Create a validation message if it doesn't exist
			               $('<div class="error_msg">* Invalid CVC Code</div>').insertAfter(this);
			            }
			 	    	valid = false;
				 	    return false;
	    		}
	    	}	    	
	    	
	    	if($(this).hasClass('numbers') == true){
	    	    var number = new Number();
	    	    number = Number($(this).val());
	    	    if (isNaN(number) == true){
	 	    	   if(!$spanVal.is("div.error_msg")){ //Create a validation message if it doesn't exist
		               $('<div class="error_msg">* Invalid Field Input</div>').insertAfter(this);
		            }
		 	    	valid = false;
			 	    return false;
	 	    	}
	    	}

	    	if ($(this).val() == ''){
	    	   if(!$spanVal.is("div.error_msg")){ //Create a validation message if it doesn't exist
	               $('<div class="error_msg">* Required Field</div>').insertAfter(this);
	            }	         
	 	    	valid = false;
		 	    return false;
	       }else{
	    	 $spanVal.remove();
	       }
	    });

	    return valid;
	}
  
  function calculateFingerprint(){
		if($('#user_terms_of_service').is(':checked') == true){
			  var amount = $('#amount').val();
			  var url = "${pageContext.request.contextPath}/lottery/payment/submitAmount.htm";

			  $.ajax({
				  type: "POST",
				  url: url,
				  data: {amount: amount}
				}).done(function ( data ) {	
					$('#fingerprint').html(data);

					if(validate_form() == true){
						$('#secure_redirect_form_id').submit();
					}
				});
		} else {
			$('#user_terms_of_service_error').html('Please accept our terms of service');
			return false;
		}
  }
  
  </script>					
  
	</div>
	
	<%@ include file="/common/footer.jsp" %>
</div>