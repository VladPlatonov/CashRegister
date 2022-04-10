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
            <h2><fmt:message key="addProduct.tp"/></h2>
        </div>


        <!-- Login Form -->
        <form name="addProduct" method="post" action="/products" >
             <c:if test="${isValid}"> <h6 style="color: red"> <fmt:message key="error.code"/></h6> </c:if>
            <c:if test="${isValid=='null'}"> <h6 style="color: red"> <fmt:message key="error.null"/></h6> </c:if>
            <input type="text" name="code"  class="fadeIn second" required placeholder="<fmt:message key="addProduct.code"/>">
            <input type="text" name="name" class="fadeIn second"required placeholder="<fmt:message key="addProduct.productName"/>"
                   <c:if test="${isValid}">value="${product.name}" </c:if>>
            <input type="text" name="description"class="fadeIn second" required placeholder="<fmt:message key="addProduct.description"/>"
                   <c:if test="${isValid}">value="${product.description}" </c:if>>
            <input type="number" class="number" min="0" name="cost"class="fadeIn second" required placeholder=<fmt:message key="addProduct.cost"/>
                    <c:if test="${isValid}">value="${product.cost}" </c:if>>
            <input type="number" class="number" min="0" name="quantity"class="fadeIn second" required placeholder="<fmt:message key="addProduct.quantity"/>"
                   <c:if test="${isValid}">value="${product.quantity}" </c:if>>
            <input hidden type="text" name="productAction" value="n!new">
            <input type="submit" class="fadeIn fourth" value="<fmt:message key="btn.addProduct"/>">
            <a class="btn btn-danger" role="button" href="/products"><fmt:message key="btn.cancel"/></a>
        </form>

    </div>
</div>
</body>
<t:colontitle/>
</html>
