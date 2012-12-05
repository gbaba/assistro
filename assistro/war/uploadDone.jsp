
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<script type="text/javascript" charset="utf-8"> 
		function submitProcess() {
			parent.location ="http://www.assistro.com/admin/operator.html";			
		}
		
		function cancel() {
			parent.location.reload();			
			parent.window.close();
		}
	</script> 
</head> 
<body> 

	<div id="formContent"> 
		<div>
			<img src="<%=request.getParameter("myurl")%>" id="picture" style="border:none;width: 250px;height: 150px;margin-left:102px;" />
		</div> 
				
		<div style="margin-left:200px;margin-top:21px;">
			<input type="button" value="Save" onclick="submitProcess();" />
		</div>	
	</div>

	<div id="waitContent" style="display:none;">
		<br /><br /><br /><br /><br /><br /><br /><br />
		<center>
			<img src="/images/loading.gif" alt="uploading..." />
		</center>
		<br /><br />
	</div>

</body> 
</html> 