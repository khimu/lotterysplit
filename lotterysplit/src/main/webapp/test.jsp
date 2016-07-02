<%@ include file="/common/include.jsp"%>

<body>
	<div data-role="page" id="ticketMyListPage" data-theme="d">

		<%@ include file="/common/menu.jsp"%>

		<div id="fb-root"></div>
		<div data-role="header" data-position="fixed" data-theme="d">
			<a href="#nav-panel" data-icon="bars" data-iconpos="notext"
				data-corners="false">Menu</a>
			<h1>Tickets Sold</h1>
			<a data-ajax="false"
				href="${pageContext.request.contextPath}/lottery/ticket/split.htm"
				data-role="button" data-icon="split" data-iconpos="right"><fmt:message
					key="list.lottery" /></a>
		</div>

		<div data-role="content">
	
				
		<style>
		h4 {
			text-align:center;
		}
		</style>

			<div class="blog_button">
				<a href="/app/ticket/list.htm?start=1"
					style="color: white; text-shadow: #FFFFFF 0 0 0;">Join A
					Lottery Pool Now</a>
			</div>
			<div class="clear"></div>

			<div class="tweets_list" style="text-shadow: #FFFFFF 0 0 0; padding: 5px;">
				<h4>Major Gamblers</h4>
				<ul>
					<li style="background-color: #ADBD0F"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumIdx${ticket.lotteryTicketId}"></div>
							</div> 
							<script>
								topBuyer();
								function topBuyer() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$(
												'#lotteryDivNumIdx${ticket.lotteryTicketId}')
												.html(lotteryNumbers);
									}
								}
							</script>
					</span>
						<div class="clear"></div>
						<div style="clear: both;"></div></li>

					<li style="background-color: #B6C710"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumIdz${ticket.lotteryTicketId}"></div>
							</div> <script>
								topBuyer();
								function topBuyer() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$(
												'#lotteryDivNumIdz${ticket.lotteryTicketId}')
												.html(lotteryNumbers);
									}
								}
							</script>
					</span>
						<div class="clear"></div>
						<div style="clear: both;"></div></li>

					<li style="background-color: #ADBD0F"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumIdu${ticket.lotteryTicketId}"></div>
							</div> <script>
								topBuyer();
								function topBuyer() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$(
												'#lotteryDivNumIdu${ticket.lotteryTicketId}')
												.html(lotteryNumbers);
									}
								}
							</script>
					</span>
						<div class="clear"></div>
						<div style="clear: both;"></div></li>
				</ul>
			</div>

			<div class="tweets_list" style="text-shadow: #FFFFFF 0 0 0; padding: 5px;">
				<h4>Frequent Listers</h4>
				<ul>
					<li class="green"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumId2"></div>
							</div> <script>
								topLister();
								function topLister() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$('#lotteryDivNumId2').html(
												lotteryNumbers);
									}
								}
							</script>
					</span>
					<div style="clear: both;"></div>
					</li>

					<li class="blue-color"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumId3"></div>
							</div> <script>
								topLister();
								function topLister() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$('#lotteryDivNumId3').html(
												lotteryNumbers);
									}
								}
							</script>
					</span>
						<div class="clear"></div>
					<li class="green"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumId5"></div>
							</div> <script>
								topLister();
								function topLister() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$('#lotteryDivNumId5').html(
												lotteryNumbers);
									}
								}
							</script>
					</span>
						<div style="clear: both;"></div>
						</li>

				</ul>
			</div>
			
			<div class="tweets_list" style="text-shadow: #FFFFFF 0 0 0; padding: 5px;">
				<h4>Major Contributors</h4>
				<ul>
					<li style="background-color: #ADBD0F"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumIdv${ticket.lotteryTicketId}"></div>
							</div> 
							<script>
								topBuyer();
								function topBuyer() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$(
												'#lotteryDivNumIdv${ticket.lotteryTicketId}')
												.html(lotteryNumbers);
									}
								}
							</script>
					</span>
						<div class="clear"></div>
						<div style="clear: both;"></div></li>

					<li style="background-color: #B6C710"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumIde${ticket.lotteryTicketId}"></div>
							</div> <script>
								topBuyer();
								function topBuyer() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$(
												'#lotteryDivNumIde${ticket.lotteryTicketId}')
												.html(lotteryNumbers);
									}
								}
							</script>
					</span>
						<div class="clear"></div>
						<div style="clear: both;"></div></li>

					<li style="background-color: #ADBD0F"><i class="tweet-icon"><img
							src="http://graph.facebook.com/525081786/picture" /></i> <span>

							<div class="page-nav">
								<div class="page-pigmintation2 top_page_nav"
									id="lotteryDivNumIddd${ticket.lotteryTicketId}"></div>
							</div> <script>
								topBuyer();
								function topBuyer() {
									var numbers = '23 11 32 33 45 31';
									if (numbers != '') {
										var array = numbers.split(' ');
										var lotteryNumbers = "<ul>";
										for (var i = 0; i < array.length; i++) {
											lotteryNumbers += '<li><a href="#" style="color:black;background-color:white;">'
													+ array[i] + '</a></li>';
										}
										lotteryNumbers += "</ul>";
										$(
												'#lotteryDivNumIddd${ticket.lotteryTicketId}')
												.html(lotteryNumbers);
									}
								}
							</script>
					</span>
						<div class="clear"></div>
						<div style="clear: both;"></div></li>
				</ul>
			</div>
			
			
			<div class="clear"></div>

			<div class="content_top-grid3">
				<h4># 1 Lister</h4>
				<div class="profile"
					style="text-shadow: #FFFFFF 0 0 0; color: white;">
					<div class="profile-details">
						<div class="profile_picture">
							<div class="profile_picture_name">
								<h2>
									<a href="#"
										style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">Jack
										Marchies</a>
								</h2>
								<p>
									<a href="#"
										style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">Antepreneur</a>
								</p>
							</div>
							<a href=""
								style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">
								<img src="http://graph.facebook.com/525081786/picture" alt="" />
							</a>
							<p>
								<a href="#"
									style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">124
									followers</a>
							</p>
						</div>
						<div class="follow">
							<a href="#"
								style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">+
								Follow</a>
						</div>
					</div>
				</div>
			</div>


			<div class="coursel">
				<h4>Sponsors</h4>
				<div class="ocarousel example_info" data-ocarousel-period="6000"
					style="display: block;">
					<div class="ocarousel_window">
						<div class="ocarousel_window_slides" style="right: 0px;">
							<div>
								<img src="http://localhost:8888/metro_tiles_uikit-pack/web/images/blog-img.jpg" alt="" />
							</div>
							<div>
								<img src="http://localhost:8888/metro_tiles_uikit-pack/web/images/blog-img.jpg" alt="" />
							</div>
							<div>
								<img src="http://localhost:8888/metro_tiles_uikit-pack/web/images/blog-img.jpg" alt="" />
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

	

			<div class="content_top-grid3">
				<h4># 1 Spender</h4>
				<div class="profile"
					style="text-shadow: #FFFFFF 0 0 0; color: white;">
					<div class="profile-details">
						<div class="profile_picture">
							<div class="profile_picture_name">
								<h2>
									<a href="#"
										style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">Jack
										Marchies</a>
								</h2>
								<p>
									<a href="#"
										style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">Antepreneur</a>
								</p>
							</div>
							<a href=""
								style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">
								<img src="http://graph.facebook.com/525081786/picture" alt="" />
							</a>
							<p>
								<a href="#"
									style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">124
									followers</a>
							</p>
						</div>
						<div class="follow">
							<a href="#"
								style="text-shadow: #FFFFFF 0 0 0; color: white; text-decoration: none">+
								Follow</a>
						</div>
					</div>
				</div>
			</div>

			<div class="content_top-grid3">

				<div class="cartton-photos">
					<h4>Fellow Players</h4>
					<ul>
						<li><a href="#"> <img
								src="http://graph.facebook.com/525081786/picture" alt="" />
						</a></li>
						<li><a href="#"> <img
								src="http://graph.facebook.com/525081786/picture" alt="" />
						</a></li>
						<li><a href="#"> <img
								src="http://graph.facebook.com/525081786/picture" alt="" />
						</a></li>
						<li><a href="#"> <img
								src="http://graph.facebook.com/525081786/picture" alt="" />
						</a></li>
						<li><a href="#"> <img
								src="http://graph.facebook.com/525081786/picture" alt="" />
						</a></li>
					</ul>
				</div>
			</div>


			<div style="clear:both"></div>	
			<br/>
			<br/>
			<div class="blog_button">
				<a href="/app/ticket/list.htm?start=1"
					style="color: white; text-shadow: #FFFFFF 0 0 0;">Join A
					Lottery Pool Now</a>
			</div>
			<div style="clear:both"></div>

			<br/>
			<br/>

			<div class="coursel">
				<h4>Sponsors</h4>
				<div class="ocarousel example_info" data-ocarousel-period="6000"
					style="display: block;">
					<div class="ocarousel_window">
						<div class="ocarousel_window_slides" style="right: 0px;">
							<div>
								<img src="http://localhost:8888/metro_tiles_uikit-pack/web/images/blog-img.jpg" alt="" />
							</div>
							<div>
								<img src="http://localhost:8888/metro_tiles_uikit-pack/web/images/blog-img.jpg" alt="" />
							</div>
							<div>
								<img src="http://localhost:8888/metro_tiles_uikit-pack/web/images/blog-img.jpg" alt="" />
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
			
			<div style="clear: both"></div>
			
			<br/>
			
			<br/>


			</div>
		</div>
</body>
</html>