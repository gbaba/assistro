<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
<head>
<title></title>
<meta name="language" content="en">
<style type="text/css">
body {
	color: #000;
	font: 12px Arial, Helvetica, sans-serif;
	margin: 10px;
}

img {
	border: 0px;
}
</style>
<script>
function uploadInputFileKeyPress(event) {
if (event.keyCode == 13) {
var uploadButton = document.getElementById('uploadBtn');
uploadButton.click();
return false;
}
return true;
}
function clickUpload() {
document.getElementById('uploadBtn').click();
document.getElementById('waitContent').style.display = 'block';
document.getElementById('formContent').style.display = 'none';
return false;
}
</script>
</head>
<body>
	<div id="formContent" style="margin-left:111px;margin-top:-50px;">
		<br> <br> <br> 
		<br> <strong>Upload a photo</strong> <br> <br> You can
		upload a JPG, GIF, or PNG file.
		<p></p>
		<form enctype="multipart/form-data" method="post"
			action="<%= blobstoreService.createUploadUrl("/upload") %>">
			<input type="file" title="" tabindex="" style="" size=""
				onmouseover="" onmouseout="" onmousemove="" onmousedown=""
				onkeyup="" onkeypress="return uploadInputFileKeyPress(event);"
				onkeydown="" onfocus="" ondblclick="" onclick=""
				onchange="return clickUpload();" onblur="" name="myFile"> <input
				id="uploadBtn" type="submit" style="display: none;" name="uploadBtn"
				value="Submit">
		</form>
	</div>
	<div id="waitContent" style="display: none;margin-left:111px;margin-top:-50px;">
		<br> <br> <br> <br>
		<br> <br> <br>
				<img alt="uploading..." src="/images/loading.gif" style="margin-left:111px"/> 
		<br> <br>
	</div>
</body>
</html>
