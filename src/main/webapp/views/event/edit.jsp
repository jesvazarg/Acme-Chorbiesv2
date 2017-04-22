<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="event/manager/edit.do" modelAttribute="event" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="manager" />
	<form:hidden path="chorbies" />
	
	<acme:input code="event.holderName" path="title" />
	<acme:input code="event.holderName" path="moment" />
	<acme:textarea code="event.holderName" path="description" />
	<acme:input code="event.holderName" path="picture" />
	<acme:input code="event.holderName" path="seats" />
	
	<acme:submit name="save" code="event.save" />
	<acme:cancel url="event/manager/list.do" code="event.cancel" />
</form:form>
