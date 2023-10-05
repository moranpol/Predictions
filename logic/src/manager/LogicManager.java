package manager;

import details.DtoWorldsList;
import enums.RequestStatus;
import enums.SimulationMode;
import exceptions.InvalidNameException;
import header.DtoSimulationQueue;
import requests.DtoNewRequest;
import requests.DtoRequestInfo;
import requests.DtoRequestsInfo;
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

    public void check() {
        simulationRequests.getRequestList().get(0).setEndedSimulations(1);
    }

    public DtoRequestsInfo createDtoRequestsInfoForUser(String username){
        List<DtoRequestInfo> requestInfoList = new ArrayList<>();

        for(Request request : simulationRequests.getRequestList()){
            if((request.getRequestStatus() == RequestStatus.APPROVED || request.getRequestStatus() == RequestStatus.REJECTED) && request.getUserName().equals(username)){
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
            for(Simulation simulation : worldManager.getSimulations()){
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


    public void updateRequestStatus(Integer requestId, String requestStatus) {
        simulationRequests.getRequestList().get(requestId).setRequestStatus(RequestStatus.valueOf(requestStatus));
    }
}
