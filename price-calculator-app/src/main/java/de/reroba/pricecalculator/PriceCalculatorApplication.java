package de.reroba.pricecalculator;

import de.reroba.pricecalculator.ingredients.IngredientDto;
import de.reroba.pricecalculator.ingredients.IngredientRepository;
import de.reroba.pricecalculator.ingredients.IngredientsApiController;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@SpringBootApplication
@Slf4j
public class PriceCalculatorApplication implements CommandLineRunner {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    IngredientsApiController ingredientsController;

    public static void main(String[] args) {
        SpringApplication.run(PriceCalculatorApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        ingredientRepository.deleteAllInBatch();

        ingredientsController.createIngredient(new IngredientDto(
                null,
                "Perlen weiß",
                BigDecimal.valueOf(12.5),
                "EUR",
                BigDecimal.valueOf(12),
                "stk")
        );
        ingredientsController.createIngredient(new IngredientDto(
                null,
                "Perlen rot",
                BigDecimal.valueOf(12.5),
                "EUR",
                BigDecimal.valueOf(12),
                "stk")
        );
        ingredientsController.createIngredient(new IngredientDto(
                null,
                "Gummiband",
                BigDecimal.valueOf(12.5),
                "EUR",
                BigDecimal.valueOf(12),
                "m")
        );
        ingredientsController.createIngredient(new IngredientDto(
                null,
                "Gummiband",
                BigDecimal.valueOf(12.5),
                "EUR",
                BigDecimal.valueOf(12),
                "m")
        );

        ingredientsController.getIngredients(null).forEach(ingredientDto -> log.info("{}", ingredientDto));
    }
/*
    public <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            //logger.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }

    public List<String[]> loadManyToManyRelationship(String fileName) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<String[]> readValues =
                    mapper.reader(String[].class).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            //logger.error("Error occurred while loading many to many relationship from file = " + fileName, e);
            return Collections.emptyList();
        }
    }*/
}
