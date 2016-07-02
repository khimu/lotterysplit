		

<%@ include file="/common/include.jsp"%>

<head>
    <title><fmt:message key="ticket.split"/></title>

    <meta name="menu" content="Home"/>
</head>
<body class="ticketdetail">

    <div data-role="page" id="ticketDetailPage" data-theme="d"> 
    
   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>    
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>Ticket Detail</h1> 
         	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/split.htm" data-role="button" data-icon="split" data-iconpos="right"><fmt:message key="list.lottery"/></a>
         </div> 
         
         <div data-role="content">    
         <!-- 
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		-- genie-mobile-middle --
		<ins class="adsbygoogle"
		     style="display:inline-block;width:320px;height:50px"
		     data-ad-client="ca-pub-2178827339426285"
		     data-ad-slot="2604799514"></ins>
		<script>
		(adsbygoogle = window.adsbygoogle || []).push({});
		</script>		
		 --> 

			<div class="ui-grid-a">
                <div class="ui-block-a">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/list.htm?start=1" data-role="button">Buy Tickets</a>
                </div>
                <div class="ui-block-b">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/mylist.htm?start=1" data-role="button">Sell Tickets</a>                   
                </div>
			</div>   			
			
			<div style="clear: both; padding: 10px;"></div>
			<c:if test="${not empty message}">
				<div class="error">${message}</div>
			</c:if>
			<div style="clear: both; padding: 5px;"></div>
						
						

		
				<ul class="list-unstyled">
			        <li>
			        	<img src="${pageContext.request.contextPath}/lottery/ticket/${ticket.id}" width="50%"/>
						<div class="post-info">						
			        		<div class="post-basic-info">
				        		<h3>${ticket.lottoGameName}</h3>
									    <div class="page-nav">
									      	<div class="page-pigmintation top_page_nav" id="lotteryDivNumId${ticket.id}">										
											</div>
										</div>	
										<div style="clear:both"></div>					        		
								    <script>
									$(document).ready(function(){       
										var numbers = '${ticket.numbers}';
										if(numbers != ''){
											var array = numbers.split(' ');
											var lotteryNumbers = "<ul>";
											for(var i = 0; i < array.length; i ++){
												lotteryNumbers += '<li><a href="#" style="color:black;background-image: url(/images/whiteball.jpeg);background-repeat:no-repeat;background-size:40px 40px;">'+array[i]+'</a></li>';
											}
											lotteryNumbers += "</ul>";   
											$('#lotteryDivNumId${ticket.id}').html(lotteryNumbers);
										}
									});
								    </script>
								<span><label>Draw Date:</label> &nbsp;&nbsp; <a href="javascript:return false;"><fmt:formatDate value="${ticket.ticketDate}" pattern="MM/dd/yyyy"/></a></span><br/>
				        		<span><label>Seller Referral Code</label> &nbsp;&nbsp; <a href="javascript:return false;">${ticket.referralCode}</a></span><br/>
				        		<c:if test="${ticket.flag}"><p style="color:red">Warning : Ticket is Flagged</p></c:if>
								<div class="comment_desc">
									<p style="font-size:12pt;">
										<a href="#">Pool Size: ${ticket.splitNumber}</a>
										<a href="#">${ticket.splitNumber - ticket.buyerCount} LEFT</a>
										<a href="#"><fmt:setLocale value="en_US"/><fmt:formatNumber value="${ticket.cost}" type="currency"/> / split</a>
									</p>
									<p>
				                        <c:set var="amount">
				                                <fmt:setLocale value="en_US"/><fmt:formatNumber value="${ticket.cost}" type="currency"/>
				                        </c:set>									
										<button data-inline="true" data-mini="true" onclick="javascript: post('Lottery pool ticket ${ticket.lottoGameName} only ${amount}. Pool size ${ticket.splitNumber}.  Buy Here: http://www.splitlottery.com//lottery/ticket/buy/${ticket.id}');" >FB Share</button>
										<c:if test="${isBusiness == true}">
											<button data-inline="true" data-mini="true"  data-type="button" onclick="window.location.href='/lottery/campaign/create.htm?lotteryTicketId=${ticket.id}';" >Create Campaign</button>
										</c:if>									
									</p>
								</div>	
			        		</div>

			        	</div>
					</li>    
					</ul>    
					<br/>

			<c:if test="${not empty lister}">
			<div class="content_top-grid3">
				<div class="profile"
					style="text-shadow: #FFFFFF 0 0 0; color: white;">
	 				<div class="profile-details">
						<div class="profile_picture">
							<div class="profile_picture_name">
								<h2>
									<a href="#" style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">${lister.firstName} ${lister.lastName}</a>
								</h2>
								<p>
									<a href="#" style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">Split Lottery Ticket Owner</a>
								</p>
							</div>
							<a href="" style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">
								<img src="http://graph.facebook.com/${lister.facebookId}/picture" alt="" />
							</a>
						</div>
					</div>
				</div>
			</div>
			</c:if>

		</div>
		
		<%@ include file="/common/footer.jsp" %>
	</div>

</body>