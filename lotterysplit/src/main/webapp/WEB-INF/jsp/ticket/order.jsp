<%@ include file="/common/include.jsp" %>

<head>
    <title><fmt:message key="ticket.split"/></title>
    <meta name="menu" content="Home"/>
</head>
<body class="ticketsplit">

    <div data-role="page" id="ticketSplitPage" data-theme="d"> 
    
	    <script>
	    	function updateNumbers(){
	    		var numbers = $( "#one" ).val() + " " + $( "#two" ).val() + " " + $( "#three" ).val() + " " + $( "#four" ).val() + " " + $( "#five" ).val() + " " + $( "#six" ).val();
	    		$('#numbers').val(numbers);
	    		$('#ticketSplitForm').submit();
	    	}
	    
	    	$(document).ready(function(){       
	    		var numbers = '${lotteryTicket.numbers}';
	    		if(numbers != ''){
	    			var array = numbers.split(' ');
	    			if(array[0] != ''){
	    				$('#one').val(array[0]);
	    			}
	    			if(array[1] != ''){
	    				$('#two').val(array[1]);
	    			}	   
	    			if(array[2] != ''){
	    				$('#three').val(array[2]);
	    			}	 
	    			if(array[3] != ''){
	    				$('#four').val(array[3]);
	    			}	    			
	    			if(array[4] != ''){
	    				$('#five').val(array[4]);
	    			}	    			
	    			if(array[5] != ''){
	    				$('#six').val(array[5]);
	    			}	    			
	    		}
	    	  $('#ticketDateId').on('datebox', function(e, p) {
	    	    if ( p.method === 'close' ) {
	    	    	$('#ticketDrawDate').val($('#ticketDateId').val());
	    	    }
	    	  });
	    	});
	    </script>

   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>    
    	
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>Order Lottery Ticket</h1> 
         </div> 
         
         <div data-role="content">     	

         	<c:if test="${not empty error}"><div class="error">${error}</div></c:if>

			<div class="ui-grid-a" >
                <div class="ui-block-a">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/list.htm?start=1" data-role="button">Buy Tickets</a>
                </div>
                <div class="ui-block-b">
                	<a data-ajax="false" href="${pageContext.request.contextPath}/lottery/ticket/mylist.htm?start=1" data-role="button">Sell Tickets</a>                   
                </div>
			</div>       

    <div class="col-sm-7">
     
	<form:form data-ajax="false" cssClass="well" action="${pageContext.request.contextPath}/lottery/ticket/order.htm" id="ticketSplitForm" commandName="lotteryTicket" method="post" accept-charset="utf-8">

			<input type="hidden" name="ticketDrawDate" id="ticketDrawDate"/>
			<form:hidden path="cost" id="cost" />

	       <div class="form-group">                                
	       	<label for="groupName" class="control-label">Group Name</label>
	        <form:input cssClass="form-control" type="text" path="groupName" />
	       </div>
			
			<div class="form-group">
        		<label for="ticketDate" class="control-label">Lottery Drawing Date</label>
				<form:input  cssClass="form-control" id="ticketDateId" type="date" data-options='{"mode":"calbox", "useNewStyle":true, "afterToday": true, "maxDays": 30}' data-role="datebox" path="ticketDate" placeholder="Lotto Ticket Drawing Date" />
			</div>

	       <div class="form-group">                                
	       	<label for="cost" class="control-label">Lottery Price</label>
	        <input cssClass="form-control" type="text" disabled="true" value="<fmt:setLocale value='en_US' /><fmt:formatNumber value='1.00' type='currency'/>" />
	       </div>
		
			<div class="form-group">
        		<label for="splitNumber" class="control-label">Lottery Pool Size</label>
				<form:input cssClass="form-control" type="text" id="splitNumber" path="splitNumber" />
				<div style="color:red" id="splitNumberError"></div>
			</div>

		   <form:hidden id="numbers" path="numbers" />
		   <label for="numbers"  class="control-label">Lottery Picks</label>
		   <div class="form-group">
		                   <div style="float:left"><input type="text" value="# 1" size="3" width="10px" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 2" size="3" width="10px" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 3" size="3" width="10px" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 4" size="3" width="10px" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 5" size="3" width="10px" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 6" size="3" width="10px" disabled /></div>
		   </div>
		   <div style="clear:both"></div>
		   <div class="form-group">
		                   <div style="float:left"><input type="text" id="one" size="3" name="one"/></div>
		                   <div style="float:left"><input type="text" id="two" size="3" width="10px" name="two"/></div>
		                   <div style="float:left"><input type="text" id="three" size="3" width="10px" name="three"/></div>
		                   <div style="float:left"><input type="text" id="four" size="3" width="10px" name="four"/></div>
		                   <div style="float:left"><input type="text" id="five" size="3" width="10px" name="five"/></div>
		                   <div style="float:left"> <input type="text" id="six" size="3" width="10px" name="six"/></div>
		   </div>
		   <div style="clear:both"></div>		
			
			<div class="form-group">
        		<label for="lottoGameName" class="control-label">Lottery Game Name</label>
				<form:select path="lottoGameName" size="1"  cssClass="form-control">
					<c:forEach items="${games}" var="game">
						<form:option value="${game.gameName}">${game.gameName}</form:option>
					</c:forEach>
				</form:select>
			</div>		 	
			<input class="btn btn-primary" type="button" onclick="updateNumbers();" value="Submit"/>
			
			<div style="display:none;"><input type="submit" id="submitForm"/></div>

			</form:form>
		</div>
  	</div>
  	<%@ include file="/common/footer.jsp" %>
</div>
</body>
