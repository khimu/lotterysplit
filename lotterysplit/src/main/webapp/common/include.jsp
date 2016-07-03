

<!DOCTYPE html>

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

<c:set var="ctx" value=""/>
<c:set var="datePattern"><fmt:message key="date.format"/></c:set>


<html lang="en">

<head>
		<title><fmt:message key="webapp.name"/></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="join lottery pool, buy lottery online, how can i buy lottery online, how can i join an online lottery pool, how can i sell my lottery ticket online"/>
		<meta http-equiv="description" content="Lottery Pool Online, create your public or private lottery ticket pool and we'll pick it up for you">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<link rel="shortcut icon" href="${ctx}/images/favicon.ico" type="image/x-icon">
    	<link rel="apple-touch-icon" href="${ctx}/images/favicon.ico">	

		<script src="${ctx}/js/jquery-1.9.1.min.js"></script>

		<script src="${ctx}/js/jquery-payment.js"></script>

        <link rel="stylesheet" href="${ctx}/jquery/jquery.mobile-1.3.2.min.css" />
        <script src="${ctx}/jquery/jquery.mobile-1.3.2.min.js"></script> 

		<script src="${ctx}/js/social.js"></script>

		<!--  required for datepicker -->

    	<link type="text/css" href="${ctx}/css/jqm-datebox.min.css" rel="stylesheet" /> 
    	 
  		<script language="javascript" type="text/javascript" src="${ctx}/js/jquery.coolfieldset.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.coolfieldset.css" />
		    	 
    	<script type="text/javascript" src="${ctx}/js/jqm-datebox.core.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/jqm-datebox.mode.calbox.min.js"></script>

		<script type="text/javascript" src="${ctx}/js/jqm-datebox.mode.datebox.min.js"></script>
  		<script type="text/javascript" src="${ctx}/js/jquery.mobile.datebox.i18n.en_US.utf8.js"></script>
		
		<link rel="stylesheet" href="${ctx}/js/jPaginate/css/style.css" />
	    <script src="${ctx}/js/jPaginate/jquery.paginate.js" type="text/javascript"></script>	
	    
	    <link rel="stylesheet" href="${ctx}/css/main.css" />
	    <link rel="stylesheet" href="${ctx}/css/detail.css" />
	    
	    
	    <script src="${ctx}/js/infinite-rotator.js" type="text/javascript"></script>	

       <script src="${ctx}/js/jquery.exif.js"></script>
       <script src="${ctx}/js/jquery.canvasResize.js"></script>
       <script src="${ctx}/js/canvasResize.js"></script>	 
       
       <script src="${ctx}/js/jquery.openCarousel.js"></script>   
	    
       <meta name="viewport" content="width=device-width, initial-scale=1">	

		<style>
		
			@media only screen and (-webkit-min-device-pixel-ratio: 2) {
				.ui-icon-split {
					background-image: url("${ctx}/images/lotterysplit.png");
					background-size: 18px 18px;
				}
				
				.ui-icon-fb {
					background-image: url("${ctx}/images/fb_login.jpeg");
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
