<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .container {
            height: 100%;
            width: 100%;
            display: flex;
            position: fixed;
            align-items: center;
            justify-content: center;
        }
    </style>

</head>
<body>

<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>Thank you for registration, <a href="/logout" style="font-weight: bold; color: blue">${pageContext.request.userPrincipal.name}</a>!</h2>
    </c:if>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
