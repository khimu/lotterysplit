<%@ include file="/common/include.jsp"%>

<head>
    <title><fmt:message key="ticket.split"/></title>

    <meta name="menu" content="Home"/>
</head>
<body class="ticketlist">

    <div data-role="page" id="ticketListPage"  data-theme="d"> 
    
    	<style>
			.entry-meta {
			  color:#999999;
			  font-size:12px;
			}    	
			
			.one-edge-shadow {
				-webkit-box-shadow: 0 8px 6px -6px black;
				   -moz-box-shadow: 0 8px 6px -6px black;
				        box-shadow: 0 8px 6px -6px black;
			}
    	</style>
    
   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>    
    	
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>Buy Tickets</h1> 
         	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/split.htm" data-role="button" data-icon="split" data-iconpos="right"><fmt:message key="list.lottery"/></a>
         </div> 
         
         <div data-role="content">     		
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- sl -->
<ins class="adsbygoogle"
     style="display:inline-block;width:320px;height:50px"
     data-ad-client="ca-pub-2178827339426285"
     data-ad-slot="6234609912"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
			<div class="ui-grid-a" class="entry-meta">
                <div class="ui-block-a">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/list.htm?start=1" data-role="button">Buy Tickets</a>
                </div>
                <div class="ui-block-b">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/mylist.htm?start=1" data-role="button">My Tickets</a>                   
                </div>
			</div>          
			
			<ul data-role="listview" style="padding:10px;">
			<li><a data-ajax="false" href="javascript: fundAccount(5.00);" class="btn btn-primary" style="color:white;">Pay Using Paypal - $5.00</a></li>
			</ul>
			<div id="div1"></div>			

         <!-- 
		<link rel="stylesheet" href="/js/boxy-0.1.4/boxy.css" />
	    <script src="/js/boxy-0.1.4/jquery.boxy.js" type="text/javascript"></script>		
         
			<script>
			
			
			$(document).ready(function() {
				$('#confirm-actuator-buy').click(function() {
					Boxy.confirm("Are you sure you want to buy ticket:", function() { 
						window.location.href = "${pageContext.request.contextPath}/lottery/ticket/buy/" + $('#ticketId').val();
					}, {title: 'Buy Ticket'});
					return false;
				});

				$('#confirm-actuator-flag').click(function() {
					Boxy.confirm("You are flagging ticket:", function() { 
						alert(ticketId);
						window.location.href = "${pageContext.request.contextPath}/lottery/ticket/flag/" + ticketId;
					}, {title: 'Flag Ticket'});
				    return false;
				});
			});	
			

			</script>
			 -->
			
		
                        <div style="clear:both;padding:10px;"></div>

                        <c:if test="${not empty message}"><div class="error">${message}</div></c:if>

                        <div style="clear:both;padding:5px;"></div>

                <c:if test="${not empty tickets}">
                <ul class="list-unstyled">
        <c:forEach items="${tickets}" var="ticket" varStatus="count">
			        <li>
                        <c:if test="${ticket.alreadyPurchased == true}">
                        <img src="${pageContext.request.contextPath}/lottery/ticket/${ticket.id}" width="50%"/>
                        </c:if>
						<div class="post-info">						
			        		<div class="post-basic-info">
								<div class="comment_desc">
								
									<h4>
										<a href="#">${ticket.lottoGameName}</a>
									</h4>
									<p style="color:black">
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
								     
										<c:set var="amount">FREE</c:set>
										<c:if test="${ticket.cost > 0}">
				                        <c:set var="amount">
				                                <fmt:setLocale value="en_US"/><fmt:formatNumber value="${ticket.cost}" type="currency"/> / split
				                        </c:set>		
				                        </c:if>	
								     
						        		Draw Date &nbsp;&nbsp; <fmt:formatDate value="${ticket.ticketDate}" pattern="MM/dd/yyyy"/><br/>
						        		Seller Referral Code &nbsp;&nbsp; ${ticket.referralCode}<br/>								
										<c:if test="${ticket.flag}"><span style="color:red">Warning : Ticket is Flagged</span></p></c:if>
										
									<p>
										<a href="#">Pool Size: ${ticket.splitNumber}</a>
										<a href="#">${ticket.splitNumber - ticket.buyerCount} LEFT</a>
										<a href="#">${amount}</a>
									</p>
									<p>						
										<button data-inline="true" data-mini="true" onclick="javascript: post('Lottery pool ticket ${ticket.lottoGameName} only ${amount}. Pool size ${ticket.splitNumber}.  Buy Here: http://www.splitlottery.com//lottery/ticket/buy/${ticket.id}');" >FB Share</button>
										<button data-inline="true" data-mini="true" onclick="javascript: confirmBuy('${ticket.lottoGameName}', '${ticket.id}');" id="confirm-actuator-buy" >BUY</button>
										<button data-inline="true" data-mini="true" onclick="javascript: confirmFlag('${ticket.lottoGameName}', '${ticket.id}');" id="confirm-actuator-flag" >Flag</button>
										<button data-inline="true" data-mini="true" data-ajax="false" onclick="window.location.href='${pageContext.request.contextPath}/lottery/ticket/buydetail/${ticket.id}';">DETAIL</button>
									</p>
									
								</div>	
			        		</div>

			        	</div>
					</li> 
					<br/>
        </c:forEach>
        </ul>
        </c:if>

        <br/><br/>
        <div style="clear:both;"></div>

<c:if test="${totalCount > 0}">			
				<div id="tickets"></div>	

				<script type="text/javascript">
				setTimeout(function() {
					$(function() {
						$("#tickets").paginate({
							count 		: ${totalCount},
							start 		: ${start},
							display     : ${start},
							border					: true,
							border_color			: '#fff',
							text_color  			: '#fff',
							background_color    	: 'black',	
							border_hover_color		: '#ccc',
							text_hover_color  		: '#000',
							background_hover_color	: '#fff', 
							images					: false,
							mouse					: 'press',
							onChange     			: function(page){
								window.location.href="${pageContext.request.contextPath}/lottery/ticket/list.htm?start=" + page;
													  }
						});
					});
				}, 1000);
				

				</script>
</c:if>
			

			
		</div>	
		
		<%@ include file="/common/footer.jsp" %>
	</div>

	

</body>