package menuChoice3;

public class DtoSimulationDetails {
    private final Integer id;
    private final String Termination;

    public DtoSimulationDetails(Integer id, String termination) {
        this.id = id;
        Termination = termination;
    }

    public Integer getId() {
        return id;
    }

    public String getTermination() {
        return Termination;
    }
}
