package property;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StringProperty extends PropertyInstance{
    private String value;

    public String getValue() {
        return value;
    }

    public StringProperty(PropertyDefinition prop) {
        super(prop.getName());
        if(prop.isRandomInit()){
            this.value = getRandomString();
        }
        else{
            this.value = (String)prop.getValue();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringProperty that = (StringProperty) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private String getRandomString(){
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

    private void initializeAsciiList(List<Integer> asciiList){
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
