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
			<b><spring:message code="chirp.moment"/>:</b>
			<jstl:out value="${chirp.moment}" />
		</li>
		
		<li>
			<b><spring:message code="chirp.subject"/>:</b>
			<jstl:out value="${chirp.subject}"/>
		</li>
		
		<li>
			<b><spring:message code="chirp.text"/>:</b>
			<jstl:out value="${chirp.text}"/>
		</li>
		
		<li>
			<b><spring:message code="chirp.sender" />:</b>
			<jstl:out value="${chirp.sender.name}" />
		</li>
		
		<li>
			<b><spring:message code="chirp.recipients" />:</b>
			<jstl:out value="${chirp.recipients}" />
		</li>
		
		<li>
			<b><spring:message code="chirp.attachments" />:</b>
			<jstl:out value="${chirp.attachments}" />
		</li>
		
	</ul>
	
</div>

<jstl:if test="${isRecipient==true}">
	<div>
		<a href="chirp/chorbi/reply.do?chirpId=${chirp.id}"><spring:message
				code="chirp.reply" /></a>
	</div>
</jstl:if>

<jstl:if test="${isRecipient==false}">
	<div>
		<a href="chirp/chorbi/forward.do?chirpId=${chirp.id}"><spring:message
				code="chirp.forward" /></a>
	</div>
</jstl:if>

<form:form method="post" action="chirp/chorbi/delete.do" modelAttribute="chirp" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="copy" />
	<form:hidden path="sender" />
	<form:hidden path="recipients" />
	<form:hidden path="attachments" />
	
	
	<jstl:if test="${chirp.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="chirp.delete" />"
			onclick="return confirm('<spring:message code="chirp.confirm.delete" />')" />&nbsp;
	</jstl:if>
</form:form>

