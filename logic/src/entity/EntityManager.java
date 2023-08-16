package entity;

import java.util.List;
import java.util.Map;

public class EntityManager {
    private final String name;
    private List<EntityInstance> entityInstance;

    public EntityManager(String name, List<EntityInstance> entityInstance) {
        this.name = name;
        this.entityInstance = entityInstance;
    }

    public String getName() {
        return name;
    }

    public List<EntityInstance> getEntityInstance() {
        return entityInstance;
    }

    public void killInstances(){
        for(int i = 0; i < entityInstance.size(); i++){
            if(entityInstance.get(i).getDead()){
                entityInstance.remove(i);
            }
        }
    }
}
