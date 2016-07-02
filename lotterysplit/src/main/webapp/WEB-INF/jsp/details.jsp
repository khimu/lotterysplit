<%@ include file="/common/include.jsp" %>

		<div data-role="page" id="loginPage" data-theme="d" data-add-back-btn="true">
		
			 <%@ include file="/common/publicmenu.jsp" %>
	         <div data-role="header" data-position="fixed" data-theme="d"> 
	               <h1><fmt:message key="online.lottery.title"/></h1> 
	         </div> 

	        <div data-role="content"> 
	        
			<div class="blog_button">
				<a data-ajax="false" href="#login-div" style="color:black;">Try Now</a>
			</div>
			<div class="clear"></div>

				<div class="form-signin">
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

				<div class="form-signin">
					<div class="priceing-tabel1 priceing-tabel1-green">
						<div class="priceing-header">
							<h4>Limited Time Only</h4>
						</div>
						<ul>
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">Free Play.  Join any available pool</a>
							</li>
						</ul>						
					</div>
					
				</div>
				<div style="clear:both"></div>	
	
				<div class="form-signin">
					<div class="priceing-tabel1 priceing-tabel1-green">
						<div class="priceing-header">
							<h4>Lottery Pool Campaign</h4>
						</div>
						<ul>
							<li>
							<a href="#" style="color:black;text-shadow:#FFFFFF 0 0 0;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">Reward your users with a chance to split your lottery winning.</a>
							</li>
						</ul>	
						<a style="color:black;text-shadow:#FFFFFF 0 0 0;" class="price-btn price-btn-green popup-with-zoom-anim" href="#/app/script">Integration Script</a>					
					</div>
				</div>
				<div style="clear:both"></div>	
				
				<div class="form-signin">
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
					
				</div>
					<div style="clear:both"></div>					
		
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
		
	</div>
	<%@ include file="/common/footer.jsp" %>
	</div>

	</body>
</html>
