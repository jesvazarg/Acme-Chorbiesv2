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

<form:form method="post" action="event/managerUser/edit.do" modelAttribute="event" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="manager" />
	<form:hidden path="chorbies" />
 	<form:hidden path="availableSeats" />
	
	<acme:input code="event.title" path="title" />
	<acme:input code="event.moment" path="moment" />
	<acme:textarea code="event.description" path="description" />
	<acme:input code="event.picture" path="picture" />
	<acme:input code="event.seats" path="seats" />
	
	<acme:submit name="save" code="event.save" />
	<jstl:if test="${event.id != 0}">
		<acme:submit name="delete" code="event.delete" />
	</jstl:if>
	<acme:cancel url="event/managerUser/list.do" code="event.cancel" />
</form:form>
