<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<jstl:choose>
		<jstl:when test="${empty searchTemplate }">
			<a href="searchTemplate/chorbi/create.do"><spring:message code="searchTemplate.create" /></a>
		</jstl:when>
		<jstl:otherwise>
			<ul>
				<li>
					<b><spring:message code="searchTemplate.relationship"/>:</b>
					<spring:message code="searchTemplate.relationship.${searchTemplate.relationship}"/>
		<!-- 			<jstl:out value="${searchTemplate.relationship}"/>  -->
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.age"/>:</b>
					<jstl:out value="${searchTemplate.age}"/>
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.genre"/>:</b>
					<spring:message code="searchTemplate.relationship.${searchTemplate.genre}"/>
		<!--			<jstl:out value="${searchTemplate.genre}"/> -->
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.coordinates.city" />:</b>
					<jstl:out value="${searchTemplate.coordinate.city}" />
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.coordinates.country" />:</b>
					<jstl:out value="${searchTemplate.coordinate.country}" />
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.coordinates.state" />:</b>
					<jstl:out value="${searchTemplate.coordinate.state}" />
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.coordinates.province" />:</b>
					<jstl:out value="${searchTemplate.coordinate.province}" />
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.keyword" />:</b>
					<jstl:out value="${searchTemplate.keyword}" />
				</li>
						
			</ul>
			
			<a href="searchTemplate/chorbi/edit.do?searchTemplateId=${searchTemplate.id }"><spring:message code="searchTemplate.edit" /></a>
		</jstl:otherwise>
	</jstl:choose>
	<br>
	<jstl:if test="${not empty searchTemplate }">
		<a href="searchTemplate/chorbi/findBySearchTemplate.do?searchTemplateId=${searchTemplate.id }"><spring:message code="searchTemplate.searchChorbies" /></a>
	</jstl:if>
</div>