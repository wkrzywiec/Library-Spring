<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add new book to library</title>
</head>
<body>

	<div id="header">
	    	<jsp:include page="shared/header.jsp"/>
	</div>
	
	<div class="container">
		<div class="form-group">
			<c:if test="${message != null}">
				<div class="alert alert-warning col-xs-offset-1 col-xs-10">
				${message}
				</div>				
			</c:if>
		</div>
	</div>
	
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="col-xs-10 col-sm-10 col-md-10">
					<form:form action="${pageContext.request.contextPath}/books/add-book" method="GET" role="form">
							<label for="inputSearch">Find a new book</label>
							<div class="input-group">
	      						<input class="form-control" placeholder="Search for..." id="search" name="search" type="text" value="${param.search}">
	      						<span class="input-group-btn">
	        						<button class="btn btn-secondary" type="submit">Search</button>
	      						</span>
	    					</div>
					</form:form>
			</div>
		</div>
	</div>
	<c:forEach items="${bookList}" var="book">
			<div class="container" style="margin-top: 5px">
	    		<div class="card">
	      			<div class="row">
	      				<div class="col-md-4">
	          				<div style="width: 15rem; height: 20rem; background: url(${book.imageLink}) center no-repeat; background-size: cover;">
	          				</div>
	        			</div>
	        			<div class="col-md-8">
	          				<div class="card-block">
	            				<h4 class="card-title">${book.title}</h4>
	            				<p class="card-text">${book.authors}	${book.publishedDate}</p>
	            				<p class="card-text">${book.description}</p>
	            				<a href="${pageContext.request.contextPath}/books/add-book/${book.googleId}?search=${param.search}" class="btn btn-primary">Add book</a>
	          				</div>
	        			</div>
	      			</div>
	    		</div>
	  		</div>
	</c:forEach>
</body>
</html>