<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Athorization Error</title>
</head>
<body>
    <div>
        <c:forEach items="${errors}" var="error">
            <div>${error.objectName} : ${error.defaultMessage}</div><br/>
        </c:forEach>
    </div>
</body>
</html>
