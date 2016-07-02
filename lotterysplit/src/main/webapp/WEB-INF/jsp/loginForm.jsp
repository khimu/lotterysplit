<%@ include file="/common/include.jsp" %>

		<div data-role="page" id="loginPage" data-theme="d">
		
			 <%@ include file="/common/publicmenu.jsp" %>
	         <div data-role="header" data-position="fixed" data-theme="d">
	         		<a href="#nav-panel" data-theme="d" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
	               <h1 style="color:white"><fmt:message key="online.lottery.title"/></h1> 
	               <a data-ajax="false" href="#login-div" style="color:black;">Login</a>
	         </div> 

	        <div data-role="content" style="padding:0px;background-color:#EEEEEE;"> 
	        
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- sl -->
<ins class="adsbygoogle"
     style="display:inline-block;width:320px;height:50px"
     data-ad-client="ca-pub-2178827339426285"
     data-ad-slot="6234609912"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>

								    <script>
									$(document).ready(function(){       
								 		  $.ajax({
								 			  url:ctx + '/lottery/powerball.htm',
								 			  type: "GET",
								 			  success:function(result){
								 				  $('#powerballWinningId').html(result);
								 			  }, error: function(XMLHttpRequest, textStatus, errorThrown){
							                        alert('Failure');
							                  }
								 		  });

								 		  $.ajax({
								 			  url:ctx + '/lottery/megamillion.htm',
								 			  type: "GET",
								 			  success:function(result){
								 				  $('#megaMillionWinningId').html(result);
								 			  }, error: function(XMLHttpRequest, textStatus, errorThrown){
							                        alert('Failure');
							                  }
								 		  });
								 		  
									});
									

									$(window).load(function() { //start after HTML, images have loaded
									 
									    var InfiniteRotator =
									    {
									        init: function()
									        {
									            var initialFadeIn = 1000;
									            var itemInterval = 5000;
									            var fadeTime = 2500;
									            var numberOfItems = $('.rotating-item').length;
									            var currentItem = 0;
									            $('.rotating-item').eq(currentItem).fadeIn(initialFadeIn);
									            var infiniteLoop = setInterval(function(){
									                $('.rotating-item').eq(currentItem).fadeOut(fadeTime);
									                if(currentItem == numberOfItems -1){
									                    currentItem = 0;
									                }else{
									                    currentItem++;
									                }
									                $('.rotating-item').eq(currentItem).fadeIn(fadeTime);
									            }, itemInterval);
									        }
									    };
									 
									    InfiniteRotator.init();
									});


								    </script>
<div class="form-signin" style="text-align:center:color:red">Internet Explorer not supported</div>

<c:if test="${not empty ticket}">
						<div class="post-info form-signin">						
			        		<div class="post-basic-info">
								<div class="comment_desc">
									<p style="color:black">
									    <div class="page-nav">
									    	<span class="page-pigmintation top_page_nav" id="lotteryDivNumId${ticket.id}"></span>
									    	<button data-inline="true" data-mini="true" onclick="javascript: confirmBuy('${ticket.lottoGameName}', '${ticket.id}');" id="confirm-actuator-buy" >Join FREE</button>
									    </div>	
										<div style="clear:both"></div>		
								    <script>
									$(document).ready(function(){       
										var numbers = '${ticket.numbers}';
										if(numbers != ''){
											var array = numbers.split(' ');
											var lotteryNumbers = "<ul>";
											for(var i = 0; i < array.length; i ++){
												lotteryNumbers += '<li><a title="${ticket.lottoGameName}" alt="${ticket.lottoGameName}" href="#" style="color:black;background-image: url(/images/whiteball.jpeg);background-repeat:no-repeat;background-size:40px 40px;">'+array[i]+'</a></li>';
											}
											lotteryNumbers += "</ul>";   
											$('#lotteryDivNumId${ticket.id}').html(lotteryNumbers);
										}
									});
									
								    </script>
									</p>
								</div>	
			        		</div>
			        	</div>
			        	<div style="clear:both"></div>
			        	<br/>
</c:if>




	
					<br/>		
					
					
				<div class="form-signin" id="login-div" style="background-color:white;padding:5px;-moz-border-radius: 15px;border-radius: 15px;">
					<input name="agreeToTermsOfService" type="checkbox" id="user_terms_of_service" />
					<div style="padding-left:50px;">By logging in with facebook, I agree to the terms of use and privacy policy.</div>			
				    <button class="btn btn-lg btn-default btn-block" onclick="checkAgreement();"><fmt:message key='button.facebook.login'/></button>
				    <div class="error_msg" id="user_terms_of_service_error"></div>
				    <p class="form-signin"><fmt:message key="updatePassword.requestRecoveryTokenLink"/></p>
				    <p class="form-signin"><fmt:message key="login.signup"><fmt:param><c:url value="/lottery/signup.htm"/></fmt:param></fmt:message></p>
                </div>		
                
                <script>
                	function checkAgreement(){
                		if($('#user_terms_of_service').is(':checked') == true){
                			fbLogin();
                			return false;
                		}else{
                			alert('Please agree to the terms of use and privacy policy');
                		}
                	}
                </script>	
		
                        
				<form:form data-ajax="false" id="loginForm" action="/lottery/login.htm" commandName="loginForm" cssClass="form-signin" onsubmit="saveUsername(this);return validateForm(this)">

					<spring:hasBindErrors name="loginForm">
						<div class="alert alert-danger alert-dismissable">
						<form:errors path="password" /><br><form:errors path="username" />
						</div>
					</spring:hasBindErrors>
					
					<c:if test="${param.error != null}">
					    <div class="alert alert-danger alert-dismissable">
					        <fmt:message key="errors.password.mismatch"/>
					    </div>
					</c:if>
					
			    	<form:input id="username" path="username" maxlength="50" placeholder="Email" cssClass="form-control" width="100px;" required="" />
			    	<form:password id="password" path="password" maxlength="30" placeholder="Password" cssClass="form-control" required=""/> 
			    	
				    <button type="submit" class="btn btn-lg btn-primary btn-block" name="login" tabindex="4">
				        <fmt:message key='button.login'/>
				    </button>	 

				</form:form>

                

                			<div style="clear:both"></div>	
               <!--
				<div class="form-signin">
					<div class="priceing-tabel1 priceing-tabel1-blue">
						<div class="priceing-header">
							<h4>Benefits Of Joining</h4>
							<a href="#" style="text-decoration:none;color:white;">
								<span class="dollor">Secure</span>
								<span class="dollor">Easy</span>
								<span class="dollor">Mobile Friendly</span>
							</a>
						</div>
						<ul>
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">We use a strong password encryption</a>
							</li>
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">We do not store credit card information</a>
							</li>							
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">We have convenient payments</a>
							</li>	
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">We support all smart phone devices</a>
							</li>	
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">Catalog your lottery tickets</a>
							</li>		
						</ul>						
						<a class="price-btn price-btn-blue popup-with-zoom-anim" href="#small-dialog">
						</a>
					</div>
					
				</div>
					<div style="clear:both"></div>	  

			   	<div class="form-signin">
							  <div class="news">
							  	  <h3>How It Works</h3>
							  	 <ul>
							  	   <li>
							  	   	  <div class="news_desc">
							  	   	  	 <h4><a href="#">Lottery Lister</a></h4>
							  	   	  	 <p>Pick up lottery ticket from store</p>
							  	   	  	 <p>List lottery ticket on splitlottery.com</p>
							  	   	  	 <p>Set lottery ticket pool size between 1 and 100</p>
							  	   	  	 <p>Earn .10 cent credit towards any <fmt:message key="company.name"/> lottery pool</p>
							  	   	  	 <p>Once the pool size is filled, you will earn back your dollar</p>
							  	   	  	 <p>When you win, you will split lottery winning with those in the lottery pool.</p>
							  	   	  	 <p>The percentage payout is divided by the pool size + 1.</p>
							  	   	  	 <p>Any empty pool slot goes back to original lottery ticket buyer</p> 
							  	   	  	 <p>Track your transactions, earnings, and spendings under "Account Balance"</p> 
							  	   	  </div>
							  	   	  <div class="clear"> </div>
							  	   </li>
							  	   <li>
							  	   	<br/>
							  	   	  <div class="news_desc">
							  	   	  	 <h4><a href="#">Lottery Buyer</a></h4>
											<p>Track all lottery tickets you bought under "Bought Tickets"</p>
											<p>Contact info.splitlotto@gmail.com with winning ticket</p>
											<p>Once the cash is paied out to lottery ticket holder, we will contact you with further actions</p>		
											<p>Track your transactions, earnings, and spendings under "Account Balance"</p> 
											<p>When you run out of cash, add more funds using paypal under "Add Cash"</p>				  	   	  
										</div>
							  	   	  <div class="clear"> </div>
							  	   </li>		
							  	   <li>
							  	   	<br/>
							  	   	  <div class="news_desc">
							  	   	  	 <h4><a href="#">Ticket Orders</a></h4>
											<p>Enter your ticket order</p>
											<p>Wait for the ticket to be processed and purchased</p>
											<p>You will see the lottery ticket in your "My List"</p>
											<p>Only winning tickets will be mail first class to verified address</p>
										</div>
							  	   	  <div class="clear"> </div>
							  	   </li>							  	   
							  	   <li>
							  	   	<br/>
							  	   	  <div class="news_desc">
							  	   	  	 <h4><a href="#">Permission and Privacy</a></h4>
											<p>Create a private group to list your ticket under</p>
											<p>Only members of your group will be able to see your ticket</p>
											<p>Order a private ticket and only you will have full rights to the winning</p>					  	   	  
										</div>
							  	   	  <div class="clear"> </div>
							  	   </li>
									<li>
							  	   	<br/>
							  	   	  <div class="news_desc">
							  	   	  	 <h4><a href="#">Referral Reward</a></h4>
											<p>Referee must be a lottery pool buyer to count</p>	
											<p>Cash is distributed once data is verified</p>		  	   	  
										</div>
							  	   	  <div class="clear"> </div>
							  	   </li>		   
							  	  </ul>
							  </div>
							  <div style="clear:both"> </div>
							  <div class="form-signin subtagline"><small>Beta Release</small></div>
							  <div style="clear:both"> </div>
				</div>	
				
				<div class="form-signin" id="freelotteryPoolId">
					<div class="priceing-tabel1 priceing-tabel1-green">
						<div class="priceing-header">
							<h4>Free Lottery Pool</h4>
						</div>
						<ul>
							<li>
								<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">Join a lottery pool every week for a chance to win</a>
							</li>
						</ul>						
					</div>
				</div>
				<div style="clear:both"></div>	

				<div class="form-signin" id="cashRewardId">
					<div class="priceing-tabel1 priceing-tabel1-green">
						<div class="priceing-header">
							<h4>Cash Reward</h4>
							<a href="#" style="text-decoration:none;color:white;">
								<span class="dollor">$</span>
								100
								<span>00</span>
								<label class="black"> cash</label>
							</a>
						</div>
						<ul>
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">for every 1000 referrals</a>
							</li>					
						</ul>						
						<a style="color:black;text-shadow:#FFFFFF 0 0 0;" class="price-btn price-btn-green popup-with-zoom-anim" href="#small-dialog">sign up and start referring</a>
					</div>
				</div>
				<div style="clear:both"></div>	
		
				<div class="form-signin" id="lotteryPoolCampaignId">
					<div class="priceing-tabel1 priceing-tabel1-green">
						<div class="priceing-header">
							<h4>Lottery Pool Campaign</h4>
						</div>
						<ul>
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">Reward your users with a chance to split your lottery winning.</a>
							</li>
						</ul>	
						<a style="color:black;text-shadow:#FFFFFF 0 0 0;" class="price-btn price-btn-green popup-with-zoom-anim" href="/app/script">Integration Script</a>					
					</div>
				</div>
				<div style="clear:both"></div>		
				
				
				<div class="form-signin" id="fbLikeCreditId">
					<div class="priceing-tabel1 priceing-tabel1-green">
						<div class="priceing-header">
							<h4>Reward</h4>
							<a href="#" style="text-decoration:none;color:white;">
								<span class="dollor">$</span>
								1
								<span>00</span>
								<label class="black"> credit</label>
							</a>
						</div>
						<ul>
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">when you like us on facebook</a>
							</li>
						</ul>						
						<a class="price-btn price-btn-green popup-with-zoom-anim" href="#small-dialog">
							<div class="fb-like" data-href="http://www.splitlottery.com" data-layout="button_count" data-action="like" data-show-faces="true"  data-share="true" ></div>          
						</a>
					</div>
					-->
				</div>
				<div style="clear:both"></div>
						
	</div>
	<%@ include file="/common/footer.jsp" %>

		
<script type="text/javascript">

    if ($.cookie("username") != null && $.cookie("username") != "") {
        $("#username").val($.cookie("username"));
        $("#password").focus();
    } else {
        $("#username").focus();
    }
    
    function saveUsername(theForm) {
        $.cookie("username",theForm.username.value, { expires: 30, path: "<c:url value="/"/>"});
    }
    
    function validateForm(form) {                                                               
        var valid = validateRequired(form);
        if (valid == false) {
            $(".form-group").addClass('error');
        }
        return valid;
    }

    function passwordHint() {
    	alert($("#username").val());
    	
        if ($("#username").val().length == 0) {
            alert("<fmt:message key="errors.required"><fmt:param><fmt:message key="label.username"/></fmt:param></fmt:message>");
            $("#username").focus();
        } else {
            location.href="<c:url value="/lottery/passwordHint"/>?username=" + $("#username").val();
        }
    }
    
    function requestRecoveryToken() {
        if ($("#username").val().length == 0) {
            alert("<fmt:message key="errors.required"><fmt:param><fmt:message key="label.username"/></fmt:param></fmt:message>");
            $("#username").focus();
        } else {
            location.href="<c:url value="/lottery/requestRecoveryToken"/>?username=" + $("#username").val();
        }
    }  
    
    function required () { 
        this.aa = new Array("username", "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.username"/></fmt:param></fmt:message>", new Function ("varName", " return this[varName];"));
        this.ab = new Array("password", "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.password"/></fmt:param></fmt:message>", new Function ("varName", " return this[varName];"));
    } 
    
</script>
	</div>

	</body>
</html>
