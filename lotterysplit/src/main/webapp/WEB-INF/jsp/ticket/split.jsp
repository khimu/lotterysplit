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
         	<h1>List Lottery Ticket</h1> 
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
     
	<form:form data-ajax="false" cssClass="well" action="${pageContext.request.contextPath}/lottery/ticket/split.htm" id="ticketSplitForm" commandName="lotteryTicket" method="post" enctype="multipart/form-data" accept-charset="utf-8">
  
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
			
			
			<input type="hidden" name="ticketDrawDate" id="ticketDrawDate"/>
			<form:hidden id="cost" path="cost" />

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
			
<%--
			<div data-role="fieldcontain">
				<form:select path="state" size="1">
					<form:option value="">State...</form:option>
					<form:option value="AL">Alabama</form:option>
					<form:option value="AK">Alaska</form:option>
					<form:option value="AZ">Arizona</form:option>
					<form:option value="AR">Arkansas</form:option>
					<form:option value="CA">California</form:option>
					<form:option value="CO">Colorado</form:option>
					<form:option value="CT">Connecticut</form:option>
					<form:option value="DE">Delaware</form:option>
					<form:option value="FL">Florida</form:option>
					<form:option value="GA">Georgia</form:option>
					<form:option value="HI">Hawaii</form:option>
					<form:option value="ID">Idaho</form:option>
					<form:option value="IL">Illinois</form:option>
					<form:option value="IN">Indiana</form:option>
					<form:option value="IA">Iowa</form:option>
					<form:option value="KS">Kansas</form:option>
					<form:option value="KY">Kentucky</form:option>
					<form:option value="LA">Louisiana</form:option>
					<form:option value="ME">Maine</form:option>
					<form:option value="MD">Maryland</form:option>
					<form:option value="MA">Massachusetts</form:option>
					<form:option value="MI">Michigan</form:option>
					<form:option value="MN">Minnesota</form:option>
					<form:option value="MS">Mississippi</form:option>
					<form:option value="MO">Missouri</form:option>
					<form:option value="MT">Montana</form:option>
					<form:option value="NE">Nebraska</form:option>
					<form:option value="NV">Nevada</form:option>
					<form:option value="NH">New Hampshire</form:option>
					<form:option value="NJ">New Jersey</form:option>
					<form:option value="NM">New Mexico</form:option>
					<form:option value="NY">New York</form:option>
					<form:option value="NC">North Carolina</form:option>
					<form:option value="ND">North Dakota</form:option>
					<form:option value="OH">Ohio</form:option>
					<form:option value="OK">Oklahoma</form:option>
					<form:option value="OR">Oregon</form:option>
					<form:option value="PA">Pennsylvania</form:option>
					<form:option value="RI">Rhode Island</form:option>
					<form:option value="SC">South Carolina</form:option>
					<form:option value="SD">South Dakota</form:option>
					<form:option value="TN">Tennessee</form:option>
					<form:option value="TX">Texas</form:option>
					<form:option value="UT">Utah</form:option>
					<form:option value="VT">Vermont</form:option>
					<form:option value="VA">Virginia</form:option>
					<form:option value="WA">Washington</form:option>
					<form:option value="WV">West Virginia</form:option>
					<form:option value="WI">Wisconsin</form:option>
					<form:option value="WY">Wyoming</form:option>
					<form:option value="CAN">CANADA</form:option>
					<form:option value="AB">Alberta</form:option>
					<form:option value="BC">British Columbia</form:option>
					<form:option value="MB">Manitoba</form:option>
					<form:option value="NB">New Brunswick</form:option>
					<form:option value="NF">Newfoundland and Labrador</form:option>
					<form:option value="NT">Northwest Territories</form:option>
					<form:option value="NS">Nova Scotia</form:option>
					<form:option value="NU">Nunavut</form:option>
					<form:option value="ON">Ontario</form:option>
					<form:option value="PE">Prince Edward Island</form:option>
					<form:option value="PQ">Quebec</form:option>
					<form:option value="SK">Saskatchewan</form:option>
					<form:option value="YT">Yukon Territory</form:option>
					<form:option value="AUS">AUSTRALIA</form:option>
					<form:option value="AC">Australian Capital Territory</form:option>
					<form:option value="NW">New South Wales</form:option>
					<form:option value="NO">Northern Territory</form:option>
					<form:option value="QL">Queensland</form:option>
					<form:option value="SA">South Australia</form:option>
					<form:option value="TS">Tasmania</form:option>
					<form:option value="VC">Victoria</form:option>
					<form:option value="WS">Western Australia</form:option>
				</form:select>

			</div>
	 --%>	
	 	
			<input class="btn btn-primary" type="button" onclick="updateNumbers();" value="Submit"/>
			
			<div style="display:none;"><input type="submit" id="submitForm"/></div>

			</form:form>
		</div>
  	</div>
  	<%@ include file="/common/footer.jsp" %>
</div>
</body>
