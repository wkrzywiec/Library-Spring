<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>	
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<title>Change your password</title>
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
					
		p {
				color: #F4A460;
		}
					
		.error {
				color: red;
		}
	
	</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div id="login-box" style="margin-top: 20px;">
				<div class="col-xs-4 col-sm-4 col-md-4">
				</div>
				<div class="col-xs-4 col-sm-4 col-md-4">
					<div class="panel panel-info" style="margin: 100px 0px 25px 0px">
					
						<div class="panel-heading">
							<div class="panel-title">Provide your new password</div>
						</div>
						
						<div class="panel-body" style="margin: 25px 50px 25px 50px">
							<form:form action="${pageContext.request.contextPath}/changePassword?userId=${param.userId}&token=${param.token}"
								 	method="POST" modelAttribute="password" class="form-horizontal"
								 	data-toggle="validator" role="form">
								<div class="form-group">
									<div class="row">
										<label for="password" class="control-label">New password:</label>
										<input id="password" type="password" class="form-control" name="password"
										placeholder="New password" value="${password.password}"
										pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$" data-error="This password is invalid!" required>
									</div>
									
									<div>
										<div class="help-block">Password should has more than 6 signs and contains at least one digit, one upper and one lower case.</div>
										<form:errors path="password" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
									
									<div class="row" style="margin-top: 15px;">
										<label for="confirmPassword" class="control-label">New password:</label>
										<input id="confirmPassword" type="password" class="form-control" name="confirmPassword"
										placeholder="Confirm Password" value="${password.confirmPassword}"
										 pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{5,}$" data-error="This password is invalid!"
										 data-match="#password" data-match-error="Password and confirm password are not matching!" required>
									</div>
									
									<div>
										<form:errors path="confirmPassword" cssClass="error"/>
										<div class="help-block with-errors"></div>
									</div>
									
									<div class="row" style="margin-top: 15px;">
										<div class="col-xs-8 col-sm-8 col-md-8">
											<button type="submit" class="btn btn-danger btn-block">Reset password</button>
										</div>
									</div>
								</div>
							</form:form>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>