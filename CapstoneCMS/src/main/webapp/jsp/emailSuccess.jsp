<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contact Us</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="icon"  href="${pageContext.request.contextPath}/img/icon.png">
    </head>
    <body>
        <!--NOSCRIPT-->
        <noscript>
        <div class="noscript">
            <h1>For the Holiday Direct experience, it is necessary to enable JavaScript.</h1>
            <a href="http://www.enable-javascript.com/" target="_blank"> <h2>Here are instructions how to enable JavaScript in your web browser.</h2></a>
        </div>
        </noscript>
        <!--END NOSCRIPT-->
        <jsp:include page="/jsp/navbar.jsp" />
        <div class="container">
            <div class="col-md-6  col-md-offset-2">
                <h2>Contact Us</h2>
                
                <p>Thank you, ${name}. We've received your email and will reply as soon as we can.</p>

            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/contact-app.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            var contextRoot = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/js/nav.js"></script>
    </body>
</html>
