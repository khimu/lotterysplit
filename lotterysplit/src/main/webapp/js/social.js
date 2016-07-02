var appId;

var fbId;
var userEmail;
var accessToken;

var d = new Date();
var todaycache = d.getMonth() + "/" + d.getDay() + "/" + d.getFullYear();

function setCookie(name)
{
        var exdate = new Date();
        exdate.setDate(exdate.getDate() + 365);
        document.cookie = name + "=1";
}

function getCookie(name)
{
        var i,x,y,ARRcookies=document.cookie.split(";");
        for (i = 0;i<ARRcookies.length;i++)
        {
          x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
          y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
          x = x.replace(/^\s+|\s+$/g, "");
          if (x == name)
          {
            return unescape(y);
          }
         }
}

// add Social elements to the pages where required with correct sharing URLs
function renderButtons() {
    var linkUrl = window.location.href;

    if ($('.googlePlus').length > 0) {
        $('.googlePlus').html('<g:plusone href="' + linkUrl + '" size="medium" style="float:right"></g:plusone>');
    };
    if ($('.fbLike').length > 0) {
        $('.fbLike').html('<fb:like send="false" href="' + linkUrl + '" layout="button_count" width="80" show_faces="false" font="arial"></fb:like>');
    };
    if ($('.fbSend').length > 0) {
        $('.fbSend').html('<fb:like send="false" href="' + linkUrl + '" layout="button_count" width="80" show_faces="false" font="arial"></fb:like>');
    };
    if ($('.tweet').length > 0) {
        $('.tweet').html('<a href="http://twitter.com/share" data-url="' + linkUrl + '" class="twitter-share-button" data-count="none" data-via="splitlottery">Tweet</a></span>')
    };
    if ($('.fbComments').length > 0) {
        $('.fbComments').html('<fb:comments href="' + linkUrl + '" num_posts="3" width="640"></fb:comments>');
    };
    if ($('.fbRecommend').length > 0) {
        $('.fbRecommend').html('<img style="border: none" alt="" src="images/like_us_home.png"><fb:activity recommendations="true" border_color="#000000" font="arial" colorscheme="dark" header="false" height="400" width="220" site="www.splitlottery.com"><span>');
    };

};


function facebookReady(){
	
    FB.init({
      appId  : appId,
      status : true,
      cookie : true,
      xfbml  : true
    });

    FB.Event.subscribe('edge.create', function(href, widget) {
    	if($('#user_terms_of_service').is(':checked') == true){
    		fbLike();
    	} else {
    		$('#user_terms_of_service_error').html('Please accept our terms of service');
    		return false;
    	}  
    });    
    
    FB.Event.subscribe('auth.authResponseChange', function(response) {
    if (response.status === 'connected') {
    // The response object is returned with a status field that lets the app know the current
    // login status of the person. In this case, we're handling the situation where they 
    // have logged in to the app.
        updateButton(response);
    }else if (response.status === 'not_authorized') {
    // In this case, the person is logged into Facebook, but not into the app, so we call
    // FB.login() to prompt them to do so. 
    // In real-life usage, you wouldn't want to immediately prompt someone to login 
    // like this, for two reasons:
    // (1) JavaScript created popup windows are blocked by most browsers unless they 
    // result from direct interaction from people using the app (such as a mouse click)
    // (2) it is a bad experience to be continually prompted to login upon page load.
    FB.login();
    } else {
    // In this case, the person is not logged into Facebook, so we call the login() 
    // function to prompt them to do so. Note that at this stage there is no indication
    // of whether they are logged into the app. If they aren't then they'll see the Login
    // dialog right after they log in to Facebook. 
    // The same caveats as above apply to the FB.login() call here.
    FB.login();
    }
  });    
    
} // end facebookReady();
    
$(document).ready(function(){
	(function () {
	    $.getScript(document.location.protocol + '//apis.google.com/js/plusone.js'); // Load Google Plus files
	    $.getScript(document.location.protocol + '//platform.twitter.com/widgets.js'); // load Twitter files
	    $.getScript(document.location.protocol + '//connect.facebook.net/en_US/all.js'); // load Facebook files

	    renderButtons(); // render the buttons in the page
	} ());	

	  if(window.FB) {
	    facebookReady();
	  } else {
	    window.fbAsyncInit = facebookReady;
	  }
		  
}); // end document().ready

function updateButton(response) {
    var button = document.getElementById('fb-auth');
        
    if (response.authResponse) {
      FB.api('/me?fields=name,id', function(response) {
    	  fbId = response.id;
      });

      button.onclick = function() {                             
        FB.logout(function(response) {
    });

      };
    } else {                        
      button.onclick = function() {     
      FB.login(function(response) {
         var aToken = response.authResponse.accessToken;
         if (response.authResponse) {
            FB.api('/me', function(response) {
                fbId = response.id;
        });    
          } else {
          }
        }, {scope:'email', perms:'publish_stream'});                                  }
    }
  }

function post(postMsg){
     FB.login(function(response) {
            if (response.authResponse) {
                    accessToken = response.authResponse.accessToken; 
			        FB.api('/me?fields=name,id,email', function(response) {
			             fbId = response.id;
			             fbPost(postMsg); 
			         });    
            }
     }, {scope: 'publish_stream,user_about_me,email'});
}

function fbLike(){
    FB.login(function(response) {
        if (response.authResponse) {
               accessToken = response.authResponse.accessToken; 
		       FB.api('/me?fields=name,id,email', function(response) {
			 		  $.ajax({
			 			  url:ctx + '/lottery/fblike.htm',
			 			  type: "POST",
			 			  data: {fbId: response.id, accessToken: accessToken, email: response.email, name: response.name},
			 			  success:function(result){
		 			             fbId = response.id;
		 			             window.location.href= ctx + "/lottery/report/balance.htm";
			 			  }, error: function(XMLHttpRequest, textStatus, errorThrown){
		                        alert('Failure');
		                  }
			 		  });			    	   
			    });                     
        }
 }, {scope: 'publish_stream,user_about_me,email'});
}

function fbLogin(){
    FB.login(function(response) {
           if (response.authResponse) {
                   accessToken = response.authResponse.accessToken; 
			       FB.api('/me?fields=name,id,email', function(response) {
			 		  $.ajax({
			 			  url:ctx + '/lottery/fblogin.htm',
			 			  type: "POST",
			 			  data: {fbId: response.id, accessToken: accessToken, email: response.email, name: response.name},
			 			  success:function(result){
			 				  window.location.href=result.result;
			 			  }, error: function(XMLHttpRequest, textStatus, errorThrown)
		                    {
		                        alert('Failure');
		                    }
			 		  });			    	   
			            fbId = response.id;
			        });    
           }
    }, {scope: 'publish_stream,user_about_me,email'});
}

function referralFBLogin(referredBy){
    FB.login(function(response) {
           if (response.authResponse) {
                   accessToken = response.authResponse.accessToken; 
			       FB.api('/me?fields=name,id,email', function(response) {
			 		  $.ajax({
			 			  url:ctx + '/lottery/fblogin.htm?referredBy='+referredBy,
			 			  type: "POST",
			 			  data: {fbId: response.id, accessToken: accessToken, email: response.email, name: response.name},
			 			  success:function(result){
			 				 window.location.href=result.result;
			 			  }, error: function(XMLHttpRequest, textStatus, errorThrown){
			 				  alert('Failure');
			 			  }		 			  
			 		  });		
			          fbId = response.id;
			        });    
           }
    }, {scope: 'publish_stream,user_about_me,email'});
}

function fbPost(postMsg){
    FB.api('/me/feed', 'post', { message: postMsg }, function(response) {
      if(response && response.id ){                  
    	  alert('success');
      }else {
    	  alert('failed');
      } 
    });
}

function confirmBuy(lotteryTicketName, ticketId){
	$('#ticketId').val(ticketId);

	var r = confirm("Are you sure you want to buy lottery ticket " + lotteryTicketName);
	
	if (r == true){
	  window.location.href = ctx + "/lottery/ticket/buy/" + ticketId + "/?"+ $.now() ;
	}
	
	return false;
}

function confirmFlag(lotteryTicketName, ticketId){
	$('#ticketId').val(ticketId);
	var r = confirm("Are you sure you want to flag lottery ticket " + lotteryTicketName);
	
	url = ctx + "/lottery/ticket/flag/" + ticketId;
	if (r == true){
		  $.ajax({url:url,cache: false,success:function(result){
			    $("#div1").html(result.result);
			  }});
	}
	return false;
}

function fundAccount(amount){
    alert("A $" + amount + ".00 fund from paypal will be posted to your account.");
    var url = ctx + "/lottery/paypal/payment?paypalAmount="+amount;
    window.location.href=url;
}

function checkAgree(){
	if($('#user_terms_of_service').is(':checked') == true){
		  $('#signupForm').submit();
	} else {
		$('#user_terms_of_service_error').html('Please accept our terms of service');
		return false;
	}
}       

function validateSplitForm(){
	var numbers = $( "#one" ).val() + " " + $( "#two" ).val() + " " + $( "#three" ).val() + " " + $( "#four" ).val() + " " + $( "#five" ).val() + " " + $( "#six" ).val();
	$('#numbers').val(numbers);
	
	if($('#splitNumber').val() > 100){
		$('#splitNumberError').html("Pool size cannot exceed 100.");
		$('#splitNumber').val('');
		return false;
	}else{
		$('#ticketSplitForm').submit();
		return false;
	}
}