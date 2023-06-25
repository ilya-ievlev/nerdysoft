package org.example.model.product;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Objects;

public class VirtualProduct extends Product {
    private static final String CODE_CAN_T_BE_NULL = "code can't be null";
    private static final String THE_EXPIRATION_DATE_CANNOT_BE_EARLIER_THAN_THE_MINIMAL_TIME = "the expiration date cannot be earlier than the minimal time";
    private static final String DATE_CAN_T_BE_NULL = "date can't be null";
    private final String code;
    private final LocalDate expirationDate;


    public VirtualProduct(String name, double price, String code, LocalDate expirationDate) {
        super(name, price);
        ChronoLocalDate minimalExpirationDate = LocalDate.from(ZonedDateTime.of(2000, 12, 1, 12, 0, 0, 0, ZoneId.of("Z")));
        if (code == null) {
            throw new IllegalArgumentException(CODE_CAN_T_BE_NULL);
        }
        if (expirationDate == null) {
            throw new IllegalArgumentException(DATE_CAN_T_BE_NULL);
        }
        if (expirationDate.isBefore(minimalExpirationDate)) {
            throw new IllegalArgumentException(THE_EXPIRATION_DATE_CANNOT_BE_EARLIER_THAN_THE_MINIMAL_TIME);
        }
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VirtualProduct that = (VirtualProduct) o;
        return Objects.equals(code, that.code) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, expirationDate);
    }
}
