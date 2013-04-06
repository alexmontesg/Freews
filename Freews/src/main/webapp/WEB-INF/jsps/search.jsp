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
<link rel="stylesheet" href="./player/mediaelementplayer.min.css" />
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.span4 {
	margin: 0 auto 20px;
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
					<s:text name="results" />
					<s:property value="%{#request.query}" escapeHtml="false" />
				</h2>
			</div>
		</header>
		<section class="searchForm span5 pull-right">
			<s:form action="Search" cssClass="navbar-search pull-right">
				<s:textfield key="query" cssClass="search-query"
					placeholder="Search" />
				<s:submit key="search" cssClass="btn .btn-mini btn-primary" />
			</s:form>
		</section>

		<aside class="subnav">
			<ul class="nav nav-pills">
				<s:if test="%{#session.user.admin}">
					<li><a href="<s:url action="admin/AdminPanel.action"/>"> <s:text
								name="admin.panel" />
					</a></li>
				</s:if>
				<li><a href="<s:url action="UploadRawForm.action"/>"><s:text
							name="upload.raw" /></a></li>
				<li><s:url action="MyVideos.action" var="myVideos">
						<s:param name="username">
							<s:property value="%{#session.user.username}" escapeHtml="false" />
						</s:param>
					</s:url> <a href="<s:property value="%{#myVideos}" escapeHtml="false"/>">
						<s:text name="my.videos" />
				</a></li>
				<li><a href="<s:url action="Logout.action"/>"><s:text
							name="logout" /></a></li>

			</ul>
		</aside>

		<section id="videos">
			<s:form action="ShowTimeline">
				<s:iterator id="video" value="%{#request.searchVideos}">
					<article class="span4">
						<header id="headline<s:property value="id" escapeHtml="false"/>"
							class="headline">
							<h3>
								<s:property value="headline[#request.lang]" escapeHtml="false" />
							</h3>
						</header>
						<video controls="controls" width="250" height="170" preload="none"
							src="media/<s:property value="id" escapeHtml="false"/>.mp4"
							type="video/mp4"
							poster="media/posters/<s:property value="id" escapeHtml="false"/>.jpeg">
							<track kind="subtitles"
								src="media/subtitles/<s:property value="id" escapeHtml="false"/>_en.srt"
								srclang="en" />
							<track kind="subtitles"
								src="media/subtitles/<s:property value="id" escapeHtml="false"/>_es.srt"
								srclang="es" />
							<track kind="subtitles"
								src="media/subtitles/<s:property value="id" escapeHtml="false"/>_pt.srt"
								srclang="pt" />
						</video>
						<div class="controllers row-fluid">
							<label for="check<s:property value="id" escapeHtml="false"/>"
								class="checkbox span5"> <input type="checkbox"
								name="selectedVideos"
								value="<s:property value="id" escapeHtml="false"/>"
								id="check<s:property value="id" escapeHtml="false"/>" /> <s:text
									name="select" />
							</label>
							<s:url action="SeeMoreInfo.action" var="info">
								<s:param name="id">
									<s:property value="id" escapeHtml="false" />
								</s:param>
							</s:url>
							<a href="<s:property value="%{#info}" escapeHtml="false"/>">
								<s:text name="see.more.info" />
							</a>
						</div>
					</article>
				</s:iterator>
				<div class="row-fluid span12">
					<s:submit cssClass="btn btn-large btn-primary pull-right"
						type="submit" key="next" />
				</div>
			</s:form>
		</section>
	</div>
	<script src="./player/jquery.js"></script>
	<script src="./player/mediaelement-and-player.min.js"></script>
	<script src="./bootstrap/js/bootstrap.min.js"></script>
	<script>
		$('audio,video').mediaelementplayer({
			success : function(player, node) {
				$('#' + node.id + '-mode').html('mode: ' + player.pluginType);
			}
		});
	</script>
</body>
</html>