package de.reroba.pricecalculator.ingredients;

public class IngredientNotFoundException extends Exception {

    public IngredientNotFoundException(Long id) {
        super(String.format("Cannot find ingredient with id [%s]", id));
    }
}
