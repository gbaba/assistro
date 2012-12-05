jQuery.support.cors = true;
var serverUrl = "http://www.assistro.com/";
function getCookie(c_name)
{
var i,x,y,ARRcookies=document.cookie.split(";");
for (i=0;i<ARRcookies.length;i++)
  {
  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
  x=x.replace(/^\s+|\s+$/g,"");
  if (x==c_name)
    {
    return unescape(y);
    }
  }
}

function setCookie(c_name,value,exdays)
{
		var exdate=new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
		document.cookie=c_name + "=" + c_value;
		return value;
}

function checkCookie()
{
var username=getCookie("widgetid");
if (username!=null && username!="")
  {
	return username;
  }
else
  {
	return setCookie("widgetid",makeid(),1);
  }
}

function makeid()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 16; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

var pm = 
{
  'timestamp' : 0,
  'message':''
};

function update_chat_ui(data)
{
  
  if(data.code > 0)
  {
   //jQuery('div.chat_container .collapsed').click();
    
    var messages = data.data;
    
    for(var i=0;i< messages.data.length ;i++)
    {
      var m = messages.data[i];
      if(m.message_timestamp!=pm.timestamp){
    	  pm.timestamp = m.message_timestamp;
      var html1 ;

      if(m.message_sender.indexOf("visitor")!= -1){
 
      html1 = '<strong class="host_name">' + m.message_sender + ': </strong>';
      }
      else{
    	  html1 = '<strong class="my_customer">' + m.message_sender + ': </strong>';
      }
      var html = 
      [
        '<div class="msg">',
         html1,
        '<span class="text">' + m.message_text + '</span>',
        '</div>'
      ];
      
      jQuery('div.chat_container .live-chat .content .chat:last').append(html.join('')).scrollTop(jQuery('div.chat_container .live-chat .content .chat:last')[0].scrollHeight);	
    }
    }
    
  }

}
     

(function(jQuery){
  // hide before Chat widget and show chat widget     
  jQuery('div.chat_container .before_chat .content .start_chat').click
  (    
    function()
    {
      
      var jQueryname = jQuery('div.chat_container .before_chat .content .before_user');
      var jQueryemail = jQuery('div.chat_container .before_chat .content .before_email');
      var message = '';
      
      var x = document.getElementById('beforeEmail').value;
      var at=x.indexOf("@");
      var dot=x.lastIndexOf(".");
	
      virtism_config.message = message;
      virtism_config.widgetid = getCookie('widgetid'); 
      
	 if(jQueryname.val() !='' && jQueryemail.val()!=''){
		 if (at<1 || dot<at+2 || dot+2>=x.length)
		  {
			 		jQuery('<div class="quick-alert">Alert! Invalid email.</div>')
				    .insertBefore('.header')
				    .fadeIn('slow')
				    .animate({opacity: 1.0}, 3000)
				    .fadeOut('slow', function() {
				      $(this).remove();
				    });
			 return false;
			  }
		 var expiremilliseconds = 24 * 60 * 60 * 1000;

		 var currdate = new Date();

		 var expirationdate = new Date(currdate.getTime() + expiremilliseconds);

		 document.cookie="username="
		                +jQueryname.val()
		                + ";expires=" + expirationdate.toGMTString()+";path=/";
      var dataString = 'beforeName='+ jQueryname.val() + '&beforeEmail=' + jQueryemail.val()+ '&code='+virtism_config.widget_code;

      jQuery.ajax({
	        url : serverUrl+'VisitorData',
	        type: 'post',
	        data: dataString,
	        success: function(result)
	        {
	        	jQuery('div.chat_container .before_chat').remove();
	    	    jQuery('div.chat_container .live-chat').show();
	         
	        },
      		error:function(){
      			jQuery('div.chat_container .before_chat').remove();
	    	    jQuery('div.chat_container .live-chat').show();
      		}
	      });  
	 }
	 else{
		jQuery('#bname').css('color','#FF0000');	 
		jQuery('div.chat_container .before_chat .content .before_user').css({'border-style':'solid','border-color':'red'});
		jQuery('#bmail').css('color','#FF0000');
		jQuery('div.chat_container .before_chat .content .before_email').css({'border-style':'solid','border-color':'red'});
	 }
    }
  );
  
  // show before chat collpased state
  jQuery('div.chat_container .before_chat .header .buttons .minimize').click
  (
    function()
    {	
      jQuery('div.chat_container .before_chat').hide();
      jQuery('div.chat_container .before_chat_collapsed').show();
    }
  );
  
  // Maximize before chat collpased state
  jQuery('div.chat_container .before_chat_collapsed .header').click
  (
    function()
    {	
      jQuery('div.chat_container .before_chat_collapsed').hide();
      jQuery('div.chat_container .before_chat').show();
    }
  );
  
  // Close before_chat Widget
  jQuery('div.chat_container .before_chat .header .buttons .cross').click
  (
    function()
    {	
      jQuery('div.chat_container .before_chat').remove();
    }
  );
  
  // Close before chat collpased state
  jQuery('div.chat_container .before_chat_collapsed .header .buttons .cross').click
  (
    function()
    {	
      jQuery('div.chat_container .before_chat_collapsed').remove();
    }
  );

  // show offline widget from collapsed state
  jQuery('div.chat_container div.collapsed_offline').click
  (
	function()
	{ 	
      jQuery('div.chat_container div.collapsed_offline').hide();
      jQuery('div.chat_container .offline_state').show();
	 }
  );
  
  // Close offline widget collapsed state
  jQuery('div.chat_container div.collapsed_offline .header .buttons .cross').click
  (
	function()
	{ 	
      jQuery('div.chat_container div.collapsed_offline').remove();
	}
  );
  
  // Minimize the Offline Chat Dialog to Collapsed State
  jQuery('div.chat_container div.offline_state .header .buttons .minimize').click
  (
    function()
    { 	
      jQuery('div.chat_container .offline_state').hide();
      
      jQuery('div.chat_container .collapsed_offline').show();
    }
  );
  
  // Close the Offline Chat Dialog
  jQuery('div.chat_container div.offline_state .header .buttons .cross').click
  (
    function()
    { 	
      jQuery('div.chat_container .offline_state').remove();
    }
  );
  
  // Minimize the Online Chat Dialog to Collapsed State
  jQuery('div.chat_container div.live-chat .header .buttons .minimize').click
  (
    function()
    { 	
      jQuery('div.chat_container .live-chat').hide();
      
      jQuery('div.chat_container .collapsed').show();
    }
  );
  
  // Maximize the online widget from collapsed state
  jQuery('div.chat_container div.collapsed').click
  (
    function()
    {
      jQuery(this).hide();
      
      jQuery(this).next('div.live-chat').show();
      
      jQuery(this).next('div.live-chat').find('.type_text').focus();
    }
  );
  
  // Close the Online Chat Dialog
  jQuery('div.chat_container div.live-chat .header .buttons .cross').click
  (
    function()
    { 	
      jQuery('div.chat_container .live-chat').remove();
    }
  );
    
  // Close the Collapsed Chat Dialog
  jQuery('div.chat_container div.collapsed .header .buttons .cross').click
  (
    function()
    { 	
      jQuery('div.chat_container .live-chat').remove();
    }
  );
  
 // send before_chat Info..

  
  
  // send offline message...
  
  jQuery('div.chat_container .offline_state .content .sendofflineMsg').click
  (
    function()
    {
      
      var jQueryname = jQuery('div.chat_container .offline_state .content .offname');
      var jQueryemail = jQuery('div.chat_container .offline_state .content .offemail');
      var jQueryoffline_message = jQuery('div.chat_container .offline_state .content .offmsg'); 
      
      var message = jQuery.trim(jQueryoffline_message.val());
      
      if(message.length == 0)
        return false;
      
      var dataString = 'subject='+jQueryname.val()+'&email='+jQueryemail.val()+'&message='+message+'&code='+virtism_config.widget_code;
      jQuery.ajax({
        url : serverUrl+'SendEmail',
        type: 'post',
        dataType: 'json',
        data: dataString,
        success: function(){ 
	 		jQuery('<div class="quick-alert">Alert! Email sent.</div>')
		    .insertBefore('.header')
		    .fadeIn('slow')
		    .animate({opacity: 1.0}, 3000)
		    .fadeOut('slow', function() {
		      $(this).remove();
		    });
            	jQueryname.val('');
         		jQueryemail.val('');
         		jQueryoffline_message.val('');  		
        },
        error:function(){
        	jQuery('<div class="quick-alert">Alert! Email sent.</div>')
		    .insertBefore('.header')
		    .fadeIn('slow')
		    .animate({opacity: 1.0}, 3000)
		    .fadeOut('slow', function() {
		      $(this).remove();
		    });
        	jQueryname.val('');
     		jQueryemail.val('');
     		jQueryoffline_message.val(''); 
        }
      });     
    }
  );
  
  // On Enter Key Send Message
  jQuery('div.chat_container .live-chat .content .type_text').keypress(function(event){      
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
              var jQuerythis = jQuery(this);
              
              var jQuerytextbox = jQuery('div.chat_container .live-chat .content .type_text');
              var jQuerypanel = jQuery('div.chat_container .live-chat .content .chat');
              
              var message = jQuery.trim(jQuerytextbox.val());
              
              if(message.length == 0)
                return false;
              
              virtism_config.message = message;
              virtism_config.widgetid = getCookie('widgetid');
             
  var data = "widget_code="+virtism_config.widget_code+"&message="+message+"&widgetid="+ virtism_config.widgetid;
		      
		      if (jQuery.browser.msie && window.XDomainRequest) {
		  	    // Use Microsoft XDR
		  	    var xdr = new XDomainRequest();
		  	    xdr.open("GET", serverUrl+"SendMessage?"+data);
		  	    xdr.onprogress = function() {};	
		  	    xdr.onload = function () {
		  	    var JSON = jQuery.parseJSON(xdr.responseText);
		  	    
		  	    if (JSON == null || typeof (JSON) == 'undefined')
		  	    {
		  	    
		  	     }
		  	    else
		  	    	{
		  	    	jQuerytextbox.val('');
		  	    	}
		  	  };
		  	 
		  	    xdr.send();
		  	}else{
              
              
              jQuery.ajax({
                url : serverUrl+'SendMessage',
                type: 'post',
                dataType: 'json',
                data: virtism_config,
                 beforeSend: function()
                {
                  jQuerythis.attr('disabled','disabled');
                },
                success: function(result)
                {
                  
                  if(result.code < 0)
                  {
                    alert(result.message);  
                  }
                  else
                  {
                    jQuerytextbox.val('');
                  }
                  
                  jQuerythis.removeAttr('disabled');
                }
              });
		  } 
        }
     
    });
  // send message handler ...
  
  jQuery('div.chat_container .live-chat .content .send').click
  (
    function()
    {
      var jQuerythis = jQuery(this);
      var jQuerytextbox = jQuery('div.chat_container .live-chat .content .type_text');
      var jQuerypanel = jQuery('div.chat_container .live-chat .content .chat');
      
      var message = jQuery.trim(jQuerytextbox.val());
      
      if(message.length == 0)
        return false;
      
      virtism_config.message = message;
      virtism_config.widgetid = getCookie('widgetid');

     
      var data = "widget_code="+virtism_config.widget_code+"&message="+message+"&widgetid="+ virtism_config.widgetid;
      
      if (jQuery.browser.msie && window.XDomainRequest) {
  	    // Use Microsoft XDR
  	    var xdr = new XDomainRequest();
  	    xdr.open("GET", serverUrl+"SendMessage?"+data);
  	    xdr.onprogress = function() {};	
  	    xdr.onload = function () {
  	    var JSON = jQuery.parseJSON(xdr.responseText);
  	    if (JSON == null || typeof (JSON) == 'undefined')
  	    {
  	    	
  	        // JSON = jQuery.parseJSON(data.firstChild.textContent);
  	    	
  	    }
  	    else
  	    	{
  	    	jQuerytextbox.val('');
  	    	}
  	   // processData(JSON);
  	   
  	    };
  	  
  	    xdr.send();
  	}else{
      
      jQuery.ajax({
        url : serverUrl+'SendMessage',
        type: 'post',
        dataType: 'json',
        data: virtism_config,
         beforeSend: function()
        {
          jQuerythis.attr('disabled','disabled');
        },
        success: function(result)
        {
          
          if(result.code < 0)
          {
            alert(result.message);  
          }
          else
          {
            jQuerytextbox.val('');
          }
          
          jQuerythis.removeAttr('disabled');
        },
        error:function (xhr, ajaxOptions, thrownError){
        	
        }  
      });
  		}
    }
  );
})(jQuery);


var data1 = 
{
  'session' : '',
  'last_id' : 0,
  'timestamp' : 0,
  'widgetid':checkCookie()
};

function messagesLongPoll(){
	
	  if(jQuery('div.chat_container .live-chat .content .chat .host_name' != null && 'div.chat_container .live-chat .content .chat .msg .host_name'== null)){
		 // alert("hi");
	  }
//	  var curUrl = window.location.href;
//	  if(curUrl.indexOf("rtrk")>-1){
//		  if(serverUrl.indexOf("rtrk")>-1)
//		  serverUrl = serverUrl.replace(".rtrk", "");
//		  if(serverUrl.indexOf("--")>-1)
//		  serverUrl = serverUrl.replace("--", ".");  
//		  //curUrl = curUrl.replace(".rtrk","");
//		  window.location="http://gryphongaragedoors.com.au/";
//	  }
	  
	jQuery.extend(data1,virtism_config);	  
	var data = "widget_code="+virtism_config.widget_code+"&last_id="+data1.last_id+"&widgetid="+ checkCookie()+"&timestamp="+data1.timestamp;
	
	  if (jQuery.browser.msie && window.XDomainRequest) {
		  	var xdr = new XDomainRequest();
			    xdr.open("GET", serverUrl+"GetMessage?"+data);
			    xdr.onprogress = function() {};	
			    xdr.onload = function () {
			    var JSON = jQuery.parseJSON(xdr.responseText);
			    if (JSON == null || typeof (JSON) == 'undefined')
			    {
			    }
			    else
			    	{
			    	update_chat_ui(JSON);
			    	data1 = JSON.data;
			    }
			    };
			  
			    xdr.send();
	  	}else{
	  jQuery.ajax({
		url : serverUrl+'GetMessage',
	    type: 'get',
	    dataType: 'json',
	    data: data,
	    cache   : false,
	    beforeSend: function()
	    {
	      
	    },
	    success: function(result)
	    { 
	      serverUrl="http://www.assistro.com/";
	      if(result.code >= 0)
	      {
	        update_chat_ui(result);
	        
	        data1 = result.data;
	      }
	    }
	  });
	 }
}


//window.onbeforeunload = function (e) {
//	  var e = e || window.event;
//	  // For IE and Firefox
//	  if (e) {
//	   // e.returnValue = 'Any string';
//		//  e.returnValue = '';
//	    var ref = "User has reloaded or leave the page";
//	    virtism_config.widgetid = getCookie('widgetid');
//	    var userdata = "refresh="+ref+"&widgetid="+ virtism_config.widgetid+"&widget_code="+virtism_config.widget_code;
//	    jQuery.ajax({
//          url : serverUrl+'CloseMessage',
//          type: 'post',
//          dataType: 'json',
//          data: userdata,
//	    });
//	  }
//	  // For Safari
//	  //return 'Any string';
//	 // return '';
//	};

//var inFormOrLink;
//jQuery('a').live('click', function() { inFormOrLink = true; });
//jQuery('form').bind('submit', function() { inFormOrLink = true; });
//jQuery(window).bind('pageshow', function (event) {inFormOrLink = true; });
//
//jQuery(window).bind("beforeunload", function() { 
//	if(inFormOrLink){
//		//alert(inFormOrLink);
//	}
//	else{
//		//alert("call sent");
//		var ref = "User has reloaded or leave the page";
//	    virtism_config.widgetid = getCookie('widgetid');
//	    var userdata = "refresh="+ref+"&widgetid="+ virtism_config.widgetid+"&widget_code="+virtism_config.widget_code;
//	    jQuery.ajax({
//         url : serverUrl+'CloseMessage',
//         type: 'post',
//         dataType: 'json',
//         data: userdata,
//	    });
//		//return inFormOrLink || confirm("Do you really want to close?");
//	}
//
//    //return inFormOrLink || confirm("Do you really want to close?"); 
//})

jQuery(function(jQuery){
jQuery(document).ready(function(){
	setInterval(function() {
		messagesLongPoll();
	}, 1000);
});
});



