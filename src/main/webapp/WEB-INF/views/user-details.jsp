<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User [${user.username}] page - Admin panel</title>
<style>
	.custom-control-label::before, 
	.custom-control-label::after {
	top: .8rem;
	width: 1.25rem;
	height: 1.25rem;
}
</style>
</head>
<body>

	<div id="header">
    	<jsp:include page="shared/header.jsp"/>
	</div>
	
	
	
	<div class="container" style="margin-top: 30px;">
		<a href="javascript:history.back()">&#8592; Go back </a>
		<h2>${user.firstName} ${user.lastName}</h2>
	</div>
	<div class="container" style="margin-top: 30px;">
		<form:form action="${pageContext.request.contextPath}/admin-panel/user/${user.id}/update"
		 method="POST" role="form" modelAttribute="user" data-toggle="validator">
			<div class="row">
				<div class="col-xs-6 col-sm-6 col-md-6">
					<div class="card">
						 <h4 class="card-header">User info</h4>
						 <div class="card-body">
						 	<div class="row" style="margin-top:20px">
							 	<div class="col">
							 		<h5>Id:</h5>
							 	</div>
							 	<div class="col">
							 		<input class="form-control" value="${user.id}" readonly/>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Username:</h5>
							 	</div>
							 	<div class="col">
							 		<input id="username" name="username" class="form-control" value="${user.username}" readonly/>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
							 	<div class="col">
							 		<h5>Email address:</h5>
							 	</div>
							 	<div class="col">
							 		<div class="input-group">
								 		<input id="email" name="email" class="form-control" value="${user.email}"
								 		placeholder="Email" value="${user.email}" pattern="^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$"
										data-error="This email is invalid!" required/>
										
										<div>
								 			<form:errors path="email" cssClass="error"/>
								 			<div class="help-block with-errors"></div>
								 		</div>
							 		</div>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
							 	<div class="col">
							 		<h5>Roles:</h5>
							 	</div>
							 	<div class="col">
							 		<c:forEach items="${user.roles}" var="role">
							 			<c:choose>
							 				<c:when test="${role.name == 'LIBRARIAN'}">
							 					<button type="button" class="btn btn-info">${role.name}</button>
							 				</c:when>
							 				<c:when test="${role.name == 'ADMIN'}">
							 					<button type="button" class="btn btn-danger">${role.name}</button>
							 				</c:when>
							 				<c:otherwise>
							 					<button type="button" class="btn btn-secondary">${role.name}</button>
							 				</c:otherwise>
							 			</c:choose>
										<c:if test="${role.name} == 'USER'">
										</c:if>
									</c:forEach>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Password:</h5>
							 	</div>
							 	<div class="col">
							 		<a href="#link" class="btn btn-warning" role="button">Reset Password</a>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Active: </h5>
							 	</div>
							 	<div class="col">
							 		<form:checkbox path = "enable"/>
							 		<c:if test="${user.enable}">
							 		</c:if>
							 		<c:if test="${!user.enable}">
							 		</c:if>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Record created: </h5>
							 	</div>
							 	<div class="col">
							 		<input class="form-control" value="${user.recordCreated}" readonly/>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>User Logs: </h5>
							 	</div>
							 	<div class="col">
							 		<a href="${pageContext.request.contextPath}/admin-panel/user/${user.id}?addit=1">Show user logs</a>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Books: </h5>
							 	</div>
							 	<div class="col">
							 		<a data-toggle="modal" href="#reserveModal">Show user current books</a>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>User book history: </h5>
							 	</div>
							 	<div class="col">
							 		<a href="${pageContext.request.contextPath}/admin-panel/user/${user.id}?addit=2">Show user book history</a>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Penalties: </h5>
							 	</div>
							 	<div class="col">
							 		<p>PENALTIES</p>
							 	</div>
						 	</div>
						 </div>
					</div>
				</div>
				<div class="col-xs-6 col-sm-6 col-md-6">
					<div class="card">
						 <h5 class="card-header">Private info</h5>
						 <div class="card-body">
						 	<div class="row" style="margin-top:20px">
							 	<div class="col">
							 		<h5>First Name:</h5>
							 	</div>
							 	<div class="col">
							 		<div class="input-group">
								 		<input id="firstName" name="firstName" class="form-control" value="${user.firstName}"
								 		placeholder="First Name" data-error="First or/and last name is missing!" required/>
						
								 		<div>
								 			<form:errors path="firstName" cssClass="error"/>
								 			<div class="help-block with-errors"></div>
								 		</div>
							 		</div>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Last Name:</h5>
							 	</div>
							 	<div class="col">
							 		<div class="input-group">
								 		<input id="lastName" name="lastName" class="form-control" value="${user.lastName}"
								 		placeholder="Last Name" data-error="First or/and last name is missing!" required/>
								 		
								 		<div>
								 			<form:errors path="lastName" cssClass="error"/>
								 			<div class="help-block with-errors"></div>
								 		</div>
							 		</div>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Phone:</h5>
							 	</div>
							 	<div class="col">
							 		<div class="input-group">
								 		<input id="phone" name="phone" class="form-control" value="${user.phone}"
								 		placeholder="XXXXXXXXX" data-error="This phone number is invalid!"/>
								 		
								 		<div>
								 			<form:errors path="phone" cssClass="error"/>
								 			<div class="help-block with-errors"></div>
								 		</div>
							 		</div>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Address:</h5>
							 	</div>
							 	<div class="col">
							 		<div class="input-group">
								 		<input id="address" name="address" class="form-control" value="${user.address}"
								 		placeholder="Address" data-error="Physical address is missing!" />
								 		
								 		<div>
								 			<form:errors path="address" cssClass="error"/>
								 			<div class="help-block with-errors"></div>
								 		</div>
							 		</div>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>Postal code:</h5>
							 	</div>
							 	<div class="col">
							 		<div class="input-group">
								 		<input id="postalCode" name="postalCode" class="form-control" value="${user.postalCode}"
								 		placeholder="XX-XXX" pattern="\d{2}-\d{3}" data-error="The postal code is invalid!"/>
								 		
								 		<div>
								 			<form:errors path="postalCode" cssClass="error"/>
								 			<div class="help-block with-errors"></div>
								 		</div>
							 		</div>
							 	</div>
						 	</div>
						 	<div class="row" style="margin-top:20px">
						 		<div class="col">
							 		<h5>City:</h5>
							 	</div>
							 	<div class="col">
							 		<div class="input-group">
								 		<input id="city" name="city" class="form-control" value="${user.city}"
								 		placeholder="City" data-error="The city is missing!"/>
								 		
								 		<div>
								 			<form:errors path="city" cssClass="error"/>
								 			<div class="help-block with-errors"></div>
								 		</div>
							 		</div>
							 	</div>
						 	</div>
						 </div>
					</div>
				</div>
			</div>
			<div class="container" style="margin-top: 15px; margin-bottom: 15px">
				<button class="btn btn-success" type="submit">Save changes</button>
			</div>
		</form:form>
		<c:if test="${param.addit == 1}">
			<table class="table table-striped" align="center">
				<thead>
	     	 		<tr>
	        			<th>Id</th>
	        			<th>Username</th>
	        			<th>Date</th>
	        			<th>Field</th>
	        			<th>From</th>
	        			<th>To</th>
	        			<th>Details</th>
	        			<th>Modified by</th>
	      			</tr>
	    		</thead>
	    		<tbody>
	    			<c:forEach items="${logs}" var="log">
						<tr>
							<td>${log.id}</td>
							<td>${log.username}</td>
							<td>${log.dated}</td>
							<td>${log.field}</td>
							<td>${log.fromValue}</td>
							<td>${log.toValue}</td>
							<td>${log.message}</td>
							<td>${log.changedByUsername}</td>
						</tr>
					</c:forEach>
	    		</tbody>
	    	</table>
		</c:if>
	
		<c:if test="${param.addit == 2}">
			<table class="table table-striped" align="center">
				<thead>
	     	 		<tr>
	        			<th>Id</th>
	        			<th>Book Title</th>
	        			<th>User</th>
	        			<th>Details</th>
	        			<th>Dated</th>
	        			<th>Actioned by</th>
	      			</tr>
	    		</thead>
	    		<tbody>
	    			<c:forEach items="${logs}" var="log">
						<tr>
							<td>${log.id}</td>
							<td><a href="${pageContext.request.contextPath}/books/${log.bookId}">${log.bookTitle}</a></td>
							<td>${log.userFirstName} ${log.userLastName}</td>
							<td>${log.message}</td>
							<td>${log.dated}</td>
							<td>${log.changedByUsername}</td>
						</tr>
					</c:forEach>
	    		</tbody>
	    	</table>
		</c:if>
	</div>
	
	<div class="modal fade" id="reserveModal" tabindex="-1" role="dialog" aria-labelledby="reserveModalLabel" aria-hidden="true">
  		<div class="modal-dialog" role="document">
    		<div class="modal-content">
      			<div class="modal-header">
        			<h5 class="modal-title" id="exampleModalLabel">User current books</h5>
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          				<span aria-hidden="true">&times;</span>
        			</button>
      			</div>
      			<div class="modal-body">
        			<table class="table table-striped table-hover" align="center">
	        			<thead>
	     	 				<tr>
			        			<th>Book</th>
			        			<th>Status</th>
			        			<th>Due date</th>
	      					</tr>
	    				</thead>
	    				<tbody>
	    					<c:forEach items="${manageList}" var="manage">
								<tr>
									<td><a href="${pageContext.request.contextPath}/books/${manage.bookId}">${manage.bookTitle}</a></td>
									<td>${manage.bookStatus}</td>
									<td>${manage.dueDate}</td>
								</tr>
							</c:forEach>
	    				</tbody>
        			</table>
      			</div>
      			<div class="modal-footer">
        			<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
      			</div>
    		</div>
  		</div>
	</div>
</body>
</html>