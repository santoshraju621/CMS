
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
        <title>Login</title>
    </head>
    <body>

        <jsp:include page="/jsp/navbar.jsp" />

        <h1>Sign in to the app</h1>

        <c:if test="${param.login_error == 1}">
            <h3>Wrong id or password</h3>
        </c:if>

        <form method="post" action="login/authenticate">
            Username: <input type="text" name="username">
            <br />
            Password: <input type="password" name="password">
            <input type="submit" value="Sign In" />
        </form>

        <script src="${pageContext.request.contextPath}/js/jquery-1.12.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            var contextRoot = "${pageContext.request.contextPath}";
        </script>
        <script src="${pageContext.request.contextPath}/js/nav.js"></script>
    </body>
</html>
