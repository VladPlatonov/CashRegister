
<%@include file="/WEB-INF/JSPF/header.jspf" %>
<html>
<head>
    <%@include file="/WEB-INF/JSPF/head.jspf" %>
    <style>
        .colform {
            float:left;
            width: 32%;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/JSPF/navbar.jspf" %>
<div class="fadeIn first">
    <div class="container-fluid" style="font-size: 20px">
        <div class="container">
            <h1><fmt:message key="profile.hello"/>, ${user.name} ${user.surname} </h1>
            <a href="/admin" class="nav-link"><h2><fmt:message key="menu.back"/></h2></a>
            <h2><fmt:message key="admin.invoice"/></h2>
            <a href="/createInvoice" role="button" class="btn btn-success"><fmt:message key="btn.create"/></a>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="products.code"/></th>
                    <th><fmt:message key="invoices.date"/></th>
                    <th><fmt:message key="invoices.note"/></th>
                    <th><fmt:message key="invoices.status"/></th>
                    <th class="text-center" width="25%" ><fmt:message key="menu.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${invoices}" var="invoice">
                    <tr>
                        <td><c:out value="${invoice.invoiceCode}"/></td>
                        <td><c:out value="${invoice.date}"/></td>
                        <td><c:out value="${invoice.invoiceNotes}"/></td>
                        <td><fmt:message key="status.${invoice.status}"/></td>
                        <td align="center">
                            <c:if test="${invoice.status == 'CREATED'}">
                                <form class="colform"  method="post" action="/invoices">
                                    <input hidden type="text" name = "invoiceAction" value="<c:out value='${invoice.invoiceId}' />!finishInvoice">
                                    <div class="btnDel">
                                        <button type="submit" class="btn btn-success btn-sm"><fmt:message key="btn.finish"/></button>
                                    </div>
                                </form>
                                <c:if test="${user_role == 'SENIOR_CASHIER' || user_role == 'ADMIN' }">
                                <form class="colform"  method="post" action="/invoices">
                                    <input hidden type="text" name = "invoiceAction" value="<c:out value='${invoice.invoiceId}' />!cancelInvoice">
                                    <div class="btnDel">
                                        <button type="submit" class="btn btn-danger btn-sm"><fmt:message key="btn.cancel"/></button>
                                    </div>
                                </form>
                                </c:if>
                            </c:if>
                            <form class="colform"  method="get" action="/editInvoice">
                                <input hidden type="text" name = "id" value="<c:out value='${invoice.invoiceId}' />">
                                <div class="btnDel">
                                    <button type="submit" class="btn btn-primary btn-sm"><fmt:message key="btn.details"/></button>
                                </div>
                            </form>
                            <c:if test="${invoice.status == 'FINISHED' || invoice.status == 'CANCELLED' && (user_role == 'ADMIN' || user_role == 'SENIOR_CASHIER')}">
                                <form class="colform"  method="post" action="/invoices">
                                    <input hidden type="text" name = "invoiceAction" value="<c:out value='${invoice.invoiceId}' />!deleteInvoice">
                                    <div class="btnDel">
                                        <button type="submit" class="btn btn-danger btn-sm"><fmt:message key="btn.del"/></button>
                                    </div>
                                </form>
                            </c:if>
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
                                    <td><a href="/invoices?page=${i}">${i}</a></td>
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