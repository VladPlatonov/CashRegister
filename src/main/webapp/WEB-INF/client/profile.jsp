
<%@include file="/WEB-INF/JSPF/header.jspf" %>
<html>
<head>
    <%@include file="/WEB-INF/JSPF/head.jspf" %>
</head>
<body>
<%@include file="/WEB-INF/JSPF/navbar.jspf" %>
<div class="container-fluid" style="font-size: 20px">
    <div class="container">
        <h1><fmt:message key="profile.hello"/>, ${user.name} ${user.surname}</h1>
    </div>
</div>

</body>
<t:colontitle/>
</html>
