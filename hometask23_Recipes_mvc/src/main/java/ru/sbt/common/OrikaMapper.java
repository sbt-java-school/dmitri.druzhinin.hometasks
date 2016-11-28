package ru.sbt.common;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;
import ru.sbt.dto.IngredientDto;
import ru.sbt.dto.RecipeDto;
import ru.sbt.dto.RelationDto;
import ru.sbt.entity.Ingredient;
import ru.sbt.entity.Recipe;
import ru.sbt.entity.Relation;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Component
public class OrikaMapper implements Mapper {
    private MapperFacade mapperFacade;

    public OrikaMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFacade = mapperFactory.getMapperFacade();

        register(mapperFactory);
    }

    protected void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(IngredientDto.class, Ingredient.class)
                .byDefault().register();

        mapperFactory.classMap(RecipeDto.class, Recipe.class)
                .byDefault().register();

        mapperFactory.classMap(RelationDto.class, Relation.class)
                .field("recipeName", "recipe.name")
                .field("ingredientName", "ingredient.name")
                .byDefault().register();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        return mapperFacade.map(sourceObject, destinationClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        return mapperFacade.mapAsList(source, destinationClass);
    }
}
