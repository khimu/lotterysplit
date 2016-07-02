<%@ include file="/common/include.jsp" %>

<head>
    <title><fmt:message key="ticket.split"/></title>
    <meta name="menu" content="Home"/>
</head>
<body class="createCampaignPage">

    <div data-role="page" id="createCampaignPage" data-theme="d"> 
    
   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>    
    	
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>Campaigns</h1> 
        </div> 
         
         <div data-role="content">     	

         	<c:if test="${not empty error}"><div class="error">${error}</div></c:if>    

    <div class="col-sm-7">
     
	<form:form data-ajax="false" cssClass="well" action="${pageContext.request.contextPath}/lottery/campaign/create.htm" id="campaign" commandName="campaign" method="post" accept-charset="utf-8">
  
			<input type="hidden" name="lotteryTicketId" id="lotteryTicketId"/>

	       <div class="form-group">                                
	       	<label for="campaignName" class="control-label">Campaign Name</label>
	        <form:input cssClass="form-control" type="text" path="campaignName" />
	       </div>

	       <div class="form-group">                                
	       	<label for="brand" class="control-label">Brand</label>
	        <form:input cssClass="form-control" type="text" path="brand" />
	       </div>	       

		   <div style="clear:both"></div>		

			<input class="btn btn-primary" type="submit" id="campaign" value="Submit"/>

			</form:form>
		</div>
  	</div>
  	<%@ include file="/common/footer.jsp" %>
</div>
</body>
