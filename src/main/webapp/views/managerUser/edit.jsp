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

<form:form action="${requestURI}" modelAttribute="createManagerForm">

	<acme:input code="manager.username" path="username" />
	<acme:password code="manager.password" path="password" />
	<acme:password code="manager.confirmPassword" path="confirmPassword" />
	<acme:input code="manager.name" path="name" />
	<acme:input code="manager.surname" path="surname" />
	<acme:input code="manager.email" path="email" />
	<acme:input code="manager.phone" path="phone" />
	<acme:input code="manager.company" path="company" />
	<acme:input code="manager.vat" path="vat" />
	
	
		<acme:input code="creditCard.holderName" path="holderName" />
		<acme:input code="creditCard.brandName" path="brandName" />
		<acme:input code="creditCard.number" path="number" />
		<acme:input code="creditCard.expirationMonth" path="expirationMonth" type="number" min="1" max="12" />
		<acme:input code="creditCard.expirationYear" path="expirationYear" type="number" min="2017" />
		<acme:input code="creditCard.cvv" path="cvv" type="number" min="100" max="999" />
	
	
	<jstl:if test="${requestURI == 'managerUser/create.do'}">
		<br/>
		<form:checkbox path="isAgree"/>
		<form:label path="isAgree">
			<spring:message code="manager.isAgree" />
			<a href="misc/conditions.do" target="_blank"><spring:message code="manager.conditions" /></a>
		</form:label>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="manager.save" />
	<acme:cancel url="" code="manager.cancel" />
</form:form>
