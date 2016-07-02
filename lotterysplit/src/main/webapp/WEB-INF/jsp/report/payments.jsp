		

<%@ include file="/common/include.jsp"%>

<head>
    <title><fmt:message key="ticket.split"/></title>

    <meta name="menu" content="Home"/>
</head>
<body class="payments">

    <div data-role="page" id="paymentsPage" data-theme="d"> 
    
   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>    
    	
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>Payment Transactions</h1> 
         	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/split.htm" data-role="button" data-icon="split" data-iconpos="right"><fmt:message key="list.lottery"/></a>
         </div> 
         
         <div data-role="content">     		 
			

                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/report/balance.htm?start=1" data-role="button">Transactions</a>                   
   
			
			<div class="ui-grid-a">
				<div class="ui-block-a">Invoice Number</div>
				<div class="ui-block-b">Details</div>	   
			</div>			
			
			<c:if test="${not empty payments}">
			<c:forEach items="${payments}" var="payment">	
				<div class="ui-grid-a reportRow">
					<div class="ui-block-a">${payment.invoiceNumber}</div>
          			<div class="ui-block-b">${payment.transactionId} <br><fmt:formatNumber type="currency" value="${payment.amount}" /> <br>${payment.email} <br>${payment.createdOn}<br>${payment.transactionMessage}</div>
				</div>
			</c:forEach>
			</c:if>

		</div>
		
<c:if test="${totalCount > 0}">
	<div id="tickets"></div>	

	<link rel="stylesheet" href="/js/jPaginate/css/style.css" />
    <script src="/js/jPaginate/jquery.paginate.js" type="text/javascript"></script>

	<script type="text/javascript">
	$(function() {
		$("#tickets").paginate({
			count 		: ${totalCount},
			start 		: ${start},
			display     : ${pageSize},
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
				window.location.href="${pageContext.request.contextPath}/lottery/report/payments.htm?start=" + page;
									  }
		});
	});
	</script>
</c:if>		
		
		<%@ include file="/common/footer.jsp" %>
	</div>

</body>