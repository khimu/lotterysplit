<%@ include file="/common/include.jsp" %>

	<body id="landingPage">
		<div data-role="page" id="landingPage" data-theme="d" data-add-back-btn="true">

   	 		<%@ include file="/common/menu.jsp" %>
 		
	         <div data-role="header" data-position="fixed" data-theme="d"> 
	         		<a href="#nav-panel" data-icon="bars" data-iconpos="notext" data-corners="false">Menu</a>
	               <h1><fmt:message key="online.lottery.title"/></h1> 
	               <a data-ajax="false" href="${pageContext.request.contextPath}/lottery/group/newgroup.htm" data-role="button" data-icon="split" data-iconpos="right"><fmt:message key="list.lottery"/></a>
	         </div> 

	        <div data-role="content"> 
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- sl -->
<ins class="adsbygoogle"
     style="display:inline-block;width:320px;height:50px"
     data-ad-client="ca-pub-2178827339426285"
     data-ad-slot="6234609912"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>

			
        <div class="ui-grid-a" class="entry-meta">
	         <div class="ui-block-a">
	                 <div class="blog_button"><a data-ajax="false" href="javascript: fundAccount('5.00');" style="color:white;text-shadow: #FFFFFF 0 0 0;">Pay Using Paypal - $5.00</a></div>
	         </div>
	         <div class="ui-block-b">
	                 <div class="blog_button"><a data-ajax="false" href="/lottery/ticket/list.htm?start=1" style="color: white; text-shadow: #FFFFFF 0 0 0;">Join A Lottery Pool Now</a></div>          
	         </div>
        </div>
		<div class="clear"></div>
		<br/>		
			
		<style>
		h4 {
			text-align:center;
		}
		</style>

			
			<div class="tweets_list" style="text-shadow: #FFFFFF 0 0 0; padding: 5px;background-color: ${bgcolor}">
				<h4>Previous Lotteries</h4>
					<c:if test="${not empty topListers}">
					<c:forEach items="${topListers}" var="topLister" varStatus="status">
					
					<c:set var="bgcolor" value="#B6C710"/>
					<c:if test="${status.index % 2 == 0}"><c:set var="bgcolor" value="#ADBD0F"/></c:if>

					<a href="#" data-role="button" style="text-shadow: -2px 2px 3px #9CBA7F;color:white;background: transparent; border: none !important;">
					<i class="tweet-icon"><img src="http://graph.facebook.com/${topLister.facebookId}/picture" /></i> <span>
							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav" id="topLister${topLister.id}"></div>
							</div> 
							<script>
								topListers();
								function topListers() {
									var numbers = '${topLister.numbers}';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:#EEEEEE;">' + array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$('#topLister${topLister.id}').html(lotteryNumbers);
									}
								}
							</script>
					</span>
					</a>
						<div style="clear: both;"></div>
					</c:forEach>
					</c:if>
			</div>
			
			
			<div class="clear"></div>


<!-- 
			<div class="coursel">
				<h4>Sponsors</h4>
				<div class="ocarousel example_info" data-ocarousel-period="6000"
					style="display: block;">
					<div class="ocarousel_window">
						<div class="ocarousel_window_slides" style="right: 0px;">
							<div>
								<img src="/images/blog-img.jpg" alt="Lottery Man" />
							</div>
							<div>
								<img src="/images/blog-img.jpg" alt="Lottery Man" />
							</div>
							<div>
								<img src="/images/blog-img.jpg" alt="Lottery Man" />
							</div>
						</div>
					</div>
					<div class="prev_next">
						<a href="#" data-ocarousel-link="left" class="prev"> <i class="prev"></i>
						</a> <a href="#" data-ocarousel-link="right" class="next"> <i class="next"></i>
						</a>
						<div class="ocarousel_indicators">
								<svg version="1.1">
								<circle data-ocarousel-link="0" cx="81" cy="20" r="6" stroke="#FFF" stroke-width="0" fill="#115779"/>
								<circle data-ocarousel-link="1" cx="99" cy="20" r="6" stroke="#FFF" stroke-width="0" fill="#FFF"/>
								<circle data-ocarousel-link="2" cx="117" cy="20" r="6" stroke="#FFF" stroke-width="0" fill="#115779"/>
								</svg>						
						</div>
						<div style="clear: both"></div>

					</div>
				</div>
			</div>
 -->
			<c:if test="${not empty player}">
			<div class="content_top-grid3">

				<div class="cartton-photos">
					<h4>Fellow Players</h4>
					<ul>
						<c:forEach items="${players}" var="player">
						<li><a href="#"> <img src="http://graph.facebook.com/${player.facebookId}/picture" alt="" />
						</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			</c:if>


			<div style="clear:both"></div>	
			<br/>
			<br/>

					<div style="clear:both"></div>	  
		
			</div>
			<%@ include file="/common/footer.jsp" %>
		</div>

	</body>
</html>
