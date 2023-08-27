package factory;

import entity.EntityDefinition;
import entity.EntityInstance;
import entity.EntityManager;
import environment.EnvironmentDefinition;
import environment.EnvironmentInstance;
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
        for (EntityDefinition entity : worldDefinition.getEntities().values()) {
            entities.put(entity.getName(), createEntityManager(entity));
        }

        return new WorldInstance(entities, createEnvironmentInstance(worldDefinition.getEnvironmentVariables()),
                worldDefinition.getRules(), worldDefinition.getTermination());
    }

    private static EnvironmentInstance createEnvironmentInstance(EnvironmentDefinition environmentDefinition){
        EnvironmentInstance environmentInstance = new EnvironmentInstance(FactoryPropertyInstance.createPropertyInstanceMap(environmentDefinition.getProperties()));
        for (PropertyDefinition prop : environmentDefinition.getProperties().values()) {
            prop.setInit(environmentInstance.getProperties().get(prop.getName()).getValue());
        }

        return environmentInstance;
    }

    private static EntityManager createEntityManager(EntityDefinition entityDefinition){
        List<EntityInstance> entityInstanceMap = new LinkedList<>();
        for (int i = 0; i < entityDefinition.getPopulation(); i++){
            entityInstanceMap.add(createEntityInstance(entityDefinition));
        }

        return new EntityManager(entityDefinition.getName(), entityInstanceMap);
    }

    private static EntityInstance createEntityInstance(EntityDefinition entityDefinition){
        return new EntityInstance(entityDefinition.getName(), FactoryPropertyInstance.createPropertyInstanceMap(entityDefinition.getPropertiesOfAllPopulation()));
    }
}
