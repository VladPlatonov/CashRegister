
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
            <h2><fmt:message key="login.top"/></h2>
        </div>

        <!-- Login Form -->
        <form method="post" action="/login">
            <c:if test="${isValid=='null'}"> <h6 style="color: red"> <fmt:message key="error.null"/></h6> </c:if>
            <c:if test="${NotFound}"> <h6 style="color: red"><fmt:message key="error.notFound"/> </h6> </c:if>
            <c:if test="${WrongPass}"> <h6 style="color: red"> <fmt:message key="error.password"/> </h6> </c:if>
            <input type="text" id="login" class="fadeIn second" required name="login" placeholder="<fmt:message key="login.log"/>">
            <input type="password" id="password" class="fadeIn third" required name="password" placeholder="<fmt:message key="login.pass"/>">
            <input type="submit" class="fadeIn fourth" value="<fmt:message key="menu.login"/>">
        </form>


    </div>
</div>
</body>
<t:colontitle/>
</html>
