<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
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
	            				<h4 class="card-title"><a href="${pageContext.request.contextPath}/books/${book.id}">${book.title}</a></h4><span>${book.status}</span>
	            				<p class="card-text">${book.authors}	${book.publishedDate}</p>
	            				<p class="card-text">${book.description}</p>
	            				<a href="${pageContext.request.contextPath}/books/${book.id}" class="btn btn-primary">View Book details</a>
	          				</div>
	        			</div>
	      			</div>
	    		</div>
	  		</div>
		</c:forEach>
		
		<div class="container" style="margin-top: 50px;">
			<nav aria-label="Books search navigation">
				<ul class="pagination">
				
				<c:if test="${param.search == null}">
					<li class="page-item">
						<a class="page-link">First</a>
					</li>
					<li class="page-item">
						<a class="page-link">1</a>
					</li>
					<li class="page-item">
						<a class="page-link">Last</a>
					</li>
				</c:if>
				
				<c:if test="${param.search != null}">
					
					<c:choose>
						<c:when test="${param.pageNo == 1}">
							<c:set var="previousPage" scope="request" value="1"/>
						</c:when>
						<c:otherwise>
							<c:set var="previousPage" scope="request" value="${param.pageNo-1}"/>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${param.pageNo == pageCount}">
							<c:set var="nextPage" scope="request" value="${param.pageNo}"/>
						</c:when>
						<c:otherwise>
							<c:set var="nextPage" scope="request" value="${param.pageNo+1}"/>
						</c:otherwise>
					</c:choose>
					
					
					<c:choose>
						<c:when test="${param.pageNo <= 3}">
							<c:set var="firstPage" scope="request" value="1" />
						</c:when>
						<c:otherwise>
							<c:set var="firstPage" scope="request" value="${param.pageNo-2}" />
						</c:otherwise>
					</c:choose>
					
				
					<c:choose>
						<c:when test="${pageCount <= 5}">
							<c:set var="lastPage" scope="request" value="${pageCount}" />
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${pageCount-param.pageNo == 0}">
									<c:set var="lastPage" scope="request" value="${param.pageNo}" />
								</c:when>
								<c:when test="${pageCount-param.pageNo == 1}">
									<c:set var="lastPage" scope="request" value="${param.pageNo+1}" />
								</c:when>
								<c:otherwise>
									<c:set var="lastPage" scope="request" value="${param.pageNo+2}" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					
					
					
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/books/search?search=<c:out value="${param.search}"/>&pageNo=1">First</a>
					</li>
					
					<li class="page-item">
						<a class="page-link" aria-label="Previous" 
						href="${pageContext.request.contextPath}/books/search?search=<c:out value="${param.search}"/>&pageNo=<c:out value="${previousPage}"/>">																		
							<span aria-hidden="true">&laquo;</span>
							<span class="sr-only">Previous</span>
						</a>
					</li>
					
					<c:forEach begin="${firstPage}" end="${lastPage}" varStatus="loop">
						
						<li class="page-item">
							<a class="page-link" href="${pageContext.request.contextPath}/books/search?search=<c:out value="${param.search}"/>&pageNo=<c:out value="${loop.index}"/>">
								<c:choose>
									<c:when test="${loop.index == param.pageNo}">
									<b>${loop.index}</b>
									</c:when>
									<c:otherwise>
									${loop.index}
									</c:otherwise>
								</c:choose>
							</a>	
						</li>
					</c:forEach>
					
					<li class="page-item">
						<a class="page-link" aria-label="Next" 
						href="${pageContext.request.contextPath}/books/search?search=<c:out value="${param.search}"/>&pageNo=<c:out value="${nextPage}"/>">												
							<span aria-hidden="true">&raquo;</span>
							<span class="sr-only">Next</span>
						</a>
					</li>
					
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/books/search?search=<c:out value="${param.search}"/>&pageNo=<c:out value="${pageCount}"/>">Last (<c:out value="${pageCount}"/>)</a>
					</li>
					
				</c:if>
				</ul>
			</nav>
		</div>
	
	</div>
	
</body>
</html>