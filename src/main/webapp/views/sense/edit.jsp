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

<form:form method="post" action="sense/chorbi/edit.do" modelAttribute="sense" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="recipient" />
	<form:hidden path="sender" />
	
	<form:label path="stars">
		<spring:message code="sense.stars" />
	</form:label>
	<form:select path="stars" >
		<form:option value="0" label="0" />
		<form:option value="1" label="1" />
		<form:option value="2" label="2" />
		<form:option value="3" label="3" />
	</form:select>
	<form:errors path="stars" cssClass="error" />
	<br/>
	
	<acme:textarea code="sense.comment" path="comment" />
	
	<acme:submit name="save" code="sense.save" />
	<acme:cancel url="chorbi/list.do" code="sense.cancel" />
</form:form>
