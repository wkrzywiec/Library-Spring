<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Manager</title>
</head>
<body>

	<div id="header">
	    	<jsp:include page="shared/header.jsp"/>
	</div>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="col-xs-10 col-sm-10 col-md-10">
				<form:form action="${pageContext.request.contextPath}/books/manager" method="GET" role="form">
					<div class="row">
						<label for="inputSearch">Find book for a user</label>
						<div class="input-group">
		      				<input class="form-control" placeholder="Search for..." id="search" name="search" type="text" value="${param.search}">
		      				<span class="input-group-btn">
		        				<button class="btn btn-secondary" type="submit">Search</button>
		      				</span>
		    			</div>
		    			<input type="hidden" name="pageNo" value=1>
					</div>
					<div class="row" style="margin-top: 5px;">
						<span style="margin-right: 15px;">Search for: </span>
						<label  class="radio-inline">
							<input type="radio" name="type" id="user"  value="1" checked/>User
							<input type="radio" name="type" id="book"  value="2"/>Book
							<input type="radio" name="type" id="both"  value="3"/>Both
						</label>
					</div>	
					<div class="row" style="margin-top: 5px;">
						<span style="margin-right: 15px;">Book status: </span>
						<label  class="radio-inline">
							<input type="radio" name="status" id="user"  value="1"/>Reserved
							<input type="radio" name="status" id="book"  value="2"/>Borrowed
							<input type="radio" name="status" id="user"  value="3" checked/>Both
						</label>
					</div>	
				</form:form>
			</div>
			
			<div class="col-xs-2 col-sm-2 col-md-2">
				<form:form action="${pageContext.request.contextPath}/books/manager/showAll" method="GET" role="form">
					<div style="margin-top:32px">
						<button class="btn btn-success bottom">Show All pending books</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="container" style="margin-top: 50px;">
		
 		<c:if test="${param.search != null}"> 
 		<div class="container" style="margin-bottom: 15px;">
 			<div class="row">
	 			<div class="col-xs-2 col-sm-2 col-md-2">
	 				<p><em><small>There are ${resultsCount} result(s).</small></em></p>
	 			</div>
	 			<div class="col-xs-9 col-sm-9 col-md-9">
	 			</div>
	 			<div class="col-xs-1 col-sm-1 col-md-1">
					<div class="btn-group">
		  				<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    				Sort by
		  				</button>
		  				<div class="dropdown-menu">
		  					<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager?search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=rel">Relevance</a>
		    				<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager?search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=bookAsc">Book: A to Z</a>
		    				<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager?search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=bookDes">Book: Z to A</a>
		    				<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager?search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=userAsc">User: A to Z</a>
		    				<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager?search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=userDes">User: Z to A</a>
		    				<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager?search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=statusAsc">Status: A to Z</a>
		    				<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager?search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=statusDes">Status: Z to A</a>
		  				</div>
					</div>
				</div>
 			</div>
 		</div>
			
		</c:if> 
		<table class="table table-striped table-hover" align="center">
			<thead>
     	 		<tr>
        			<th>Book</th>
        			<th>User Name</th>
        			<th>Book status</th>
        			<th>Due date</th>
        			<th>Action</th>
      			</tr>
    		</thead>
    		<tbody>
				<c:forEach items="${manageList}" var="manage">
					<tr>
						<td><a href="${pageContext.request.contextPath}/books/${manage.bookId}">${manage.bookTitle}</a></td>
						<td><a href="${pageContext.request.contextPath}/admin-panel/user/${manage.userId}">${manage.userFirstName} ${manage.userLastName}</a></td>
						<td>${manage.bookStatus}</td>
						<td>${manage.dueDate}</td>
						<td>
							<c:choose>
								<c:when test="${manage.bookStatus == 'RESERVED'}">
									<a href="${pageContext.request.contextPath}/books/manager?action=1&id=${manage.bookId}&search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=${param.sort}" class="btn btn-sm btn-warning" role="button">Borrow</a>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath}/books/manager?action=2&id=${manage.bookId}&search=${param.search}&pageNo=${param.pageNo}&type=${param.type}&status=${param.status}&sort=${param.sort}" class="btn btn-sm btn-success" role="button">Return</a>
								</c:otherwise>
							</c:choose>
							 
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<jsp:include page="shared/pagination.jsp"/>
	</div>
</body>
</html>