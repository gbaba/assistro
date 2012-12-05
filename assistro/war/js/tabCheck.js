function setActive() {
		  aObj = document.getElementById('ui-main-menu').getElementsByTagName('a');
		  for(i=0;i<aObj.length;i++) { 
		    if(document.location.href.indexOf(aObj[i].href)>=0) {
		      aObj[i].className='ui-menu-active';
		    }
		  }
		}



