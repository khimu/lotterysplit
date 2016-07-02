function setValues() {
	localStorage.setItem("userName", $('#userName').val());
	localStorage.setItem("password", $('#password').val());
	localStorage.setItem("signature", $('#signature').val());

}
function populateValues() {
	var userName = localStorage.getItem("userName") != null ? localStorage
			.getItem("userName") : "hsspro_1355820242_biz_api1.gmail.com";
	var password = localStorage.getItem("password") != null ? localStorage
			.getItem("password") : "1355820300";
	var signature = localStorage.getItem("signature") != null ? localStorage
			.getItem("signature")
			: "Ai1PaghZh5FmBLCDCTQpwG8jB264Aye0h1hh.bldOVENjvi-tZAx7oji";
	$('#userName').val(userName);
	$('#password').val(password);
	$('#signature').val(signature);

}
//function getDoc() {
//	if ($('#response-li').hasClass('active')) {
//		$('#step1').hide();
//		$('#step2').show();
//	}
//	if ($('#form-li').hasClass('active')) {
//		$('#step2').hide();
//		$('#step1').show();
//	}
//}
function doSubmit() {
	var url = "hss?userName=" + encodeURIComponent($('#userName').val())
			+ "&password=" + encodeURIComponent($('#password').val())
			+ "&signature=" + encodeURIComponent($('#signature').val())
			+ "&buttontype=" + encodeURIComponent($('#buttontype').val())
			+ "&buttoncode=" + encodeURIComponent($('#buttoncode').val())
			+ "&subtotal=" + encodeURIComponent($('#subtotal').val())
			+ "&shipping=" + encodeURIComponent($('#shipping').val())
			+ "&handling=" + encodeURIComponent($('#handling').val()) + "&tax="
			+ encodeURIComponent($('#tax').val()) + "&street1="
			+ encodeURIComponent($('#street1').val()) + "&street2="
			+ encodeURIComponent($('#street2').val()) + "&city="
			+ encodeURIComponent($('#city').val()) + "&country="
			+ encodeURIComponent($('#country').val()) + "&zip="
			+ encodeURIComponent($('#zip').val()) + "&state="
			+ encodeURIComponent($('#state').val()) + "&returnURL="
			+ encodeURIComponent($('#returnURL').val());
	if ($('#checkboxTemplate').is(':checked')) {
		url += "&checkboxTemplate="
				+ encodeURIComponent($('#checkboxTemplate').val());
	}
	$
			.ajax({
				type : "POST",
				dataType : "json",
				url : url,
				timeout : 20000,
				success : function(data) {
					if (data != null) {
						$('#form-1').removeClass("active");
						$('#response-1').addClass("active");
						$('#form-li').removeClass("active");
						$('#response-li').addClass("active");

					}
					if (data.email != null) {
						$('#step1').hide();
						$('#step2').show();
						$('#error').hide();
						$('#response').show();
						var emaillink = "";
						var emailiframe = "";
						if (data.displayIframe != null) {
							emailiframe += "<iframe  src=\""
									+ data.email
									+ "\" name=\"emailLink_iframe\" width=\"600px\" height=\"570px\"></iframe>";

							emaillink += "<iframe src=\""
									+ data.email
									+ "name=\"emailLink_iframe\" width=\"570px\" height=\"540px\"></iframe>";
							$('#emailInner').val(emaillink);
							$('#emailInner').show();
						} else {
							emailiframe += "<a href=\"" + data.email
									+ "\"><small>" + data.email
									+ "</small></a>";
							$('#emailInner').hide();
						}
						$('#emailIframe').html("");
						$('#emailIframe').html(emailiframe);

					}
					if (data.website != null) {
						var website = data.website;
						var regex = /<br\s*[\/]?>/gi;
						website = website.replace(regex, "");
						websiteiframe="";
						if (data.displayIframe != null) {

							website = website
									.replace("<form",
											"<form  target='website_iframe' name='hss_form' ");
							website = "<iframe name=\"website_iframe\" width=\"570px\" height=\"540px\"></iframe>"
									+ website;
						
					}
						websiteiframe =  website;
						$("#websitetext").val(website);
						$('#websiteIframe').html("");
						$('#websiteIframe').html(websiteiframe);
					}
					if (data.errorCode != null) {
						$('#response').hide();
						$('#error').show();
						$('#errorCode').append("<small>")
								.append(data.errorCode).append("</small>");
						$('#shortMsg').append("<small>").append(data.shortMsg)
								.append("</small>");
						$('#longMsg').append("<small>").append(data.longMsg)
								.append("</small>");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
				},
				contentType : "application/json"
			});
}
