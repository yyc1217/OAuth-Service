<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Permission Request</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,800italic,400,600,800"
          type="text/css">
    <link rel="stylesheet" href="resource/css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="resource/css/Login.css" type="text/css"/>

</head>

<body>

<div id="login-container">

    <div id="logo-back"></div>
    <div id="logo">
        <img src="resource/img/logo/logo-login.png" alt="logo"/>
    </div>

    <div id="login">

        <h3>Permission Request</h3>
        <h5>USER: ${it.portalID}</h5>
        <h5>CLIENT: <a href="${it.clientURL}">${it.clientName}</a></h5>
        <div class="form-group">
            <form action="redirect" method="GET">
                For following permissions
                <div class="permission-group">
                    <c:forEach items="${it.scope}" var="permission">
                        <div class="permission-item">${permission}</div>
                    </c:forEach>
                </div>
                <input type="hidden" name="state" value=${it.state}>
                <input type="submit" id="login-btn" class="btn btn-default" value="Agree"/>
            </form>
        </div>

    </div>

</div>


</body>
</html>