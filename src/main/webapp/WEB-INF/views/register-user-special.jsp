<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.5/validator.min.js"></script>
	
	<style>
		h1 {
		    font-family: "Arial Black", Gadget, sans-serif;
		    font-size: 400%;
		    letter-spacing: 4px;
		    color: #F0E68C;
			}
			
		p 	{
				color: #F4A460;
			}
			
		.error {
				color: red;
			}
	</style>
	<title>Library portal - administrator panel (Create new user)</title>
</head>
<body>

	<div id="header">
    	<jsp:include page="shared/header.jsp"/>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12">
				<div id="register-box" style="margin-top: 20px;">
					<div class="card">
						<h5 class="card-header text-white bg-secondary mb-3">
							Create new librarian/admin user
						</h5>
						<div class="card-body"> 
							<form:form action="${pageContext.request.contextPath}/admin-panel/new-user"
							 	method="POST" modelAttribute="user" class="form-horizontal"
							 	data-toggle="validator" role="form">
							 	
							 	<div class="form-group">
								 	<div class="row">
								 		<div class="col-xs-6 col-sm-6 col-md-6">
								 			<label for="firstName">First Name</label>
								 		</div>
								 		
								 		<div class="col-xs-6 col-sm-6 col-md-6">
								 			<label for="lastName">Last Name</label>
								 		</div>
								 	
								 		<div class="col-xs-6 col-sm-6 col-md-6">
								 			<div class="input-group mb-3">
	  											<div class="input-group-prepend">
	    											<span class="input-group-text" id="basic-addon1"><i class="fas fa-user"></i></span>
	  											</div>
	  											<input type="text" class="form-control" placeholder="First Name" id="firstName" name="firstName" value="${user.firstName}"
	  												data-error="First or/and last name is missing!" required>
											</div>
								 		</div>
								 		<div class="col-xs-6 col-sm-6 col-md-6">
								 			<div class="input-group mb-3">
	  											<div class="input-group-prepend">
	    											<span class="input-group-text" id="basic-addon1"><i class="fas fa-user"></i></span>
	  											</div>
	  											<input type="text" class="form-control" placeholder="Last Name" id="lastName" name="lastName" value="${user.lastName}"
	  												data-error="First or/and last name is missing!" required>
											</div>
								 		</div>
								 		
								 		<div class="col-xs-6 col-sm-6 col-md-6">
									 		<div>
												<form:errors path="firstName" cssClass="error" element="div"/>
												<div class="help-block with-errors"></div>
											</div>
										</div>
										
										<div class="col-xs-6 col-sm-6 col-md-6">
									 		<div>
												<form:errors path="lastName" cssClass="error"/>
												<div class="help-block with-errors"></div>
											</div>
										</div>
								 	</div>
							 	</div>
							 	
							 	<div class="form-group">
								 	<div class="row">
								 		<div class="col-xs-6 col-sm-6 col-md-6">
								 			<label for="username">Username</label>
								 		</div>
								 		
								 		<div class="col-xs-6 col-sm-6 col-md-6">
								 			<label for="email">Email address</label>
								 		</div>
								 	
								 		<div class="col-xs-6 col-sm-6 col-md-6">
								 			<div class="input-group mb-3">
	  											<div class="input-group-prepend">
	    											<span class="input-group-text" id="basic-addon1"><i class="fas fa-user"></i></span>
	  											</div>
	  											<input type="text" class="form-control" placeholder="Username" id="username" name="username" value="${user.username}"
	  												data-error="Username is missing!" required>
											</div>
								 		</div>
								 		<div class="col-xs-6 col-sm-6 col-md-6">
								 			<div class="input-group mb-3">
	  											<div class="input-group-prepend">
	    											<span class="input-group-text" id="basic-addon1"><i class="fas fa-envelope"></i></span>
	  											</div>
	  											<input id="email" type="text" class="form-control" name="email"
										 placeholder="Email" value="${user.email}" pattern="^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$"
										 data-error="This email is invalid!" required>
											</div>
								 		</div>
								 		
								 		<div class="col-xs-6 col-sm-6 col-md-6">
									 		<div>
												<form:errors path="username" cssClass="error" />
												<div class="help-block with-errors"></div>
											</div>
										</div>
										<div class="col-xs-6 col-sm-6 col-md-6">
									 		<div>
												<form:errors path="email" cssClass="error" element="div"/>
												<div class="help-block with-errors"></div>
											</div>
										</div>
								 	</div>
							 	</div>
							 	
							 	<div class="row">
							 		<div class="col-xs-6 col-sm-6 col-md-6">
							 			<label for="password">Password</label>
							 		</div>
							 		
							 		<div class="col-xs-6 col-sm-6 col-md-6">
							 			<label for="confirmPassword">Confirm Password</label>
							 		</div>
							 	
							 		<div class="col-xs-6 col-sm-6 col-md-6">
							 			<div class="input-group mb-3">
  											<div class="input-group-prepend">
    											<span class="input-group-text" id="basic-addon1"><i class="fas fa-unlock"></i></span>
  											</div>
  											<input type="password" class="form-control" placeholder="Password" id="password" name="password"
  												pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$" 
  												data-error="This password is invalid!" required>
										</div>
							 		</div>
							 		<div class="col-xs-6 col-sm-6 col-md-6">
							 			<div class="input-group mb-3">
  											<div class="input-group-prepend">
    											<span class="input-group-text" id="basic-addon1"><i class="fas fa-unlock"></i></span>
  											</div>
  											<input type="password" class="form-control" placeholder="Confirm Password" id="confirmPassword" name="confirmPassword"
  												pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{5,}$" data-error="This password is invalid!"
										 		data-match="#password" data-match-error="Password and confirm password are not matching!" required>
										</div>	
							 		</div>
							 		
							 		<div class="col-xs-6 col-sm-6 col-md-6">
								 		<div>
								 			<div class="help-block">Password should has more than 6 signs and contains at least one digit, one upper and one lower case.</div>
											<form:errors path="password" cssClass="error"/>
											<div class="help-block with-errors"></div>
										</div>
									</div>
							 		
							 		<div class="col-xs-6 col-sm-6 col-md-6">
								 		<div>
											<form:errors path="confirmPassword" cssClass="error"/>
											<div class="help-block with-errors"></div>
										</div>
									</div>
							 		
							 		<div style="margin-top:10px" class="col-xs-6 col-sm-6 col-md-6">
								 		<div class="form-check">
	  										<input class="form-check-input" type="radio" name="role" id="librarian" value="LIBRARIAN" checked>
	  										<label class="form-check-label" for="librarian">
	    										Librarian user
	  										</label>
										</div>
										<div class="form-check">
	  										<input class="form-check-input" type="radio" name="role" id="admin" value="ADMIN">
	  										<label class="form-check-label" for="admin">
	    										Admin user
	  										</label>
										</div>
									</div>
									<div class="col-xs-6 col-sm-6 col-md-6">
										<button type="submit" class="btn btn-success bottom">Create new user</button>
									</div>
							 	</div>
							 	
							 	<input type="hidden" name="birthday" value="01/01/1990">
							 	<input type="hidden" name="phone" value="111111111">
							 	<input type="hidden" name="address" value="No address">
							 	<input type="hidden" name="postalCode" value="00-000">
							 	<input type="hidden" name="city" value="No City">
							 </form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
</html>