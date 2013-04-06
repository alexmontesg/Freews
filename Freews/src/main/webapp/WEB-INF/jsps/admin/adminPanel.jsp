<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Freews</title>
<s:head />
<script src="../player/jquery.js"></script>
<script src="../player/mediaelement-and-player.min.js"></script>
<link rel="stylesheet" href="../player/mediaelementplayer.min.css" />
<script src="../bootstrap/js/bootstrap.min.js"></script>
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
}
</style>
<link href="../bootsrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">
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
		<h2>
			<s:text name="admin.panel" />
		</h2>
		<aside class="subnav">
			<ul class="nav nav-pills">
				<li><a href="<s:url action="UploadInfographicForm.action"/>">
						<s:text name="admin.infographic" />
				</a></li>
			</ul>
		</aside>
		<section id="videos">
			<header>
				<h2>
					<s:text name="pending.videos" />
				</h2>
			</header>
			<s:actionerror />
			<s:iterator id="rawVideos" value="%{#request.rawVideos}">
				<article class="row-fluid">
					<aside class="pull-right span6">
						<header>
							<h3>
								<s:property value="headline" escapeHtml="false" />
							</h3>
						</header>
						<p>
							<s:property value="description" escapeHtml="false" />
						</p>
						<aside class="subnav">
							<ul class="nav nav-pills">
								<s:url action="MakeClip.action" var="clip">
									<s:param name="id">
										<s:property value="id" escapeHtml="false" />
									</s:param>
								</s:url>
								<li><a
									href="<s:property value="%{#clip}" escapeHtml="false"/>"> <s:text
											name="make.clip" />
								</a></li>
								<li><s:url action="DownloadRaw.action" var="download">
										<s:param name="id">
											<s:property value="id" escapeHtml="false" />
										</s:param>
									</s:url> <a
									href="<s:property value="%{#download}" escapeHtml="false"/>">
										<s:text name="download.video" />
								</a></li>
								<li><s:url action="DeleteRawVideo.action" var="delete">
										<s:param name="id">
											<s:property value="id" escapeHtml="false" />
										</s:param>
									</s:url> <a href="<s:property value="%{#delete}" escapeHtml="false"/>">
										<s:text name="delete.raw.video" />
								</a></li>
							</ul>
						</aside>
					</aside>
					<video controls="controls" width="350" height="230" preload="none"
						src="../media/<s:property value="id" escapeHtml="false"/>.mp4"
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