package rule;

import entity.EntityInstance;
import entity.EntityManager;
import rule.action.Action;
import rule.action.Context;
import rule.action.SecondaryEntity;
import world.WorldDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Rule implements Serializable {
    private final String name;
    private final List<Action> actionList;
    private final Activation activation;

    public Rule(String name, List<Action> actionList, Activation activation) {
        this.name = name;
        this.actionList = actionList;
        this.activation = activation;
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public String getName() {
        return name;
    }

    public Activation getActivation() {
        return activation;
    }

    public void activeRule(Map<String, EntityManager> entities, Integer currentTick, WorldDefinition worldDefinition){
        if(activation.checkIfActivate(currentTick)){
            Context context = new Context(entities, worldDefinition);
            for(EntityManager entityManager : entities.values()){
                for(EntityInstance mainEntityInstance : entityManager.getEntityInstance()){
                    for (Action action : actionList){
                        try {
                            if(action.getMainEntityName().equals(mainEntityInstance.getName())){
                                context.setMainEntityInstance(mainEntityInstance);
                                if(action.getSecondaryEntity() != null){
                                    List<EntityInstance> secondEntityList = secondaryEntityInstances(entities, action.getSecondaryEntity(), worldDefinition);
                                    for (EntityInstance secondEntityInstance : secondEntityList){
                                        context.setSecondEntityInstance(secondEntityInstance);
                                        action.activateAction(context);
                                    }
                                } else {
                                    action.activateAction(context);
                                }
                            }
                        } catch (Exception e){
                            throw new RuntimeException("Rule name: " + name + "\n    " + e.getMessage());
                        }
                    }
                }
                killEntityInstances(entities);
                updateNewEntityInstances(context);
            }
        }
    }

    private void updateNewEntityInstances(Context context){
        for (EntityInstance entityInstance : context.getNewEntityInstances()) {
            context.getEntities().get(entityInstance.getName()).addInstance(entityInstance);
        }
    }

    private void killEntityInstances(Map<String, EntityManager> entities){
        for (EntityManager entity : entities.values()) {
            entity.killInstances();
        }
    }

    private List<EntityInstance> secondaryEntityInstances(Map<String, EntityManager> entities, SecondaryEntity secondaryEntity, WorldDefinition worldDefinition){
        List<EntityInstance> secondaryEntityInstances = new ArrayList<>();
        List<EntityInstance> filteredByCondition = entities.get(secondaryEntity.getSecondEntityName()).getEntityInstance();
        Context context = new Context(entities, worldDefinition);
        if(secondaryEntity.getCondition() != null){
            filteredByCondition = entities.get(secondaryEntity.getSecondEntityName()).getEntityInstance().stream()
                    .filter(entityInstance -> {
                        context.setMainEntityInstance(entityInstance);
                        return secondaryEntity.getCondition().invokeCondition(context);
                    })
                    .collect(Collectors.toList());
        }

        if (secondaryEntity.getCount().equalsIgnoreCase("all")){
            return filteredByCondition;
        } else{
            int count = Integer.parseInt(secondaryEntity.getCount());
            count = Math.min(count, filteredByCondition.size());
            for (int i = 0; i < count; i++){
                int randomNum = ThreadLocalRandom.current().nextInt(0, filteredByCondition.size());
                secondaryEntityInstances.add(filteredByCondition.get(randomNum));
            }
            return secondaryEntityInstances;
        }
    }
}
