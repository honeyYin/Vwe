// JavaScript Document
function swapImages10(){
	if($('#myGallery10  a').next().length>0){
      var $active = $('#myGallery10 .active');
      var $next = ($('#myGallery10 .active').next().length > 0) ? $('#myGallery10 .active').next() : $('#myGallery10 a:first');
      $active.fadeOut(function(){
	      $active.removeClass('active');
	      $next.fadeIn().addClass('active');
      });
      }
    }
	function swapImages11(){
		if($('#myGallery11  a').next().length>0){
      var $active = $('#myGallery11 .active');
      var $next = ($('#myGallery11 .active').next().length > 0) ? $('#myGallery11 .active').next() : $('#myGallery11 a:first');
      $active.fadeOut(function(){
	      $active.removeClass('active');
	      $next.fadeIn().addClass('active');
      });
      }
    }
	function swapImages12(){
		if($('#myGallery12  a').next().length>0){
      var $active = $('#myGallery12 .active');
      var $next = ($('#myGallery12 .active').next().length > 0) ? $('#myGallery12 .active').next() : $('#myGallery12 a:first');
      $active.fadeOut(function(){
	      $active.removeClass('active');
	      $next.fadeIn().addClass('active');
      });
      }
    }
	function swapImages20(){
		if($('#myGallery20  a').next().length>0){
      var $active = $('#myGallery20 .active');
      var $next = ($('#myGallery20 .active').next().length > 0) ? $('#myGallery20 .active').next() : $('#myGallery20 a:first');
      $active.fadeOut(function(){
	      $active.removeClass('active');
	      $next.fadeIn().addClass('active');
      });
      }
    }
	function swapImages30(){
		if($('#myGallery30  a').next().length>0){
      var $active = $('#myGallery30 .active');
      var $next = ($('#myGallery30 .active').next().length > 0) ? $('#myGallery30 .active').next() : $('#myGallery30 a:first');
      $active.fadeOut(function(){
	      $active.removeClass('active');
	      $next.fadeIn().addClass('active');
      });
      }
    }
	function swapImages31(){
		if($('#myGallery31  a').next().length>0){
      var $active = $('#myGallery31 .active');
      var $next = ($('#myGallery31 .active').next().length > 0) ? $('#myGallery31 .active').next() : $('#myGallery31 a:first');
      $active.fadeOut(function(){
	      $active.removeClass('active');
	      $next.fadeIn().addClass('active');
      });
      }
    }

	
	function hitpic(id,link){
		$.ajax({
              type: "post",
			  url: "advAction!hitAdi.action",
			  data:"id="+id,
			  success: function(msg){
				  window.open(link, "_blank");
    	    	 }
			});
	}
	
    $(document).ready(function(){
    	var div1=document.getElementById("one");
    	var div2=document.getElementById("two");
    	var div3=document.getElementById("three")
    	$.ajax({
              type: "post",
			  url: "advAction!getTop.action",
			  success: function(msg){
				    div1.innerHTML=msg;
    	    	 }
			});
    	$.ajax({
              type: "post",
			  url: "advAction!getCenter.action",
			  success: function(msg){
				    div2.innerHTML=msg;
    	    	 }
			});
    	$.ajax({
              type: "post",
			  url: "advAction!getBottom.action",
			  success: function(msg){
				    div3.innerHTML=msg;
    	    	 }
			});
    	$.ajax({
              type: "post",
			  url: "advAction!getMethod.action",
			  success: function(msg){
    		var obj = eval(msg);
    		for(i = 0; i < obj.length; i++){
    			
    		  setInterval(obj[i].method, parseInt(obj[i].time));
    		
    		}
    		
				
    	    	 }
			});
    })
    


  