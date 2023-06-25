package org.example.model.product;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private static final String NAME_OF_PRODUCT_CAN_T_BE_NULL = "name of product can't be null";
    private static final String PRICE_CAN_T_BE_LOWER_THAT_ZERO = "price can't be lower that zero";
    protected String name;

    //    protected BigDecimal price; //this is the best practice to use BigDecimal instead of double when dealing with money
    protected double price;

    protected Product(String name, double price) {
        if (name == null) {
            throw new IllegalArgumentException(NAME_OF_PRODUCT_CAN_T_BE_NULL);
        }
        if (price <= 0) {
            throw new IllegalArgumentException(PRICE_CAN_T_BE_LOWER_THAT_ZERO);
        }
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
