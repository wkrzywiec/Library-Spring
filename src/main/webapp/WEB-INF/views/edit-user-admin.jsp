<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User [${user.username}] page - Admin panel</title>
</head>
<body>

	<div id="header">
    	<jsp:include page="shared/header.jsp"/>
	</div>
	
	
	
	<div class="container" style="margin-top: 30px;">
		<a href="javascript:history.back()">&#8592; Go back to Admin Panel</a>
		<h2>${user.firstName} ${user.lastName}</h2>
	</div>
	<div class="container" style="margin-top: 30px;">
		<div class="row">
			<div class="col-xs-6 col-sm-6 col-md-6">
				<div class="card">
					 <h4 class="card-header">User info</h4>
					 <div class="card-body">
					 	<div class="row" style="margin-top:20px">
						 	<div class="col">
						 		<h5>Id:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.id}" readonly/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Username:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.username}"/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
						 	<div class="col">
						 		<h5>Email address:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.email}"/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Password:</h5>
						 	</div>
						 	<div class="col">
						 		<a href="#link" class="btn btn-warning" role="button">Reset Password</a>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Active: </h5>
						 	</div>
						 	<div class="col">
						 		<div class="btn-group btn-group-toggle" data-toggle="buttons">
								  <label class="btn btn-success">
								    <input type="radio" name="options" id="option2" autocomplete="off" > YES
								  </label>
								  <label class="btn btn-danger">
								    <input type="radio" name="options" id="option3" autocomplete="off"> NO
								  </label>
								</div>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Record created: </h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.id}" readonly/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Last sign in: </h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.id}" readonly/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>User Logs: </h5>
						 	</div>
						 	<div class="col">
						 		<a href="#">Show user logs</a>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>User book history: </h5>
						 	</div>
						 	<div class="col">
						 		<a href="#">Show user book history</a>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Penalties: </h5>
						 	</div>
						 	<div class="col">
						 		<p>PENALTIES</p>
						 	</div>
					 	</div>
					 </div>
				</div>
			</div>
			<div class="col-xs-6 col-sm-6 col-md-6">
				<div class="card">
					 <h5 class="card-header">Private info</h5>
					 <div class="card-body">
					 	<div class="row" style="margin-top:20px">
						 	<div class="col">
						 		<h5>First Name:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.firstName}"/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Last Name:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.lastName}"/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Phone:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.phone}"/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Address:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.phone}"/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>Postal code:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.phone}"/>
						 	</div>
					 	</div>
					 	<div class="row" style="margin-top:20px">
					 		<div class="col">
						 		<h5>City:</h5>
						 	</div>
						 	<div class="col">
						 		<input class="form-control" value="${user.phone}"/>
						 	</div>
					 	</div>
					 </div>
				</div>
			</div>
		</div>
		<div class="container" style="margin-top: 15px; margin-bottom: 15px">
			<button class="btn btn-success" type="submit">Save changes</button>
		</div>
	</div>
	
	<script>
		if (${user.enable} == 1){
			$(document).ready(function() {
			    $(".btn").slice(1,2).button("toggle");
			});
		} else {
			$(document).ready(function() {
			    $(".btn").slice(2,3).button("toggle");
			});
		}
		
	</script>
</body>
</html>