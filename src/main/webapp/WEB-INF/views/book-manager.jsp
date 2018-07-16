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
							<input type="radio" name="option" id="user"  value="1"/>User
							<input type="radio" name="option" id="book"  value="2"/>Book
							<input type="radio" name="option" id="both"  value="3"/>Both
						</label>
					</div>	
					<div class="row" style="margin-top: 5px;">
						<span style="margin-right: 15px;">Book status: </span>
						<label  class="radio-inline">
							<input type="radio" name="status" id="user"  value="1"/>Reserved
							<input type="radio" name="status" id="book"  value="2"/>Borrowed
							<input type="radio" name="status" id="user"  value="3"/>Both
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
			<p><em><small>There are ${resultsCount} result(s).</small></em></p>
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
						<td>${manage.bookTitle}</td>
						<td>${manage.userFirstName} ${manage.userLastName}</td>
						<td>${manage.bookStatus}</td>
						<td>${manage.dueDate}</td>
						<td>
							<c:choose>
								<c:when test="${manage.bookStatus == 'RESERVED'}">
									<a href="${pageContext.request.contextPath}/books/manager/${user.id}" class="btn btn-sm btn-warning" role="button">Borrow</a>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath}/books/manager/${user.id}" class="btn btn-sm btn-success" role="button">Receive</a>
								</c:otherwise>
							</c:choose>
							 
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="container" style="margin-top: 50px;">
			<nav aria-label="Users search navigation">
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
						<a class="page-link" href="${pageContext.request.contextPath}/admin-panel?search=<c:out value="${param.search}"/>&pageNo=1">First</a>
					</li>
					
					<li class="page-item">
						<a class="page-link" aria-label="Previous" 
						href="${pageContext.request.contextPath}/admin-panel?search=<c:out value="${param.search}"/>&pageNo=<c:out value="${previousPage}"/>">																		
							<span aria-hidden="true">&laquo;</span>
							<span class="sr-only">Previous</span>
						</a>
					</li>
					
					<c:forEach begin="${firstPage}" end="${lastPage}" varStatus="loop">
						
						<li class="page-item">
							<a class="page-link" href="${pageContext.request.contextPath}/admin-panel?search=<c:out value="${param.search}"/>&pageNo=<c:out value="${loop.index}"/>">
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
						href="${pageContext.request.contextPath}/admin-panel?search=<c:out value="${param.search}"/>&pageNo=<c:out value="${nextPage}"/>">												
							<span aria-hidden="true">&raquo;</span>
							<span class="sr-only">Next</span>
						</a>
					</li>
					
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/admin-panel?search=<c:out value="${param.search}"/>&pageNo=<c:out value="${pageCount}"/>">Last (<c:out value="${pageCount}"/>)</a>
					</li>
					
				</c:if>
				</ul>
			</nav>
		</div>
	</div>
</body>
</html>