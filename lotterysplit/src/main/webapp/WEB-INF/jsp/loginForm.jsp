
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
		</div> <!-- content -->

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
		</div> <!-- page -->

	</body>
</html>
