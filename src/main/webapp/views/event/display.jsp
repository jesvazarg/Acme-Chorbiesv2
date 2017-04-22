<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div>
	<ul>
		<li>
			<h3><jstl:out value="${event.title}" /></h3>
		</li>
		
		<li>
			<b><spring:message code="event.moment"/>:</b>
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${event.moment}" />
		</li>
		
		<li>
			<img src="${event.picture}" style = "max-width: 400 px; max-height: 400px;"/>
		</li>
		
		<li>
			<b><spring:message code="event.description" />:</b>
			<jstl:out value="${event.description}" />
		</li>
		
		<li>
			<b><spring:message code="event.seats" />:</b>
			<jstl:out value="${event.seats}" />
		</li>
		<security:authorize access="hasAnyRole('ADMIN','CHORBI','MANAGER')">
			<li>
				<b><spring:message code="event.manager" />:</b>
				<a href="profile/display.do?actorId=${event.manager.id}"><jstl:out value="${event.manager.name}" /></a>
			</li>
		</security:authorize>
		
	</ul>
	
	<security:authorize access="hasAnyRole('ADMIN','CHORBI','MANAGER')">
		<b><spring:message code="event.chorbies" /></b><br/>
		<display:table name="${event.chorbies}" id="chorbi" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
			<acme:column code="event.chorbi.name" property="name" sortable="true"/>
			<acme:column code="event.chorbi.surname" property="surname" sortable="true"/>
			<acme:column code="event.chorbi.birthDate" property="birthDate" format="{0,date,dd-MM-yyyy}" sortable="true"/>
			<acme:column code="event.chorbi.genre" property="genre" sortable="false"/>
			<spring:message code="event.chorbi.profile" var="profileHeader" />
			<display:column title="${profileHeader}">
				<a href="profile/display.do?actorId=${chorbi.id}"><spring:message code="event.display"/></a>
			</display:column>
		</display:table>
	</security:authorize>
	
</div>

