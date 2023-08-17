package helpers;

import enums.PropertyType;
import exceptions.ParseFailedException;

public abstract class ParseFunctions {
    public static Integer parseNumericTypeToInt(Object value){
        if(value instanceof Float){
            Float floatObj = (Float)value;
            return floatObj.intValue();
        }else{
            return (Integer)value;
        }
    }

    public static Float parseNumericTypeToFloat(Object value){
        if (value instanceof Integer){
            Integer intValue = (Integer)value;
            return intValue.floatValue();
        } else{
            return (Float)value;
        }
    }

    public static Object parseInitByType(PropertyType type, String value) {
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
