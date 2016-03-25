<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Holiday Direct - HOME</title>
        <!-- Bootstrap core CSS -->
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
            <hr>
            <div class="container">

                <div class="row">
                    <div class="col-md-6">
                        <img src="${pageContext.request.contextPath}/img/wreath.jpg" alt="Wreath" class="img-responsive">
                    </div>

                    <div class="col-md-6">
                        <h1>Holiday Direct: Direct-to-market Holiday Supplies</h1>
                        <p>Holiday Direct was founded in 1986 and has grown to be the number one holiday decoration supplier for stores in the World! If you'd like a quote, or to see the season's offerings, don't hesitate to contact us.</p>

                        <a class="btn btn-sm btn-info" style="margin-top: 2px; margin-bottom:2px;" href='${pageContext.request.contextPath}/contact' type="button">Contact Holiday Direct</a>
                    </div>

                </div>

            </div>
            <hr>
            <div class="container-fluid">
                <div class="row">
                    <h2>From Our Blog</h2>
                    <div class="col-lg-12 col-sm-12 col-md-12" style="border: 1px solid black">
                        <!--BLOG CONTENT--> 

                        <div>
                            <h3>${post.title}</h3>
                            <p>${post.content}</p>
                            <a class="btn btn-sm  btn-info pull-right" style="margin-top: 2px; margin-bottom:2px;" href="" type="button">Read More</a>
                        </div>

                    </div>
                    <!--END BLOG CONTENT--> 
                </div>
            </div>
        </div>
    </div>
    <p>
        <br />
        <br />
    </p>

    <!-- Placed at the end of the document so the pages load faster MUST BE PLACED BEFORE TINYMC-->
    <script src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/tinymce/tinymce.min.js" type="text/javascript"></script>

    <!--TINYMC-->
    <!--    <script>
            tinymce.init({
                selector: '#my_editor',
                plugins: ["image"],
                file_browser_callback: function (field_name, url, type, win) {
                    if (type == 'image')
                        $('#upload_form input').click();
                }
            });
        </script>-->
    <!--END TINYMC-->

    <script type="text/javascript">
        var contextRoot = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/nav.js"></script>




</body>
</html>

