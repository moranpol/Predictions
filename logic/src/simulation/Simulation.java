package simulation;

import enums.TerminationType;
import factory.FactoryInstance;
import jaxb.LoadXml;
import world.WorldDefinition;
import world.WorldInstance;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Simulation implements Serializable {
    private final String startDateFormat;
    private final Integer id;
    private final WorldInstance worldInstance;
    private TerminationType terminationReason;
    private final WorldDefinition worldDefinition;

    public Simulation(Integer id, WorldInstance worldInstance, WorldDefinition worldDefinition) {
        this.id = id;
        this.worldInstance = worldInstance;
        this.worldDefinition = worldDefinition;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");
        startDateFormat = now.format(format);
    }

    public String getStartDateFormat() {
        return startDateFormat;
    }

    public Integer getId() {
        return id;
    }

    public WorldInstance getWorldInstance() {
        return worldInstance;
    }

    public TerminationType getTerminationReason() {
        return terminationReason;
    }

    public void runSimulation(){
        long startTime = System.currentTimeMillis();
        Integer seconds = worldInstance.getTermination().getSeconds();
        Integer ticks = worldInstance.getTermination().getTicks();

        if(ticks != null && seconds != null){
            runSimulationByTicksAndSeconds(ticks, seconds, startTime);
        } else if (ticks == null && seconds != null) {
            runSimulationBySeconds(seconds, startTime);
        }else{
            runSimulationByTicks(ticks);
        }
    }

    private void runSimulationByTicksAndSeconds(Integer ticks, Integer seconds, long startTime){
        long maxRunTimeMilliSec = seconds * 1000;
        terminationReason = TerminationType.TICKS;

        for (int currentTick = 1; currentTick < ticks; currentTick++){
            if (System.currentTimeMillis() - startTime >= maxRunTimeMilliSec){
                terminationReason = TerminationType.SECONDS;
                break;
            }
            worldInstance.runSimulationTick(currentTick, worldDefinition);
        }
    }

    private void runSimulationBySeconds(Integer seconds, long startTime){
        int currentTick = 1;
        long maxRunTimeMilliSec = seconds * 1000;
        terminationReason = TerminationType.SECONDS;

        while (System.currentTimeMillis() - startTime < maxRunTimeMilliSec){
            worldInstance.runSimulationTick(currentTick, worldDefinition);
            currentTick++;
        }
    }

    private void runSimulationByTicks(Integer ticks){
        terminationReason = TerminationType.TICKS;

        for (int currentTick = 1; currentTick < ticks; currentTick++){
            worldInstance.runSimulationTick(currentTick, worldDefinition);
        }
    }

    public static void main(String[] args) {
        LoadXml xml = new LoadXml();
        WorldDefinition worldDefinition = xml.loadAndValidateXml("C:\\Users\\moran\\Documents\\Education\\Second year\\Java\\tests\\master-ex2.xml");
        WorldInstance worldInstance = FactoryInstance.createWorldInstance(worldDefinition);
        Simulation simulation = new Simulation(0, worldInstance, worldDefinition);
        simulation.runSimulation();
        System.out.println("finished");
    }
}
