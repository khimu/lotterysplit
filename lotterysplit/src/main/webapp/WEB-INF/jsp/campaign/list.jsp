<%@ include file="/common/include.jsp"%>

<head>
    <title><fmt:message key="ticket.split"/></title>

    <meta name="menu" content="Home"/>
</head>
<body class="campaignPage">

    <div data-role="page" id="campaignPage"  data-theme="d"> 
    
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
         	<h1>Campaigns</h1> 
         	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/campaign/create.htm" data-role="button" data-iconpos="right">New</a>
         </div> 
         
         <div data-role="content">     		       			

                        <div style="clear:both;padding:10px;"></div>

                        <c:if test="${not empty message}"><div class="error">${message}</div></c:if>

                        <div style="clear:both;padding:5px;"></div>

            <c:if test="${not empty campaigns}">
            
			<div data-role="collapsible-set"  data-mini="true">         
			<h3>Campaigns</h3>     
        	<c:forEach items="${campaigns}" var="campaign" varStatus="count">
        		<div data-role="collapsible" data-collapsed="true" >
        		<h3>${campaign.campaignName} ${campaign.brand}</h3>
        		<p>
					<img src="${pageContext.request.contextPath}/lottery/ticket/${ticket.id}" width="50%"/>
					<div class="post-info">						
		        		<div class="post-basic-info">
							<div class="comment_desc">
								<p style="color:black">
								    <div class="page-nav">
										Pool Size &nbsp;&nbsp; ${fn:length(campaign.pool)}<br/>
									</div>	
									<div style="clear:both"></div>	
									
								  	<div data-role="collapsible" data-collapsed="true" >
								    	<h4>Members</h4>
								    	<c:forEach items="${campaign.pool}" var="pool" varStatus="count">
								    	<p>${pool}</p>
								    	</c:forEach>
								    </div>										
								</p>
							</div>
						</div>
					</div>
				</p>
				</div>
			</c:forEach>
			</div>       		
        </c:if>

        <br/><br/>
        <div style="clear:both;"></div>

			
		</div>	
		
		<%@ include file="/common/footer.jsp" %>
	</div>

	

</body>