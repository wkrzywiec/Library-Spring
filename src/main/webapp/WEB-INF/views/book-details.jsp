<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<div class="row"><b>Status:</b> &nbsp; ${book.status}</div>
				<div class="row"><b>Publisher:</b>	&nbsp; ${book.publisher}</div>
				<div class="row"><b>Published date:</b>	 &nbsp; ${book.publishedDate}</div>
				<div class="row"><b>Pages:</b>	&nbsp; ${book.pageCount}</div>
				<div class="row"><b>Average Rating:</b>	&nbsp; ${book.rating}</div>
				<div class="row"><b>Google ID:</b>	&nbsp; <a href="http://books.google.pl/books?id=${book.googleId}">${book.googleId}</a></div>
				<div class="row"><b>ISBN 10:</b>	&nbsp; ${book.isbn_10}</div>
				<div class="row"><b>ISBN 13:</b>	&nbsp; ${book.isbn_13}</div>
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
	
	
</body>
</html>