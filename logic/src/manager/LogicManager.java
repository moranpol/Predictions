package manager;

import details.DtoWorldsList;
import enums.SimulationMode;
import header.DtoSimulationQueue;
import simulation.Simulation;

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

    public void ReadXmlFile(InputStream xmlFile){
        WorldManager worldManager = new WorldManager();
        worldManager.ReadXmlFile(xmlFile);
        worldManagerMap.put(worldManager.getWorldName(), worldManager);
    }

    public DtoWorldsList getDtoWorldsList(){
        List<String> worldsList = new ArrayList<>(worldManagerMap.keySet());
        return new DtoWorldsList(worldsList);
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
}
