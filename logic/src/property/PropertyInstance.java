package property;

import enums.PropertyType;

import java.io.Serializable;

public abstract class PropertyInstance implements Serializable {
    private final String name;
    private Integer currValueCounterByTicks;
    private Integer sumCurrValueCounterByTicks;
    private Integer changesCountValueByTicks;

    public PropertyInstance(String name) {
        this.name = name;
        currValueCounterByTicks = 0;
        sumCurrValueCounterByTicks = 1;
        changesCountValueByTicks = 0;
    }

    public abstract Object getCurrValue();

    public abstract Object getPastValue();

    public abstract PropertyType getType();

    public abstract void setPastValue();

    public abstract void setCurrValue(Object currValue);

    public Float getAverageValueCounterByTicks(){
        if(changesCountValueByTicks == 0){
            return sumCurrValueCounterByTicks.floatValue();
        }
        return (float) (sumCurrValueCounterByTicks / changesCountValueByTicks);
    }

    public Integer getCurrValueCounterByTicks() {
        return currValueCounterByTicks;
    }

    public String getName() {
        return name;
    }

    public void setValueCounterByTicks(){
        if (getCurrValue() == getPastValue()){
            currValueCounterByTicks++;
            sumCurrValueCounterByTicks++;
        } else{
            currValueCounterByTicks = 0;
            changesCountValueByTicks++;
            setPastValue();
        }
    }
}
