<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:set name="theme" value="'simple'" scope="page" />
<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script src="./player/jquery.js"></script>
<script src="./bootstrap/js/bootstrap.min.js"></script>
<script src="./player/mediaelement-and-player.min.js"></script>
<link rel="stylesheet" href="./player/mediaelementplayer.min.css" />
<script>
	var map;

	function initialize(lat, lon, title) {
		var mapOptions = {
			zoom : 13,
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			center : new google.maps.LatLng(lat, lon),
			panControl : false,
			rotateControl : false,
			streetViewControl : false,
			zoomControl : false,
			overviewMapControl : false
		};
		map = new google.maps.Map(document.getElementById('mapCanvas'),
				mapOptions);
		var marker = new google.maps.Marker({
			map : map,
			draggable : true,
			animation : google.maps.Animation.DROP,
			position : new google.maps.LatLng(lat, lon)
		});
		marker.setTitle(title);
	}
</script>
<title>Freews</title>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
}

#mapCanvas {
	min-height: 250px;
	margin-bottom: 2em;
}

#mapCanvas img {
	max-width: none;
}

.tags .badge a {
	color: rgb(255, 255, 255);
}

.tags {
	margin-bottom: 2em;
}

h2 {
	margin-top: 1em;
	margin-bottom: 1em;
}

.mejs-video {
	margin-bottom: 1em;
}
</style>
<link href="./bootsrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<s:head />
</head>
<body
	onload="initialize('<s:property escapeHtml="false" value="%{#request.video.lat}" />', 
	'<s:property escapeHtml="false" value="%{#request.video.lon}" />', 
	'<s:property value="%{#request.video.headline[#request.lang]}" escapeHtml="false" />')">
	<div class="container" itemscope
		itemtype="http://schema.org/NewsArticle">
		<header class="row-fluid" itemprop="publisher" itemscope
			itemtype="http://schema.org/Organization">
			<div class="span1">
				<a href="<s:url action="SeekVideos.action"/>"><img
					src="./logo_freews.png" itemprop="logo" /></a>
			</div>
			<div class="span6">
				<h1 itemprop="name">Freews</h1>
			</div>
		</header>
		<h2 itemprop="headline">
			<s:property value="%{#request.video.headline[#request.lang]}"
				escapeHtml="false" />
		</h2>
		<aside class="pull-right span4">
			<div class="row-fluid tags" itemprop="keywords">
				<s:iterator value="%{#request.video.tags[#request.lang]}">
					<s:url action="Search.action" var="search">
						<s:param name="query">
							<s:property escapeHtml="false" />
						</s:param>
					</s:url>
					<span class="badge badge-info"><a
						href="<s:property escapeHtml="false" value="%{#search}"/>"> <s:property
								escapeHtml="false" />
					</a></span>
				</s:iterator>
			</div>
			<div id="mapCanvas" class="span12 row-fluid"
				itemprop="contentLocation" itemscope
				itemtype="http://schema.org/Place">
				<div itemprop="geo" itemscope
					itemtype="http://schema.org/GeoCoordinates">
					<meta itemprop="latitude"
						content="<s:property escapeHtml="false" value="%{#request.video.lat}" />" />
					<meta itemprop="longitude"
						content="<s:property escapeHtml="false" value="%{#request.video.lon}" />" />
				</div>
			</div>
			<s:if test="%{#session.user.admin}">
				<aside class="subnav">
					<ul class="nav nav-pills">
						<s:if test="%{#session.user.admin}">
							<li><s:url action="admin/EditClipForm.action" var="edit">
									<s:param name="video">
										<s:property value="%{#request.video.id}" escapeHtml="false" />
									</s:param>
								</s:url> <a href="<s:property escapeHtml="false" value="%{#edit}"/>">
									<s:text name="edit.clip" />
							</a></li>
							<li><s:url action="admin/DeleteClip.action" var="delete">
									<s:param name="id">
										<s:property value="%{#request.video.id}" escapeHtml="false" />
									</s:param>
								</s:url> <a href="<s:property escapeHtml="false" value="%{#delete}"/>">
									<s:text name="delete.clip" />
							</a></li>

						</s:if>
					</ul>
				</aside>
			</s:if>
		</aside>
		<section class="span7">
			<div itemprop="video" itemscope
				itemtype="http://schema.org/VideoObject">
				<video controls="controls" width="575" height="500" preload="none"
					src="media/<s:property value="%{#request.video.id}" escapeHtml="false"/>.mp4"
					type="video/mp4"
					poster="media/posters/<s:property value="%{#request.video.id}" escapeHtml="false"/>.jpeg">
					<track kind="subtitles"
						src="media/subtitles/<s:property value="%{#request.video.id}" escapeHtml="false"/>_en.srt"
						srclang="en" />
					<track kind="subtitles"
						src="media/subtitles/<s:property value="%{#request.video.id}" escapeHtml="false"/>_es.srt"
						srclang="es" />
					<track kind="subtitles"
						src="media/subtitles/<s:property value="%{#request.video.id}" escapeHtml="false"/>_pt.srt"
						srclang="pt" />
				</video>
				<meta itemprop="thumbnail"
					content="media/posters/<s:property value="%{#request.video.id}" escapeHtml="false"/>.jpeg" />
				<meta itemprop="encodingFormat" content="mpeg4" />
				<meta itemprop="url"
					content="media/<s:property value="%{#request.video.id}" escapeHtml="false"/>.mp4" />
				<meta itemprop="videoFrameSize" content="1280x720" />
				<meta itemprop="videoQuality" content="2400" />
			</div>
			<p itemprop="articleBody">
				<s:property value="%{#request.video.description[#request.lang]}"
					escapeHtml="false" />
			</p>
			<p class="pull-right">
				<s:text name="taken.on" />
				<time itemprop="dateCreated"
					datetime="<s:date name="%{#request.video.date}" format="yyyy/MM/dd" />">
					<s:date name="%{#request.video.date}" format="dd/MM/yyyy" />
				</time>
				.
				<s:text name="uploaded.on" />
				<time itemprop="datePublished"
					datetime="<s:date name="%{#request.video.uploadDate}" format="yyyy/MM/dd" />">
					<s:date name="%{#request.video.uploadDate}" format="dd/MM/yyyy" />
				</time>
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