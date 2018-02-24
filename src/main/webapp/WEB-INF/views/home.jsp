<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

	<style>
		body {
		
			margin:0;
		
		}
	
	</style>

<title>Library portal - home page</title>
</head>
<body>

	<div id="header">
    	<jsp:include page="shared/header.jsp"/>
	</div>

<h2>Home page</h2>


	User <security:authentication property="principal.username"/>
	<br>
	Role: <security:authentication property="principal.authorities"/>
	<br>
	<form:form action="${pageContext.request.contextPath}/logout"
		method="POST">
		
		<input type="submit" value="Logout" />
		
	</form:form>

</body>
</html>