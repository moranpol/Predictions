package userRequests;

import enums.RequestStatus;
import termination.Termination;

public class Request {
    private final Integer id;
    private final String userName;
    private final String worldName;
    private final Integer numOfWantedSimulations;
    private final Termination termination;
    private RequestStatus requestStatus = RequestStatus.APPROVED;
    private Integer runningSimulations = 0;
    private Integer endedSimulations = 0;

    public Request(Integer id, String userName, String worldName, Integer numOfWantedSimulations, Termination termination) {
        this.id = id;
        this.userName = userName;
        this.worldName = worldName;
        this.numOfWantedSimulations = numOfWantedSimulations;
        this.termination = termination;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setRunningSimulations(Integer runningSimulations) {
        this.runningSimulations = runningSimulations;
    }

    public void setEndedSimulations(Integer endedSimulations) {
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

    public Termination getTermination() {
        return termination;
    }

    public RequestStatus getRequestStatus() {
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
