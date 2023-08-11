package property;

import enums.PropertyType;
import exceptions.InvalidNameException;
import exceptions.MissMatchValuesException;
import exceptions.ValueOutOfBoundException;
import jaxb.schema.generated.PRDEnvProperty;
import jaxb.schema.generated.PRDProperty;
import jaxb.schema.generated.PRDRange;
import jaxb.schema.generated.PRDValue;

import java.util.Objects;

public class PropertyDefinition {
    private PropertyType type;
    private final String name;
    private Boolean isRandomInit = null;
    private Range range = null;
    private Object init = null;

    public PropertyDefinition(PRDProperty property) {
        validateName(property.getPRDName());
        this.name = property.getPRDName();
        validateType(property.getType());
        validateEntityPropertyInit(property.getPRDValue(), property.getPRDRange());
        this.isRandomInit = property.getPRDValue().isRandomInitialize();
    }

    public PropertyDefinition(PRDEnvProperty property) {
        validateName(property.getPRDName());
        this.name = property.getPRDName();
        validateType(property.getType());
        validateEnvironmentPropertyInit(property.getPRDRange());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyDefinition property = (PropertyDefinition) o;
        return isRandomInit == property.isRandomInit && type == property.type && Objects.equals(name, property.name) && Objects.equals(range, property.range) && Objects.equals(init, property.init);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, isRandomInit, range, init);
    }

    @Override
    public String toString(){
        return ("Name: " + this.name + "\nType:" + this.type.name().toLowerCase() + this.range +
                "\nIs random: " + this.isRandomInit + "\n");
    }

    public String getName() {
        return name;
    }

    public PropertyType getType() {
        return type;
    }

    public boolean isRandomInit() {
        return isRandomInit;
    }

    public Range getRange() {
        return range;
    }

    public Object getValue() {
        return init;
    }

    //todo - maybe open another class validator
    private void validateName(String name){
        if(name == null){
            throw new InvalidNameException("Property must have a name");
        }
    }

    private void validateType(String type){
        try{
            this.type = Enum.valueOf(PropertyType.class, type);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new MissMatchValuesException(this.name + "property must have a type: decimal\\float\\string\\boolean.");
        }
    }

    private void validateEntityPropertyInit(PRDValue value, PRDRange range){
        if(value == null) {
            throw new MissMatchValuesException(this.name + "property must have a value");
        }
        if(!value.isRandomInitialize()){
            validateInitType(value);
        }
        if(isNumericValue()){
            if(value.isRandomInitialize() && range == null){
                throw new MissMatchValuesException("For random init in " + this.name + " property, you need to insert range.");
            }
            if(range != null){
                validateInitInRange(range);
                this.range = new Range(range);
            }
        }
    }

    private void validateEnvironmentPropertyInit(PRDRange range) {
        if(isNumericValue()){
            if(range != null){
                this.range = new Range(range);
            }
            else {
                throw new MissMatchValuesException(this.name + "environment property, must have a range.");
            }
        }
    }

    private void validateInitType(PRDValue value){
        try{
            switch (this.type){
                case DECIMAL:
                    this.init = Integer.parseInt(value.getInit());
                    break;
                case FLOAT:
                    this.init = Float.parseFloat(value.getInit());
                    break;
                case BOOLEAN:
                    this.init = convertBoolean(value.getInit());
                    break;
            }
        } catch (NumberFormatException e) {
            throw new MissMatchValuesException(this.name + "property is type " + this.type + " and init not match type");
        }catch (MissMatchValuesException e){
            throw new MissMatchValuesException(this.name + "property is type boolean and " + e.getMessage());
        }
    }

    private boolean isNumericValue(){
        return (this.type == PropertyType.DECIMAL || this.type == PropertyType.FLOAT);
    }

    //todo - check if move to another class (maybe convert class)
    private boolean convertBoolean(String value){
        if(Objects.equals(value, "true")){
            return true;
        } else if (Objects.equals(value, "false")) {
            return false;
        }else{
            throw new MissMatchValuesException("init value must be true\\false");
        }
    }

    private void validateInitInRange(PRDRange range){
        if((float)this.init < range.getFrom() || (float)this.init > range.getTo()){
            throw new ValueOutOfBoundException(this.name + "property", (float)range.getFrom(), (float)range.getTo(), (float)this.init);
        }
    }
}
