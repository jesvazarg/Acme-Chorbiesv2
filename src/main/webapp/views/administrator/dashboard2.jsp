<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.level.c"/></b></legend>
	<h3><spring:message code="admin.dashboard2.c1"/></h3>
		<display:table name="managesSortedEvents" id="row" requestURI="${requestURImanagesSortedEvents}" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.chorbi.name" property="name"/>
			<acme:column code="admin.dashboard.chorbi.surname" property="surname"/>
			
		</display:table>
		
		
	<h3><spring:message code="admin.dashboard2.c2"/></h3>
		<display:table name="managersAmountDueFee" id="row" requestURI="${requestURImanagersAmountDueFee}" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.chorbi.name" property="[0].name"/>
			<acme:column code="admin.dashboard.fee" property="[1]"/>
			
		</display:table>

	<h3><spring:message code="admin.dashboard2.c3"/></h3>
		<display:table name="chorbiesOrderByEventRegistered" id="row" requestURI="${requestURIchorbiesOrderByEventRegistered}" pagesize="5" class="displaytag">
				
				<acme:column code="admin.dashboard.chorbi.name" property="name"/>
				<acme:column code="admin.dashboard.chorbi.surname" property="surname"/>
				
		</display:table>
		
	<h3><spring:message code="admin.dashboard2.c4"/></h3>
		<display:table name="chorbiesAmountDueFee" id="row" requestURI="${requestURIchorbiesAmountDueFee}" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.chorbi.name" property="[0].name"/>
			<acme:column code="admin.dashboard.fee" property="[1]"/>
			
		</display:table>
</fieldset>

<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.level.b"/></b></legend>
	<h3><spring:message code="admin.dashboard2.b1"/></h3>
		<ul>
			<li>
				<b><spring:message code="admin.dashboard.min"/>:</b>
				<jstl:out value="${minMaxAvgStarsPerChorbi[0]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.avg"/>:</b>
				<jstl:out value="${minMaxAvgStarsPerChorbi[1]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.max"/>:</b>
				<jstl:out value="${minMaxAvgStarsPerChorbi[2]}"/>
			</li>
		</ul>

	<h3><spring:message code="admin.dashboard2.b2"/></h3>
		<display:table name="chorbiesSortedByAvgNumberOfStars" id="row" requestURI="${requestURIchorbiesSortedByAvgNumberOfStars}" pagesize="5" class="displaytag">
				
				<acme:column code="admin.dashboard.chorbi.name" property="name"/>
				<acme:column code="admin.dashboard.chorbi.surname" property="surname"/>
				
		</display:table>



</fieldset>