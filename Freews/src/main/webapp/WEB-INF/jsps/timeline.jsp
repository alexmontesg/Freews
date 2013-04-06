<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:set name="theme" value="'simple'" scope="page" />
<title>Freews</title>
<script src="./jquery-1.8.0.js"></script>
<script src="./ui/jquery.ui.core.js"></script>
<script src="./ui/jquery.ui.widget.js"></script>
<script src="./ui/jquery.ui.mouse.js"></script>
<script src="./ui/jquery.ui.sortable.js"></script>
<script src="./bootstrap/js/bootstrap.min.js"></script>
<script>
	$(function() {
		$("#sortable").sortable();
		$("#sortable").disableSelection();
	});

	$(document)
			.ready(
					function() {
						$("#submit")
								.submit(
										function() {
											var result = $('#sortable')
													.sortable('toArray');
											for ( var i = 0; i < result.length; i++) {
												$("#submit")
														.append(
																"<input name=selectedVideos value=" + result[i] + 
						" style=visibility:hidden />");
											}
										});
					});
</script>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

#sortable li {
	float: left;
}

ul {
	list-style: none;
}
</style>
<link href="./bootsrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
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
				<h2>
					<s:text name="choose.order" />
				</h2>
			</div>
		</header>
		<section id="videos" class="span12">
			<ul id="sortable">
				<s:iterator id="video" value="%{#request.videostoappend}"
					status="rowStatus">
					<li id="<s:property escapeHtml="false"/>"><img width="250"
						height="170"
						src="media/posters/<s:property escapeHtml="false"/>.jpeg" /></li>
				</s:iterator>
			</ul>
		</section>
		<section id="form" class="span4">
			<header>
				<h2>
					<s:text name="choose.style" />
				</h2>
			</header>
			<s:form id="submit" action="AppendVideos">
				<s:select name="category" list="%{#session.categories}"
					listKey="value" listValue="value" />
				<s:textfield key="title" cssClass="input-block-level" />
				<s:submit cssClass="btn .btn-mini btn-primary" />
			</s:form>
		</section>
	</div>
</body>
</html>