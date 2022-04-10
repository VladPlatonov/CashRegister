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
            <h2><fmt:message key="admin.editInvoice"/></h2>
            <h2 class="text-center"><fmt:message key="payments.total"/>: ${total} </h2>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="payments.invoiceCode"/></th>
                    <th><fmt:message key="payments.productCode"/></th>
                    <th><fmt:message key="products.quantity"/></th>
                    <th><fmt:message key="products.cost"/></th>
                    <c:if test="${status == 'CREATED'}">
                        <th class="text-center" width="15%"><fmt:message key="menu.action"/></th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td><c:out value="${order.invoiceCode}"/></td>
                        <td><c:out value="${order.productCode}"/></td>
                        <td>
                            <c:if test="${status == 'CREATED'}">
                                <c:if test="${isValidSet==order.orderId}"> <h6 style="color: red"><fmt:message key="error.quantity"/></h6></c:if>
                                <input class="form-control form-control-sm" size="1" min="0"  type="number" form = "form${order.orderId}" name = "setQuantity" value="<c:out value="${order.quantity}"/>">
                            </c:if>
                            <c:if test="${status == 'FINISHED' || status == 'CANCELLED'}">
                                <c:out value="${order.quantity}"/>
                            </c:if>
                        </td>
                        <td><c:out value="${order.orderValue}"/></td>
                        <c:if test="${status == 'CREATED'}">
                            <td align="center">
                                <form class="colform" id="form${order.orderId}" method="post" action="/editInvoice">
                                    <input hidden type="text" name = "orderAction" value="<c:out value='${order.orderId}' />!updateOrder">
                                    <div class="btnUpdate">
                                        <button type="submit" class="btn btn-primary btn-sm"><fmt:message key="btn.update"/></button>
                                    </div>
                                </form>
                                <c:if test="${user_role == 'SENIOR_CASHIER' || user_role == 'ADMIN' }">
                                <form class="colform"  method="post" action="/editInvoice">
                                    <input hidden type="text" name = "orderAction" value="<c:out value='${order.orderId}' />!deleteOrder">
                                    <div class="btnDel">
                                        <button type="submit" class="btn btn-danger btn-sm"><fmt:message key="btn.del"/></button>
                                    </div>
                                </form>
                                </c:if>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
                <c:if test="${status == 'CREATED'}">
                <thead>
                <tr>
                    <th colspan="2"><fmt:message key="payments.productCode"/></th>
                    <th colspan="2"><fmt:message key="products.quantity"/></th>
                    <th class="text-center" width="15%"><fmt:message key="menu.action"/></th>
                </tr>
                </thead>
                <tbody>
                <form id="add" method="post" action="/editInvoice" >
                    <tr>
                        <td colspan="2">
                            <select  class="form-control"  name="productCode" required>
                                <c:forEach items="${products}" var="product">
                                    <option><c:out value="${product.code}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                        <td colspan="2">
                            <c:if test="${isNull==true}"> <h6 style="color: red"> <fmt:message key="error.null"/></h6> </c:if>
                            <c:if test="${isValid==true}"> <h6 style="color: red"><fmt:message key="error.quantity"/></h6></c:if>
                            <input class="form-control form-control-sm" size="1" type="number" min="0" max="1000000" name="addQuantity"  required /></td>
                        <td align="center" >
                            <input class="input" type="text"  hidden name="orderAction" value="<c:out value='${invoiceId}'/>!addOrder"/>
                            <button type="submit"  form = "add" class="btn btn-success btn-sm" ><fmt:message key="btn.addProduct"/></button>
                        </td>
                    </tr>
                </form>
                </c:if>
                <table border="1" cellpadding="5" cellspacing="5">
                    <tr>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <td>${i}</td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="/editInvoice?id=${invoiceId}&page=${i}">${i}</a></td>
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