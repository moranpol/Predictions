package requests;

public class DtoRequestInfo {
    private final Integer id;
    private final String userName;
    private final String worldName;
    private final Integer numOfWantedSimulations;
    private final DtoTermination termination;
    private final String requestStatus;
    private final Integer runningSimulations;
    private final Integer endedSimulations;

    public DtoRequestInfo(Integer id, String userName, String worldName, Integer numOfWantedSimulations, DtoTermination termination, String requestStatus, Integer runningSimulations, Integer endedSimulations) {
        this.id = id;
        this.userName = userName;
        this.worldName = worldName;
        this.numOfWantedSimulations = numOfWantedSimulations;
        this.termination = termination;
        this.requestStatus = requestStatus;
        this.runningSimulations = runningSimulations;
        this.endedSimulations = endedSimulations;
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

    public String getRequestStatus() {
        return requestStatus;
    }

    public Integer getRunningSimulations() {
        return runningSimulations;
    }

    public Integer getEndedSimulations() {
        return endedSimulations;
    }

    public Integer getId() {
        return id;
    }
}
