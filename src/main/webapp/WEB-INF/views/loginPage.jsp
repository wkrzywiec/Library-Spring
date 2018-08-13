<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>	
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<title>Library login page</title>
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

    <div class="container" style="margin-top: 50px;">
		<div class="col-xs-3 col-sm-3 col-md-3">
		</div>
		
		<div class="col-xs-6 col-sm-6 col-md-6">
			<h1>Library Portal</h1>
			<div class="description">
				<p>
	            	This is a place where you find and lend books that you will love!
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
			
			<div class="col-xs-5 col-sm-5 col-md-5">
				<div id="login-box" style="margin-top: 20px;">
					<div class="panel panel-info">
					
						<div class="panel-heading">
							<div class="panel-title">Sign In</div>
							<div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="${pageContext.request.contextPath}/forgot-password">Forgot password?</a></div>
						</div>
						
						<div class="panel-body" style="margin: 25px 50px 25px">
						
							<form:form action="${pageContext.request.contextPath}/login" 
							   method="POST" class="form-horizontal">
							   
							   <div class="form-group">
							   		<c:if test="${param.error != null}">

										<div class="alert alert-danger col-xs-offset-1 col-xs-10">
										Invalid username and password.
										</div>
								
									</c:if>
								
									<c:if test="${param.logout!= null}">
								
										<div class="alert alert-success col-xs-offset-1 col-xs-10">
										You have successfully logout.
										</div>
									</c:if>
									
									<c:if test="${message != null}">
										<div class="alert alert-info col-xs-offset-1 col-xs-10">
											${message}
										</div>			
									</c:if>
									
							   </div>
							   Please provide your credentials below:
							   	<div style="margin-bottom: 25px" class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
							
									<input type="text" name="username" placeholder="username" class="form-control">
								</div>

						
								<div style="margin-bottom: 15px" class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
							
									<input type="password" name="password" placeholder="password" class="form-control" >
								</div>
						
								<div class="input-group">
                        			<div class="checkbox">
                            			<label>
                                			<input id="remember-me" type="checkbox" name="remember-me" value="1"> Remember me
                               			</label>
                        			</div>
                        		</div>

								<div style="margin-top: 10px" class="form-group">						
									<div class="col-xs-6 col-sm-6 col-md-6 controls">
										<button type="submit" class="btn btn-success btn-block">Login</button>
									</div>
								</div>
							   
							   	
							   
							</form:form>
						
							<div class="form-group">
                    			<div class="col-xs-12 col-sm-12 col-md-12 control">
                        			<div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                            				Don't have an account?
	                            			<a href="${pageContext.request.contextPath}/register-user">
	                            			Sign Up Here
                             				</a>
                             		</div>
                    			</div>
                			</div>   
						
						</div>
						
					</div>
				</div>
			</div>
			
			<div class="col-xs-4 col-sm-4 col-md-4">
			</div>
		
		</div>
	</div>

</body>
</html>