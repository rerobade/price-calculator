package de.reroba.pricecalculator.ingredients;

import de.reroba.pricecalculator.shared.Price;
import de.reroba.pricecalculator.shared.Volume;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Ingredient {

    Long id;

    String name;

    Price price;

    Volume volume;

    IngredientDto toIngredientDto() {
        return new IngredientDto(
                id,
                name,
                price.getAmount(),
                price.getCurrency().getCurrencyCode(),
                volume.getQuantity(),
                volume.getUnit());
    }
}
