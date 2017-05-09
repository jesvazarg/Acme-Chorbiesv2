<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form method="post" action="chirp/forward.do" modelAttribute="chirp" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<form:hidden path="copy" />

	<%-- <acme:select items="${recipients}" itemLabel="name" code="chirp.recipients" path="recipients" /> --%>
	
	<form:label path="recipients">
		<spring:message code="chirp.recipients" />
	</form:label>	
	<form:select id="${id}" path="recipients">
		<%-- <form:option value="0" label="----" /> --%>		
		<form:options items="${recipients}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors path="${path}" cssClass="error" />
	
	<acme:input code="chirp.subject" path="subject" />
	
	<acme:textarea code="chirp.text" path="text" />
	
	<acme:input code="chirp.attachments" path="attachments" />
	
	
	<acme:submit name="save" code="chirp.save" />
	
	<acme:cancel url="chorbi/list.do" code="chirp.cancel" />
	
</form:form>