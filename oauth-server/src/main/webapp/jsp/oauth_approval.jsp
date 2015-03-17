<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <title>OAuth Approval</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,800italic,400,600,800" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resource/css/bootstrap.min.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="/resource/css/Login.css"/>" type="text/css"/>
</head>

<body>

<div id="login-container">

    <div id="logo-back"></div>
    <div id="logo">
        <img src="<c:url value="/resource/img/logo/logo-login.png"/>" alt="logo"/>
    </div>

    <div id="login">

        <h3>Permission Request</h3>
        <h5>USER: ${user_id}</h5>
        <h5>CLIENT: <a href="${client.url}">${client.name}</a></h5>
        <div class="form-group">
            For following permissions
            <div class="permission-group">
                <c:forEach items="${scope}" var="permission">
                    <div class="permission-item">${permission.name}</div>
                </c:forEach>
            </div>
            <form:form action="${confirm_page}" method="POST">
                <input name='approval' value='true' type='hidden'/>
                <input type="submit" id="accept-btn" class="btn btn-default" value="Accept"/>
            </form:form>
            <form:form action="${confirm_page}" method="POST">
                <input name='approval' value='false' type='hidden'/>
                <input type="submit" id="decline-btn" class="btn btn-default" value="Decline"/>
            </form:form>
        </div>

    </div>

</div>


</body>
</html>