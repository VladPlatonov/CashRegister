
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
        <a href="/admin" class="nav-link"><h2><fmt:message key="menu.back"/></h2></a>
        <h2><fmt:message key="admin.products"/></h2>
        <form class="colform" method="post" action="/products">
            <input hidden type="text" name = "productAction" value="add!addProduct">
            <div class="btnEdit">
                <button type="submit" class="btn btn-primary"><fmt:message key="btn.addProduct"/></button>
            </div>
        </form>
    </div>
</div>
<div class="container">
    <div class="row">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th><fmt:message key="products.code"/></th>
                <th><fmt:message key="products.name"/></th>
                <th><fmt:message key="products.description"/></th>
                <th><fmt:message key="products.cost"/></th>
                <th><fmt:message key="products.quantity"/></th>
                <th class="text-center" width="25%"><fmt:message key="menu.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${products}" var="product">
                <tr>
                    <td><c:out value="${product.code}"/></td>
                    <td><c:out value="${product.name}"/></td>
                    <td><c:out value="${product.description}"/></td>
                    <td><c:out value="${product.cost}"/></td>
                    <td><c:out value="${product.quantity}"/></td>
                    <td align="center">
                        <form class="colform" id="form${product.id}" method="post" action="/products">
                            <input hidden type="text" name = "productAction" value="<c:out value='${product.id}' />!editProduct">
                            <div class="btnEdit">
                                <button type="submit" class="btn btn-primary"><fmt:message key="btn.edit"/></button>
                            </div>
                        </form>
                        <form class="colform" id="form${product.id}" method="post" action="/products">
                            <input hidden type="text" name = "productAction" value="<c:out value='${product.id}' />!deleteProduct">
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
                                <td><a href="/products?page=${i}">${i}</a></td>
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