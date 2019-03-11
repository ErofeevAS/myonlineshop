
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/login.css" />" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />" />
	<title>Registration</title>
</head>



<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
				<div class="card card-signin my-5">
					<div class="card-body">
						<h5 class="card-title text-center">Registration</h5>
						<c:if test="${not empty error}">
							<div class="alert alert-warning" role="alert">
								<c:out value="${error}"></c:out>
							</div>
						</c:if>
						<c:if test="${not empty info}">
							<div class="alert alert-warning" role="alert">
								<c:out value="${info}"></c:out>
							</div>
						</c:if>
						<form action= ${pageContext.request.contextPath}/shop?command=registration method="post" class="form-signin" >
							<input type="hidden" name="command" value="registration"/>
							<c:if test="${not empty messages.email}">
								<div class="alert alert-warning" role="alert">
									<c:out value="${messages.email}"></c:out>
								</div>
							</c:if>
							<div class="form-label-group">
								<input type="email" id="inputEmail" class="form-control" name="email" placeholder="Email address" required autofocus>
								<label for="inputEmail">Email address</label>
							</div>
							<c:if test="${not empty messages.password}">
								<div class="alert alert-warning" role="alert">
									<c:out value="${messages.password}"></c:out>
								</div>
							</c:if>
							<div class="form-label-group">
								<input type="password" id="inputPassword" class="form-control" name="password" placeholder="Password" required>
								<label for="inputPassword">Password</label>
							</div>
							<c:if test="${not empty messages.repassword}">
								<div class="alert alert-warning" role="alert">
									<c:out value="${messages.repassword}"></c:out>
								</div>
							</c:if>
							<div class="form-label-group">
								<input type="password" id="inputRePassword" class="form-control" name="repassword" placeholder="Repeat password" required>
								<label for="inputRePassword">Repaet password</label>
							</div>
							<c:if test="${not empty messages.firstname}">
								<div class="alert alert-warning" role="alert">
									<c:out value="${messages.firstname}"></c:out>
								</div>
							</c:if>
							<div class="form-label-group">
								<input type="text" id="inputFirstName" class="form-control" name="firstname" placeholder="Frist Name" required autofocus>
								<label for="inputFirstName">First Name</label>
							</div>
							<c:if test="${not empty messages.lastname}">
								<div class="alert alert-warning" role="alert">
									<c:out value="${messages.lastname}"></c:out>
								</div>
							</c:if>
							<div class="form-label-group">
								<input type="text" id="inputLastName" class="form-control" name="lastname"placeholder="Last Name" required autofocus>
								<label for="inputLastName">Last Name</label>
							</div>
							<c:if test="${not empty messages.address}">
								<div class="alert alert-warning" role="alert">
									<c:out value="${messages.address}"></c:out>
								</div>
							</c:if>
							<div class="form-label-group">
								<input type="text" id="inputAddress" class="form-control" name="address"placeholder="Address" required autofocus>
								<label for="inputAddress">Address</label>
							</div>
							<c:if test="${not empty messages.telephone}">
								<div class="alert alert-warning" role="alert">
									<c:out value="${messages.telephone}"></c:out>
								</div>
							</c:if>
							<div class="form-label-group">
								<input type="text" id="telephone" class="form-control" name="telephone"placeholder="Telephone" required autofocus>
								<label for="telephone">Telephone</label>
							</div>

							<button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">register</button>
							<hr class="my-4">

							<div id="register-link" class="text-right">
								<a href=" ${pageContext.request.contextPath}/shop?command=login"class="text-info">back</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src ="js/bootstrap.min.js"></script>
</body>


