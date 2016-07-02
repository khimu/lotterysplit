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
			<a href="#" data-rel="close">Close Menu</a>
		</li>
        
		<li><a href="${pageContext.request.contextPath}/lottery/news" alt="Split Lottery News" title="Split Lottery News">News</a></li>

		<li><a href="${pageContext.request.contextPath}/lottery/terms" alt="Split Lottery Terms Of Use" title="Split Lottery Terms Of Use">Terms Of Use</a></li>
		
		<li><a href="${pageContext.request.contextPath}/lottery/privacy" alt="Split Lottery Privacy" title="Split Lottery Privacy">Privacy Policy</a></li>
	</ul>
</div>