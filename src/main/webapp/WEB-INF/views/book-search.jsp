<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search book in Library</title>
</head>
<body>

	<div id="header">
		<jsp:include page="shared/header.jsp"/>
	</div>
	
	<div class="container" style="margin-top: 50px;">
		<h2 class="subtitle">What book would you like to find in our library?</h2>
		<div class="row">
			<div class="col-xs-10 col-sm-10 col-md-10">
					<form:form action="${pageContext.request.contextPath}/books/search" method="GET" role="form">
							<label for="inputSearch">Search book in library</label>
							<div class="input-group">
	      						<input class="form-control" placeholder="Search for..." id="search" name="search" type="text" value="${param.search}">
	      						<span class="input-group-btn">
	        						<button class="btn btn-secondary" type="submit">Search</button>
	      						</span>
	    					</div>
	    					<input type="hidden" name="pageNo" value=1>
					</form:form>
			</div>
		</div>
	</div>
	
	<div class="container" style="margin-top: 50px;">
		<c:if test="${param.search != null}"> 
			<p><em><small>There are ${resultsCount} result(s).</small></em></p>
		</c:if> 
		<c:forEach items="${bookList}" var="book">
			<div class="container" style="margin-top: 5px">
	    		<div class="card">
	      			<div class="row">
	      				<div class="col-md-4">
	          				<div style="width: 15rem; height: 20rem; background: url(${book.imageLink}) center no-repeat; background-size: cover;"></div>
	        			</div>
	        			<div class="col-md-8">
	          				<div class="card-block">
	            				<h4 class="card-title"><a href="${pageContext.request.contextPath}/books/${book.id}">${book.title}</a></h4>
	            				
	            				<c:choose>
									<c:when test="${book.status == 'AVAILABLE'}">
										<span class="book-status-available">
									</c:when>
									<c:when test="${book.status == 'RESERVED'}">
										<span class="book-status-reserved">
									</c:when>
									<c:otherwise>
										<span class="book-status-borrowed">
									</c:otherwise>
								</c:choose>
	            					${book.status}
	            				</span>
	            				<p class="card-text">${book.authors}	${book.publishedDate}</p>
	            				<p class="card-text">${book.description}</p>
	            				<a href="${pageContext.request.contextPath}/books/${book.id}" class="btn btn-primary">View Book details</a>
	          				</div>
	        			</div>
	      			</div>
	    		</div>
	  		</div>
		</c:forEach>
		
		<jsp:include page="shared/pagination.jsp"/>
	</div>
	
</body>
</html>