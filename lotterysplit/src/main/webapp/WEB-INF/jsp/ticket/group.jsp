<%@ include file="/common/include.jsp" %>

<head>
    <title><fmt:message key="ticket.split"/></title>
    <meta name="menu" content="Home"/>
</head>

<body class="group">

    <div data-role="page" id="group" data-theme="d"> 

   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>    
    	
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>Group Lottery Ticket</h1> 
         </div> 
         
         <div data-role="content">     	

         	<c:if test="${not empty error}"><div class="error">${error}</div></c:if>  

    <div class="col-sm-7">
     
		<div class="ui-grid-a" class="entry-meta">
			<div class="ui-block-a">
			        <div class="blog_button"><a data-ajax="false" href="/lottery/ticket/split.htm" style="color: white; text-shadow: #FFFFFF 0 0 0;">Just List It</a></div>
			</div>
			<div class="ui-block-b">
			        <div class="blog_button"><a data-ajax="false" href="/lottery/ticket/order.htm"style="color: white; text-shadow: #FFFFFF 0 0 0;">Just Order It</a></div>          
			</div>
		</div>
		<div class="clear"></div>
		<br/>     
			  
			<c:choose>
				<c:when test="${not empty groupName}">
					<div style="clear:both"></div>	
					<br/>
					<br/>
					<div class="blog_button">
						<a data-ajax="false" href="/lottery/ticket/split.htm?groupName=${groupName}" style="color: white; text-shadow: #FFFFFF 0 0 0;">List under group ${groupName}</a>
					</div>
					<div style="clear:both"></div>
					<br/>
					<br/>  	
					<div style="clear:both"></div>	
					<br/>
					<br/>
					<div class="blog_button">
						<a data-ajax="false" href="/lottery/ticket/order.htm?groupName=${groupName}" style="color: white; text-shadow: #FFFFFF 0 0 0;">Order under group ${groupName}</a>
					</div>
					<div style="clear:both"></div>
					<br/>
					<br/>  					
				</c:when>
				<c:otherwise>
					<form:form data-ajax="false" cssClass="well" action="${pageContext.request.contextPath}/lottery/group/newgroup.htm" id="groupForm" commandName="groupForm" method="post" accept-charset="utf-8">
						<form:hidden path="action" />
						<h3>Create A Private Lottery Pool</h3>
						<div class="form-group">
				       		<label for="groupName" class="control-label">Group Name</label>
							<form:input cssClass="form-control" type="text" id="groupName" path="groupName" />
						</div>
				
						<div class="form-group">
				       		<label for="memberEmails" class="control-label">Members</label>
				       		<form:textarea path="memberEmails" placeholder="email1, email2"></form:textarea>
						</div>
				
						<input type="submit" class="btn btn-primary" id="groupForm" value="Submit" />
				
					</form:form>				
				</c:otherwise>
			</c:choose>
	</div>
  	
  	</div>
  	<%@ include file="/common/footer.jsp" %>
</div>
</body>
