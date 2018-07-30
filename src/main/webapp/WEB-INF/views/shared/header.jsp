<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">	
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">

  <a class="navbar-brand" href="${pageContext.request.contextPath}/">Library portal</a>

  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
    </li>
    
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" id="navbardrop" data-toggle="dropdown">
      		Books
      </a>
      
      <div class="dropdown-menu dropdown-menu-right">
        <a class="dropdown-item" href="${pageContext.request.contextPath}/books/search">Search book</a>
        
        <security:authorize access="hasAuthority('LIBRARIAN')">
        	<a class="dropdown-item" href="${pageContext.request.contextPath}/books/add-book/">Add new book</a>
        </security:authorize>
        
        <security:authorize access="hasAuthority('ADMIN')">
        	<a class="dropdown-item" href="${pageContext.request.contextPath}/books/add-book/">Add new book</a>
        </security:authorize>
        
        <security:authorize access="hasAuthority('ADMIN')">
        	<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager/">Manage books</a>
        </security:authorize>
        
        <security:authorize access="hasAuthority('LIBRARIAN')">
        	<a class="dropdown-item" href="${pageContext.request.contextPath}/books/manager/">Manage books</a>
        </security:authorize>

      </div>
    </li>
    
    <security:authorize access="hasAuthority('ADMIN')">
    </security:authorize>
    
    <security:authorize access="hasAuthority('ADMIN')">
    <li class="nav-item">
      <a class="nav-link" href="${pageContext.request.contextPath}/admin-panel">Users</a>
    </li>
    </security:authorize>
  </ul>
  
  <ul class="navbar-nav ml-auto">
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" id="navbardrop" data-toggle="dropdown" style="color: #656370;">
      	<i class="fas fa-user-circle" style="font-size: 20px"></i>
      	<security:authentication property="principal.username"/>
      </a>
      
      <div class="dropdown-menu dropdown-menu-right">
        <a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Profile</a>
        <a class="dropdown-item" href="#">Change password</a>
        
        <form:form id="form1" action="${pageContext.request.contextPath}/logout" method="post">
        <a class="dropdown-item" href="javascript:;" onclick="parentNode.submit()" style="background: #f3f1f4">Sign out</a>
      	</form:form>
      	
      </div>
    </li>
  </ul>
  
  
</nav>


