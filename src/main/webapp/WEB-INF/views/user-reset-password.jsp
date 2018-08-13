<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css">	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>	
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<title>Forgot Password? Provide your email address</title>
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
			<div class="form-group">
				<c:if test="${message != null}">
					<div class="alert alert-danger col-xs-offset-1 col-xs-10">
						${message}
					</div>			
				</c:if>
			</div>
		</div>
		<div class="row">
			<div id="login-box" style="margin-top: 20px;">
				<div class="col-xs-4 col-sm-4 col-md-4">
				</div>
				<div class="col-xs-4 col-sm-4 col-md-4">
					<div class="panel panel-info" style="margin: 100px 0px 25px 0px">
					
						<div class="panel-heading">
							<div class="panel-title">Reset password</div>
						</div>
						
						<div class="panel-body" style="margin: 25px 50px 25px 50px">
							<form:form action="${pageContext.request.contextPath}/submit-forgot-password"
								 	method="POST" modelAttribute="email" class="form-horizontal"
								 	role="form">
								<div class="form-group">
									<div class="row">
										<label for="email" class="control-label">Your email address:</label>
										<input id="email" type="text" class="form-control" name="email"
										placeholder="Email address" value="${user.lastName}">
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