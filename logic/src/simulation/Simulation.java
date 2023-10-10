package simulation;

import enums.SimulationMode;
import termination.Termination;
import userRequests.Request;
import world.WorldDefinition;
import world.WorldInstance;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Simulation implements Serializable, Runnable {
    private final String startDateFormat;
    private final Integer id;
    private final WorldInstance worldInstance;
    private final WorldDefinition worldDefinition;
    private final Termination termination;
    private final Request request;
    private SimulationMode simulationMode;
    private Integer ticks;
    private Integer seconds;
    private String failedReason;
    private Integer pauseTime;
    private long startTime;

    public Simulation(Integer id, WorldInstance worldInstance, WorldDefinition worldDefinition, Termination termination, Request request) {
        this.id = id;
        this.worldInstance = worldInstance;
        this.worldDefinition = worldDefinition;
        this.termination = termination;
        this.request = request;
        simulationMode = SimulationMode.RUNNING;
        ticks = 1;
        seconds = 0;
        failedReason = null;
        pauseTime = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");
        startDateFormat = now.format(format);
    }

    public Request getRequest() {
        return request;
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

    @Override
    public void run(){
        startTime = System.currentTimeMillis();
        Integer maxSeconds = termination.getSeconds();
        Integer maxTicks = termination.getTicks();

        try {
            if (maxTicks != null && maxSeconds != null) {
                runSimulationByTicksAndSeconds(maxTicks, maxSeconds);
            } else if (maxTicks == null && maxSeconds != null) {
                runSimulationBySeconds(maxSeconds);
            } else if (maxTicks != null) {
                runSimulationByTicks(maxTicks);
            } else {
                runSimulationByUser();
            }

            simulationMode = SimulationMode.ENDED;
        } catch (Exception e){
            simulationMode = SimulationMode.FAILED;
            failedReason = "Simulation id " + id + " failed.\n" + e.getMessage();
        } finally {
            request.setRunningSimulations(request.getRunningSimulations() - 1);
            request.setEndedSimulations(request.getEndedSimulations() + 1);
        }
    }

    private void runSimulationByTicksAndSeconds(Integer maxTicks, Integer maxSeconds){
        long maxRunTimeMilliSec = maxSeconds * 1000;

        for (ticks = 1; ticks <= maxTicks; ticks++){
            checkSleep();
            setSeconds();
            if(simulationMode == SimulationMode.ENDED){
                break;
            } else if (System.currentTimeMillis() - startTime - (pauseTime * 1000) >= maxRunTimeMilliSec){
                break;
            }

            worldInstance.runSimulationTick(ticks, worldDefinition);
            checkPause();
        }
    }

    private void runSimulationBySeconds(Integer maxSeconds){
        long maxRunTimeMilliSec = maxSeconds * 1000;

        while (System.currentTimeMillis() - startTime - (pauseTime * 1000) < maxRunTimeMilliSec){
            checkSleep();
            setSeconds();
            if(simulationMode == SimulationMode.ENDED){
                break;
            }

            worldInstance.runSimulationTick(ticks, worldDefinition);
            checkPause();
            ticks++;
        }
    }

    private void runSimulationByTicks(Integer maxTicks){

        for (ticks = 1; ticks <= maxTicks; ticks++){
            checkSleep();
            setSeconds();
            if(simulationMode == SimulationMode.ENDED){
                break;
            }

            worldInstance.runSimulationTick(ticks, worldDefinition);
            checkPause();
        }
    }

    private void runSimulationByUser(){
        while (simulationMode != SimulationMode.ENDED) {
            checkSleep();
            setSeconds();
            worldInstance.runSimulationTick(ticks, worldDefinition);
            checkPause();
            ticks++;
        }
    }

    private void checkPause(){
        if(simulationMode == SimulationMode.PAUSED){
            long pauseStartTime = System.currentTimeMillis();
            synchronized (this){
                try{
                    this.wait();
                } catch (Exception ignore){}
            }
            pauseTime += (int)((System.currentTimeMillis() - pauseStartTime) / 1000);
        }
    }

    private void checkSleep(){
        if(worldInstance.getSleep() != null){
            try {
                Thread.sleep(worldInstance.getSleep());
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private void setSeconds(){
        seconds = (int)((System.currentTimeMillis() - startTime) / 1000) - pauseTime;
    }
}
