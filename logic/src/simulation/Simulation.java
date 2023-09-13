package simulation;

import enums.SimulationMode;
import enums.TerminationType;
import factory.FactoryInstance;
import jaxb.LoadXml;
import jdk.nashorn.internal.ir.WhileNode;
import world.WorldDefinition;
import world.WorldInstance;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;

public class Simulation implements Serializable, Runnable {
    private final String startDateFormat;
    private final Integer id;
    private final WorldInstance worldInstance;
    private TerminationType terminationReason;
    private final WorldDefinition worldDefinition;
    private SimulationMode simulationMode;
    private Integer ticks;
    private Integer seconds;
    private String failedReason;

    public Simulation(Integer id, WorldInstance worldInstance, WorldDefinition worldDefinition) {
        this.id = id;
        this.worldInstance = worldInstance;
        this.worldDefinition = worldDefinition;
        simulationMode = SimulationMode.RUNNING;
        ticks = 1;
        seconds = 0;
        failedReason = null;

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

    public SimulationMode getSimulationMode(){return simulationMode;}

    public Integer getTicks() {
        return ticks;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public WorldDefinition getWorldDefinition() {
        return worldDefinition;
    }

    public void setSimulationMode(SimulationMode simulationMode) {
        this.simulationMode = simulationMode;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
        Integer maxSeconds = worldInstance.getTermination().getSeconds();
        Integer maxTicks = worldInstance.getTermination().getTicks();

        if(maxTicks != null && maxSeconds != null){
            runSimulationByTicksAndSeconds(maxTicks, maxSeconds, startTime);
        } else if (maxTicks == null && maxSeconds != null) {
            runSimulationBySeconds(maxSeconds, startTime);
        }else if (maxTicks != null){
            runSimulationByTicks(maxTicks, startTime);
        } else{
            runSimulationByUser(startTime);
        }

        simulationMode = SimulationMode.ENDED;
    }

    private void runSimulationByTicksAndSeconds(Integer maxTicks, Integer maxSeconds, long startTime){
        long maxRunTimeMilliSec = maxSeconds * 1000;
        terminationReason = TerminationType.TICKS;

        for (ticks = 1; ticks < maxTicks; ticks++){
            seconds = (int)((System.currentTimeMillis() - startTime) / 1000);

            if(checkAndUpdateSimulationEnded(startTime)){
                break;
            } else if (System.currentTimeMillis() - startTime >= maxRunTimeMilliSec){
                seconds = (int)((System.currentTimeMillis() - startTime) / 1000);
                terminationReason = TerminationType.SECONDS;
                break;
            }

            worldInstance.runSimulationTick(ticks, worldDefinition);
        }
    }

    private void runSimulationBySeconds(Integer maxSeconds, long startTime){
        long maxRunTimeMilliSec = maxSeconds * 1000;
        terminationReason = TerminationType.SECONDS;

        while (System.currentTimeMillis() - startTime < maxRunTimeMilliSec){
            seconds = (int)((System.currentTimeMillis() - startTime) / 1000);
            if(checkAndUpdateSimulationEnded(startTime)){
                break;
            }

            worldInstance.runSimulationTick(ticks, worldDefinition);
            ticks++;
        }
    }

    private void runSimulationByTicks(Integer maxTicks, long startTime){
        terminationReason = TerminationType.TICKS;

        for (ticks = 1; ticks < maxTicks; ticks++){
            seconds = (int)((System.currentTimeMillis() - startTime) / 1000);
            if(checkAndUpdateSimulationEnded(startTime)){
                break;
            }

            worldInstance.runSimulationTick(ticks, worldDefinition);
        }
    }

    private void runSimulationByUser(long startTime){
        while (simulationMode != SimulationMode.ENDED){
            seconds = (int)((System.currentTimeMillis() - startTime) / 1000);
            worldInstance.runSimulationTick(ticks, worldDefinition);
            ticks++;
        }

        terminationReason = TerminationType.User;
    }

    private Boolean checkAndUpdateSimulationEnded(long startTime){
        if(simulationMode == SimulationMode.ENDED){
            seconds = (int)((System.currentTimeMillis() - startTime) / 1000);
            terminationReason = TerminationType.User;
            return true;
        }
        return false;
    }
}
