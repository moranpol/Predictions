package property;

import enums.PropertyType;

import java.io.Serializable;
import java.util.Objects;

public abstract class PropertyInstance implements Serializable {
    private final String name;
    private Integer currValueCounterByTicks;

    public PropertyInstance(String name) {
        this.name = name;
        currValueCounterByTicks = 1;
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
