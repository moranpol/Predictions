package details.DtoAction;

public abstract class DtoAction {
    private final String name;
    private final String mainEntityName;
    private final String secondaryEntityName;


    public DtoAction(String name, String mainEntityName, String secondaryEntityName) {
        this.name = name;
        this.mainEntityName = mainEntityName;
        this.secondaryEntityName = secondaryEntityName;
    }

    public String getName() {
        return name;
    }

    public String getMainEntityName() {
        return mainEntityName;
    }
    public String getSecondaryEntityName() {
        return secondaryEntityName;
    }

}
