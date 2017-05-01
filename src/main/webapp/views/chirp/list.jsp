<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="chirps" id="chirp" requestURI="chirp/list.do" class="displaytag">
	

	
	<acme:column code="chirp.subject" property="subject"/>
	<acme:column code="chirp.text" property="text"/>
	<acme:column code="chirp.moment" property="moment"/>
	<acme:column code="chirp.sender" property="sender.name"/>
	<acme:column code="chirp.recipients" property="recipients[0].name"/>
	<security:authorize access="hasAnyRole('ADMIN','CHORBI', 'MANAGER')">
	<display:column>
		<a href="chirp/display.do?chirpId=${chirp.id}"><spring:message
		code="chirp.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>




