package org.example.model;

import org.example.model.product.Product;

import java.util.List;

public class Order {
    private static final String PRODUCT_LIST_CAN_T_BE_NULL = "product list can't be null";
    private static final String USER_CAN_T_BE_NULL = "user can't be null";
    private final User user;
    private final List<Product> productList;

    private Order(User user, List<Product> productList) {
        this.user = user;
        this.productList = productList;
    }

    public static Order createOrder(User user, List<Product> productList) {
        if (user == null) {
            throw new IllegalArgumentException(USER_CAN_T_BE_NULL);
        }
        if (productList == null) {
            throw new IllegalArgumentException(PRODUCT_LIST_CAN_T_BE_NULL);
        }
        return new Order(user, productList);
    }

    public User getUser() {
        return user;
    }

    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", productList=" + productList +
                '}';
    }
}
