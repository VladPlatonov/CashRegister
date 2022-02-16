<%@ page import="com.epam.finalproject.model.UserRole" %>
<%@include file="/WEB-INF/JSPF/header.jspf" %>
<c:set var="userRoles" value="<%=UserRole.values()%>"/>
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
            <a href="/admin" class="nav-link"><h2><fmt:message key="menu.back"/></h2></a>
            <h2><fmt:message key="admin.users"/></h2>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="users.name"/></th>
                    <th><fmt:message key="users.surname"/></th>
                    <th><fmt:message key="users.userRole"/></th>
                    <th class="text-center" width="25%"><fmt:message key="menu.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="userL">
                    <tr>
                        <td><c:out value="${userL.name}"/></td>
                        <td><c:out value="${userL.surname}"/></td>
                        <td>
                        <select  class="form-control" form ="form${userL.id}" name="UserRole" required>
                            <c:forEach items="${userRoles}" var="userRole">
                                <option <c:if test="${userRole == userL.userRole}">selected </c:if> value="${userRole}"> <fmt:message key="role.${userRole}"/></option>
                            </c:forEach>
                        </select>
                        </td>
                        <td align="center">
                            <form class="colform" id="form${userL.id}" id=""form${userL.id}"" method="post" action="/users">
                                <input hidden type="text" name = "userAction" value="<c:out value='${userL.id}' />!updateUser">
                                <div class="btnEdit">
                                    <button type="submit" class="btn btn-primary"><fmt:message key="btn.edit"/></button>
                                </div>
                            </form>
                            <form class="colform" id="form${userL.id}" method="post" action="/users">
                                <input hidden type="text" name = "userAction" value="<c:out value='${userL.id}' />!deleteUser">
                                <div class="btnDel">
                                    <button type="submit" class="btn btn-danger"><fmt:message key="btn.del"/></button>
                                </div>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                <table border="1" cellpadding="5" cellspacing="5">
                    <tr>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <td>${i}</td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="/users?page=${i}">${i}</a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </table>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<t:colontitle/>
</html>