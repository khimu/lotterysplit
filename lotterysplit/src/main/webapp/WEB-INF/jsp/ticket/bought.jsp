		

<%@ include file="/common/include.jsp"%>

<head>
    <title><fmt:message key="ticket.split"/></title>

    <meta name="menu" content="Home"/>
</head>
<body class="bought">

	

    <div data-role="page" id="boughtPage" data-theme="d"> 
    
   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>    
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>Tickets Bought</h1> 
         	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/split.htm" data-role="button" data-icon="split" data-iconpos="right"><fmt:message key="list.lottery"/></a>
         </div> 
         
         <div data-role="content">     

			<div class="ui-grid-a">
                <div class="ui-block-a">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/bought.htm?start=1" data-role="button">Bought Tickets</a>
                </div>
                <div class="ui-block-b">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/sold.htm?start=1" data-role="button">Sold Tickets</a>                   
                </div>
			</div>   
    			
			<div style="clear:both;padding:10px;"></div>
			
			<c:if test="${not empty message}"><div class="error">${message}</div></c:if>
			
			<div style="clear:both;padding:5px;"></div>
			
            <c:if test="${not empty tickets}">
            <ul class="list-unstyled">
            <c:forEach items="${tickets}" var="ticket">
			        <li>
			        	<img src="${pageContext.request.contextPath}/lottery/ticket/${ticket.lotteryTicketId}" width="50%"/>
						<div class="post-info">						
			        		<div class="post-basic-info">
								<div class="comment_desc">
									<p style="color:black"> 
									    <div class="page-nav">
									      	<div class="page-pigmintation top_page_nav" id="lotteryDivNumId${ticket.lotteryTicketId}">										
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
											$('#lotteryDivNumId${ticket.lotteryTicketId}').html(lotteryNumbers);
										}
									});
								    </script>					
						        		Draw Date &nbsp;&nbsp; <fmt:formatDate value="${ticket.ticketDate}" pattern="MM/dd/yyyy"/><br/>
						        		Buyer Referral Code &nbsp;&nbsp; ${ticket.referralCode}<br/>								
									</p>
									<p>
										<button data-inline="true" data-mini="true" data-ajax="false" onclick="window.location.href='${pageContext.request.contextPath}/lottery/ticket/buydetail/${ticket.lotteryTicketId}';">DETAIL</button>							
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
				window.location.href="${pageContext.request.contextPath}/lottery/ticket/bought.htm?start=" + page;
									  }
		});
	});
	</script>
</c:if>
		 </div>
		<%@ include file="/common/footer.jsp" %>
	</div>

	

</body>