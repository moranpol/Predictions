package helpers;

import enums.PropertyType;
import exceptions.ParseFailedException;

public abstract class ParseFunctions {
    public synchronized static Integer parseNumericTypeToInt(Object value){
        if(value instanceof Float){
            Float floatObj = (Float)value;
            return floatObj.intValue();
        } else if(value instanceof Double){
            Double doubleValue = (Double) value;
            return doubleValue.intValue();
        } else{
            return (Integer)value;
        }
    }

    public synchronized static Float parseNumericTypeToFloat(Object value){
        if (value instanceof Integer){
            Integer intValue = (Integer)value;
            return intValue.floatValue();
        } else if(value instanceof Double){
            Double doubleValue = (Double) value;
            return doubleValue.floatValue();
        } else{
            return (Float)value;
        }
    }

    public synchronized static Object parseInitByType(PropertyType type, String value) {
        Object init = new Object();
        switch (type) {
            case DECIMAL:
                try{
                    init = Integer.parseInt(value);
                } catch (NumberFormatException e){
                    throw new ParseFailedException("", PropertyType.DECIMAL);
                }
                break;
            case FLOAT:
                try {
                    init = Float.parseFloat(value);
                } catch (NumberFormatException e){
                    throw new ParseFailedException("", PropertyType.FLOAT);
                }
                break;
            case BOOLEAN:
                init = Boolean.parseBoolean(value);
                break;
            case STRING:
                init = value;
                break;
        }

        return init;
    }

}
