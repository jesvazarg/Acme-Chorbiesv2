<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="configuration/administrator/edit.do" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<form:label path="time">
		<spring:message code="configuration.time"/>
	</form:label>
	<form:input path="time" />
		<form:errors cssClass="error" path="time"/>
	<br/>
	
	<form:label path="feeManager">
		<spring:message code="configuration.feeManager"/>
	</form:label>
	<form:input path="feeManager" />
		<form:errors cssClass="error" path="feeManager"/>
	<br/>
	
	<form:label path="feeChorbi">
		<spring:message code="configuration.feeChorbi"/>
	</form:label>
	<form:input path="feeChorbi" />
		<form:errors cssClass="error" path="feeManager"/>
	<br/>

	<input type="submit" name="save"
		value="<spring:message code="configuration.save" />" />
	
	<input type="button" name="cancel"
		value="<spring:message code="configuration.cancel" />"
		onclick="location='configuration/administrator/list.do'" />
</form:form>