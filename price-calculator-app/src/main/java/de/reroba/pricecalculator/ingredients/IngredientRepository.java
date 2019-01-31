package de.reroba.pricecalculator.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientDto, Long> {

    List<IngredientDto> findByNameStartsWithIgnoreCase(String name);

}
