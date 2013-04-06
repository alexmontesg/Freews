<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!doctype html>
<html>
<head>
<sx:head />
<s:head />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="./ui-darkness/jquery-ui-1.8.23.custom.css"
	type="text/css" />
<script src="./jquery-1.8.0.js"></script>
<script src="./ui/jquery.ui.core.js"></script>
<script src="./ui/jquery.ui.datepicker.js"></script>
<s:if test="%{#request.lang=='es'}">
	<script src="./ui/i18n/jquery.ui.datepicker-es.js"></script>
</s:if>
<s:if test="%{#request.lang=='pt'}">
	<script src="./ui/i18n/jquery.ui.datepicker-pt.js"></script>
</s:if>
<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<s:set name="theme" value="'simple'" scope="page" />
<script src="./bootstrap/js/bootstrap.min.js"></script>
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
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
<link href="./bootsrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<script>
	var marker;
	var map;

	function initialize() {
		$("#UploadRaw_takenDate").datepicker({
			maxDate : '+0',
			defaultDate : '0'
		});
		var mapOptions = {
			zoom : 1,
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			center : new google.maps.LatLng(0, 0)
		};
		map = new google.maps.Map(document.getElementById('mapCanvas'),
				mapOptions);
		google.maps.event.addListener(map, 'rightclick', function(e) {
			addMarker(e);
		});
		google.maps.event.addListener(map, 'click', function(e) {
			addMarker(e);
		});
	}

	function addMarker(event) {
		if (marker == null) {
			marker = new google.maps.Marker({
				map : map,
				draggable : true,
				animation : google.maps.Animation.DROP,
				position : event.latLng
			});
			marker.setTitle('Freews');
			updateCoordenades();
			google.maps.event.addListener(marker, 'position_changed',
					function() {
						updateCoordenades();
					});
		}
	}

	function updateCoordenades() {
		document.getElementById('UploadRaw_lat').setAttribute('value',
				marker.getPosition().lat());
		document.getElementById('UploadRaw_lon').setAttribute('value',
				marker.getPosition().lng());
	}
</script>
<title>Freews</title>
</head>
<body onload="initialize()">
	<div class="container">
		<header class="row-fluid">
			<div class="span1">
				<a href="<s:url action="SeekVideos.action"/>"><img
					src="./logo_freews.png" /></a>
			</div>
			<div class="span6">
				<h1>Freews</h1>
				<h2>
					<s:text name="upload.clip" />
				</h2>
			</div>
		</header>
		<section class="uploadForm">
			<s:form action="UploadRaw" method="POST"
				enctype="multipart/form-data" cssClass="span7">
				<label class="control-label" for="clip"><s:text name="clip" /></label>
				<s:file key="clip" />
				<label class="control-label" for="headline"><s:text
						name="headline" /></label>
				<s:textfield key="headline" cssClass="input-block-level" />
				<label class="control-label" for="description"><s:text
						name="description" /></label>
				<s:textarea key="description" />
				<s:hidden key="lat" />
				<s:hidden key="lon" />
				<label class="control-label" for="takenDate"><s:text
						name="takenDate" /></label>
				<s:textfield key="takenDate" cssClass="input-block-level" />
				<s:submit key="submit"
					cssClass="btn btn-large btn-primary pull-right" />
			</s:form>
		</section>
		<div id="mapCanvas" class="span4"></div>
	</div>
</body>
</html>