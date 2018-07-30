<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	
	<c:if test="${newUserRegister == true}">
		<div class="alert alert-success" role="alert">
  			New user has been successfully created!
		</div>
	</c:if>
	
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="col-xs-10 col-sm-10 col-md-10">
					<form:form action="${pageContext.request.contextPath}/admin-panel" method="GET" role="form">
							<label for="inputSearch">Find user (by username, name, email)</label>
							<div class="input-group">
	      						<input class="form-control" placeholder="Search for..." id="search" name="search" type="text" value="${param.search}">
	      						<span class="input-group-btn">
	        						<button class="btn btn-secondary" type="submit">Search</button>
	      						</span>
	    					</div>
	    					<input type="hidden" name="pageNo" value=1>
					</form:form>
			</div>
			
			<div class="col-xs-2 col-sm-2 col-md-2">
				<form:form action="${pageContext.request.contextPath}/admin-panel/new-user" method="GET" role="form">
					<div style="margin-top:32px">
						<button class="btn btn-success bottom">Create new user</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	
	<div class="container" style="margin-top: 50px;">
		
 		<c:if test="${param.search != null}"> 
			<p><em><small>There are ${resultsCount} result(s).</small></em></p>
		</c:if> 
		<table class="table table-striped" align="center">
			<thead>
     	 		<tr>
        			<th>Id</th>
        			<th>Username</th>
        			<th>Email</th>
        			<th>Roles</th>
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
						<td>
							<c:forEach items="${user.roles}" var="role">
								${role.name}
							</c:forEach>
						</td>
						<td>${user.enable}</td>
						<td>
							<a href="${pageContext.request.contextPath}/admin-panel/user/${user.id}" class="btn btn-sm btn-success" role="button">Edit</a> 
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<jsp:include page="shared/pagination.jsp"/>
	</div>

</body>
</html>