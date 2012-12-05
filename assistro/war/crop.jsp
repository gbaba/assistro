<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Agent Picture Cropper</title>

<style type="text/css">
body {
	color: #000;
	font: 12px Arial, Helvetica, sans-serif;
	margin: 10px;
}
</style>
<script src="/js/cropper/prototype.js" type="text/javascript"></script>
<script src="/js/cropper/scriptaculous.js" type="text/javascript"></script>
<script src="/js/cropper/cropper.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
	var x1 = -1, y1 = -1, x2 = -1, y2 = -1, iw = -1, ih = -1;

	function onEndCrop(coords, dimensions) {
		x1 = coords.x1;
		y1 = coords.y1;
		x2 = coords.x2;
		y2 = coords.y2;
		//w = dimensions.width;
		//h = dimensions.height;
		iw = document.getElementById('picture').width;
		ih = document.getElementById('picture').height;
	}

	function load() {
		new Cropper.ImgWithPreview('picture', {
			previewWrap : 'preview',
			minWidth : 53,
			minHeight : 53,
			ratioDim : {
				x : 53,
				y : 53
			},
			displayOnInit : true,
			onEndCrop : onEndCrop
		});
	}

	function submitProcess() {
		document.getElementById('formContent').style.display = 'none';
		document.getElementById('waitContent').style.display = 'block';
		window.location.href = 'process.jsp?croppedImg='
				+
<%=request.getParameter("myurl")%>
	+ '&x1=' + x1 + '&y1='
				+ y1 + '&x2=' + x2 + '&y2=' + y2 + '&iw=' + iw + '&ih=' + ih;

	}

	function cancel() {
		parent.avatarCanceled();
	}

	Event.observe(window, 'load', function() {
		load();
	});
</script>
</head>
<body>

	<div id="formContent">
		<div style="height: 250px;">
			<center> <img src="<%=request.getParameter("myurl")%>"
				alt="Select the area you want to keep" id="picture"
				style="border: none;" id="imgcro"/> </center>
		</div>

		<hr />

		<div>
			<div style="float: left;">
				<b>Chat Image</b>
			</div>
			<div style="margin-left: 10px; margin-right: 10px; float: left;">
				<div id="preview" style="border: solid 1px black;"></div>
			</div>
			<span> To make adjustments to your thumbnail image, drag and
				resize the dotted lines in your profile photo above. </span>
		</div>

		<div style="clear: left; width: 100%; text-align: center;">
			<input type="button" value="Save" onclick="submitProcess();" /> <input
				type="button" value="Cancel" onclick="cancel();" />
		</div>
	</div>

	<div id="waitContent" style="display: none;">
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<center> <img src="/img/wbg/loading.gif" alt="uploading..." />
		</center>
		<br />
		<br />
	</div>

</body>
</html>
