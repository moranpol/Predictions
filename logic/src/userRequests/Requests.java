package userRequests;

import requests.DtoNewRequest;
import requests.DtoTermination;
import termination.Termination;

import java.util.ArrayList;
import java.util.List;

public class Requests {
    private final List<Request> requestList = new ArrayList<>();
    private Integer numOfRequests = 0;

    public List<Request> getRequestList() {
        return requestList;
    }

    public void addSimulationRequest(DtoNewRequest dtoNewRequest) {
        requestList.add(new Request(numOfRequests, dtoNewRequest.getUserName(), dtoNewRequest.getWorldName(), dtoNewRequest.getNumOfWantedSimulations(), createTermination(dtoNewRequest.getTermination())));
        numOfRequests++;
    }

    private Termination createTermination(DtoTermination dtoTermination) {
        return new Termination(dtoTermination.getTicks(), dtoTermination.getSeconds(), dtoTermination.getUser());
    }
}
