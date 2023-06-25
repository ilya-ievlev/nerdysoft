package org.example.model.product;

import java.util.Objects;

public class RealProduct extends Product {
    private static final String SIZE_CAN_T_BE_LOWER_THAT_ZERO = "size can't be lower that zero";
    private static final String WEIGHT_CAN_T_BE_LOWER_THAT_ZERO = "weight can't be lower that zero";
    private final int size;
    private final int weight;

    public RealProduct(String name, double price, int size, int weight) {
        super(name, price);
        if (size <= 0) {
            throw new IllegalArgumentException(SIZE_CAN_T_BE_LOWER_THAT_ZERO);
        }
        if (weight <= 0) {
            throw new IllegalArgumentException(WEIGHT_CAN_T_BE_LOWER_THAT_ZERO);
        }
        this.size = size;
        this.weight = weight;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RealProduct that = (RealProduct) o;
        return size == that.size && weight == that.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size, weight);
    }
}
