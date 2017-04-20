<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="searchTemplate/chorbi/edit.do" modelAttribute="searchTemplate" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="results" />
	<form:hidden path="updateMoment" />
	
	<spring:message code="searchTemplate.relationship.love" var="love"/>
	<spring:message code="searchTemplate.relationship.activities" var="activities"/>
	<spring:message code="searchTemplate.relationship.friendship" var="friendship"/>
	
	
	
	<form:label path="relationship">
		<spring:message code="searchTemplate.relationship" />
	</form:label>
	<form:select path="relationship" >
		<form:option value="" label="----" /> 
		<form:option value="Love" label="${love}" />
		<form:option value="Activities" label="${activities}" />
		<form:option value="Friendship" label="${friendship}" />
	</form:select>
	<form:errors path="relationship" cssClass="error" />
	<br/>
	
	<spring:message code="searchTemplate.relationship.man" var="man"/>
	<spring:message code="searchTemplate.relationship.woman" var="woman"/>
	
	<form:label path="genre">
		<spring:message code="searchTemplate.genre" />
	</form:label>
	<form:select path="genre" >
		<form:option value="" label="----" /> 
		<form:option value="Man" label="${man}" />
		<form:option value="Woman" label="${woman}" />
	</form:select>
	<form:errors path="genre" cssClass="error" />
	<br/>

	<acme:input code="searchTemplate.age" path="age" />
	<acme:input code="searchTemplate.coordinates.city" path="coordinate.city" />
	<acme:input code="searchTemplate.coordinates.country" path="coordinate.country" />
	<acme:input code="searchTemplate.coordinates.state" path="coordinate.state" />
	<acme:input code="searchTemplate.coordinates.province" path="coordinate.province" />
	<acme:input code="searchTemplate.keyword" path="keyword" />
	
	
	<acme:submit name="save" code="searchTemplate.save" />
	
	<acme:cancel url="searchTemplate/chorbi/display.do" code="searchTemplate.cancel" />
	
	<span class="message"><spring:message code="creditcard.${creditCardError}" /></span>
	
</form:form>