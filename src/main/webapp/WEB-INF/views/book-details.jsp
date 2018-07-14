<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${book.title} - details</title>
</head>
<body>

	<div id="header">
    	<jsp:include page="shared/header.jsp"/>
	</div>
	
	<div class="container" style="margin-top: 30px;">
		<a href="javascript:history.back()">&#8592; Go back to Search Page</a>
	</div>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="col-3 big-box">
				<div style="width: 15rem; height: 20rem; background: url(${book.imageLink}) center no-repeat; background-size: cover;"></div>
			</div>
			
			<div class="col-9">
				<div class="row"><h2>${book.title}</h2></div>
				<div class="row"><h3>
					<c:forEach items="${book.authors}" var="author">
								${author} |
					</c:forEach>
				</h3></div>
				<div class="row"><b>Status:</b> &nbsp; 
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
				</div>
				<div class="row"><b>Publisher:</b>	&nbsp; ${book.publisher}</div>
				<div class="row"><b>Published date:</b>	 &nbsp; ${book.publishedDate}</div>
				<div class="row"><b>Pages:</b>	&nbsp; ${book.pageCount}</div>
				<div class="row"><b>Average Rating:</b>	&nbsp; ${book.rating}</div>
				<div class="row"><b>Google ID:</b>	&nbsp; <a href="http://books.google.pl/books?id=${book.googleId}">${book.googleId}</a></div>
				<div class="row"><b>ISBN 10:</b>	&nbsp; ${book.isbn_10}</div>
				<div class="row"><b>ISBN 13:</b>	&nbsp; ${book.isbn_13}</div>
				<div class="row align-items-center">
					<div class="col-1 align-middle" style="padding: 0px 5px 0px 0px;"><b>Action:</b></div>
					<div class="col-4">
						<c:choose>
							<c:when test="${book.status == 'AVAILABLE'}">
								<button type="button" class="btn btn-success" data-toggle="modal" data-target="#reserveModal">
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn-success" data-toggle="modal" data-target="#reserveModal" disabled>
							</c:otherwise>
						</c:choose>
  						Reserve
						</button>
						<security:authorize access="hasAuthority('ADMIN')">
        					<a href="${pageContext.request.contextPath}/books/${book.id}?addit=1" class="btn btn-info" role="button">Show logs</a>
        				</security:authorize>
        				<security:authorize access="hasAuthority('LIBRARIAN')">
        					<a href="${pageContext.request.contextPath}/books/${book.id}?addit=1" class="btn btn-info" role="button">Show logs</a>
        				</security:authorize>
					</div>
				</div>
			</div>
		</div>
		<hr>
		<div class="row">
			<h4>Categories:</h4>
		</div>
		<div class="row">
			<c:forEach items="${book.bookCategories}" var="category">
								${category} |
			</c:forEach>
		</div>
		
		<div class="row">
			<h3>Description:</h3>
		</div>
		<div class="row">
			${book.description}
		</div>
	</div>
	
	<div class="modal fade" id="reserveModal" tabindex="-1" role="dialog" aria-labelledby="reserveModalLabel" aria-hidden="true">
  		<div class="modal-dialog" role="document">
    		<div class="modal-content">
      			<div class="modal-header">
        			<h5 class="modal-title" id="exampleModalLabel">Make a reservation</h5>
        			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          				<span aria-hidden="true">&times;</span>
        			</button>
      			</div>
      			<div class="modal-body">
        		<p>Please confirm that you would like to make a reservation for <b>${book.title}</b>.</p>
        		<p>Remember that you have <b><u>2 days</u></b> to pick up the book from our library. Otherwise it will be canceled.
      			</div>
      			<div class="modal-footer">
        			<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        			<a href="${pageContext.request.contextPath}/books/${book.id}?action=reserve" class="btn btn-primary" role="button">Confirm</a>
      			</div>
    		</div>
  		</div>
	</div>
	
</body>
</html>