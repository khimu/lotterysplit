		

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
         	<h1>Purchase Activities</h1> 
         	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/split.htm" data-role="button" data-icon="split" data-iconpos="right"><fmt:message key="list.lottery"/></a>
         </div> 
         
         <div data-role="content">     	

			<div class="ui-grid-a">
                <div class="ui-block-a">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/payment/form.htm" data-role="button">Add Cash</a>
                </div>
                <div class="ui-block-b">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/report/payments.htm?start=1" data-role="button">Payments</a>                   
                </div>
			</div>     

			<div data-role="collapsible-set">
				<div class="ui-grid-a">
	                <div class="ui-block-a">
	                	Current Balance
	                </div>
	                <div class="ui-block-b">
	                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${sheet.currentBalance}" type="currency"/>                   
	                </div>
	                <div class="ui-block-a">
	                	Total Transaction
	                </div>
	                <div class="ui-block-b">
	                	${sheet.totalTransactions}                
	                </div>         
	                <div class="ui-block-a">
	                	Last Modified
	                </div>
	                <div class="ui-block-b">
	               	 	<fmt:formatDate value="${sheet.lastModified}" pattern="MM/dd/yyyy HH:mm:ss"/>
	                </div>     
	                <div class="ui-block-a">
	                	Created On
	                </div>
	                <div class="ui-block-b">
	                	<fmt:formatDate value="${sheet.createdOn}" pattern="MM/dd/yyyy HH:mm:ss"/>
	                </div>  
	                <div class="ui-block-a">
	                	Total Fee
	                </div>
	                <div class="ui-block-b">
	                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${sheet.totalFee}" type="currency"/>        
	                </div>  
	                <div class="ui-block-a">
	                	Total Earned
	                </div>
	                <div class="ui-block-b">
	                	 <fmt:setLocale value="en_US"/><fmt:formatNumber value="${sheet.totalCredit}" type="currency"/>        
	                </div>  
	                <div class="ui-block-a">
	                	Total Spent
	                </div>
	                <div class="ui-block-b">
	                	 <fmt:setLocale value="en_US"/><fmt:formatNumber value="${sheet.totalDebit}" type="currency"/>      
	                </div> 
				</div>  
			</div> 
			
			<div data-role="collapsible-set"  data-mini="true">
			  <h3>Balance</h3>
			  <div data-role="collapsible" data-collapsed="true" >
			    <h1>Daily</h1>
			    <p>
			    	<c:if test="${not empty sheet.dailyBalance}">
			    		
					<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Day
		                </div>
		                <div class="ui-block-b">
		                	Amount           
		                </div>				 
					<c:forEach items="${sheet.dailyBalance}" var="entry">				
		                <div class="ui-block-a">
		                	<utils:day date="${entry.key}" /> 
		                </div>
		                <div class="ui-block-b">
		                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>            
		                </div>
					</c:forEach>
					</div>		
					</c:if>	    
				</p>
			  </div>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Monthly</h1>
			    <p>
			    	<c:if test="${not empty sheet.monthlyBalance}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Month
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>			    	
					<c:forEach items="${sheet.monthlyBalance}" var="entry">
		                <div class="ui-block-a">
		                	<utils:month date="${entry.key}" />
		                </div>
		                <div class="ui-block-b">
		                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
		                </div>
					</c:forEach>
					</div>			
					</c:if>    
				</p>
			  </div>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Yearly</h1>
			    <p>
			    	<c:if test="${not empty sheet.yearlyBalance}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Year
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>			    	
						<c:forEach items="${sheet.yearlyBalance}" var="entry">
			                <div class="ui-block-a">
			                	<utils:month date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>	
					</div>	
					</c:if>	    
				</p>
			  </div>			  
			</div>		
			
			<div data-role="collapsible-set" data-mini="true">
			  <h3>Fee</h3>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Daily</h1>
			    <p>
			    	<c:if test="${not empty sheet.dailyFee}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Daily
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.dailyFee}" var="entry">
			                <div class="ui-block-a">
			                	<utils:day date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
						</div>	
					</c:if>		    
				</p>
			  </div>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Monthly</h1>
			    <p>
			    	<c:if test="${not empty sheet.monthlyFee}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Monthly
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.monthlyFee}" var="entry">
			                <div class="ui-block-a">
			                	<utils:month date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
					</div>			
					</c:if>    
				</p>
			  </div>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Yearly</h1>
			    <p>
			    	<c:if test="${not empty sheet.yearlyFee}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Yearly
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.yearlyFee}" var="entry">
			                <div class="ui-block-a">
			                	<utils:year date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
					</div>			
					</c:if>  			        
				</p>
			  </div>			  
			</div>			

			<div data-role="collapsible-set" data-mini="true">
			  <h3>Earned</h3>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Daily</h1>
			    <p>
			    	<c:if test="${not empty sheet.dailyCredit}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Daily
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.dailyCredit}" var="entry">
			                <div class="ui-block-a">
			                	<utils:day date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
					</div>			
					</c:if>  				    	    
				</p>
			  </div>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Monthly</h1>
			    <p>
			    	<c:if test="${not empty sheet.monthlyCredit}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Monthly
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.monthlyCredit}" var="entry">
			                <div class="ui-block-a">
			                	<utils:month date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
					</div>			
					</c:if>  
				</p>
			  </div>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Yearly</h1>
			    <p>
			    	<c:if test="${not empty sheet.yearlyCredit}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Monthly
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.yearlyCredit}" var="entry">
			                <div class="ui-block-a">
			                	<utils:year date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
					</div>			
					</c:if>    
				</p>
			  </div>			  
			</div>			

			<div data-role="collapsible-set" data-mini="true">
			  <h3>Spent</h3>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Daily</h1>
			    <p>
			    	<c:if test="${not empty sheet.dailyDebit}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Daily
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.dailyDebit}" var="entry">
			                <div class="ui-block-a">
			                	<utils:day date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
					</div>			
					</c:if>      
				</p>
			  </div>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Monthly</h1>
			    <p>
			    	<c:if test="${not empty sheet.monthlyDebit}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Monthly
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.monthlyDebit}" var="entry">
			                <div class="ui-block-a">
			                	<utils:month date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
					</div>			
					</c:if> 			    	    
				</p>
			  </div>
			  <div data-role="collapsible" data-collapsed="true">
			    <h1>Yearly</h1>
			    <p>
			    	<c:if test="${not empty sheet.yearlyDebit}">
			    	<div class="ui-grid-a">
		                <div class="ui-block-a">
		                	Monthly
		                </div>
		                <div class="ui-block-b">
		                	Amount               
		                </div>				    
						<c:forEach items="${sheet.yearlyDebit}" var="entry">
			                <div class="ui-block-a">
			                	<utils:year date="${entry.key}" />
			                </div>
			                <div class="ui-block-b">
			                	<fmt:setLocale value="en_US"/><fmt:formatNumber value="${entry.value} " type="currency"/>               
			                </div>
						</c:forEach>
					</div>			
					</c:if>
				</p>
			  </div>			  
			</div>	
			
			<div style="clear:both;padding:10px;"></div>		

			</div>
			<%@ include file="/common/footer.jsp" %>
	</div>

</body>