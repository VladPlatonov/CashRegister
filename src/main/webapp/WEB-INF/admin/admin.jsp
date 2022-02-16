<%@include file="/WEB-INF/JSPF/header.jspf" %>
<html>
<head>
    <%@include file="/WEB-INF/JSPF/head.jspf" %>
</head>
<body>
<%@include file="/WEB-INF/JSPF/navbar.jspf" %>
<div class="fadeIn first">
    <div class="container-fluid" style="font-size: 20px">
        <div class="container">
            <h1><fmt:message key="profile.hello"/>, ${user.name} ${user.surname} </h1>
            <h2> <fmt:message key="role.Name"/>: <fmt:message key="role.${user_role}"/></h2>
            <c:if test="${user_role == 'ADMIN'}">
            <a href="/users" class="nav-link"><h2><fmt:message key="admin.users"/></h2></a>
            </c:if>
            <c:if test="${user_role == 'ADMIN' || user_role == 'MERCHANT'}">
            <a href="/products" class="nav-link"><h2><fmt:message key="admin.products"/></h2></a>
            </c:if>
            <c:if test="${user_role == 'ADMIN' || user_role == 'SENIOR_CASHIER' || user_role == 'CASHIER'}">
            <a href="/invoices" class="nav-link"><h2><fmt:message key="admin.invoice"/></h2></a>
            </c:if>
        </div>
    </div>
</body>
<t:colontitle/>
</html>