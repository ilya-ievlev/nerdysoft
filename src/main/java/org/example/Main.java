package org.example;

import org.example.factory.ProductFactory;
import org.example.model.Order;
import org.example.model.User;
import org.example.model.product.Product;
import org.example.model.product.RealProduct;
import org.example.util.SingletonVirtualProductCodeManager;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    private static final String LIST_OF_PRODUCTS_CAN_T_BE_NULL = "List of products can't be null";
    private static final String LIST_OF_ORDERS_CAN_T_BE_NULL = "List of orders can't be null";
    private static final String PRODUCT_IN_CALCULATE_AVERAGE_AGE_CANT_BE_NULL = "Product in calculateAverageAge cant be null";
    private static final String TOTAL_WEIGHT_OF_ORDER_IS_BIGGER_THAT_INTEGER_LIMIT = "total weight of order is bigger that integer limit";

    public static void main(String[] args) {

        //TODO Create User class with method createUser
        // User class fields: name, age;
        // Notice that we can only create user with createUser method without using constructor or builder
        User user1 = User.createUser("Alice", 32);
        User user2 = User.createUser("Bob", 19);
        User user3 = User.createUser("Charlie", 20);
        User user4 = User.createUser("John", 27);

        //TODO Create factory that can create a product for a specific type: Real or Virtual
        // Product class fields: name, price
        // Product Real class additional fields: size, weight
        // Product Virtual class additional fields: code, expiration date

        Product realProduct1 = ProductFactory.createRealProduct("Product A", 20.50, 10, 25);
        Product realProduct2 = ProductFactory.createRealProduct("Product B", 50, 6, 17);

        Product virtualProduct1 = ProductFactory.createVirtualProduct("Product C", 100, "xxx", LocalDate.of(2023, 5, 12));
        Product virtualProduct2 = ProductFactory.createVirtualProduct("Product D", 81.25, "yyy", LocalDate.of(2024, 6, 20));


        //TODO Create Order class with method createOrder
        // Order class fields: User, List<Price>
        // Notice that we can only create order with createOrder method without using constructor or builder
        List<Order> orders = new ArrayList<>() {{
            add(Order.createOrder(user1, List.of(realProduct1, virtualProduct1, virtualProduct2)));
            add(Order.createOrder(user2, List.of(realProduct1, realProduct2)));
            add(Order.createOrder(user3, List.of(realProduct1, virtualProduct2)));
            add(Order.createOrder(user4, List.of(virtualProduct1, virtualProduct2, realProduct1, realProduct2)));
        }};

        //TODO 1). Create singleton class which will check the code is used already or not
        // Singleton class should have the possibility to mark code as used and check if code used
        // Example:
        // singletonClass.useCode("xxx")
        // boolean isCodeUsed = virtualProductCodeManager.isCodeUsed("xxx") --> true;
        // boolean isCodeUsed = virtualProductCodeManager.isCodeUsed("yyy") --> false;

        System.out.println("1. Create singleton class VirtualProductCodeManager \n");
        SingletonVirtualProductCodeManager virtualProductCodeManager = SingletonVirtualProductCodeManager.getInstance();
        virtualProductCodeManager.useCode("code123");
        boolean isCodeUsed1 = virtualProductCodeManager.isCodeUsed("code123");
        boolean isCodeUsed2 = virtualProductCodeManager.isCodeUsed("code311");
        System.out.println("Is code1 used: " + isCodeUsed1 + "\n");
        System.out.println("Is code2 used: " + isCodeUsed2 + "\n");

        //TODO 2). Create a functionality to get the most expensive ordered product
        Product mostExpensive = getMostExpensiveProduct(orders);
        System.out.println("2. Most expensive product: " + mostExpensive + "\n");

        //TODO 3). Create a functionality to get the most popular product(product bought by most users) among users
        Product mostPopular = getMostPopularProduct(orders);
        System.out.println("3. Most popular product: " + mostPopular + "\n");

        //TODO 4). Create a functionality to get average age of users who bought realProduct2
        double averageAge = calculateAverageAge(realProduct2, orders);
        System.out.println("4. Average age is: " + averageAge + "\n");

        //TODO 5). Create a functionality to return map with products as keys and a list of users
        // who ordered each product as values
        Map<Product, List<User>> productUserMap = getProductUserMap(orders);
        System.out.println("5. Map with products as keys and list of users as value \n");
        productUserMap.forEach((key, value) -> System.out.println("key: " + key + " " + "value: " + value + "\n"));

        //TODO 6). Create a functionality to sort/group entities:
        // a) Sort Products by price
        // b) Sort Orders by user age in descending order
        List<Product> productsByPrice = sortProductsByPrice(List.of(realProduct1, realProduct2, virtualProduct1, virtualProduct2));
        System.out.println("6. a) List of products sorted by price: " + productsByPrice + "\n");
        List<Order> ordersByUserAgeDesc = sortOrdersByUserAgeDesc(orders);
        System.out.println("6. b) List of orders sorted by user agge in descending order: " + ordersByUserAgeDesc + "\n");

        //TODO 7). Calculate the total weight of each order
        Map<Order, Integer> result = calculateWeightOfEachOrder(orders);
        System.out.println("7. Calculate the total weight of each order \n");
        result.forEach((key, value) -> System.out.println("order: " + key + " " + "total weight: " + value + "\n"));
    }

    private static Product getMostExpensiveProduct(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException(LIST_OF_ORDERS_CAN_T_BE_NULL);
        }
        return orders.stream()
                .flatMap(order -> order.getProductList().stream())
                .max(Comparator.comparing(Product::getPrice))
                .orElse(null);
    }

    //not the fastest way to do this operation, but in case you need to get result
    // of how many UNIQUE users bought the product you can do this. Just use the following code below
//    private static Product getMostPopularProduct(List<Order> orders) {
//        if (orders == null) {
//            throw new IllegalArgumentException(LIST_OF_ORDERS_CAN_T_BE_NULL);
//        }
//        Map<Product, List<User>> productUserMap = getProductUserMap(orders);
//        int numberOfUniqueProductPurchases = 0;
//        Product mostPopularProduct = null;
//        for (Map.Entry<Product, List<User>> entry : productUserMap.entrySet()) {
//
//            Set<User> uniqueUserSet = new HashSet<>(entry.getValue());
//            int sizeOfUniqueUserSet = uniqueUserSet.size();
//            if (numberOfUniqueProductPurchases < sizeOfUniqueUserSet) {
//                numberOfUniqueProductPurchases = sizeOfUniqueUserSet;
//                mostPopularProduct = entry.getKey();
//            }
//        }
//
//        return mostPopularProduct;
//    }

    private static Product getMostPopularProduct(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException(LIST_OF_ORDERS_CAN_T_BE_NULL);
        }

        Map<Product, List<User>> productUserMap = getProductUserMap(orders);

        return productUserMap.entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getValue().size()))
                .map(Map.Entry::getKey)
                .orElse(null);  // This returns null if there is no popular product
    }


    private static double calculateAverageAge(Product product, List<Order> orders) {
        if (product == null) {
            throw new IllegalArgumentException(PRODUCT_IN_CALCULATE_AVERAGE_AGE_CANT_BE_NULL);
        }
        if (orders == null) {
            throw new IllegalArgumentException(LIST_OF_ORDERS_CAN_T_BE_NULL);
        }
        return orders.stream()
                .filter(order -> order.getProductList().contains(product))
                .mapToInt(order -> order.getUser().getAge())
                .average()
                .orElse(0);
    }

    private static Map<Product, List<User>> getProductUserMap(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException(LIST_OF_ORDERS_CAN_T_BE_NULL);
        }
        return orders.stream()
                .flatMap(order -> order.getProductList().stream()
                        .map(product -> new AbstractMap.SimpleEntry<>(product, order.getUser())))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    private static List<Product> sortProductsByPrice(List<Product> products) {
        if (products == null) {
            throw new IllegalArgumentException(LIST_OF_PRODUCTS_CAN_T_BE_NULL);
        }
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    private static List<Order> sortOrdersByUserAgeDesc(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException(LIST_OF_ORDERS_CAN_T_BE_NULL);
        }
        return orders.stream()
                .sorted(Comparator.comparing((Order o) -> o.getUser().getAge()).reversed())
                .collect(Collectors.toList());
    }

    private static Map<Order, Integer> calculateWeightOfEachOrder(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException(LIST_OF_ORDERS_CAN_T_BE_NULL);
        }
        return orders.stream().collect(Collectors.toMap(Function.identity(), order -> {
            long weight = order.getProductList().stream()
                    .filter(product -> product instanceof RealProduct)
                    .mapToLong(product -> ((RealProduct) product).getWeight())
                    .reduce(0L, Long::sum);

            if (weight > Integer.MAX_VALUE) {
                //unfortunately I have to do this because I can only put integers into Map (that was your task and I can't change method signature)
                //in case you try to sell the international space station and the death star at the same time (obvious overweight)
                throw new ArithmeticException(TOTAL_WEIGHT_OF_ORDER_IS_BIGGER_THAT_INTEGER_LIMIT);
            }
            return (int) weight;
        }));
    }
}