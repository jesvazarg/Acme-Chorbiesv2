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
	<%-- <b><spring:message code="configuration.banners"/>:</b>
	<display:table name="${configuration.banners}" id="banner" class="displaytag" pagesize="5" keepStatus="true">
		<spring:message code="configuration.url" var="urlHeader"/>
		 <display:column title="${ulrHeader}">
			<a href="${banner}"></a>
		</display:column> 
	</display:table> --%>
	
	
	
</display:table>


<a href="configuration/administrator/edit.do"><spring:message
	code="configuration.editTime"/>
</a>

