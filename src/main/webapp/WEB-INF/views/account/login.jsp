
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<link rel="stylesheet" href="/static/css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="/static/css/signin.css" type="text/css" />

<!--[if Lt IE9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif] -->
</head>
<body>
	<div class="container">
		<spring:url value="/j_spring_security_check" var="login" />
		<form class="form-signin" action="${login}" method="post">
			<h2 class="form-signin-heading">Login</h2>
			<input type="text" name="j_username" class="form-control" autofocus placeholder=<spring:message code="label.login.username" /> required/>
			<input type="password" name="j_password" class="form-control" placeholder=<spring:message code="label.login.password" /> required/>
			<c:if test="${not empty param.authenticationNok}">
				<p>
					<font color="red"> <spring:message code="label.login.failed"
							arguments="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					</font>
				</p>
			</c:if>
			<button class="btn btn-lg btn-primary btn-block" type="submit"
				id="login">Login</button>
		</form>
	</div>
</body>
</html>







