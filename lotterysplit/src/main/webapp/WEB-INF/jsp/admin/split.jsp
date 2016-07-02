<%@ include file="/common/include.jsp" %>

<head>
    <title><fmt:message key="ticket.split"/></title>
    <meta name="menu" content="Home"/>
</head>
<body class="ticketsplit">

    <div data-role="page" id="ticketSplitPage" data-theme="d"> 
    
	    <script>
	    
	    	$(document).ready(function(){       
	    		var numbers = '${lotteryTicket.numbers}';
	    		if(numbers != ''){
	    			var array = numbers.split(' ');
	    			if(array[0] != ''){
	    				$('#one').val(array[0] + " ");
	    			}
	    			if(array[1] != ''){
	    				$('#two').val(array[1] + " ");
	    			}	   
	    			if(array[2] != ''){
	    				$('#three').val(array[2] + " ");
	    			}	 
	    			if(array[3] != ''){
	    				$('#four').val(array[3] + " ");
	    			}	    			
	    			if(array[4] != ''){
	    				$('#five').val(array[4] + " ");
	    			}	    			
	    			if(array[5] != ''){
	    				$('#six').val(array[5] + " ");
	    			}	    			
	    		}
	    	});
	    </script>

   	 	<%@ include file="/common/menu.jsp" %>
   	 	
    	<div id="fb-root"></div>    
    	
         <div data-role="header" data-position="fixed" data-theme="d"> 
         	<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
         	<h1>Fulfill Lottery Ticket Order</h1> 
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
     
	<form:form data-ajax="false" cssClass="well" action="${pageContext.request.contextPath}/lottery/admin/split.htm" id="ticketSplitForm" commandName="lotteryTicket" method="post" enctype="multipart/form-data" accept-charset="utf-8">
  
			<div class="form-group">
       			<label for="imageBytes" class="control-label">Lottery Ticket Upload</label>
				<form:input type="file" cssClass="form-control" path="imageBytes" id="file"/>
			</div>
<script>
$('#file').change(function(e) {
    var file = e.target.files[0];
    $.canvasResize(file, {
        width: 50,
        height: 0,
        crop: false,
        quality: 80,
        callback: function(data, width, height) {
        	$('#file').val(data);
            $(img).attr('src', data);
        }
    });
});
</script>			
			
			<form:hidden path="id" />
			<form:hidden path="ticketDate" id="ticketDate"/>
			<form:hidden path="cost" id="cost" />
			<form:hidden path="splitNumber" id="splitNumber" />
			<form:hidden path="numbers" id="numbers" />
			<form:hidden path="lottoGameName" />
			<form:hidden path="groupName" />
			<form:hidden path="createdOn"/>
			<form:hidden path="userId"/>
			<form:hidden path="ticketNumber"/>
			<form:hidden path="referralCode"/>
			<form:hidden path="slotSize"/>
			<form:hidden path="buyerCount"/>
			<form:hidden path="flag"/>
			<form:hidden path="facebookId"/>

			<div class="form-group">
       			<label for="ticketDate" class="control-label">Lottery Draw Date</label>
       			<div style="clear:both"></div>
				<fmt:formatDate pattern="MM/dd/yyyy" value="${lotteryTicket.ticketDate}"/>
			</div>

			<div style="clear:both"></div>
		   <label for="numbers"  class="control-label">Lottery Picks</label>
		   <div style="clear:both"></div>
		   <div class="form-group">
		                   <div style="float:left"><input type="text" value="# 1" size="3" width="10px" id="one" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 2" size="3" width="10px"  id="two" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 3" size="3" width="10px"  id="three" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 4" size="3" width="10px"  id="four" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 5" size="3" width="10px"  id="five" disabled /></div>
		                   <div style="float:left"><input type="text" value="# 6" size="3" width="10px"  id="six" disabled /></div>
		   </div>
		   <div style="clear:both"></div>		
				 	
			<input type="submit" id="submitForm" value="Submit"/>

			</form:form>
		</div>
  	</div>
  	<%@ include file="/common/footer.jsp" %>
</div>
</body>
