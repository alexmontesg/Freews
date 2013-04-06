<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!doctype html>
<html>
<head>
<sx:head />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../ui-darkness/jquery-ui-1.8.23.custom.css"
	type="text/css" />
<script src="../jquery-1.8.0.js"></script>
<script src="../ui/jquery.ui.core.js"></script>
<script src="../ui/jquery.ui.datepicker.js"></script>
<s:if test="%{#request.lang=='es'}">
	<script src="../ui/i18n/jquery.ui.datepicker-es.js"></script>
</s:if>
<s:if test="%{#request.lang=='pt'}">
	<script src="../ui/i18n/jquery.ui.datepicker-pt.js"></script>
</s:if>
<title>Freews</title>
<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

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

<script>
	var marker;
	var map;
	function initialize(lat, lon) {
		$("#UploadClip_takenDate").datepicker({
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
		marker = new google.maps.Marker({
			map : map,
			draggable : true,
			animation : google.maps.Animation.DROP,
			position : new google.maps.LatLng(lat, lon)
		});
		marker.setTitle('Freews');
		updateCoordenades();
		google.maps.event.addListener(marker, 'position_changed', function() {
			updateCoordenades();
		});
	}

	function updateCoordenades() {
		document.getElementById('UploadClip_lat').setAttribute('value',
				marker.getPosition().lat());
		document.getElementById('UploadClip_lon').setAttribute('value',
				marker.getPosition().lng());
	}
	
	function processFiles(files, lang) {
		var file = files[0];
		var reader = new FileReader();
		reader.onload = function (e) {
			var output = document.getElementById('UploadClip_subs_' + lang);
			output.textContent = e.target.result;
		};
		reader.readAsText(file);
	}
</script>
<s:head />
</head>
<body
	onload="initialize(<s:property value="%{#request.lat}" escapeHtml="false"/>, <s:property value="%{#request.lon}" escapeHtml="false"/>)">

	<div class="container">
		<header class="row-fluid">
			<div class="span1">
				<a href="<s:url action="../SeekVideos.action"/>"><img
					src="../logo_freews.png" /></a>
			</div>
			<div class="span6">
				<h1>Freews</h1>
				<h2>
					<s:text name="upload.clip" />
				</h2>
			</div>
		</header>
		<s:form action="UploadClip" method="POST"
			enctype="multipart/form-data" cssClass="span7">
			<label class="control-label" for="clip"><s:text name="clip" /></label>
			<s:file key="clip" />
			<label class="control-label" for="headline_es"><s:text
					name="headline_es" /></label>
			<s:textfield key="headline_es" value="%{#request.headline['es']}"
				cssClass="input-block-level" />
			<label class="control-label" for="description_es"><s:text
					name="description_es" /></label>
			<s:textarea key="description_es"
				value="%{#request.description['es']}" cssClass="input-block-level" />
			<label class="control-label" for="headline_en"><s:text
					name="headline_en" /></label>
			<s:textfield key="headline_en" value="%{#request.headline['en']}"
				cssClass="input-block-level" />
			<label class="control-label" for="description_en"><s:text
					name="description_en" /></label>
			<s:textarea key="description_en"
				value="%{#request.description['en']}" />
			<label class="control-label" for="headline_pt"><s:text
					name="headline_pt" /></label>
			<s:textfield key="headline_pt" value="%{#request.headline['pt']}"
				cssClass="input-block-level" />
			<label class="control-label" for="description_pt"><s:text
					name="description_pt" /></label>
			<s:textarea key="description_pt"
				value="%{#request.description['pt']}" />
			<label class="control-label" for="takenDate"><s:text
					name="takenDate" /></label>
			<s:textfield key="takenDate" value="%{#request.date}"
				cssClass="input-block-level" />
			<s:hidden key="subs_es" />
			<s:hidden key="subs_en" />
			<s:hidden key="subs_pt" />
			<s:hidden key="lat" value="%{#request.lat}" />
			<s:hidden key="lon" value="%{#request.lon}" />
			<s:hidden key="id" value="%{#request.id}" />
			<s:submit key="submit"
				cssClass="btn btn-large btn-primary pull-right" />
		</s:form>
		<section class="span4">
			<div id="mapCanvas"></div>
			<label class="control-label" for="inputSubsEs"><s:text
					name="subs.es.file" /></label> <input id="inputSubsEs" type="file"
				accept="text/plain" onchange="processFiles(this.files, 'es')" /> <label
				class="control-label" for="inputSubsEn"><s:text
					name="subs.en.file" /></label> <input id="inputSubsEn" type="file"
				accept="text/plain" onchange="processFiles(this.files, 'en')" /> <label
				class="control-label" for="inputSubsPt"><s:text
					name="subs.pt.file" /></label> <input id="inputSubsPt" type="file"
				accept="text/plain" onchange="processFiles(this.files, 'pt')" />
		</section>
	</div>
</body>
</html>