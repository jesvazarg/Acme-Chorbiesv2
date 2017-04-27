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

<display:table name="configuration" id="configuration" requestURI="configuration/list.do" class="displaytag">
	
	<%-- <spring:message code="configuration.time" var="timeHeader" />
	<display:column property="time" title="${timeHeader}" sortable="true" /> --%>
	
	<acme:column code="configuration.time" property="time"/>
	
	<acme:column code="configuration.feeManager" property="feeManager"/>
	
	<acme:column code="configuration.feeChorbi" property="feeChorbi"/>

</display:table>


<a href="configuration/administrator/edit.do"><spring:message
	code="configuration.editConfiguration"/>
</a>

<a href="configuration/administrator/collect.do"><spring:message
	code="configuration.collect"/>
</a>
