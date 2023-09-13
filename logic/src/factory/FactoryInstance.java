package factory;

import entity.EntityDefinition;
import entity.EntityInstance;
import entity.EntityManager;
import environment.EnvironmentDefinition;
import environment.EnvironmentInstance;
import grid.Grid;
import property.*;
import world.WorldDefinition;
import world.WorldInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class FactoryInstance {
    public static WorldInstance createWorldInstance(WorldDefinition worldDefinition){
        Map<String, EntityManager> entities = new HashMap<>();
        Grid grid = createGrid(worldDefinition.getGrid());
        for (EntityDefinition entity : worldDefinition.getEntities().values()) {
            entities.put(entity.getName(), createEntityManager(entity, grid));
        }

        return new WorldInstance(entities, createEnvironmentInstance(worldDefinition.getEnvironmentVariables()),
                worldDefinition.getRules(), worldDefinition.getTermination(), grid);
    }

    private static Grid createGrid(Grid definitionGrid){
        return new Grid(definitionGrid.getRows(), definitionGrid.getCols());
    }

    private static EnvironmentInstance createEnvironmentInstance(EnvironmentDefinition environmentDefinition){
        return new EnvironmentInstance(FactoryPropertyInstance.createPropertyInstanceMap(environmentDefinition.getProperties()));
    }

    private static EntityManager createEntityManager(EntityDefinition entityDefinition, Grid grid){
        List<EntityInstance> entityInstanceMap = new LinkedList<>();
        for (int i = 0; i < entityDefinition.getPopulation(); i++){
            entityInstanceMap.add(createEntityInstance(entityDefinition, grid));
        }

        return new EntityManager(entityDefinition.getName(), entityInstanceMap);
    }

    public static EntityInstance createEntityInstance(EntityDefinition entityDefinition, Grid grid){
        EntityInstance entity = new EntityInstance(entityDefinition.getName(),
                FactoryPropertyInstance.createPropertyInstanceMap(entityDefinition.getPropertiesOfAllPopulation()));
        grid.updateNewInstanceInRandomLocation(entity);
        return entity;
    }
}
