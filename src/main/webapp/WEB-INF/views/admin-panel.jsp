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
	
	<div class="container" style="margin-top: 50px;">
		<div class="col-xs-3 col-sm-3 col-md-3">
			
		</div>
		
		<div class="col-xs-8 col-sm-8 col-md-8">
			<div style="margin-bottom: 25px">
				<form:form action="${pageContext.request.contextPath}/admin-panel" method="GET" role="form">
						<label for="inputSearch">Find user (by username, name, email)</label>
						<div class="input-group">
      						<input class="form-control" placeholder="Search for..." id="search" name="search" type="text">
      						<span class="input-group-btn">
        						<button class="btn btn-secondary" type="submit">Search</button>
      						</span>
    					</div>
    					<input type="hidden" name="pageNo" value=1>
				</form:form>
			</div>
		
		</div>
		
		<div class="col-xs-1 col-sm-1 col-md-1">
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
						<button type="button" class="btn btn-sm btn-success">Edit</button> 
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