
<%@include file="/WEB-INF/JSPF/header.jspf" %>
<html>
<head>
<%@include file="/WEB-INF/JSPF/head.jspf" %>
</head>
<body>
<%@include file="/WEB-INF/JSPF/navbar.jspf" %>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Tabs Titles -->
        <div class="fadeIn first">
            <h2><fmt:message key="registration.top"/></h2>
        </div>


        <!-- Login Form -->
        <form name="registration" method="post" action="/registration" >
            <c:if test="${isValid=='null'}"> <h6 style="color: red"> <fmt:message key="error.null"/></h6> </c:if>
            <c:if test="${WrongLogin}"> <h6 style="color: red"> <fmt:message key="error.login"/> </h6> </c:if>
            <input type="text" name="login"  class="fadeIn second" required placeholder="<fmt:message key="login.log"/>">
            <input type="password" id="password" class="fadeIn third" required name="password" placeholder="<fmt:message key="login.pass"/>">
            <input type="text" name="name" class="fadeIn second"required placeholder="<fmt:message key="registration.name"/>">
            <input type="text" name="surname"class="fadeIn second" required placeholder="<fmt:message key="registration.surname"/>">
            <input type="submit" class="fadeIn fourth" value="<fmt:message key="menu.signup"/>">
        </form>

    </div>
</div>
</body>
<t:colontitle/>
</html>
