package factory;

import entity.EntityDefinition;
import entity.EntityInstance;
import entity.EntityManager;
import environment.EnvironmentDefinition;
import environment.EnvironmentInstance;
import property.*;
import world.WorldDefinition;
import world.WorldInstance;

import java.util.Map;

public abstract class FactoryInstance {
    public static WorldInstance createWorldInstance(WorldDefinition worldDefinition){

        return null;
    }

    private static EnvironmentInstance createEnvironmentInstance(EnvironmentDefinition environmentDefinition){
        //todo
        //return new EnvironmentInstance(FactoryPropertyInstance.createPropertyInstanceMap(environmentDefinition.getProperties()));
        return null;
    }

    private static EntityManager createEntityManager(EntityDefinition entityDefinition){
        //todo
        return null;
    }
}
