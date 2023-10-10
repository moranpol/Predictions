package manager;

import details.DtoWorldsList;
import enums.RequestStatus;
import enums.SimulationMode;
import exceptions.InvalidNameException;
import header.DtoSimulationQueue;
import newExecution.DtoNewExecution;
import newExecution.DtoStartExecution;
import requests.DtoNewRequest;
import requests.DtoRequestInfo;
import requests.DtoRequestsInfo;
import results.simulationEnded.DtoSimulationEndedDetails;
import results.simulationFailed.DtoSimulationFailedDetails;
import results.simulationRunningDetails.DtoSimulationRunningDetails;
import results.simulations.DtoSimulationInfo;
import results.simulations.DtoSimulationsInfoList;
import simulation.Simulation;
import userRequests.Request;
import userRequests.Requests;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LogicManager {
    private final Map<String, WorldManager> worldManagerMap = new HashMap<>();
    private ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    private final Requests simulationRequests = new Requests();
    private Integer simulationCount = 0;

    public void ReadXmlFile(InputStream xmlFile){
        try {
            WorldManager worldManager = new WorldManager();
            worldManager.ReadXmlFile(xmlFile);
            if(worldManagerMap.containsKey(worldManager.getWorldName())){
                throw new InvalidNameException("world name " + worldManager.getWorldName() + " already exist.");
            }
            worldManagerMap.put(worldManager.getWorldName(), worldManager);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void createSimulationRequest(DtoNewRequest dtoNewRequest){
        simulationRequests.addSimulationRequest(dtoNewRequest);
    }

    public DtoRequestsInfo createDtoRequestsInfoForUser(String username){
        List<DtoRequestInfo> requestInfoList = new ArrayList<>();

        for(Request request : simulationRequests.getRequestList()){
            if(request.getRequestStatus() != RequestStatus.PENDING && request.getUserName().equals(username)){
                requestInfoList.add(createDtoRequestInfo(request));
            }
        }

        return new DtoRequestsInfo(requestInfoList);
    }

    public DtoRequestsInfo createDtoRequestsInfoForManager(){
        List<DtoRequestInfo> requestInfoList = new ArrayList<>();

        for(Request request : simulationRequests.getRequestList()){
            requestInfoList.add(createDtoRequestInfo(request));
        }
        return new DtoRequestsInfo(requestInfoList);
    }

    private DtoRequestInfo createDtoRequestInfo(Request request) {
        String termination;
        if (request.getTermination().getHuman()){
            termination = "By User";
        } else if (request.getTermination().getSeconds() != null && request.getTermination().getTicks() != null) {
            termination = "Ticks - " + request.getTermination().getTicks() + ", seconds - " + request.getTermination().getSeconds();
        } else if (request.getTermination().getSeconds() == null && request.getTermination().getTicks() != null) {
            termination = "Ticks - " + request.getTermination().getTicks();
        } else{
            termination = "Seconds - " + request.getTermination().getSeconds();
        }

        return new DtoRequestInfo(request.getId(), request.getUserName(), request.getWorldName(), request.getNumOfWantedSimulations(), termination, request.getRequestStatus().toString().toLowerCase(), request.getRunningSimulations(), request.getEndedSimulations());
    }

    public DtoWorldsList getDtoWorldsList(){
        List<String> worldsList = new ArrayList<>(worldManagerMap.keySet());
        return new DtoWorldsList(worldsList);
    }

    public WorldManager getWorldManager(String worldName){
        return worldManagerMap.get(worldName);
    }

    public DtoSimulationQueue createDtoSimulationQueue(){
        int countEnded = 0;

        for(WorldManager worldManager: worldManagerMap.values()) {
            for(Simulation simulation : worldManager.getSimulations().values()){
                if(simulation.getSimulationMode() == SimulationMode.ENDED || simulation.getSimulationMode() == SimulationMode.FAILED){
                    countEnded++;
                }
            }
        }

        return new DtoSimulationQueue(countEnded, executorService.getQueue().size(), executorService.getActiveCount());
    }

    public void changeThreadPoolSize(Integer threadNum) {
        executorService.setCorePoolSize(threadNum);
    }

    public DtoNewExecution createDtoNewExecution(Integer requestId){
        return worldManagerMap.get(simulationRequests.getRequestList().get(requestId).getWorldName()).createDtoNewExecution();
    }

    public void updateRequestStatus(Integer requestId, String requestStatus) {
        simulationRequests.getRequestList().get(requestId).setRequestStatus(RequestStatus.valueOf(requestStatus.toUpperCase()));
    }

    public DtoStartExecution createNewSimulation(DtoStartExecution dtoSendExecution, Integer requestId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        DtoStartExecution dtoStartExecution = worldManagerMap.get(request.getWorldName()).createNewSimulation(dtoSendExecution, request, simulationCount);

        synchronized (this){
            simulationCount++;
        }

        return dtoStartExecution;
    }

    public void startSimulation(Integer simulationId, Integer requestId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        worldManagerMap.get(request.getWorldName()).simulationRun(executorService, simulationId);
        request.setRunningSimulations(request.getRunningSimulations() + 1);
        if(request.getEndedSimulations() + request.getRunningSimulations() == request.getNumOfWantedSimulations()){
            request.setRequestStatus(RequestStatus.FINISHED);
        }
    }

    public DtoStartExecution createDtoStartExecution(Integer requestId, Integer simulationId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        return worldManagerMap.get(request.getWorldName()).createDtoStartExecution(simulationId, requestId);
    }

    public DtoSimulationsInfoList createDtoSimulationsInfoListForUser(String username) {
        List<DtoSimulationInfo> dtoSimulationInfoList = new ArrayList<>();

        for(WorldManager worldManager : worldManagerMap.values()){
            for (Simulation simulation : worldManager.getSimulations().values()){
                if(simulation.getRequest().getUserName().equals(username)){
                    dtoSimulationInfoList.add(worldManager.createDtoSimulationInfo(simulation.getId()));
                }
            }
        }

        return new DtoSimulationsInfoList(dtoSimulationInfoList);
    }

    public DtoSimulationRunningDetails createDtoSimulationRunningDetails(Integer requestId, Integer simulationId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        return worldManagerMap.get(request.getWorldName()).createDtoSimulationRunningDetails(simulationId);
    }

    public void pauseSimulation(Integer requestId, Integer simulationId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        worldManagerMap.get(request.getWorldName()).pauseSimulation(simulationId);
    }

    public void resumeSimulation(Integer requestId, Integer simulationId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        worldManagerMap.get(request.getWorldName()).resumeSimulation(simulationId);
    }

    public void stopSimulation(Integer requestId, Integer simulationId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        worldManagerMap.get(request.getWorldName()).stopSimulation(simulationId);
    }

    public DtoSimulationFailedDetails createDtoSimulationFailedDetails(Integer requestId, Integer simulationId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        return worldManagerMap.get(request.getWorldName()).createDtoSimulationFailedDetails(simulationId);
    }

    public DtoSimulationEndedDetails createDtoSimulationEndedDetails(Integer requestId, Integer simulationId) {
        Request request = simulationRequests.getRequestList().get(requestId);
        return worldManagerMap.get(request.getWorldName()).createDtoSimulationEndedDetails(simulationId);
    }
}
