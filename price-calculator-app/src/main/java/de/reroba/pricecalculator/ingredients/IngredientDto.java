package de.reroba.pricecalculator.ingredients;

import de.reroba.pricecalculator.shared.Price;
import de.reroba.pricecalculator.shared.Volume;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String currency;

    @NotNull
    private BigDecimal volume;

    private String unit;
/*
    String color;

    String type; // perlen, schnur, farbe, papier

    String size; // masse

    String material; // Material - Holz, Glas, Glasschliff, Swarovski, ...

    String EAN;
*/
    Ingredient toIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName(name);
        ingredient.setPrice(Price.of(price, Currency.getInstance(currency)));
        ingredient.setVolume(Volume.of(volume, unit));
        return ingredient;
    }
}
