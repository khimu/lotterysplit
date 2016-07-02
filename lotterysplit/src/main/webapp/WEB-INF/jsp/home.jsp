<%@ include file="/common/include.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta name="menu" content="Home"/>
         
</head>
<body class="home">

    <div data-role="page" id="home" data-theme="b"> 
    		
    	<%@ include file="/common/menu.jsp" %>
		<div data-role="header" data-position="fixed">
			<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
			<h1>Lottery Split</h1>
			<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/split.htm" data-role="button" data-icon="split" data-iconpos="right">Split Lotto Ticket</a>          
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
			
			<input type="hidden" id="ticketId" />
			
			<c:if test="${not empty message}"><div class="error">${message}</div></c:if>

			<c:if test="${not empty tickets}">
			<ul data-role="listview">
			<c:forEach items="${tickets}" var="ticket">
				<li>
					<div class="btn-default">${ticket.lottoGameName}<br/>${ticket.ticketNumber}</div>
			    	<div class="ui-grid-a">
		              	<div class="ui-block-a">
		              		<a href="${pageContext.request.contextPath}/lottery/ticket/${ticket.id}" rel="prettyPhoto"><img src="${pageContext.request.contextPath}/lottery/ticket/${ticket.id}" width="100px" height="100px"/></a>
		              	</div>
		          		<div class="ui-block-b">
						  	<ul style="list-style-type: none;padding-top:10px;">
						  		<li>
						  		<a href="javascript: confirmBuy('${ticket.lottoGameName}', '${ticket.id}');" id="confirm-actuator-buy" class="btn btn-default">BUY</a> 
						  		| <a href="javascript: confirmFlag('${ticket.lottoGameName}', '${ticket.id}');" id="confirm-actuator-flag" class="btn btn-default">FLAG</a> &nbsp;&nbsp;<div id="div1"></div></li>
						  		<li style="color:blue;">${ticket.splitNumber - ticket.buyerCount} LEFT at ${ticket.cost / ticket.splitNumber}&cent;</li>		  		
							  	<li>Draw Date:  <fmt:formatDate value="${ticket.ticketDate}" pattern="MM/dd/yyyy HH:mm:ss"/></li>
							  	<c:if test="${ticket.flag == true}"><div style="color:red">Warning : Ticket is Flagged</div></c:if>
						  	</ul>
		              	</div>
			       	</div>
				</li>
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
							window.location.href="${pageContext.request.contextPath}/lottery/ticket/list.htm?start=" + page;
												  }
					});
				});
				</script>
</c:if>
			
			<!-- 
			<div id="buyDialog" title="Confirmation Required" >
				Are you sure you want to buy this lotto ticket?
			</div>
			
			<div id="flagDialog" title="Confirmation Required">
				Are you sure you want to flag this lotto ticket?
			</div>
			-->

			<div class="ui-grid-c" style="padding:10px;">
                <div class="ui-block-a">
					<div class="fb-like" data-href="https://developers.facebook.com/docs/plugins/" data-layout="button_count" data-action="like" data-show-faces="true"  data-share="true" ></div>                
				</div>
				<div class="ui-block-b"></div>
                <div class="ui-block-c"><div class="googlePlus"></div></div>
                <div class="ui-block-c"><div class="tweet"></div></div>
			</div>   		
				
<div id="logged-in"><img src="/images/fb-login.jpeg" width="72" height="72" /><div id="my-account"></div><p id="fbname" style="padding-left:5px;">FB Login</p></div>   
<div id="logged-out" style="display:none;"><img src="/images/fb_login.png" width="72" height="72" /><div id='fb-auth'></div><p id="fbname"></p></div> 

		</div>
		
		<%@ include file="/common/footer.jsp" %>
</div>
</body>
