package com.epam.finalproject.model;

public class Product {

    private Integer id;
    private String code;
    private String name;
    private String description;
    private Integer cost;
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Product) return false;

        Product product = (Product) o;
        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        return code.equals(product.code);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Product" +
                "code='" + code + '\'' +
                "\n name='" + name + '\'' +
                "\n description='" + description + '\'' +
                "\n cost=" + cost +
                "\n quantity=" + quantity +
                '\n';
    }
}
