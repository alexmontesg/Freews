<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:set name="theme" value="'simple'" scope="page" />
<script src="../bootstrap/js/bootstrap.min.js"></script>
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

#mapCanvas {
	min-height: 250px;
	margin-bottom: 2em;
}

#mapCanvas img {
	max-width: none;
}
</style>
<link href="../bootsrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<title>Freews</title>
<s:head />
</head>
<body>

	<div class="container">
		<header class="row-fluid">
			<div class="span1">
				<a href="<s:url action="../SeekVideos.action"/>"><img
					src="../logo_freews.png" /></a>
			</div>
			<div class="span6">
				<h1>Freews</h1>
			</div>
		</header>
		<s:form action="UploadInfographic" method="POST"
			enctype="multipart/form-data" cssClass="span5">
			<h2>
				<s:text name="upload.infographic" />
			</h2>
			<label class="control-label" for="infographic"><s:text
					name="infographic" /></label>
			<s:file key="infographic" />
			<label class="control-label" for="category"><s:text
					name="category" /></label>
			<s:select name="category" list="%{#session.categories}"
				listKey="value" listValue="value" />
			<label class="control-label" for="type"><s:text name="type" /></label>
			<s:select name="type" list="types" />
			<s:submit key="submit" cssClass="btn btn-large btn-primary" />
		</s:form>

		<s:form action="AddCategory" cssClass="span4 pull-right">
			<h2>
				<s:text name="add.category" />
			</h2>
			<label class="control-label" for="category"><s:text
					name="category" /></label>
			<s:textfield key="category" cssClass="input-block-level" />
			<s:submit key="add" cssClass="btn btn-large btn-primary" />
		</s:form>
	</div>
</body>
</html>