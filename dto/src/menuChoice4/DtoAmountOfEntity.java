package menuChoice4;

public class DtoAmountOfEntity {
    private final String name;
    private final Integer startAmount;
    private final Integer endAmount;

    public DtoAmountOfEntity(String name, Integer startAmount, Integer endAmount) {
        this.name = name;
        this.startAmount = startAmount;
        this.endAmount = endAmount;
    }

    public String getName() {
        return name;
    }

    public Integer getStartAmount() {
        return startAmount;
    }

    public Integer getEndAmount() {
        return endAmount;
    }
}
