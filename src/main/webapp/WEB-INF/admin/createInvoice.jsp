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
            <a href="/invoices" class="nav-link"><h2><fmt:message key="menu.back"/></h2></a>
            <h2><fmt:message key="admin.createInvoice"/></h2>
            <c:if test="${isValid==true}"> <h6 style="color: red"><fmt:message key="error.quantity"/></h6></c:if>
            <button type="submit"  form = "checked" class="btn btn-success"><fmt:message key="btn.create"/></button>
        </div>
    </div>
    <form id = "checked" method="post" action="/createInvoice">
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
                    <th class="text-center"><fmt:message key="payments.setQuantity"/></th>
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
                        <td><input class="form-check-input" hidden name = "setQuantity" value="${product.code}" >
                            <input class="form-control form-control-sm" size="1" type="number" min = "0" max="${product.quantity}  " name="setQuantity" value="0"/></td>
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
                                    <td><a href="/createInvoice?page=${i}">${i}</a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tr>
                </table>
                </tbody>
            </table>
        </div>
    </div>
    </form>
</div>
</body>
<t:colontitle/>