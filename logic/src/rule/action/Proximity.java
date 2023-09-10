package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
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

    public String getDepth(){
        return of.getString();
    }

    public int getActionAmount(){
        return actionList.size();
    }

    @Override
    public void activateAction(Context context) {
        Integer circle = getCircle(context.getMainEntityInstance(), context.getSecondEntityInstance(), context.getSecondEntityName());
        if(circle == null){
            return;
        }

        EntityInstance targetEntity = getEntityInProximity(context.getMainEntityInstance(), context.getGrid(), circle);
        if(targetEntity != null) {
            Context newContext = new Context(context.getEntities(), context.getWorldDefinition(), context.getGrid());
            newContext.setMainEntityInstance(context.getMainEntityInstance());
            newContext.setSecondEntityInstance(targetEntity);
            newContext.setSecondEntityName(targetEntityName);
            invokeListActions(actionList, newContext);
        }
    }

    private EntityInstance getEntityInProximity(EntityInstance entityInstance, Grid grid, Integer circle) {
        Integer entityRow = entityInstance.getLocation().getRow();
        Integer entityCol = entityInstance.getLocation().getCol();
        int positiveRow, positiveCol;
        for (int row = entityRow - circle; row < entityRow + circle; row++){
            positiveRow = (row + grid.getRows()) % grid.getRows();
            for (int col = entityCol - circle; col < entityCol + circle; col++){
                positiveCol = (col + grid.getCols()) % grid.getCols();
                if(grid.getGrid()[positiveRow][positiveCol]!= null &&
                        grid.getGrid()[positiveRow][positiveCol].getName().equals(targetEntityName)){
                    return grid.getGrid()[positiveRow][positiveCol];
                }
            }
        }

        return null;
    }

    private Integer getCircle(EntityInstance entityInstance, EntityInstance targetEntity, String secondEntityName){
        Object circle = of.getValue(entityInstance, targetEntity, secondEntityName);
        if(circle == null){
            return null;
        }

        if(of.getType() == PropertyType.DECIMAL){
            return (Integer)circle ;
        } else if (of.getType() == PropertyType.FLOAT) {
            Float floatCircle = (Float)circle;
            return floatCircle.intValue();
        } else {
            throw new ParseFailedException("Proximity action failed, Expression of", PropertyType.FLOAT);
        }
    }
}
