package menuChoice4;

public class DtoPastSimulationDetails {
    private final String runDate;
    private final Integer id;

    public DtoPastSimulationDetails(String runDate, Integer id) {
        this.runDate = runDate;
        this.id = id;
    }

    public String getRunDate() {
        return runDate;
    }

    public Integer getId() {
        return id;
    }
}
