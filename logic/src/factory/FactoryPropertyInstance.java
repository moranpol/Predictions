package factory;

import property.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class FactoryPropertyInstance {
    public static Map<String, PropertyInstance> createPropertyInstanceMap(Map<String, PropertyDefinition> properties){
        Map<String, PropertyInstance> propertiesInstance = new HashMap<>();
        for (PropertyDefinition prop : properties.values()) {
            propertiesInstance.put(prop.getName(), createPropertyInstance(prop));
        }

        return propertiesInstance;
    }

    public static PropertyInstance createPropertyInstance(PropertyDefinition property) {
        PropertyInstance newProperty = null;
        switch (property.getType()) {
            case DECIMAL:
                newProperty = createIntProperty(property);
                break;
            case FLOAT:
                newProperty = createFloatProperty(property);
                break;
            case STRING:
                newProperty = createStringProperty(property);
                break;
            case BOOLEAN:
                newProperty = createBooleanProperty(property);
                break;
        }

        return newProperty;
    }

    private static IntProperty createIntProperty(PropertyDefinition property){
        Integer value = null;
        if(property.isRandomInit()){
            value = ThreadLocalRandom.current().nextInt(property.getRange().getFrom().intValue(),
                    (property.getRange().getTo().intValue()) + 1);
        }
        else{
            value = (Integer)property.getValue() ;
        }

        return new IntProperty(property.getName(), property.getRange(), value);
    }

    private static FloatProperty createFloatProperty(PropertyDefinition property){
        Float value = null;
        if(property.isRandomInit()){
            Random random = new Random();
            value = property.getRange().getFrom().floatValue() + random.nextFloat() *
                    (property.getRange().getTo().floatValue() - property.getRange().getFrom().floatValue());
        }
        else{
            value = (Float)property.getValue() ;
        }

        return new FloatProperty(property.getName(), property.getRange(), value);
    }

    private static StringProperty createStringProperty(PropertyDefinition property){
        String value = null;
        if(property.isRandomInit()){
            value = getRandomString();
        }
        else{
            value = (String)property.getValue();
        }

        return new StringProperty(property.getName(), value);
    }

    private static BooleanProperty createBooleanProperty(PropertyDefinition property){
        Boolean value = null;
        if(property.isRandomInit()){
            Random random = new Random();
            value = random.nextBoolean();
        }
        else{
            value = (boolean)property.getValue();
        }

        return new BooleanProperty(property.getName(), value);
    }

    private static String getRandomString(){
        StringBuilder returnString = new StringBuilder();
        int sizeOfString = ThreadLocalRandom.current().nextInt(1, 51);
        List<Integer> asciiList = new ArrayList<>();
        initializeAsciiList(asciiList);

        for(int i = 0; i < sizeOfString; i++){
            int randomIndex = new Random().nextInt(asciiList.size());
            int randomInteger = asciiList.get(randomIndex);
            returnString.append((char) randomInteger);
        }

        return returnString.toString();
    }

    private static void initializeAsciiList(List<Integer> asciiList){
        // Add ASCII values for digits 0-9
        for (int i = 48; i <= 57; i++) {
            asciiList.add(i);
        }

        // Add ASCII values for uppercase letters A-Z
        for (int i = 65; i <= 90; i++) {
            asciiList.add(i);
        }

        // Add ASCII values for lowercase letters a-z
        for (int i = 97; i <= 122; i++) {
            asciiList.add(i);
        }

        asciiList.add(32);
        asciiList.add(33);
        asciiList.add(63);
        asciiList.add(95);
        asciiList.add(45);
        asciiList.add(46);
        asciiList.add(40);
        asciiList.add(41);
    }
}
