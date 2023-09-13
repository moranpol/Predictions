package helpers;

import enums.ActionType;
import enums.PropertyType;
import jaxb.schema.generated.PRDBySecond;
import jaxb.schema.generated.PRDByTicks;

public abstract class CheckFunctions {
    public synchronized static Boolean isPRDTerminationByTicks(Object termination){
        return (termination.getClass() == PRDByTicks.class);
    }

    public synchronized static Boolean isPRDTerminationBySeconds(Object termination){
        return (termination.getClass() == PRDBySecond.class);
    }

    public synchronized static Boolean isNumericValue(PropertyType type){
        return (type == PropertyType.DECIMAL || type == PropertyType.FLOAT);
    }

    public synchronized static Boolean isNumericAction(ActionType type){
        return (type == ActionType.INCREASE || type == ActionType.DECREASE || type == ActionType.CALCULATION);
    }
}
