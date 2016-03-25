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
                <form id="email-form" class="form-horizontal" action="${pageContext.request.contextPath}/contact" method="post">
                    <div class="form-group">
                        <label for="name" class="col-md-4 control-label">Your Name:</label>
                        <div class="col-md-8">
                            <input type="text" name="name" class="form-control" id="name" placeholder="Name" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email" class="col-md-4 control-label">Email Address:</label>
                        <div class="col-md-8">
                            <input type="text" name="email" class="form-control" id="email" placeholder="Example: you@email.com" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="subject" class="col-md-4 control-label">Subject:</label>
                        <div class="col-md-8">
                            <input type="text" name="subject" class="form-control" id="subject" placeholder="Subject" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="message" class="col-md-4 control-label">Message:</label>
                        <div class="col-md-8">
                            <textarea class="form-control" name="message" rows="5" id="message" placeholder="Message"></textarea>
                            
                        </div>
                    </div>
                        <div id="add-email-validation-errors"></div>
                        <input type="submit" class="btn btn-default pull-right" value="Send Email" />
                </form>

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
