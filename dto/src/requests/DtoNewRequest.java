package requests;

public class DtoNewRequest {
    private final String userName;
    private final String worldName;
    private final Integer numOfWantedSimulations;
    private final DtoTermination termination;

    public DtoNewRequest(String userName, String worldName, Integer numOfWantedSimulations, DtoTermination termination) {
        this.userName = userName;
        this.worldName = worldName;
        this.numOfWantedSimulations = numOfWantedSimulations;
        this.termination = termination;
    }

    public String getUserName() {
        return userName;
    }

    public String getWorldName() {
        return worldName;
    }

    public Integer getNumOfWantedSimulations() {
        return numOfWantedSimulations;
    }

    public DtoTermination getTermination() {
        return termination;
    }
}
