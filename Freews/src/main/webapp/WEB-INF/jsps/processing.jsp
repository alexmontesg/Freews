<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:set name="theme" value="'simple'" scope="page" />
<script src="./bootstrap/js/bootstrap.min.js"></script>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
}
</style>
<link href="./bootsrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<title>Freews</title>
<s:head />
</head>
<body>
	<div class="container">
		<header class="row-fluid">
			<div class="span1">
				<a href="<s:url action="SeekVideos.action"/>"><img
					src="./logo_freews.png" /></a>
			</div>
			<div class="span6">
				<h1>Freews</h1>
			</div>
		</header>
		<p>
			<s:text name="processing" />
			. <a href="<s:url action="SeekVideos.action"/>"> <s:text
					name="return" />
			</a>
		</p>
	</div>
</body>
</html>