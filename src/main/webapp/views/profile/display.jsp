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
			<b><spring:message code="profile.name"/>:</b>
			<jstl:out value="${profile.name}" />
		</li>
		
		<li>
			<b><spring:message code="profile.surname"/>:</b>
			<jstl:out value="${profile.surname}"/>
		</li>
		
		<li>
			<b><spring:message code="profile.email"/>:</b>
			<jstl:out value="${email}"/>
		</li>
		
		<li>
			<b><spring:message code="profile.phone" />:</b>
			<jstl:out value="${phone}" />
		</li>
		
		<jstl:if test="${isAdmin==false}">
		
		
		<li>
			<b><spring:message code="profile.picture" />:</b><br/>
			<img src="${profile.picture}" style = "max-width: 200 px; max-height: 200px;"/>
		</li>
			
		<li>
			<b><spring:message code="profile.description" />:</b>
			<jstl:out value="${description}" />
		</li>
		
		<li>	
			<b><spring:message code="profile.relationship" />:</b>
			<jstl:out value="${profile.relationship}" />
		</li>
		
		<%-- <li>	
			<b><spring:message code="profile.birthDate" />:</b>
			<jstl:out value="${profile.birthDate}" />
		</li> --%>
		
		<li>	
			<b><spring:message code="profile.genre" />:</b>
			<jstl:out value="${profile.genre}" />
		</li>
		
		<li>	
			<b><spring:message code="profile.coordinates" />:</b>
			<jstl:out value="${profile.coordinate.city}" />
			
			<jstl:out value="${profile.coordinate.country}" />
			
			<jstl:out value="${profile.coordinate.state}" />
			
			<jstl:out value="${profile.coordinate.province}" />
		</li>
			
		</jstl:if>
		
	</ul>
	
</div>

<security:authorize access="hasRole('CHORBI')">
	<jstl:if test="${sameActor==false}">
	<div>
		<acme:button code="chirp.create" url="chirp/chorbi/create.do?chorbieId=${profile.id}"/>
	</div>
	</jstl:if>
	<jstl:if test="${sameActor==true}">
		<jstl:if test="${profile.creditCard != null}">
			<acme:button code="profile.creditCard.display" url="creditCard/chorbi/display.do"/>
		</jstl:if>
		<jstl:if test="${profile.creditCard == null}">
			<acme:button code="profile.creditCard.create" url="creditCard/chorbi/create.do"/>
		</jstl:if>
	</jstl:if>
</security:authorize>

<h2><spring:message code="profile.likeThem"/></h2>
<display:table name="${likeThem}" id="sense" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	<acme:column code="profile.name" property="sender.name" sortable="true"/>
	<acme:column code="profile.surname" property="sender.surname" sortable="true"/>
	<acme:column code="profile.relationship" property="sender.relationship" sortable="true"/>
	<acme:column code="profile.birthDate" property="sender.birthDate" format="{0,date,dd-MM-yyyy}" sortable="true"/>
	<acme:column code="profile.sense.stars" property="stars" sortable="true"/>
	<spring:message code="profile.sense.comment" var="commentHeader" />
	<display:column title="${commentHeader}">
		<jstl:if test="${sense.comment != null}">
			<jstl:out value="${sense.comment}"/>
		</jstl:if>
	</display:column>
	<spring:message code="profile.profile" var="profileHeader" />
	<display:column title="${profileHeader}">
		<a href="profile/display.do?actorId=${sense.sender.id}"><spring:message code="profile.display"/></a>
	</display:column>
</display:table>


<%-- 
	<div>
		<a href="chirp/chorbi/forward.do?chirpId=${chirp.id}"><spring:message
				code="chirp.reply" /></a>
	</div>
 --%>

