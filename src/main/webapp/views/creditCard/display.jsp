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

<div>
	<ul>
		<li>
			<b><spring:message code="creditCard.holderName" /></b>
			<jstl:out value="${creditCard.holderName}"/>
		</li>
		<li>
			<b><spring:message code="creditCard.brandName" /></b>
			<jstl:out value="${creditCard.brandName}"/>
		</li>
		<li>
			<b><spring:message code="creditCard.number" /></b>
			<jstl:out value="${creditCard.number}"/>
		</li>
		<li>
			<b><spring:message code="creditCard.expirationMonth" /></b>
			<jstl:out value="${creditCard.expirationMonth}"/>
		</li>
		<li>
			<b><spring:message code="creditCard.expirationYear" /></b>
			<jstl:out value="${creditCard.expirationYear}"/>
		</li>
		<li>
			<b><spring:message code="creditCard.cvv" /></b>
			<jstl:out value="${creditCard.cvv}"/>
		</li>
		
		<acme:button url="creditCard/chorbi/edit.do" code="creditCard.edit"/>
		<acme:button url="creditCard/chorbi/delete.do" code="creditCard.delete"/>
		
	</ul>
</div>

