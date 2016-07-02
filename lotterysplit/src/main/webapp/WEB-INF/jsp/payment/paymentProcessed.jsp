<%@ page import="java.util.Map" %>
<%@ page import="net.authorize.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  </head>
  <body>
  <script type="text/javascript">
  <!--
    var referrer = document.referrer;
    if (referrer.substr(0,7)=="http://") referrer = referrer.substr(7);
    if (referrer.substr(0,8)=="https://") referrer = referrer.substr(8);
    if(referrer && referrer.indexOf(document.location.hostname) != 0) {

    // Use Javascript to redirect the page to the receipt redirect url.  If Javascript is not
    // available, then the <meta> refresh tag will handle the redirect.
    document.location = "${receiptUrlBuffer}";
   }
 //-->
 </script>
 <noscript><meta http-equiv="refresh" content="0;url=${receiptUrlBuffer}"></noscript>
  </body>
</html>