package world;

import java.util.ArrayList;
import java.util.List;

public class EntityCountGraph {
    private final List<Integer> entityQuantity;

    public EntityCountGraph() {
        entityQuantity = new ArrayList<>();
    }

    public List<Integer> getEntityQuantity() {
        return entityQuantity;
    }

    public void setEntityQuantity(Integer amount){
        entityQuantity.add(amount);
    }
}
