<%--
 * action-1.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${events}" id="event" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	
	<acme:column code="event.title" property="title" sortable="true"/>
	<acme:column code="event.moment" property="moment" sortable="true"/>
	<acme:column code="event.description" property="description" sortable="false"/>
	<spring:message code="event.seatsAvailable" var="seatsAvailableHeader" />
	<display:column title="${seatsAvailableHeader}" sortable="true">
		<jstl:set var="available" value="${event.seats - event.chorbies.size}"/>
		<jstl:if test="${available > 0}">
			<jstl:out value="${available}"/>
		</jstl:if>
		<jstl:if test="${available == 0}">
			<spring:message code="event.soldOut" />
		</jstl:if>
	</display:column>
	
	<security:authorize access="isAuthenticated()">
	
		<spring:message code="event.display" var="displayHeader" />
		<display:column title="${displayHeader}">
			<a href="event/actor/display.do?eventId=${event.id}"><spring:message code="event.display"/></a>
		</display:column>
		
		<security:authorize access="hasRole('MANAGER')">
			<spring:message code="event.edit" var="editHeader" />
			<display:column title="${editHeader}">
				<jstl:if test="${event.manager.id == principal.id}">
					<a href="event/manager/edit.do?eventId=${event.id}"><spring:message code="event.edit"/></a>
				</jstl:if>
			</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('CHORBI')">
			<jstl:if test="${available > 0}">
				<a href="event/chorbi/register.do?eventId=${event.id}"><spring:message code="event.register"/></a>
			</jstl:if>
			<jstl:if test="${available == 0}">
				<a href="event/chorbi/unregister.do?eventId=${event.id}"><spring:message code="event.unregister"/></a>
			</jstl:if>
		</security:authorize>
		
	</security:authorize>
	
</display:table>
