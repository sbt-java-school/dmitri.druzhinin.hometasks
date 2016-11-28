package ru.sbt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.common.Mapper;
import ru.sbt.dao.IngredientDao;
import ru.sbt.dto.IngredientDto;
import ru.sbt.entity.Ingredient;
import ru.sbt.exception.BusinessException;

import java.util.List;

/**
 * Представляет слой службы для работы с ингредиентами.
 */
@Service
public class IngredientService {

    private final IngredientDao ingredientDao;

    private final Mapper mapper;

    private static final Logger logger= LoggerFactory.getLogger(IngredientService.class);

    @Autowired
    public IngredientService(Mapper mapper, IngredientDao ingredientDao) {
        this.mapper = mapper;
        this.ingredientDao = ingredientDao;
    }

    /**
     * Отображает объект класса IngredientDto в объект класса Ingredient и сохраняет его.
     */
    @Transactional
    public void save(IngredientDto ingredientDto) {
        Ingredient ingredient = mapper.map(ingredientDto, Ingredient.class);
        ingredientDao.update(ingredient);
    }

    /**
     * @return объект класса IngredientDto по имени.
     */
    @Transactional
    public IngredientDto get(String name) {
        logger.info("get " + name);
        Ingredient ingredient = ingredientDao.get(name);
        if (ingredient != null) {
            return mapper.map(ingredient, IngredientDto.class);
        } else {
            logger.info("Not exists " + name);
            throw new BusinessException("Ingredient with same name not exists");
        }
    }

    /**
     * @return список всех ингредиентов, отображенных в тип IngredientDto.
     */
    @Transactional
    public List<IngredientDto> getAll() {
        List<Ingredient> ingredients = ingredientDao.getAll();
        return mapper.mapAsList(ingredients, IngredientDto.class);
    }

    /**
     * Удаляет ингредиент по имени.
     */
    @Transactional
    public void delete(String name) {
        logger.info("delete " + name);
        ingredientDao.delete(name);
    }

}
