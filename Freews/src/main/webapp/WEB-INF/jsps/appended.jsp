<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="./ui-darkness/jquery-ui-1.8.23.custom.css"
	type="text/css" />
<title>Freews</title>
<s:head />
<script src="./jquery-1.8.0.js"></script>
<script src="./ui/jquery.ui.core.js"></script>
<script src="./ui/jquery.ui.widget.js"></script>
<script src="./ui/jquery.ui.accordion.js"></script>
<script>
	$(function() {
		$("#accordion").accordion({
			active : false,
			collapsible : true
		});
	});
</script>
<script src="./player/mediaelement-and-player.min.js"></script>
<link rel="stylesheet" href="./player/mediaelementplayer.min.css" />
<s:set name="theme" value="'simple'" scope="page" />
<script src="./bootstrap/js/bootstrap.min.js"></script>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}
</style>
<link href="./bootsrap/css/bootstrap-responsive.min.css"
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
				<a href="<s:url action="SeekVideos.action"/>"><img
					src="./logo_freews.png" /></a>
			</div>
			<div class="span6">
				<h1>Freews</h1>
				<h2>
					<s:text name="result.appended" />
				</h2>
			</div>
		</header>
		<section class="singleVideo">
			<video controls="controls" width="575" height="500"
				src="media/bulletins/<s:property value="%{#request.appendedvideo}" escapeHtml="false"/>.mp4"
				type="video/mp4">
			</video>
		</section>
		<section class="span5">
			<div id="accordion" class="embed">
				<p>
					<a href="#"><s:text name="embed.it" /></a>
				</p>
				<div>
					<textarea rows="6" class="span4">
&lt;video controls&gt;
	&lt;source src="http://<s:property value="%{#request.mediaurl}"
							escapeHtml="false" />.mp4" type="video/mp4" /&gt;
&lt;/video&gt;
				</textarea>
				</div>
			</div>
		</section>
		<section class="span5">
				<s:url action="DownloadBulletin.action" var="download">
					<s:param name="id">
						<s:property value="%{#request.appendedvideo}" escapeHtml="false" />
					</s:param>
				</s:url>
				<p>
					<a href="<s:property escapeHtml="false" value="%{#download}"/>" class="btn btn-large btn-primary">
						<s:text name="download.it" />
					</a>
				</p>
			</section>
	</div>
	<script>
		$('audio,video').mediaelementplayer({
			success : function(player, node) {
				$('#' + node.id + '-mode').html('mode: ' + player.pluginType);
			}
		});
	</script>
</body>
</html>