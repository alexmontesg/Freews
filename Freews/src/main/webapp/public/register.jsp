<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Freews</title>
<s:head />
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
		<s:form action="Register" cssClass="form-signin">
			<h2 class="form-signin-heading">
				<s:text name="register" />
			</h2>
			<label class="control-label" for="firstName"><s:text
					name="firstName" /></label>
			<s:textfield key="firstName" value="%{#session.preUser.firstName}"
				cssClass="input-block-level" />
			<label class="control-label" for="lastName"><s:text
					name="lastName" /></label>
			<s:textfield key="lastName" value="%{#session.preUser.lastName}"
				cssClass="input-block-level" />
			<label class="control-label" for="mail"><s:text name="mail" /></label>
			<s:textfield key="mail" value="%{#session.preUser.mail}"
				cssClass="input-block-level" />
			<label class="control-label" for="username"><s:text
					name="username" /></label>
			<s:textfield key="username" cssClass="input-block-level" />
			<label class="control-label" for="password"><s:text
					name="password" /></label>
			<s:password key="password" cssClass="input-block-level" />
			<label class="control-label" for="country"><s:text
					name="country" /></label>
			<s:textfield key="country" cssClass="input-block-level" />
			<s:submit cssClass="btn btn-large btn-primary pull-right"
				type="submit" key="submit" />
		</s:form>
	</div>
</body>
</html>