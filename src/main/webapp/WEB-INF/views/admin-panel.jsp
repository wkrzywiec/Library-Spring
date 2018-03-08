<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Library portal - administrator panel</title>
</head>
<body>

	<div id="header">
    	<jsp:include page="shared/header.jsp"/>
	</div>

	<table class="table table-striped" align="center">
		<thead>
     	 	<tr>
        		<th>Id</th>
        		<th>Username</th>
        		<th>Email</th>
        		<th>Active</th>
        		<th>Edit user</th>
      		</tr>
    	</thead>
    	<tbody>
			<c:forEach items="${userList}" var="user">
				<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td>${user.email}</td>
				<td>${user.enable}</td>
				</tr>
			</c:forEach>
		</tbody>
	
	</table>

</body>
</html>