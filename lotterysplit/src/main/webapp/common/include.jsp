<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://www.appfuse.org/tags/spring" prefix="appfuse" %>
<%@ taglib prefix="l" uri="/WEB-INF/UtilsTag.tld" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="datePattern"><fmt:message key="date.format"/></c:set>

<html lang="en">

<head>
		<title><fmt:message key="webapp.name"/></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="create private lottery pool, order lottery tickets picked up for you, lottery ticket pick up service, save a history of your lottery ticket purchase, scan your lottery tickets online, catalog your lottery tickets, lottery winning, join tons of lottery pools online and increase your chance of winning millions,lottery ticket, split lottery, lottery online, lottery, lotto, Oregon Win For Life, Oregon Powerball, Oregon Mega Millions, Oregon Pick 4, Oregon MegaBucks, Washington MEGA Millions, Washington Powerball, California SuperLotto Plus, California Powerball, California Fantasy 5, California MEGA Millions, California 2nd Chance, Massachusetts The Numbers Game DAY, Massachusetts The Numbers Game EVE, Massachusetts Megabucks, Massachusetts Mass Cash, Massachusetts Mega Millions, Massachusetts Lucky for Life, Massachusetts Powerball, California Daily 3 DAY, California Daily 3 EVE , California Daily 4, California Fantasy 5, California Daily Derby, California SuperLotto Plus, California Mega Millions, California Powerball, Weekly Grand, Megabucks Plus, Win For Life, Mega Millions, Pick 3, Pick 4, Megabucks and Paycheck, Powerball, Hot Lotto, Wild Card 2 and 2 By 2, US Lotteries, United States lotteries, classification of US lottery games, 3-digit numbers, 4-digit numbers, multi-number lottery games,interstate US lotterry games, Arizona Pick 5, Arizona The Pick, California, Fantasy 5, Colorado Cash 5, Colorado  Lotto, Colorado Match Play, Connecticut Cash 5,Connecticut Classic Lotto, Delaware Multi-win Lotto,Florida Fantasy 5,Florida Lotto, Georgia Fantasy 5,Illinois Little Lotto, Illinois Lotto, Indiana Lucky5, Indiana Mix and Match,	Indiana Hoosier Lotto, Iowa Cash Game, Kentucky Three Line Lotto, Louisiana Easy 5, Louisiana Lotto,Maryland MultiMatch, Massachusetts Megabucks, Massachusetts Mass Cash,Massachusetts Cash Winfall, Michigan Fantasy 5, Michigan Lotto47,Minnesota Northstar Cash, Minnesota Gopher 5, Missouri Show Me Cash,Missouri Lotto, Montana MT Cash,Nebraska Pick 5,New Jersey Jersey Cash 5,New Jersey Pick 6 Lotto,New York Take Five,New York Sweet-Millions,North Carolina Cash 5,Ohio Rolling Cash 5,Ohio Classic Lotto,Oklahoma Cash 5,Oregon Win for Life,Oregon Megabucks,Pennsylvania Treasure Hunt,Pennsylvania Cash 5,South Carolina Palmetto Cash 5,South Dakota Dakota Cash,Tennessee Pick 5,Texas Cash Five,Texas Lotto Texas,Virginia Cash 5,Washington Hit 5,Washington Lotto,Wisconsin Badger 5,Wisconsin SuperCash!,Wisconsin Megabucks,West Virginia Cash 25,California Daily 4,Connecticut Play4,Delaware Play 4,District Of Columbia DC-4,Florida Play 4,Georgia Cash 4,Illinois Pick 4,Indiana daily4,Iowa Pick 4,Kentucky Pick 4,Louisiana Pick 4,Maryland Pick 4,Massachusetts The Numbers Game,Michigan Daily 4,Missouri Pick 4,New Jersey Pick 4,New York Win 4,North Carolina Pick 4,Ohio Pick 4,Oregon Pick 4,Pennsylvania Big 4,Rhode Island The Numbers,South Carolina Pick 4,Tennessee Cash 4,Texas Pick 4,Virginia Pick 4,Wisconsin Pick 4,West Virginia Daily 4,Maine Pick 4,New Hampshire Pick 4,Vermont Pick 4,Arizona Pick 3,California Daily 3,Connecticut Play3,Delaware Play 3,District Of Columbia DC Lucky Numbers,Florida Cash 3,Georgia Cash 3,Idaho Pick 3,Illinois Pick 3,Indiana daily3,Iowa Pick 3,Kansas Pick 3,Kentucky Pick 3,Louisiana Pick 3,Maryland Pick 3,Michigan Daily 3,Minnesota Daily 3,Missouri Pick 3,Nebraska Pick 3,New Jersey Pick 3,New Mexico Pick 3,New York Numbers,North Carolina Pick 3,Ohio Pick 3,Oklahoma Pick 3,Pennsylvania Daily Number,South Carolina Pick 3,Tennessee Cash 3,Texas Pick 3,Virginia Pick 3,Washington The Daily Game,Wisconsin Pick 3,West Virginia Daily 3,Maine Pick 3,New Hampshire Pick 3,Vermont Pick 3"/>
		<meta http-equiv="description" content="Lottery Pool Online, create your public or private lottery ticket pool and we'll pick it up for you">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		
		<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    	<link rel="apple-touch-icon" href="/images/favicon.ico">	

		<script src="/js/jquery-1.9.1.min.js"></script>

		<script src="/js/jquery-payment.js"></script>

        <link rel="stylesheet" href="/jquery/jquery.mobile-1.3.2.min.css" />
        <script src="/jquery/jquery.mobile-1.3.2.min.js"></script> 

		<script src="/js/social.js"></script>

		<!--  required for datepicker -->

    	<link type="text/css" href="/css/jqm-datebox.min.css" rel="stylesheet" /> 
    	 
  		<script language="javascript" type="text/javascript" src="/js/jquery.coolfieldset.js"></script>
		<link rel="stylesheet" type="text/css" href="/css/jquery.coolfieldset.css" />
		    	 
    	<script type="text/javascript" src="/js/jqm-datebox.core.min.js"></script>
		<script type="text/javascript" src="/js/jqm-datebox.mode.calbox.min.js"></script>

		<script type="text/javascript" src="/js/jqm-datebox.mode.datebox.min.js"></script>
  		<script type="text/javascript" src="/js/jquery.mobile.datebox.i18n.en_US.utf8.js"></script>
		
		<link rel="stylesheet" href="/js/jPaginate/css/style.css" />
	    <script src="/js/jPaginate/jquery.paginate.js" type="text/javascript"></script>	
	    
	    <link rel="stylesheet" href="/css/main.css" />
	    <link rel="stylesheet" href="/css/detail.css" />
	    
	    
	    <script src="/js/infinite-rotator.js" type="text/javascript"></script>	

       <script src="/js/jquery.exif.js"></script>
       <script src="/js/jquery.canvasResize.js"></script>
       <script src="/js/canvasResize.js"></script>	 
       
       <script src="/js/jquery.openCarousel.js"></script>   
	    
       <meta name="viewport" content="width=device-width, initial-scale=1">	

		<style>
		
			@media only screen and (-webkit-min-device-pixel-ratio: 2) {
				.ui-icon-split {
					background-image: url("/images/lotterysplit.png");
					background-size: 18px 18px;
				}
				
				.ui-icon-fb {
					background-image: url("/images/fb_login.jpeg");
					background-size: 18px 18px;
				}				
			}
		
		</style>
		
		<script type="text/javascript" charset="utf-8">
		  $(document).ready(function(){
			appId = "${appId}";
		    //$("a[rel^='prettyPhoto']").prettyPhoto();
		    $('.ui-loader').hide();
		  });
		  
		  var ctx = "${pageContext.request.contextPath}";
		</script>

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-48366783-1', 'splitlottery.com');
  ga('send', 'pageview');

</script>
		
</head>
