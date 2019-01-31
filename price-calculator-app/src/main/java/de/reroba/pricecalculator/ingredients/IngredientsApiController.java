package de.reroba.pricecalculator.ingredients;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "ingredients/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientsApiController {

    private final ModelMapper modelMapper;

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientsApiController(ModelMapper modelMapper, IngredientRepository ingredientRepository) {
        this.modelMapper = modelMapper;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    @ResponseBody
    public List<IngredientDto> getIngredients(
            @RequestParam(name = "name", required = false) String name
    ) {
        final List<IngredientDto> ingredients;
        if (StringUtils.isEmpty(name)) {
            ingredients = ingredientRepository.findAll();
        } else {
            ingredients = ingredientRepository.findByNameStartsWithIgnoreCase(name);
        }

        return new ArrayList<>(ingredients);
    }

    @GetMapping(path = "{ingredientId}")
    @ResponseBody
    public IngredientDto getIngredient(
            @PathVariable(name = "ingredientId") Long ingredientId
    ) {
        try {
            return ingredientRepository.getOne(ingredientId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Not Found", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public IngredientDto createIngredient(
            @RequestBody IngredientDto ingredientDto
    ) {
        Ingredient ingredient = ingredientDto.toIngredient();
        ingredient.setId(null);
        return ingredientRepository.save(ingredient.toIngredientDto());
    }
/*
    @PutMapping
    public void updateIngredients(
            @RequestBody List<IngredientDto> ingredientDtos
    ) {
        try {
            if (!CollectionUtils.isEmpty(ingredientDtos)) {
                for(IngredientDto ingredientDto: ingredientDtos) {
                    Ingredient ingredient = convertToEntity(ingredientDto);
                    ingredientService.updateIngredient(ingredient);
                }
            }
        } catch (IngredientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Provide correct Ingredient Ids", e);
        }
    }
*/
    @PutMapping(path="{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    public IngredientDto updateIngredient(
            @PathVariable(name = "ingredientId") Long ingredientId,
            @RequestBody IngredientDto ingredientDto
    ) {
        try {
            Ingredient ingredient = ingredientDto.toIngredient();
            Ingredient currentIngredient = ingredientRepository.getOne(ingredientId).toIngredient();
            currentIngredient.setName(ingredient.getName());
            currentIngredient.setPrice(ingredient.getPrice());
            currentIngredient.setVolume(ingredient.getVolume());
            return ingredientRepository.save(currentIngredient.toIngredientDto());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Ingredient Id", e);
        }
    }

    @DeleteMapping(path = "{ingredientId}")
    public void deleteIngredient(
            @PathVariable(name = "ingredientId") Long ingredientId
    ) {
        if(ingredientRepository.existsById(ingredientId)) {
            ingredientRepository.deleteById(ingredientId);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Ingredient Id");
        }
    }
/*
    @DeleteMapping
    public void deleteIngredients() {
        repo.deleteAll();
    }*/

    private IngredientDto convertToDto(Ingredient ingredient) {
        IngredientDto ingredientDto = modelMapper.map(ingredient, IngredientDto.class);
        /*ingredientDto.setSubmissionDate(ingredient.getSubmissionDate(),
                userService.getCurrentUser().getPreference().getTimezone());*/
        return ingredientDto;
    }

    private Ingredient convertToEntity(IngredientDto ingredientDto) {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
        return ingredient;
    }
/*
    private Ingredient convertToEntity(IngredientDto ingredientDto) throws ParseException, IngredientNotFoundException {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
        ingredient.setSubmissionDate(ingredientDto.getSubmissionDateConverted(
                userService.getCurrentUser().getPreference().getTimezone()));

        if (ingredientDto.getId() != null) {
            Ingredient oldIngredient = ingredientService.getIngredientById(ingredientDto.getId());
            ingredient.setRedditID(oldIngredient.getRedditID());
            ingredient.setSent(oldIngredient.isSent());
        }
        return ingredient;
    }*/
}
