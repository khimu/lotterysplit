<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%@ page import="urn.ebay.apis.eBLBaseComponents.ErrorType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang='en'>
<head>
<meta charset='utf-8' />
<title>Website Payments Pro Hosted sample</title>
<meta content='width=device-width, initial-scale=1.0' name='viewport' />
<meta content='Website Payments Pro Hosted sample' name='description' />
<meta content='' name='author' />
<!-- Le styles -->
<link href='css/bootstrap.css' rel='stylesheet' />
<link href='css/bootstrap-responsive.css' rel='stylesheet' />
<link href='css/home.css' rel='stylesheet' />
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body onload="populateValues();">

	<%
		URL currentURL = new URL(request.getRequestURL().toString());
		URL returnURL = new URL(currentURL, "index.html");
		String email = (String) request.getAttribute("email");
		String website = (String) request.getAttribute("website");
		boolean displayIframe = (Boolean) (request
				.getAttribute("displayIframe") != null ? request
				.getAttribute("displayIframe") : false);
		List<ErrorType> errorList = (List<ErrorType>) request
				.getAttribute("Error");
	%>
	<div class='container-fluid'>
		<div class='row-fluid'>
			<div class='span4 sidebar pull-right'>

				<h5>Website Payments Pro Hosted Solution</h5>

				<small> You can quickly set up your website to receive
					payments from your customers by doing the following: </small>
				<ol>
					<li><small> When the buyer clicks to purchase the
							item, call <code>BMCreateButton</code> from the <em>Button
								Manager API.</em>
					</small></li>
					<li><small> Redirect the buyer to the email link URL
							or use the website code. Both are returned in the response.</small></li>
				</ol>

				<p>Use the forms in the left panel to simulate these steps and
					continue reading below for more details.</p>

				<hr />
				<div id="step1">
					<h5>Step 1:</h5>
					<small> PayPal recommends using the <em>Button Manager
							API</em> to programmatically initiate the <em>Website Payments
							Pro Hosted Solution</em> checkout flow on your website. When the buyer
						clicks on your checkout button, call the <code>BMCreateButton</code>
						(<a
						href="https://www.x.com/developers/paypal/documentation-tools/api/bmcreatebutton-api-operation-nvp">NVP</a>
						| <a
						href="https://www.x.com/developers/paypal/documentation-tools/api/bmcreatebutton-api-operation-soap">SOAP</a>)
						API operation and pass information about the purchase in the
						request. You can use the <a
						href="https://www.x.com/developers/paypal/documentation-tools/paypal-sdk-index">ButtonManager
							SDK</a> to perform this step.
					</small> <br />

					<h5>BMCreateButton API request parameters</h5>
					<ul>
						<li><small>Button Type must be 'PAYMENT'.</small></li>
						<li><small>Button Code can be set to one of these
								'TOKEN', 'ENCRYPTED' or 'CLEARTEXT'. ('HOSTED' is not allowed).</small></li>
						<li><small>You have the option to display your PayPal
								payment page within an iframe on your website. If you would like
								to use this option, be sure to pass <code>TEMPLATE=templateD</code>
								in the button variables list.
						</small></li>
					</ul>
					<h5>Recommended Best Practices:</h5>
					<ul>
						<li><small>If you have already collected the buyer's
								shipping address, PayPal recommends that you pass it to your
								PayPal Payment Page so the buyer does not have to re-enter this
								information. </small></li>
						<li><small>The return URL is the page on your website
								where you would like the buyer to return to from the PayPal
								payment page. This is an optional field, but PayPal recommends
								you pass <code>return=http://www.yourcompany.com/returnpage.php</code>
								in the button variables list.
						</small></li>
						<li><small>PayPal also recommends that you pass line
								item and cost details to your PayPal payment page. This includes
								tax and shipping costs. When you show the buyer the breakdown of
								the charges, the buyer is more likely to go through with the
								payment. </small></li>
					</ul>

					<small>You can pass this recommended information and other
						values in the button variables list. For a complete list of
						Payment Page information and configuration variables please refer
						to: <a
						href="https://cms.paypal.com/cms_content/GB/en_GB/files/developer/HostedSolution.pdf#page=17"><em>here</em></a>
						and <a
						href="https://cms.paypal.com/cms_content/GB/en_GB/files/developer/HostedSolution.pdf#page=40"><em>here</em></a>
					</small> <br />

					<hr />
				</div>
				<div id="step2" style='display: none;'>

					<h5>Step 2:</h5>
					<small>Direct the buyer to your PayPal payment page where
						he or she can complete the payment. You can choose one of the
						following options: </small>
					<h5>BMCreateButton API response parameters</h5>
					<ol>
						<li><small>Use the email link URL (SOAP: <code>Email</code>
								, NVP: <code>EMAILLINK</code> ) - <strong><em>Recommended</em></strong>:
								Redirect the buyer to your PayPal Payment page using the URL
								returned in the response.
						</small></li>
						<li><small>Use the website code (SOAP: <code>Website</code>,
								NVP: <code>WEBSITECODE</code>): <br /> This code is a button
								form that you can display on your web site. The buyer will be
								redirected to your PayPal payment page only after clicking the
								button.
						</small></li>
					</ol>

					<hr />

					<h5>Next Steps:</h5>
					<ul>
						<li><small>Download the <a
								href="https://www.x.com/developers/paypal/documentation-tools/paypal-sdk-index">Button
									Manager API SDK</a>.
						</small></li>
						<li><small>To learn more about using the Button
								Manager API with Website Payments Pro Hosted Solution, see
								Chapter 5 of the <em><a
									href="https://cms.paypal.com/cms_content/GB/en_GB/files/developer/HostedSolution.pdf">integration
										documentation</a></em>.
						</small></li>
					</ul>
				</div>
			</div>



			<div class='span8 content'>

				<ul class='nav nav-tabs'>

					<li id="form-li" class="active"><a data-toggle='tab'
						href='#form-1' onclick="getDoc();">Form</a></li>
					<li id="response-li"><a data-toggle='tab' href='#response-1' onclick="getDoc();">Response</a></li>
					<!-- <div <%=(email != null || errorList != null) ? "style='display:none;'"
					: ""%>></div>-->
					<a style="float: right" href="#myModal" role="button"
						class="btn btn-primary" data-toggle="modal"
						onclick="populateValues();">API Credentials</a>
				</ul>

				<div class='tab-content'>
					<form>
						<div id="myModal" class="modal hide fade" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h3>API Credentials</h3>
							</div>
							<div class="modal-body form-horizontal">

								<div class="control-group">
									<label class="control-label" for="userName">UserName</label>
									<div class="controls">
										<input type="text" id="userName" name="userName" value='' />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="password">Password</label>
									<div class="controls">
										<input type="text" id="password" name="password" value='' />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="signature">Signature</label>
									<div class="controls">
										<input type="text" id="signature" name="signature" value='' />
									</div>
								</div>

							</div>
							<div class="modal-footer">
								<a href="#" class="btn btn-primary" data-dismiss="modal"
									onclick="setValues();">Ok</a> <a href="#" class="btn "
									data-dismiss="modal">Cancel</a>
							</div>
						</div>
					</form>
					<div class='tab-pane active' id='form-1'>

						<form name="formAction" method="post">
							<input type="hidden"
								value="<%=request.getParameter("userName")%>"></input>
							<fieldset>
								<legend>Payment Options</legend>
								<div class='control-group select optional'>
									<div class="controls controls-row">
										<label class='select optional control-label span3'
											for='PaymentAction'>ButtonType</label> <label
											class='select optional control-label span3'>ButtonCode</label>
									</div>
									<div class='controls controls-row'>
										<input class='string input-small optional span3'
											id='buttontype' name='buttontype' type='text' value='PAYMENT'
											readonly="readonly" /> <select
											class='select input-small optional span3' id='buttoncode'
											name='buttoncode'>
											<option value=''></option>
											<option selected='selected' value='TOKEN'>TOKEN</option>
											<option value='ENCRYPTED'>ENCRYPTED</option>
											<option value='CLEARTEXT'>CLEARTEXT</option>
										</select>
									</div>
								</div>

								<div class='control-group string optional '>
									<div class="controls controls-row">
										<label class='string optional control-label span2'>SubTotal</label>
										<label class='string optional control-label span2'>Shipping</label>
										<label class='string optional control-label span2'>Tax</label>
										<label class='string optional control-label span2'>Handling</label>
										<label class='string optional control-label span2'>Currency
											Code</label>
									</div>
									<div class='controls controls-row'>
										<input class='string optional span2' id='subtotal'
											name='subtotal' type='text' value='2.0' /> <input
											class='string optional span2' id='shipping' name='shipping'
											type='text' value='2.0' /> <input
											class='string optional span2' id='tax' name='tax' type='text'
											value='2.0' /> <input class='string optional span2'
											id='handling' name='handling' type='text' value='2.0' /> <input
											class='string optional span2' id='currencyCode'
											name='currencyCode' type='text' value='USD' />
									</div>
								</div>
								<legend>Shipping Address</legend>

								<div class='control-group string optional'>
									<label class='string optional control-label'>Street1</label>
									<div class='controls'>
										<input class='string optional' id='street1' name='street1'
											type='text' value='Ape Way' />
									</div>
								</div>
								<div class='control-group string optional'>
									<label class='string optional control-label'>Street2</label>
									<div class='controls'>
										<input class='string optional' id='street2' name='street2'
											type='text' value='' />
									</div>
								</div>
								<div class='control-group string optional'>
									<label class='string optional control-label'>City</label>
									<div class='controls'>
										<input class='string optional' id='city' name='city'
											type='text' value='Austin' />
									</div>
								</div>
								<div class='control-group string optional'>
									<label class='string optional control-label'>State</label>
									<div class='controls'>
										<input class='string optional' id='state' name='state'
											type='text' value='TX' />
									</div>
								</div>
								<div class='control-group string optional'>
									<label class='string optional control-label'>Country</label>
									<div class='controls'>
										<input class='string optional' id='country' name='country'
											type='text' value='US' />
									</div>
								</div>
								<div class='control-group string optional'>
									<label class='string optional control-label'>Zip</label>
									<div class='controls'>
										<input class='string optional' id='zip' name='zip' type='text'
											value='785432' />
									</div>
								</div>

								<div class='control-group string optional'>
									<label class='string optional control-label'>Return
										URL(optional)</label>
									<div class='controls'>
										<input class='string input-xlarge' id='returnURL'
											name='returnURL' type='text' value='<%=returnURL%>' />
									</div>
								</div>
								<div class='control-group string optional'>
									<label class="checkbox"> <input type="checkbox"
										id="checkboxTemplate" name="checkboxTemplate"
										value="templateD" />Enable payments within your website
									</label>
								</div>
								<div class='control-group string optional' id="afterbutton">
									<input class="btn btn-primary" type='button' value='Submit'
										onclick="doSubmit();" />

								</div>
							</fieldset>
						</form>
					</div>


					<div style="overflow: hidden" class='tab-pane' id='response-1'>
						<div id="error" style='display: none;'>
							<table>
								<tr>
									<td><small>ErrorCode:</small></td>
									<td id="errorCode"></td>
								</tr>
								<tr>
									<td><small>ShortMsg:</small></td>
									<td id="shortMsg"></td>
								</tr>
								<tr>
									<td><small>LongMsg:</small></td>
									<td id="longMsg"></td>
								</tr>
							</table>
						</div>
						<div <%=errorList == null ? "style='display:none;'" : ""%>>
							<%
								if (errorList != null && errorList.size() != 0) {
									for (ErrorType e : errorList) {
							%>
							<table>
								<tr>
									<td><small>ErrorCode:</small></td>
									<td><small><%=e.getErrorCode()%></small></td>
								</tr>
								<tr>
									<td><small>ShortMsg:</small></td>
									<td><small><%=e.getShortMessage()%></small></td>
								</tr>
								<tr>
									<td><small>LongMsg:</small></td>
									<td><small><%=e.getLongMessage()%></small></td>
								</tr>
							</table>

							<%
								}
								}
							%>
						</div>
						<div id="response" style='display: none;'>
							<p>
								<small>ButtonManager API response contains Email and
									Website as response parameters. When user click either of
									these, he or she will be redirected to PayPal payment hosted
									checkout page.You can integrate Email link(recommended) or
									website with iframe.</small>
							</p>
							<hr />
							<div id="email">
								<label class='string control-label'><small><u>Email
											Link</u></small></label>
								<textarea id="emailInner" style="width: 590px" rows='8'
									class='code yaml input-xxlarge'>
									</textarea>
							</div>
							<div id="emailIframe"></div>
							<div id="website">
								<label class='string control-label'><small><u>Website
											Button form code</u></small></label>
								<textarea id="websitetext" rows='8'
									class='code yaml input-xxlarge'></textarea>
							</div>
							<div id="websiteIframe"></div>






						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src='js/jquery-1.8.3.min.js' type='text/javascript'></script>
	<script src='js/bootstrap.js' type='text/javascript'></script>
	<script src='js/hss.js' type='text/javascript'></script>

</body>
</html>