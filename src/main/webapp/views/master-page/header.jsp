<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<div>
	<img src="images/logo.png" alt="Acme-Chorbies Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/action-1.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>
					<li><a href="configuration/administrator/list.do"><spring:message code="master.page.administrator.configuration" /></a></li>
					<li><a href="banner/administrator/list.do"><spring:message code="master.page.administrator.banners" /></a></li>
					<li><a href="dashboard/administrator/list.do"><spring:message code="master.page.administrator.dasboard" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CHORBI')">
			<li><a class="fNiv"><spring:message	code="master.page.chirps" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chirp/chorbi/listIn.do"><spring:message code="master.page.chirps.received" /></a></li>
					<li><a href="chirp/chorbi/listOut.do"><spring:message code="master.page.chirps.sent" /></a></li>
				</ul>
			</li>
			<li><a href="searchTemplate/chorbi/display.do"><spring:message code="master.page.searchTemplate.display" /></a></li>	
					
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="chorbi/create.do"><spring:message code="master.page.chorbi.create" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<security:authorize access="hasAnyRole('CHORBI', 'ADMIN')">
				<li><a href="chorbi/list.do"><spring:message code="master.page.chorbi.list" /></a></li>
			</security:authorize>		
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasAnyRole('CHORBI', 'ADMIN')">
						<li><a href="profile/myProfile.do"><spring:message code="master.page.profile.myProfile" /></a></li>
						<security:authorize access="hasRole('CHORBI')">
							<li><a href="chorbi/edit.do"><spring:message code="master.page.chorbi.edit" /></a></li>
						</security:authorize>				
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

