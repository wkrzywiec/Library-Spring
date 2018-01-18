<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
		 href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>	
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
	body {
		background: #556270;  /* fallback for old browsers */
		background: -webkit-linear-gradient(to left, #FF6B6B, #556270);  /* Chrome 10-25, Safari 5.1-6 */
		background: linear-gradient(to left, #FF6B6B, #556270); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */	
	}

</style>

<title>Library Portal - Register new Reader</title>
</head>
<body>
	<div class="container" style="margin-top: 50px;">
		<div class="col-xs-3 col-sm-3 col-md-3">
		</div>
		
		<div class="col-xs-6 col-sm-6 col-md-6">
			<h1>Library Portal</h1>
			<div class="description">
				<p>
	            	Your adventure with books begins right here! Register as a new reader and borrow thousands of books!
	        	</p>
			</div>
		</div>
			
		<div class="col-xs-3 col-sm-3 col-md-3">
		</div>
	</div>


	<div class="container">
		<div class="row">
		
			<div class="col-xs-3 col-sm-3 col-md-3">
			</div>
			
			<div class="col-xs-6 col-sm-6 col-md-6">
				<div id="register-box" style="margin-top: 50px;">
					<div class="panel panel-info">
					
						<div class="panel-heading">
							<div class="panel-title">Register new Reader</div>
						</div>
						
						<div class="panel-body" style="margin: 25px 50px 25px">
						
							<form:form action="${pageContext.request.contextPath}/"
							 	method="POST" class="form-horizontal">
							
								<div class="col-xs-6 col-sm-6 col-md-6">
									<label>First name:</label>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6">
									<label>Last name:</label>
								</div>
								
								<div class="col-xs-6 col-sm-6 col-md-6" style="padding-bottom: 20px">
									<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
									<input id="firstname" type="text" class="form-control" name="firstname" placeholder="First Name">
									</div>
								</div>
								
								<div class="col-xs-6 col-sm-6 col-md-6" style="padding-bottom: 20px">
									<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
									<input id="lastname" type="text" class="form-control" name="lastname" placeholder="Last Name">
									</div>
								</div>
								
								<div>
									<label>Username:</label>
								</div>
							
								<div class="input-group" style="padding-bottom: 20px">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
									<input id="username" type="text" class="form-control" name="username" placeholder="Username">
								</div>
								
								<div>
									<label>Email address:</label>
								</div>
								
								<div class="input-group" style="padding-bottom: 20px">
									<span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
									<input id="email" type="text" class="form-control" name="email" placeholder="Email">
								</div>
								
								<div>
									<label>Password:</label>
								</div>
								
								<div class="input-group" style="padding-bottom: 20px">
									<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
									<input id="password" type="text" class="form-control" name="password" placeholder="Password">
								</div>
								
								<div>
									<label>Confirm Password:</label>
								</div>
								
								<div class="input-group" style="padding-bottom: 20px">
									<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
									<input id="confirmpassword" type="text" class="form-control" name="confirmpassword" placeholder="Confirm Password">
								</div>
								
								<div>
									<label>Phone:</label>
								</div>
								
								<div class="input-group" style="padding-bottom: 20px">
									<span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
									<input id="phone" type="text" class="form-control" name="phone" placeholder="XXXX-XXXXXXXXX">
								</div>
								
								<div>
									<label>Adress:</label>
								</div>
								
								<div class="input-group" style="padding-bottom: 20px">
									<span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span>
									<input id="adress" type="text" class="form-control" name="adress" placeholder="Adress">
								</div>
								
								<div class="col-xs-5 col-sm-5 col-md-5">
									<label>Postal code:</label>
								</div>
								<div class="col-xs-7 col-sm-7 col-md-7">
									<label>City:</label>
								</div>
								
								
								<div class="col-xs-5 col-sm-5 col-md-5" style="padding-bottom: 20px">
									<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-th-list"></i></span>
									<input id="postalcode" type="text" class="form-control" name="postalcode" placeholder="XX-XXX">
									</div>
								</div>
								
								
								<div class="col-xs-7 col-sm-7 col-md-7">
									<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-th-list"></i></span>
									<input id="city" type="text" class="form-control" name="city" placeholder="City">
									</div>
								</div>
								
								<button type="submit" class="btn btn-success btn-block">Signup</button>
							
							</form:form>
						
						
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-xs-3 col-sm-3 col-md-3">
			</div>
			
		</div>
	</div>
</body>
</html>