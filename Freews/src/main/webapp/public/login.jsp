<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Freews</title>
<s:head />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<s:set name="theme" value="'simple'" scope="page" />
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading,.form-signin .checkbox {
	margin-bottom: 10px;
	color: rgb(0, 0, 0);
}

}
.form-signin input[type="text"],.form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
<link href="../bootsrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>

<body>

	<div class="container">

		<header class="row-fluid">
			<div class="span1">
				<img src="../logo_freews.png" />
			</div>
			<div class="span6">
				<h1>Freews</h1>
			</div>
		</header>
		<s:form cssClass="form-signin" action="Login">
			<h2 class="form-signin-heading">
				<s:text name="login" />
			</h2>
			<label class="control-label" for="username"><s:text
					name="username" /></label>
			<s:textfield key="username" name="username"
				cssClass="input-block-level" />
			<label class="control-label" for="password"><s:text
					name="password" /></label>
			<s:password key="password" name="password"
				cssClass="input-block-level" />
			<s:submit cssClass="btn btn-large btn-primary pull-right"
				type="submit" key="submit" />
		</s:form>

		<!-- TODO Cambiar parámetros -->
		<div class="pull-right">
			<a
				href="https://www.facebook.com/dialog/oauth?client_id=255491544567442&
		redirect_uri=http://156.35.95.154/Freews/FacebookLogin.action&
		scope=email"><s:text
					name="login.facebook" /></a> | <a href="./register.jsp"><s:text
					name="register" /></a>
		</div>
	</div>
	<!-- /container -->

	<script src="../jquery-1.8.0.js"></script>
	<script src="../bootstrap/js/bootstrap.min.js"></script>
</body>
</html>