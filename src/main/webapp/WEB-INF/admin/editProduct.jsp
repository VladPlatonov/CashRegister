
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
            <h2><fmt:message key="editProduct.top"/></h2>
        </div>


        <!-- Login Form -->
        <form name="editProduct" method="post" action="/products" >
            <input type="text" hidden name="code"  class="fadeIn second" value="${product.code}">
            <input type="text" name="name" class="fadeIn second"required placeholder="<fmt:message key="addProduct.productName"/>" value="${product.name}">
            <input type="text" name="description"class="fadeIn second" required placeholder="<fmt:message key="addProduct.description"/>" value="${product.description}">
            <input type="number" min ="0" name="cost"class="number" required placeholder="<fmt:message key="addProduct.cost"/>" value="${product.cost}" required>
            <input type="number" min ="0" name="quantity"class="number" required placeholder="<fmt:message key="addProduct.quantity"/>" value="${product.quantity}">
            <input type="text" hidden name="id" class="fadeIn second" value="${product.id}">
            <input hidden type="text" name="productAction" value="update!editProduct">
            <input type="submit" class="fadeIn fourth" value="<fmt:message key="btn.save"/>">
            <a class="btn btn-danger" role="button" href="/products"><fmt:message key="btn.cancel"/></a>
        </form>

    </div>
</div>
</body>
<t:colontitle/>
</html>

