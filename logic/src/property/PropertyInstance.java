package property;

import enums.PropertyType;

import java.io.Serializable;

public abstract class PropertyInstance implements Serializable {
    private final String name;
    private Integer currValueCounterByTicks;

    public PropertyInstance(String name) {
        this.name = name;
        currValueCounterByTicks = 0;
    }

    public abstract Object getValue();

    public Integer getCurrValueCounterByTicks() {
        return currValueCounterByTicks;
    }

    public abstract void setValue(Object value);

    public void setCurrValueCounterByTicks(Integer currValueCounterByTicks) {
        this.currValueCounterByTicks = currValueCounterByTicks;
    }

    public String getName() {
        return name;
    }

    public abstract PropertyType getType();
}
