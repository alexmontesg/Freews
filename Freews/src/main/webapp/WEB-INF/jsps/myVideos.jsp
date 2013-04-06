<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<s:set name="theme" value="'simple'" scope="page" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Freews</title>
<s:head />
<script src="./player/jquery.js"></script>
<script src="./player/mediaelement-and-player.min.js"></script>
<link rel="stylesheet" href="./player/mediaelementplayer.min.css" />
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
		<h2>
			<s:text name="my.videos" />
		</h2>
		<section id="videos">
			<s:iterator id="video" value="%{#request.videolist}">
				<article class="row-fluid">
					<header>
						<h3>
							<s:property value="headline[#request.lang]" escapeHtml="false" />
						</h3>
					</header>
					<aside class="pull-right span6">
						<p>
							<s:property value="description[#request.lang]" escapeHtml="false" />
						</p>
					</aside>
					<video controls="controls" width="350" height="230" preload="none"
						src="media/<s:property value="id" escapeHtml="false"/>.mp4"
						type="video/mp4">
					</video>
				</article>
				<hr />
			</s:iterator>
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