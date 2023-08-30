package rule.action;

import java.io.Serializable;

public class SecondaryEntity implements Serializable {
    private final String secondEntityName;
    private final String count;
    private final Condition condition;

    public SecondaryEntity(String secondEntityName, String count, Condition condition) {
        this.secondEntityName = secondEntityName;
        this.count = count;
        this.condition = condition;
    }

    public String getSecondEntityName() {
        return secondEntityName;
    }

    public String getCount() {
        return count;
    }

    public Condition getCondition() {
        return condition;
    }
}
