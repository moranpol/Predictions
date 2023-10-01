package manager;

import details.*;
import details.DtoAction.*;
import entity.EntityDefinition;
import entity.EntityInstance;
import entity.EntityManager;
import enums.SimulationMode;
import factory.FactoryInstance;
import header.DtoSimulationQueue;
import helpers.CheckFunctions;
import helpers.ParseFunctions;
import jaxb.LoadXml;
import newExecution.DtoRerunExecution;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import newExecution.dtoEntities.DtoEntityNames;
import newExecution.dtoEntities.DtoGrid;
import newExecution.dtoEnvironment.DtoEnvironment;
import property.PropertyDefinition;
import property.Range;
import results.*;
import results.simulationEnded.*;
import results.simulations.DtoSimulationInfo;
import results.simulations.DtoSimulationEntity;
import rule.Activation;
import rule.Rule;
import rule.action.*;
import simulation.Simulation;
import world.EntityCountGraph;
import world.WorldDefinition;
import header.DtoXmlPath;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WorldManager {
    private String worldName;
    private WorldDefinition worldDefinition;
    private final List<Simulation> simulations;
    private Integer simulationCount = 0;

    public WorldManager() {
        simulations = new ArrayList<>();
    }

    public String getWorldName() {
        return worldName;
    }

    public void ReadXmlFile(InputStream xmlFile){
        LoadXml loadXml = new LoadXml();
        worldDefinition = loadXml.loadAndValidateXml(xmlFile);
        worldName = loadXml.getPrdWorld().getName();
        simulationCount = 0;
    }

    private Map<String,DtoProperty> createDtoEntityPropertyMap(Map<String, PropertyDefinition> propertyDefinitionMap) {
        Map<String,DtoProperty> dtoEntity = new HashMap<>();
        for (PropertyDefinition prop: propertyDefinitionMap.values()) {
            dtoEntity.put(prop.getName(),createEntityPropertyDto(prop) );
        }
        return dtoEntity;
    }

    private DtoProperty createEntityPropertyDto(PropertyDefinition prop) {
        if(prop.getRange() == null){
            return new DtoProperty(prop.getName(), prop.getType().toString().toLowerCase(), null, prop.isRandomInit());
        } else {
            return new DtoProperty(prop.getName(), prop.getType().toString().toLowerCase(), createDtoRange(prop.getRange()),
                    prop.isRandomInit());
        }
    }

    public DtoRange createDtoRange(Range range){
        return new DtoRange(range.getFrom(), range.getTo());
    }

    public DtoAction createDtoAction(Action action){
        switch (action.getClass().getSimpleName()) {
            case "Calculation":
                Calculation calculation = (Calculation) action;
                return new DtoCalculation("Calculation", action.getMainEntityName(), action.getSecondaryEntityName(),
                        calculation.getArg1String(),calculation.getArg2String(), calculation.getArithmeticString());
            case "SingleCondition":
                SingleCondition singleCondition = (SingleCondition) action;
                return new DtoSimpleCondition("Single Condition", action.getMainEntityName(), action.getSecondaryEntityName(),
                        singleCondition.getPropertyString(), singleCondition.getValueString(), singleCondition.getOperatorString());
            case "MultipleCondition":
                MultipleCondition multipleCondition = (MultipleCondition) action;
                return new DtoMultipleCondition("Multiple Condition", action.getMainEntityName(), action.getSecondaryEntityName(),
                        multipleCondition.getLogicString(), multipleCondition.getConditionsAmount());
            case "Proximity":
                Proximity proximity = (Proximity) action;
                return new DtoProximity("Proximity", action.getMainEntityName(), action.getSecondaryEntityName(),
                        proximity.getDepth(), proximity.getActionAmount());
            case "Kill":
                return new DtoKill("Kill", action.getMainEntityName(), action.getSecondaryEntityName());
            case "Replace":
                return new DtoReplace("Replace",action.getMainEntityName(), action.getSecondaryEntityName());
            case "Decrease":
                Decrease decrease = (Decrease) action;
                return new DtoDecrease("Decrease", action.getMainEntityName(), action.getSecondaryEntityName(),
                        decrease.getPropertyName(), decrease.getExpressionString());
            case "Increase":
                Increase increase = (Increase) action;
                return new DtoIncrease("Increase", action.getMainEntityName(), action.getSecondaryEntityName(),
                        increase.getPropertyName(),increase.getExpressionString());
            case "Set":
                Set set = (Set) action;
                return new DtoSet("Set", action.getMainEntityName(), action.getSecondaryEntityName(),
                        set.getExpressionString(), set.getPropertyName());
        }
        return null;
    }

    private DtoActivation createDtoActivation(Activation activation){
        return new DtoActivation(activation.getTicks(), activation.getProbability());
    }

    public DtoWorldInfo getDtoWorldInfo(){
        Map<String, DtoEntityInfo> dtoEntityInfoMap = createDtoEntityMap();
        Map<String, DtoRuleInfo> dtoRuleInfoMap = createDtoRuleMap();
        DtoTermination dtoTermination = createDtoTermination();
        Map<String,DtoEnvironmentInfo>  dtoEnvironmentInfoMap = createDtoEnvironmentInfoMap();
        DtoGridInfo dtoGridInfo = createDtoGridInfo();
        return new DtoWorldInfo(worldName, dtoEntityInfoMap, dtoRuleInfoMap, dtoTermination, dtoEnvironmentInfoMap, dtoGridInfo);
    }

    private DtoGridInfo createDtoGridInfo() {
        return new DtoGridInfo(worldDefinition.getGrid().getRows(), worldDefinition.getGrid().getRows());
    }

    private Map<String, DtoEntityInfo> createDtoEntityMap() {
        Map<String, DtoEntityInfo> dtoEntityInfoMap = new HashMap<>();
        for (EntityDefinition entity : worldDefinition.getEntities().values()) { // null !
            dtoEntityInfoMap.put(entity.getName() , createDtoEntityMap(entity));
        }
        return dtoEntityInfoMap;
    }

    public DtoEntityInfo createDtoEntityMap(EntityDefinition entityDefinition) {
        return new DtoEntityInfo(entityDefinition.getName(), createDtoEntityPropertyMap(entityDefinition.getPropertiesOfAllPopulation()));
    }

    public DtoRuleInfo createDtoRule(Rule rule){
        List<DtoAction> dtoAction = new ArrayList<>();

        for (Action action : rule.getActionList()) {
            dtoAction.add(createDtoAction(action));
        }

        return new DtoRuleInfo(rule.getName(), createDtoActivation(rule.getActivation()), dtoAction);
    }

    public Map<String, DtoRuleInfo> createDtoRuleMap(){
        Map<String, DtoRuleInfo> dtoRuleInfoMap = new HashMap<>();

        for (Rule rule : worldDefinition.getRules()) {
            dtoRuleInfoMap.put(rule.getName(),createDtoRule(rule));
        }
        return dtoRuleInfoMap;
    }

    public DtoTermination createDtoTermination(){
        return new DtoTermination(worldDefinition.getTermination().getTicks(), worldDefinition.getTermination().getSeconds(), worldDefinition.getTermination().getHuman());
    }

    public Map<String,DtoEnvironmentInfo> createDtoEnvironmentInfoMap(){
        Map<String,DtoEnvironmentInfo> dtoEnvironmentInfoMap = new HashMap<>();

        for (PropertyDefinition environment : worldDefinition.getEnvironmentVariables().getProperties().values()) {
            if(environment.getRange() != null){
                dtoEnvironmentInfoMap.put(environment.getName(),new DtoEnvironmentInfo(environment.getName(), environment.getType().toString().toLowerCase(),
                        createDtoRange(environment.getRange())));
            } else{
                dtoEnvironmentInfoMap.put(environment.getName(), new DtoEnvironmentInfo(environment.getName(), environment.getType().toString().toLowerCase(),
                        null));
            }
        }

        return dtoEnvironmentInfoMap;
    }

    public DtoEntityNames createEntityNameDto(){
        List<String> entityNames = new ArrayList<>();

        for(EntityDefinition entityDefinition : worldDefinition.getEntities().values()){
            entityNames.add(entityDefinition.getName());
        }

        return new DtoEntityNames(entityNames);
    }

    public DtoGrid createGridDto(){
        return new DtoGrid(worldDefinition.getGrid().getRows(), worldDefinition.getGrid().getCols());
    }

    public List<DtoEnvironment> createDtoEnvironment(){
        List<DtoEnvironment> environmentInfos= new ArrayList<>();

        for (PropertyDefinition environment : worldDefinition.getEnvironmentVariables().getProperties().values()){
            if(CheckFunctions.isNumericValue(environment.getType())){
                environmentInfos.add(new DtoEnvironment(environment.getName(), environment.getType().toString().toLowerCase(),
                        environment.getRange().getFrom(), environment.getRange().getTo()));
            } else {
                environmentInfos.add(new DtoEnvironment(environment.getName(), environment.getType().toString().toLowerCase(),null, null));
            }
        }

        return environmentInfos;
    }

    public DtoRerunExecution createDtoRerunExecution(DtoSimulationChoice simulationId){
        return new DtoRerunExecution(createDtoEntitiesPopulationList(simulationId), createDtoEnvironmentInitializeList(simulationId));
    }

    private List<DtoEnvironmentInitialize> createDtoEnvironmentInitializeList(DtoSimulationChoice simulationId) {
        List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList = new ArrayList<>();

        for(PropertyDefinition environment : simulations.get(simulationId.getId()).getWorldDefinition().getEnvironmentVariables().getProperties().values()){
            if(!environment.isRandomInit()){
                dtoEnvironmentInitializeList.add(new DtoEnvironmentInitialize(environment.getName(), environment.getValue()));
            }
        }

        return dtoEnvironmentInitializeList;
    }

    private List<DtoEntitiesPopulation> createDtoEntitiesPopulationList(DtoSimulationChoice simulationId) {
        List<DtoEntitiesPopulation> dtoEntitiesPopulationList = new ArrayList<>();

        for(EntityDefinition entityDefinition : simulations.get(simulationId.getId()).getWorldDefinition().getEntities().values()){
            dtoEntitiesPopulationList.add(new DtoEntitiesPopulation(entityDefinition.getName(), entityDefinition.getPopulation()));
        }

        return dtoEntitiesPopulationList;
    }

    public void startSimulation(ThreadPoolExecutor executorService, List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList, List<DtoEntitiesPopulation> dtoEntitiesPopulationList) {
        WorldDefinition newWorldDefinition = new WorldDefinition(worldDefinition);
        updateEnvironment(dtoEnvironmentInitializeList, newWorldDefinition);
        updateEntitiesPopulation(dtoEntitiesPopulationList, newWorldDefinition);
        Simulation simulation = new Simulation(simulationCount, FactoryInstance.createWorldInstance(newWorldDefinition), newWorldDefinition);
        simulations.add(simulationCount, simulation);
        simulationCount++;
        simulationRun(executorService, simulation);
    }

    public void simulationRun(ThreadPoolExecutor executorService, Simulation simulation){
        executorService.submit(simulation);
    }

    private void updateEnvironment(List<DtoEnvironmentInitialize> environments, WorldDefinition worldDefinition){
        for (DtoEnvironmentInitialize environment : environments) {
            worldDefinition.getEnvironmentVariables().getProperties().get(environment.getName()).setInit(environment.getValue());
            worldDefinition.getEnvironmentVariables().getProperties().get(environment.getName()).setRandomInit(false);
        }
    }

    private void updateEntitiesPopulation(List<DtoEntitiesPopulation> entitiesPopulation, WorldDefinition worldDefinition){
        for (DtoEntitiesPopulation entityPopulation : entitiesPopulation){
            worldDefinition.getEntities().get(entityPopulation.getName()).setPopulation(entityPopulation.getPopulation());
        }
    }

    public DtoSimulationEndedDetails createDtoSimulationEndedDetails(DtoSimulationChoice simulationId){
        Simulation simulation = simulations.get(simulationId.getId());
        return new DtoSimulationEndedDetails(simulation.getId(), simulation.getStartDateFormat(), createDtoEntityMap(simulation));
    }

    private Map<String, DtoSimulationEndedEntity> createDtoEntityMap(Simulation simulation){
        Map<String, DtoSimulationEndedEntity> entityMap = new HashMap<>();

        for (EntityManager entity : simulation.getWorldInstance().getEntities().values()){
            entityMap.put(entity.getName(), new DtoSimulationEndedEntity(entity.getName(),
                    createDtoPropertyMap(worldDefinition.getEntities().get(entity.getName()).getPropertiesOfAllPopulation(), entity.getName(), simulation),
                    createEntityQuantityGraph(simulation, entity.getName())));
        }

        return entityMap;
    }

    private List<DtoEntityQuantityGraph> createEntityQuantityGraph(Simulation simulation, String entityName) {
        EntityCountGraph entityCountGraph = simulation.getWorldInstance().getEntityCountGraphMap().get(entityName);
        List<DtoEntityQuantityGraph> dtoEntityQuantityGraph = new ArrayList<>();
        dtoEntityQuantityGraph.add(new DtoEntityQuantityGraph(1, entityCountGraph.getEntityQuantity().get(0)));

        for(int i = 1; i < entityCountGraph.getEntityQuantity().size() - 1; i++){
            dtoEntityQuantityGraph.add(new DtoEntityQuantityGraph(i * 5000, entityCountGraph.getEntityQuantity().get(i)));
        }
        dtoEntityQuantityGraph.add(new DtoEntityQuantityGraph(simulation.getTicks(), simulation.getWorldInstance().getEntities().get(entityName).getEntityInstance().size()));

        return dtoEntityQuantityGraph;
    }

    private Map<String, DtoPropertyResults> createDtoPropertyMap(Map<String, PropertyDefinition> properties, String entityName, Simulation simulation) {
        Map<String, DtoPropertyResults> propertyResultsMap = new HashMap<>();
        for (PropertyDefinition property : properties.values()){
            if(CheckFunctions.isNumericValue(property.getType())){
                propertyResultsMap.put(property.getName(), new DtoPropertyResults(property.getName(),
                        property.getType().toString().toLowerCase(), property.getValue(),
                        createPropertyHistogram(entityName, property.getName(), simulation),
                        getConsistency(simulation, entityName, property.getName()),
                        getAverageOfPropertyInPopulation(simulation, entityName, property.getName())));
            } else {
                propertyResultsMap.put(property.getName(), new DtoPropertyResults(property.getName(),
                        property.getType().toString().toLowerCase(), property.getValue(),
                        createPropertyHistogram(entityName, property.getName(), simulation),
                        getConsistency(simulation, entityName, property.getName()),null));
            }
        }

        return propertyResultsMap;
    }

    public Map<String, DtoPropertyHistogram> createPropertyHistogram(String entityName, String propertyName, Simulation simulation){
        Map<String, DtoPropertyHistogram> amountOfValues = new HashMap<>();
        DtoPropertyHistogram propertyValueDetails;
        for (EntityInstance entity :
                simulation.getWorldInstance().getEntities().get(entityName).getEntityInstance()) {
            String propertyValueName = entity.getProperties().get(propertyName).getCurrValue().toString();
            if(!amountOfValues.containsKey(propertyValueName)){
                propertyValueDetails = new DtoPropertyHistogram(propertyValueName, 1);
            } else{
                propertyValueDetails = new DtoPropertyHistogram(propertyValueName,
                        amountOfValues.get(propertyValueName).getAmount() + 1);
            }
            amountOfValues.put(propertyValueName, propertyValueDetails);
        }

        return amountOfValues;
    }

    public List<DtoSimulationInfo> createDtoSimulationInfoList(){
        List<DtoSimulationInfo> simulationDetails = new ArrayList<>();
        for (Simulation simulation : simulations) {
            simulationDetails.add(new DtoSimulationInfo(simulation.getId(), simulation.getSimulationMode().toString().toLowerCase(),
                    simulation.getFailedReason(), simulation.getStartDateFormat(), simulation.getTicks(), simulation.getSeconds(), null));
        }

        return simulationDetails;
    }

    public DtoSimulationInfo createDtoSimulationInfo(DtoSimulationChoice simulationId){
        Simulation simulation = simulations.get(simulationId.getId());
        return new DtoSimulationInfo(simulation.getId(), simulation.getSimulationMode().toString().toLowerCase(),
                simulation.getFailedReason(), simulation.getStartDateFormat(), simulation.getTicks(), simulation.getSeconds(), createDtoSimulationEntityList(simulation));
    }

    private List<DtoSimulationEntity> createDtoSimulationEntityList(Simulation simulation) {
        List<DtoSimulationEntity> simulationEntities = new ArrayList<>();
        for(EntityManager entity : simulation.getWorldInstance().getEntities().values()){
            simulationEntities.add(new DtoSimulationEntity(entity.getName(), entity.getEntityInstance().size()));
        }

        return simulationEntities;
    }

    private Float getConsistency(Simulation simulation, String entityName, String propertyName){
        float consistency = 0;
        for(EntityInstance entityInstance : simulation.getWorldInstance().getEntities().get(entityName).getEntityInstance()){
            consistency += entityInstance.getProperties().get(propertyName).getAverageValueCounterByTicks();
        }

        int population = simulation.getWorldInstance().getEntities().get(entityName).getEntityInstance().size();
        if(population == 0){
            return null;
        }
        return consistency / population;
    }

    private Float getAverageOfPropertyInPopulation(Simulation simulation, String entityName, String propertyName){
        float average = 0;
        for(EntityInstance entityInstance : simulation.getWorldInstance().getEntities().get(entityName).getEntityInstance()){
            average += ParseFunctions.parseNumericTypeToFloat(entityInstance.getProperties().get(propertyName).getCurrValue());
        }

        int population = simulation.getWorldInstance().getEntities().get(entityName).getEntityInstance().size();
        if(population == 0){
            return null;
        }
        return average / population;
    }

    public void stopSimulation(DtoSimulationChoice simulationChoice) {
        Simulation simulation = simulations.get(simulationChoice.getId());
        if(simulation.getSimulationMode() == SimulationMode.PAUSE){
            simulations.get(simulationChoice.getId()).setSimulationMode(SimulationMode.ENDED);
            synchronized (simulation){
                simulation.notify();
            }
        } else{
            simulations.get(simulationChoice.getId()).setSimulationMode(SimulationMode.ENDED);
        }
    }

    public void pauseSimulation(DtoSimulationChoice simulationChoice){
        simulations.get(simulationChoice.getId()).setSimulationMode(SimulationMode.PAUSE);
    }

    public void resumeSimulation(DtoSimulationChoice simulationChoice){
        Simulation simulation = simulations.get(simulationChoice.getId());
        simulation.setSimulationMode(SimulationMode.RUNNING);
        synchronized (simulation){
            simulation.notify();
        }
    }

    public void futureSimulation(DtoSimulationChoice simulationChoice){
        Simulation simulation = simulations.get(simulationChoice.getId());
        simulation.setSimulationMode(SimulationMode.FUTURE);
        synchronized (simulation){
            simulation.notify();
        }
    }


}



