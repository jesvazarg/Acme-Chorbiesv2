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

<form:form action="${requestURI}" modelAttribute="createChorbiForm">
	<acme:input code="chorbi.username" path="username" />
	<acme:password code="chorbi.password" path="password" />
	<acme:password code="chorbi.confirmPassword" path="confirmPassword" />
	<acme:input code="chorbi.name" path="name" />
	<acme:input code="chorbi.surname" path="surname" />
	<acme:input code="chorbi.email" path="email" />
	<acme:input code="chorbi.phone" path="phone" />
	<acme:input code="chorbi.picture" path="picture" />
	<acme:textarea code="chorbi.description" path="description" />
	
	<spring:message code="chorbi.relationship.love" var="love"/>
	<spring:message code="chorbi.relationship.activities" var="activities"/>
	<spring:message code="chorbi.relationship.friendship" var="friendship"/>
	<form:label path="relationship">
		<spring:message code="chorbi.relationship" />
	</form:label>
	<form:select path="relationship" >
		<form:option value=" " label="----" /> 
		<form:option value="${love}" label="${love}" />
		<form:option value="${activities}" label="${activities}" />
		<form:option value="${friendship}" label="${friendship}" />
	</form:select>
	<form:errors path="relationship" cssClass="error" />
	<br/>
	
	<acme:input code="chorbi.birthDate" path="birthDate" />
	
	<spring:message code="chorbi.genre.man" var="man"/>
	<spring:message code="chorbi.genre.woman" var="woman"/>
	<form:label path="genre">
		<spring:message code="chorbi.genre" />
	</form:label>
	<form:select path="genre" >
		<form:option value=" " label="----" /> 
		<form:option value="${man}" label="${man}" />
		<form:option value="${woman}" label="${woman}" />
	</form:select>
	<form:errors path="genre" cssClass="error" />
	<br/>
	
	<acme:input code="chorbi.coordinate.city" path="city" />
	<acme:input code="chorbi.coordinate.country" path="country" />
	<acme:input code="chorbi.coordinate.state" path="state" />
	<acme:input code="chorbi.coordinate.province" path="province" />
	
	<jstl:if test="${requestURI == 'chorbi/create.do'}">
		<br/>
		<form:checkbox path="isAgree"/>
		<form:label path="isAgree">
			<spring:message code="chorbi.isAgree" />
			<a href="misc/conditions.do" target="_blank"><spring:message code="chorbi.conditions" /></a>
		</form:label>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="chorbi.save" />
	<acme:cancel url="" code="chorbi.cancel" />
</form:form>
