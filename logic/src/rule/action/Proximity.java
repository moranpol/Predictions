package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFailedException;
import grid.Grid;
import rule.action.expression.Expression;

import java.util.List;

public class Proximity extends Action{
    private final String targetEntityName;
    private final Expression of;
    private final List<Action> actionList;

    public Proximity(String entityName, SecondaryEntity secondaryEntity, String targetEntityName, Expression of, List<Action> actionList) {
        super(entityName, secondaryEntity);
        this.targetEntityName = targetEntityName;
        this.of = of;
        this.actionList = actionList;
    }

    @Override
    public void activateAction(Context context) {
        EntityInstance entityInstance;
        try{
            entityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    Proximity action failed.");
        }
        Context newContext = new Context(context.getEntities(), context.getWorldDefinition(), context.getGrid());
        newContext.setMainEntityInstance(entityInstance);
        for (EntityInstance targetEntity : context.getEntities().get(targetEntityName).getEntityInstance()){
            Integer circle = getCircle(entityInstance, targetEntity);
            if(checkProximity(entityInstance, context.getGrid(), targetEntity, circle)){
                newContext.setSecondEntityInstance(targetEntity);
                for (Action action : actionList){
                    action.activateAction(newContext);
                }
            }
        }
    }

    private boolean checkProximity(EntityInstance entityInstance, Grid grid, EntityInstance targetEntity, Integer circle) {
        Integer entityRow = entityInstance.getLocation().getRow();
        Integer entityCol = entityInstance.getLocation().getCol();
        Integer targetRow = targetEntity.getLocation().getRow();
        Integer targetCol = targetEntity.getLocation().getCol();
        for (int row = entityRow - circle; row < entityRow + circle; row++){
            for (int col = entityCol - circle; col < entityCol + circle; col++){
                if(((row + grid.getRows()) % grid.getRows()) == targetRow && ((col + grid.getCols()) % grid.getCols()) == targetCol){
                    return true;
                }
            }
        }
        return false;
    }

    private Integer getCircle(EntityInstance entityInstance, EntityInstance targetEntity){
        if(of.getType() == PropertyType.DECIMAL){
            return (Integer) of.getValue(entityInstance, targetEntity);
        } else if (of.getType() == PropertyType.FLOAT) {
            Float circle = (Float)of.getValue(entityInstance, targetEntity);
            return circle.intValue();
        } else {
            throw new ParseFailedException("Proximity action failed, Expression of", PropertyType.FLOAT);
        }
    }
}
