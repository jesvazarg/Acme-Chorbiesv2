<%--
 * index.jsp
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

<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 

<security:authorize access="hasRole('BANNED')">
	<h1 style="background:Tomato;border:solid;border-color:black;font-weight:bold"><spring:message code="mensaje.isBanned"/></h1>
</security:authorize>

<security:authorize access="isAnonymous()">
	<img src="${banner}" style = "max-width: 200 px; max-height: 200px;"/>
</security:authorize>

<script type="text/javascript">
    window.cookieconsent_options = {"message":"<spring:message code="mensaje.script.cookies"/>","dismiss":"Aceptar","learnMore":"Ver más","link":"http://www.google.com/intl/es-419/policies/technologies/types/","theme":"dark-bottom"};
</script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/1.0.9/cookieconsent.min.js"></script>

