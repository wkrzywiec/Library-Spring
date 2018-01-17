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
<title>Library login page</title>
</head>
<body>

    <div>
			
		<div id="loginbox" style="margin-top: 150px;"
			class="mainbox col-md-3 col-md-offset-5 col-sm-8 col-sm-offset-1">
					
			<div class="panel panel-info">

				<div class="panel-heading">
					<div class="panel-title">Sign In</div>
					<div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="www.google.com">Forgot password?</a></div>
				</div>

				<div style="padding-top: 30px" class="panel-body">
				
				
					<form:form action="${pageContext.request.contextPath}/login" 
							   method="POST" class="form-horizontal">

					
					    <div class="form-group">
					        <div class="col-xs-15">
					            <div>
	
								<c:if test="${param.error != null}">

									<div class="alert alert-danger col-xs-offset-1 col-xs-10">
										Invalid username and password.
									</div>
								
								</c:if>
								
								<c:if test="${param.logout!= null }">
								
									<div class="alert alert-success col-xs-offset-1 col-xs-10">
										You have successfully logout.
									</div>
								</c:if>

					            </div>
					        </div>
					    </div>

						
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
							<div class="col-sm-6 controls">
								<button type="submit" class="btn btn-success btn-block">Login</button>
							</div>
						</div>
						
					</form:form>
	
					<div class="form-group">
                    	<div class="col-md-12 control">
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

</body>
</html>