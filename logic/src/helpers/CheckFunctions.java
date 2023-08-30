package helpers;

import enums.ActionType;
import enums.PropertyType;
import jaxb.schema.generated.PRDBySecond;
import jaxb.schema.generated.PRDByTicks;

import java.util.Objects;

public abstract class CheckFunctions {
    public static Boolean isPRDTerminationByTicks(Object termination){
        return (termination.getClass() == PRDByTicks.class);
    }

    public static Boolean isPRDTerminationBySeconds(Object termination){
        return (termination.getClass() == PRDBySecond.class);
    }

    public static Boolean isNumericValue(PropertyType type){
        return (type == PropertyType.DECIMAL || type == PropertyType.FLOAT);
    }

    public static Boolean isNumericAction(ActionType type){
        return (type == ActionType.INCREASE || type == ActionType.DECREASE || type == ActionType.CALCULATION);
    }
}
