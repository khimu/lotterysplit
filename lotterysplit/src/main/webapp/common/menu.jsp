<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<div data-role="panel" data-position="left" data-position-fixed="false" data-display="reveal" id="nav-panel" data-theme="d" class="nav-search">
	<ul data-role="listview" data-theme="d" data-divider-theme="d" style="margin-top:-16px;">
		<li data-filtertext="wai-aria voiceover accessibility screen reader">
			<div><fmt:message key="company.name"/></div>
		</li>
		<li data-icon="delete">
			<a href="#" data-rel="close"><fmt:message key="close.menu"/></a>
		</li>
		<li><a data-ajax="false" href="${pageContext.request.contextPath}/lottery/landing.htm">Home</a></li>
		
		<li><a data-ajax="false" href="${pageContext.request.contextPath}/lottery/signup.htm?referredBy=${referralCode}">Referral Link <br/>(${referralCode})</a></li>
        
        <li><a data-ajax="false" href="<c:url value='${pageContext.request.contextPath}/lottery/ticket/bought.htm?start=1'/>" alt="Lottery History" title="Lottery History"><fmt:message key="lottery.history"/></a></li>	
        
        <li><a data-ajax="false" href="<c:url value='${pageContext.request.contextPath}/lottery/ticket/list.htm?start=1'/>" alt="Lottery Listed" title="Lottery Listed"><fmt:message key="lottery.listed"/></a></li>
        
        <li><a data-ajax="false" href="<c:url value='${pageContext.request.contextPath}/lottery/report/payments.htm?start=1'/>" alt="Split Lottery Payments" title="Split Lottery Payments">Payments</a></li>

        <li><a data-ajax="false" href="<c:url value='${pageContext.request.contextPath}/lottery/report/balance.htm?start=1'/>" alt="Split Lottery Account Balance" title="Split Lottery Account Balance"><fmt:message key="account.balance"/></a></li>

        <li><a data-ajax="false" href="<c:url value='${pageContext.request.contextPath}/lottery/group/newgroup.htm?action=list'/>" alt="Split Lottery Update Group" title="Split Lottery Update Group">Create/Update Group</a></li>

        <li><a data-ajax="false" href="<c:url value='${pageContext.request.contextPath}/lottery/group/newgroup.htm?action=order'/>" alt="Split Lottery Order" title="Split Lottery Order">Order Ticket</a></li>

		<li><a href="${pageContext.request.contextPath}/lottery/news" alt="Split Lottery News" title="Split Lottery News"><fmt:message key="news"/></a></li>

		<li><a href="${pageContext.request.contextPath}/lottery/terms" alt="Split Lottery Terms Of Use" title="Split Lottery Terms Of Use"><fmt:message key="terms.of.use"/></a></li>
		
		<li><a href="${pageContext.request.contextPath}/lottery/privacy" alt="Split Lottery Privacy" title="Split Lottery Privacy"><fmt:message key="privacy.policy"/></a></li>
		
		<li><a data-ajax="false" href="${pageContext.request.contextPath}/lottery/logout.htm"><fmt:message key="user.logout" /></a></li>
  
	</ul>
</div>