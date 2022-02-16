package com.epam.finalproject.model;

public class Order {

    private Integer orderId;
    private Long invoiceCode;
    private String productCode;
    private Double quantity;
    private Double orderValue;

    public Integer getOrderId() {
        return orderId;
    }

    public Long getInvoiceCode() {
        return invoiceCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getOrderValue() {
        return orderValue;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setInvoiceCode(Long invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Order) return false;

        Order payment = (Order) o;

        if (orderId != null ? !orderId.equals(payment.orderId) : payment.orderId != null) return false;
        return invoiceCode.equals(payment.invoiceCode);
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + invoiceCode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Payment" +
                "\n orderCode=" + invoiceCode +
                "\n productCode='" + productCode + '\'' +
                "\n quantity=" + quantity +
                "\n paymentValue=" + orderValue +
                '\n';
    }
}