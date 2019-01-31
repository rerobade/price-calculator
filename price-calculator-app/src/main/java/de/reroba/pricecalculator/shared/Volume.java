package de.reroba.pricecalculator.shared;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Volume {

    public static final Price ZERO = Price.of(BigDecimal.ZERO, null);

    private final BigDecimal quantity;

    private final String unit;

    private Volume(BigDecimal quantity, String unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public static Volume of(BigDecimal quantity, String unit) {
        return new Volume(quantity, unit);
    }
}
