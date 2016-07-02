		

<%@ include file="/common/include.jsp"%>

<head>
    <title><fmt:message key="ticket.split"/></title>

    <meta name="menu" content="Home"/>
</head>
<body class="ticketmylist">

	

    <div data-role="page" id="ticketMyListPage" data-theme="d"> 
    
   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>  
    	  
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>My Tickets</h1> 
         	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/split.htm" data-role="button" data-icon="split" data-iconpos="right"><fmt:message key="list.lottery"/></a>
         </div> 
         
         <div data-role="content">     	

			<div class="ui-grid-a" class="entry-meta">
                <div class="ui-block-a">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/list.htm?start=1" data-role="button">Buy Tickets</a>
                </div>
                <div class="ui-block-b">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/mylist.htm?start=1" data-role="button">My Tickets</a>                   
                </div>
			</div>       
						
                        <div style="clear:both;padding:10px;"></div>
                        
		<c:if test="${not empty tickets}">
		<ul class="list-unstyled">
        <c:forEach items="${tickets}" var="ticket" varStatus="count">
			        <li>
			        	<img src="${pageContext.request.contextPath}/lottery/ticket/${ticket.id}" width="50%"/>
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
										Draw Date &nbsp;&nbsp; <fmt:formatDate value="${ticket.ticketDate}" pattern="MM/dd/yyyy"/><br/>
						        		Seller Referral Code &nbsp;&nbsp; ${ticket.referralCode}<br/>								
										<c:if test="${ticket.flag}"><span style="color:red">Warning : Ticket is Flagged</span></p></c:if>
									<p>
										<a href="#">Pool Size: ${ticket.splitNumber}</a>
										<a href="#">${ticket.splitNumber - ticket.buyerCount} LEFT</a>
										<a href="#"><fmt:setLocale value="en_US"/><fmt:formatNumber value="${ticket.cost}" type="currency"/> / split</a>
									</p>
									<p>
				                        <c:set var="amount">
				                                <fmt:setLocale value="en_US"/><fmt:formatNumber value="${ticket.cost}" type="currency"/>
				                        </c:set>	
				                        <button data-inline="true" data-mini="true" data-ajax="false" onclick="javascript: post('Join My Lottery Ticket ${ticket.lottoGameName} For Only ${amount} With ${ticket.splitNumber} Other Participants:  Buy Here: http://www.splitlottery.com/${pageContext.request.contextPath}/lottery/ticket/buy/${ticket.id}');">FB Share</button>   
										<button data-inline="true" data-mini="true" data-ajax="false" onclick="window.location.href='${pageContext.request.contextPath}/lottery/ticket/detail/${ticket.id}';">DETAIL</button>
									</p>
								</div>	
			        		</div>

			        	</div>
					</li>        
					<br/>
        </c:forEach>
        </ul>
        </c:if>

	<div style="clear:both;padding:10px;"></div>

<c:if test="${totalCount > 0}">
	<div id="tickets"></div>	

	<script type="text/javascript">
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
				window.location.href="${pageContext.request.contextPath}/lottery/ticket/mylist.htm?start=" + page;
									  }
		});
	});
	</script>
</c:if>

	</div>
	<%@ include file="/common/footer.jsp" %>

</div>	

</body>