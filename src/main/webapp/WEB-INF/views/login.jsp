<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="validation"/>


<!DOCTYPE html>
<html lang=${language}>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Log in with your account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>
<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
               aria-expanded="false">
                <fmt:message key="login.language"/><span class="caret"></span></a>
            <ul class="dropdown-menu">
                <form id="languageRU" action="bla" method="get" style="display: none">
                    <input type="hidden" name="language" value="ru"/>
                    <input name="lang" type="text" value="ru"/>
                </form>
                <form id="languageEN" action="bla" method="get" style="display: none">
                    <input type="hidden" name="language" value="en"/>
                    <input name="lang" type="text" value="en"/>

                </form>
                <li>
                    <a href="" onclick="$('#languageRU').submit(); return false;">Русский</a>


                </li>
                <li>
                    <a href="" onclick="$('#languageEN').submit(); return false;">English</a>
                </li>
            </ul>
        </li>
    </ul>
</div>
<div class="container">

    <form method="POST" action="login" class="form-signin">
        <h2 class="form-heading"><fmt:message key="login.login"/></h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="username" type="text" class="form-control" placeholder=
            <fmt:message key="login.username"/>
                    autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder=<fmt:message
                    key="login.password"></fmt:message>>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                        key="login.button"/></button>
            <h4 class="text-center"><a href="${contextPath}/registration"><fmt:message key="create.lable"/></a></h4>
        </div>

    </form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>