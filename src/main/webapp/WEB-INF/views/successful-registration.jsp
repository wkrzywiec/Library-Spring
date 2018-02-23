<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
</style>


<title>Library portal - successful registration of new user</title>
</head>
<body>

	<div class="container" style="margin-top: 50px;">
		<div class="col-xs-3 col-sm-3 col-md-3">
		</div>
	
		<div class="col-xs-6 col-sm-6 col-md-6">
			<h1>Congratulations!</h1>
			<div class="description" style="margin-bottom: 50px;">
				<p>
	            	You have been successfully registered as a new user. In a couple of minutes you will receive confirmation email.
	        	</p>
			</div>
			
			<div class="description">
				<p>
					Go back to login <a href="${pageContext.request.contextPath}">page</a>
				</p>
			</div>
			
		</div>
		
		<div class="col-xs-3 col-sm-3 col-md-3">
		</div>
	
	
	</div>
	
</body>
</html>