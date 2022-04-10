package com.epam.finalproject.model;


import java.time.LocalDateTime;

public class Invoice {
    private Integer invoiceId;
    private Long invoiceCode;
    private Integer userId;
    private InvoiceStatus status;
    private LocalDateTime date;
    private String invoiceNotes;


    public Invoice() {

    }

    /** Getters */

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public Long getInvoiceCode() {
        return invoiceCode;
    }

    public Integer getUserId() {
        return userId;
    }


    public InvoiceStatus getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getInvoiceNotes() {
        return invoiceNotes;
    }




    /** Setters */

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setInvoiceCode(Long invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setInvoiceNotes(String invoiceNotes) {
        this.invoiceNotes = invoiceNotes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        Invoice invoice = (Invoice) o;

        if (invoiceId != null ? !invoiceId.equals(invoice.invoiceId) : invoice.invoiceId != null) return false;
        return invoiceCode.equals(invoice.invoiceCode);
    }

    @Override
    public int hashCode() {
        int result = invoiceId != null ? invoiceId.hashCode() : 0;
        result = 31 * result + invoiceCode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Invoice:" +
                "\ninvoiceCode=" + invoiceCode +
                "\n userName='" + userId + '\'' +
                "\n status=" + status +
                "\n date=" + date +
                "\n invoiceNotes='" + invoiceNotes + '\'' +
                '\n';
    }
}
