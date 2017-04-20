<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.level.c"/></b></legend>
	<h3><spring:message code="admin.dashboard.c1"/></h3>
		<display:table name="numberChorbiPerCountryAndCity" id="row" requestURI="${requestURInumberChorbiPerCountryAndCity}" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.coordinate.country" property="[0]"/>
			<acme:column code="admin.dashboard.coordinate.city" property="[1]"/>
			<acme:column code="admin.dashboard.chorbies.number" property="[2]"/>
			
		</display:table>
		
	<h3><spring:message code="admin.dashboard.c2"/></h3>
		<ul>
			<li>
				<b><spring:message code="admin.dashboard.min"/>:</b>
				<jstl:out value="${minMaxAvgAgeOfChorbi2[0]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.avg"/>:</b>
				<jstl:out value="${minMaxAvgAgeOfChorbi2[1]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.max"/>:</b>
				<jstl:out value="${minMaxAvgAgeOfChorbi2[2]}"/>
			</li>
		</ul>
		
	<h3><spring:message code="admin.dashboard.c3"/></h3>
		<br/>
		<jstl:out value="${ratioChorbiesWithNullOrInvalidCreditcard}" />
		<br/><br/>
		
	<h3><spring:message code="admin.dashboard.c4"/></h3>
		<br/>
		<jstl:out value="${ratioPerChorbiAndSearchTemplateRelationship}" />
		<br/><br/>
	</fieldset>
	
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.level.b"/></b></legend>
		<h3><spring:message code="admin.dashboard.b1"/></h3>
		<display:table name="chorbiesSortedGotLikes" id="row" requestURI="${requestURIchorbiesSortedGotLikes}" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.chorbi.name" property="name"/>
			<acme:column code="admin.dashboard.chorbi.surname" property="surname"/>
			<acme:column code="admin.dashboard.chorbi.email" property="email"/>
			
		</display:table>
		
		<h3><spring:message code="admin.dashboard.b2"/></h3>
		<ul>
			<li>
				<b><spring:message code="admin.dashboard.min"/>:</b>
				<jstl:out value="${minAvgMaxOfSenses[0]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.avg"/>:</b>
				<jstl:out value="${minAvgMaxOfSenses[1]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.max"/>:</b>
				<jstl:out value="${minAvgMaxOfSenses[2]}"/>
			</li>
		</ul>
	</fieldset>
	
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.level.a"/></b></legend>
	<h3><spring:message code="admin.dashboard.a1"/></h3>
		<ul>
			<li>
				<b><spring:message code="admin.dashboard.min"/>:</b>
				<jstl:out value="${minMaxAvgReciveChirps[0]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.avg"/>:</b>
				<jstl:out value="${minMaxAvgReciveChirps[1]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.max"/>:</b>
				<jstl:out value="${minMaxAvgReciveChirps[2]}"/>
			</li>
		</ul>
		
		<h3><spring:message code="admin.dashboard.a2"/></h3>
		<ul>
			<li>
				<b><spring:message code="admin.dashboard.min"/>:</b>
				<jstl:out value="${minAvgMaxChirpsSent[0]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.avg"/>:</b>
				<jstl:out value="${minAvgMaxChirpsSent[1]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.max"/>:</b>
				<jstl:out value="${minAvgMaxChirpsSent[2]}"/>
			</li>
		</ul>
		
		<h3><spring:message code="admin.dashboard.a3"/></h3>
		<display:table name="findChorbiMoreReciveChirps" id="row" requestURI="${requestURIfindChorbiMoreReciveChirps}" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.chorbi.name" property="name"/>
			<acme:column code="admin.dashboard.chorbi.surname" property="surname"/>
			<acme:column code="admin.dashboard.chorbi.email" property="email"/>
			
		</display:table>
		
		<h3><spring:message code="admin.dashboard.a4"/></h3>
		<display:table name="findChorbiMoreSentChirps" id="row" requestURI="${requestURIfindChorbiMoreSentChirps}" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.chorbi.name" property="name"/>
			<acme:column code="admin.dashboard.chorbi.surname" property="surname"/>
			<acme:column code="admin.dashboard.chorbi.email" property="email"/>
			
		</display:table>
		
	</fieldset>