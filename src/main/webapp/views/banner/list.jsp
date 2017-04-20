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

<display:table name="banners" id="banner" requestURI="banner/list.do" class="displaytag">
	
	
	<acme:column code="banner.picture" property="picture" sortable="true"/>
	<spring:message code="chorbi.profile" var="profileHeader" />
	<display:column title="${bannerHeader}">
		<a href="banner/administrator/edit.do?bannerId=${banner.id}"><spring:message code="banner.edit"/></a>
	</display:column>
		
	
</display:table>


<a href="banner/administrator/create.do"><spring:message
	code="banner.create"/>
</a>

