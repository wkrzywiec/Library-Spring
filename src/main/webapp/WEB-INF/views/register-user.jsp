<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>	
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.5/validator.min.js"></script>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
	
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
	<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
	
	<style>
	body {
		background: #556270;  /* fallback for old browsers */
		background: -webkit-linear-gradient(to left, #FF6B6B, #556270);  /* Chrome 10-25, Safari 5.1-6 */
		background: linear-gradient(to left, #FF6B6B, #556270); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */	
			}
			
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
	
	
	<title>Library Portal - Reader registration</title>
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
				<div id="register-box" style="margin-top: 20px;">
					<div class="panel panel-info">
					
						<div class="panel-heading">
							<div class="panel-title">Become a new reader</div>
						</div>
						
						<div class="panel-body" style="margin: 25px 50px 25px">
						
							<form:form action="${pageContext.request.contextPath}/register-user"
							 	method="POST" modelAttribute="book" class="form-horizontal"
							 	data-toggle="validator" role="form">
													
								<div class="form-group">
									<div class="col-xs-6 col-sm-6 col-md-6">
										<label for="firstName" class="control-label">First name:</label>
									</div>
									<div class="col-xs-6 col-sm-6 col-md-6">
										<label for="lastName" class="control-label">Last name:</label>
									</div>
									
									<div class="col-xs-6 col-sm-6 col-md-6" style="padding-bottom: 5px">
										<div class="input-group">
											<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
											<input id="firstName" type="text" class="form-control" name="firstName"
											 placeholder="First Name" value="${user.firstName}"
											 data-error="First or/and last name is missing!" required>
										</div> 
									</div>

									<div class="col-xs-6 col-sm-6 col-md-6" style="padding-bottom: 5px">
										<div class="input-group">
										<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
										<input id="lastname" type="text" class="form-control" name="lastName"
										 placeholder="Last Name" value="${user.lastName}"
										 data-error="First or/and last name is missing!" required>
										</div>
									</div>
									
									<div>
										<div class="col-xs-6 col-sm-6 col-md-6">
											<form:errors path="firstName" cssClass="error"/>
										</div>
										<div class="col-xs-6 col-sm-6 col-md-6">
											<form:errors path="lastName" cssClass="error"/>
										</div>
									</div>
									<p>
									<div class="help-block with-errors"></div>
								</div>
								
								<div class="form-group">
									<div>
										<label for="username" class="control-label">Username:</label>
									</div>
								
									<div class="input-group" style="padding-bottom: 5px">
										<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
										<input id="username" type="text" class="form-control" name="username"
										 placeholder="Username" value="${user.username}"
										 data-error="Username is missing!" required>
									</div>
									
									<div>
										<form:errors path="username" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label for="email" class="control-label">Email address:</label>
									</div>
									
									<div class="input-group" style="padding-bottom: 5px">
										<span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
										<input id="email" type="text" class="form-control" name="email"
										 placeholder="Email" value="${user.email}" pattern="^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$"
										 data-error="This email is invalid!" required>
									</div>
									
									<div>
										<form:errors path="email" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label for="password" class="control-label">Password:</label>
									</div>
									
									<div class="input-group" style="padding-bottom: 5px">
										<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
										<input id="password" type="password" class="form-control" name="password"
										 placeholder="Password" 
										 pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$" data-error="This password is invalid!" required>
									</div>
								
									<div>
										<div class="help-block">Password should has more than 6 signs and contains at least one digit, one upper and one lower case.</div>
										<form:errors path="password" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label for="confirmPassword" class="control-label">Confirm Password:</label>
									</div>
									
									<div class="input-group" style="padding-bottom: 5px">
										<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
										<input id="confirmpassword" type="password" class="form-control"
										 name="confirmPassword" placeholder="Confirm Password" 
										 pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{5,}$" data-error="This password is invalid!"
										 data-match="#password" data-match-error="Password and confirm password are not matching!" required> 
									</div>
										
									<div>
										<form:errors path="confirmPassword" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
									
								</div>
								
								<div class="form-group">
									<div>
										<label for="phone" class="control-label">Phone:</label>
									</div>
									
									<div class="input-group" style="padding-bottom: 5px">
										<span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
										<input id="phone" type="number" class="form-control" name="phone" 
										placeholder="XXXXXXXXX" value="${user.phone}"
										data-error="This phone number is invalid!" required>
									</div>
									
									<div>
										<form:errors path="phone" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label for="birthday" class="control-label">Birthday:</label>
									</div>
									
	 								<div class="input-group input-append date" id="datePicker" style="padding-bottom: 5px">
										<span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
										<input id="birthday" type="text" class="form-control" name="birthday" 
										placeholder="DD/MM/YYYY" data-error="This date format is invalid!" required>
									</div>
									
									<div>
										<form:errors path="birthday" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label for="address" class="control-label">Adress:</label>
									</div>
									
									<div class="input-group" style="padding-bottom: 5px">
										<span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span>
										<input id="address" type="text" class="form-control" name="address"
										 placeholder="Address" value="${user.address}"
										 data-error="Physical address is missing!" required>
									</div>
									
									<div>
										<form:errors path="address" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
									
								</div>
								
								<div class="col-xs-5 col-sm-5 col-md-5">
									<label for="postalCode" class="control-label">Postal code:</label>
								</div>
								<div class="col-xs-7 col-sm-7 col-md-7">
									<label for="city" class="control-label">City:</label>
								</div>
									
								<div class="form-group">
									<div class="col-xs-5 col-sm-5 col-md-5" style="padding-bottom: 5px">
										<div class="input-group">
										<span class="input-group-addon"><i class="glyphicon glyphicon-th-list"></i></span>
										<input id="postalCode" type="text" class="form-control" name="postalCode"
											placeholder="XX-XXX" value="${user.postalCode}" pattern="\d{2}-\d{3}"
											data-error="The postal code is invalid!" required>
										</div>
									</div>
										
									<div class="col-xs-7 col-sm-7 col-md-7">
										<div class="input-group">
										<span class="input-group-addon"><i class="glyphicon glyphicon-th-list"></i></span>
										<input id="city" type="text" class="form-control" name="city" 
										placeholder="City" value="${user.city}"
										data-error="The city is missing!" required>
										</div>
									</div>
										
									<div>
										<form:errors path="postalCode" cssClass="error"/>
										<br>
										<form:errors path="city" cssClass="error"/>
									</div>
									<div class="help-block with-errors"></div>
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
	
	<script type="text/javascript">
		$(document).ready(function() {
	    	$('#datePicker')
	        	.datepicker({
	            	format: 'dd/mm/yyyy'
	        	}),
	        $('form').validator()
		});
    </script>
    


</body>
</html>