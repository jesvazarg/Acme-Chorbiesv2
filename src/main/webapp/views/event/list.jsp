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
	<acme:column code="event.moment" property="moment" format="{0,date,dd-MM-yyyy HH:mm}" sortable="true"/>
	<acme:column code="event.description" property="description" sortable="false"/>
	<spring:message code="event.seatsAvailable" var="seatsAvailableHeader" />
	<display:column title="${seatsAvailableHeader}" sortable="true">
		<jstl:set var="available" value="${false}"/>
		<jstl:if test="${event.seats > chorbies.size}">
			<jstl:set var="available" value="${true}"/>
		</jstl:if>
		<jstl:if test="${available}">
			<jstl:out value="${event.seats - chorbies.size}"/>
		</jstl:if>
		<jstl:if test="${!available}">
			<spring:message code="event.soldOut" />
		</jstl:if>
	</display:column>
	
	<security:authorize access="isAuthenticated()">
	
		<spring:message code="event.display" var="displayHeader" />
		<display:column title="${displayHeader}">
			<a href="event/display.do?eventId=${event.id}"><spring:message code="event.display"/></a>
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
			<spring:message code="event.register" var="registerHeader" />
			<display:column title="${registerHeader}">
				<jstl:if test="${event.chorbies.contains(principal)}">
					<a href="event/chorbi/unregister.do?eventId=${event.id}"><spring:message code="event.unregister"/></a>
				</jstl:if>
				<jstl:if test="${!event.chorbies.contains(principal)}">
					<jstl:if test="${available}">
						<a href="event/chorbi/register.do?eventId=${event.id}"><spring:message code="event.register"/></a>
					</jstl:if>
				</jstl:if>
			</display:column>
		</security:authorize>
		
	</security:authorize>
	
</display:table>
