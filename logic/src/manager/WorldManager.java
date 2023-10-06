package manager;

import details.*;
import details.DtoAction.*;
import entity.EntityDefinition;
import entity.EntityInstance;
import entity.EntityManager;
import enums.SimulationMode;
import factory.FactoryInstance;
import helpers.CheckFunctions;
import helpers.ParseFunctions;
import jaxb.LoadXml;
import newExecution.DtoNewExecution;
import newExecution.DtoStartExecution;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import newExecution.dtoEntities.DtoEntityNames;
import newExecution.dtoEnvironment.DtoEnvironment;
import property.PropertyDefinition;
import property.PropertyInstance;
import results.simulationEnded.*;
import results.simulationFailed.DtoSimulationFailedDetails;
import results.simulationRunningDetails.DtoSimulationRunningDetails;
import results.simulations.DtoSimulationInfo;
import results.simulationRunningDetails.DtoSimulationEntity;
import results.simulations.DtoSimulationsInfoList;
import rule.Activation;
import rule.Rule;
import rule.action.*;
import simulation.Simulation;
import userRequests.Request;
import world.EntityCountGraph;
import world.WorldDefinition;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Simulation> getSimulations() {
        return simulations;
    }

    public void ReadXmlFile(InputStream xmlFile){
        LoadXml loadXml = new LoadXml();
        worldDefinition = loadXml.loadAndValidateXml(xmlFile);
        worldName = loadXml.getPrdWorld().getName();
        simulationCount = 0;
    }

    private List<DtoProperty> createDtoEntityPropertyList(Map<String, PropertyDefinition> propertyDefinitionMap) {
        List<DtoProperty> dtoEntity = new ArrayList<>();
        for (PropertyDefinition prop: propertyDefinitionMap.values()) {
            dtoEntity.add(createEntityPropertyDto(prop) );
        }
        return dtoEntity;
    }

    private DtoProperty createEntityPropertyDto(PropertyDefinition prop) {
        if(prop.getRange() == null){
            return new DtoProperty(prop.getName(), prop.getType().toString().toLowerCase(), prop.isRandomInit(), 0.0, 0.0);
        } else {
            return new DtoProperty(prop.getName(), prop.getType().toString().toLowerCase(), prop.isRandomInit(), prop.getRange().getFrom(), prop.getRange().getTo());
        }
    }

    public DtoAction createDtoAction(Action action){
        switch (action.getClass().getSimpleName()) {
            case "Calculation":
                Calculation calculation = (Calculation) action;
                return new DtoAction("Calculation", action.getMainEntityName(), action.getSecondaryEntityName(), createDtoCalculation(calculation),
                        null, null, null, null, null, null, null);
            case "SingleCondition":
                SingleCondition singleCondition = (SingleCondition) action;
                return new DtoAction("Single Condition", action.getMainEntityName(), action.getSecondaryEntityName(), null, null, null, null,
                        null, null, null, createDtoSingleCondition(singleCondition));
            case "MultipleCondition":
                MultipleCondition multipleCondition = (MultipleCondition) action;
                return new DtoAction("Multiple Condition", action.getMainEntityName(), action.getSecondaryEntityName(), null, null, null,
                        createDtoMultipleCondition(multipleCondition), null, null, null, null);
            case "Proximity":
                Proximity proximity = (Proximity) action;
                return new DtoAction("Proximity", action.getMainEntityName(), action.getSecondaryEntityName(), null, null, null,
                        null, createDtoProximity(proximity), null, null, null);
            case "Kill":
                return new DtoAction("Kill", action.getMainEntityName(), action.getSecondaryEntityName(), null, null, null,
                        null, null, null, null, null);
            case "Replace":
                Replace replace = (Replace) action;
                return new DtoAction("Replace",action.getMainEntityName(), replace.getEntityNameToCreate(), null, null, null, null,
                        null, createDtoReplace(replace), null, null);
            case "Decrease":
                Decrease decrease = (Decrease) action;
                return new DtoAction("Decrease", action.getMainEntityName(), action.getSecondaryEntityName(), null, createDtoDecrease(decrease), null, null,
                        null, null, null, null);
            case "Increase":
                Increase increase = (Increase) action;
                return new DtoAction("Increase", action.getMainEntityName(), action.getSecondaryEntityName(), null, null, createDtoIncrease(increase),
                        null, null, null, null, null);
            case "Set":
                Set set = (Set) action;
                return new DtoAction("Set", action.getMainEntityName(), action.getSecondaryEntityName(), null, null, null, null,
                       null, null, createDtoSet(set), null);
        }
        return null;
    }

    private DtoCalculation createDtoCalculation(Calculation calculation){
        return new DtoCalculation(calculation.getArg1String(),calculation.getArg2String(), calculation.getArithmeticString());
    }

    private DtoSingleCondition createDtoSingleCondition(SingleCondition singleCondition){
        return new DtoSingleCondition(singleCondition.getPropertyString(), singleCondition.getValueString(), singleCondition.getOperatorString());
    }

    private DtoMultipleCondition createDtoMultipleCondition(MultipleCondition multipleCondition){
        return new DtoMultipleCondition(multipleCondition.getLogicString(), multipleCondition.getConditionsAmount());
    }

    private DtoProximity createDtoProximity(Proximity proximity){
        return new DtoProximity(proximity.getDepth(), proximity.getActionAmount());
    }

    private DtoReplace createDtoReplace(Replace replace){
        return new DtoReplace(replace.getMode().toString());
    }

    private DtoDecrease createDtoDecrease(Decrease decrease){
        return new DtoDecrease(decrease.getPropertyName(), decrease.getExpressionString());
    }

    private DtoIncrease createDtoIncrease(Increase increase){
        return new DtoIncrease(increase.getPropertyName(),increase.getExpressionString());
    }

    private DtoSet createDtoSet(Set set){
        return new DtoSet(set.getExpressionString(), set.getPropertyName());
    }

    private DtoActivation createDtoActivation(Activation activation){
        return new DtoActivation(activation.getTicks(), activation.getProbability());
    }

    public DtoWorldInfo getDtoWorldInfo(){
        Map<String, DtoEntityInfo> dtoEntityInfoMap = createDtoEntityMap();
        Map<String, DtoRuleInfo> dtoRuleInfoMap = createDtoRuleMap();
        List<DtoEnvironmentInfo>  dtoEnvironmentInfoList = createDtoEnvironmentInfoList();
        DtoGridInfo dtoGridInfo = createDtoGridInfo();
        return new DtoWorldInfo(dtoEntityInfoMap, dtoRuleInfoMap, dtoEnvironmentInfoList, dtoGridInfo, worldName);
    }

    private DtoGridInfo createDtoGridInfo() {
        return new DtoGridInfo(worldDefinition.getGrid().getRows(), worldDefinition.getGrid().getRows());
    }

    private Map<String, DtoEntityInfo> createDtoEntityMap() {
        Map<String, DtoEntityInfo> dtoEntityInfoMap = new HashMap<>();
        for (EntityDefinition entity : worldDefinition.getEntities().values()) {
            dtoEntityInfoMap.put(entity.getName() , createDtoEntityMap(entity));
        }
        return dtoEntityInfoMap;
    }

    private DtoEntityInfo createDtoEntityMap(EntityDefinition entityDefinition) {
        return new DtoEntityInfo(entityDefinition.getName(), createDtoEntityPropertyList(entityDefinition.getPropertiesOfAllPopulation()));
    }

    private DtoRuleInfo createDtoRule(Rule rule){
        List<DtoAction> dtoAction = new ArrayList<>();

        for (Action action : rule.getActionList()) {
            dtoAction.add(createDtoAction(action));
        }

        return new DtoRuleInfo(rule.getName(), createDtoActivation(rule.getActivation()), dtoAction);
    }

    private Map<String, DtoRuleInfo> createDtoRuleMap(){
        Map<String, DtoRuleInfo> dtoRuleInfoMap = new HashMap<>();

        for (Rule rule : worldDefinition.getRules()) {
            dtoRuleInfoMap.put(rule.getName(),createDtoRule(rule));
        }
        return dtoRuleInfoMap;
    }

    private List<DtoEnvironmentInfo> createDtoEnvironmentInfoList(){
        List<DtoEnvironmentInfo> dtoEnvironmentInfoList = new ArrayList<>();

        for (PropertyDefinition environment : worldDefinition.getEnvironmentVariables().getProperties().values()) {
            if(environment.getRange() != null){
                dtoEnvironmentInfoList.add(new DtoEnvironmentInfo(environment.getName(), environment.getType().toString().toLowerCase(),
                        environment.getRange().getFrom(), environment.getRange().getTo()));
            } else{
                dtoEnvironmentInfoList.add(new DtoEnvironmentInfo(environment.getName(), environment.getType().toString().toLowerCase(), 0.0, 0.0));
            }
        }

        return dtoEnvironmentInfoList;
    }

    private DtoEntityNames createEntityNameDto(){
        List<String> entityNames = new ArrayList<>();

        for(EntityDefinition entityDefinition : worldDefinition.getEntities().values()){
            entityNames.add(entityDefinition.getName());
        }

        return new DtoEntityNames(entityNames);
    }

    public DtoNewExecution createDtoNewExecution(){
        return new DtoNewExecution(createEntityNameDto(), createDtoEnvironment(), worldDefinition.getGrid().getRows() * worldDefinition.getGrid().getCols());
    }

    private List<DtoEnvironment> createDtoEnvironment(){
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

//    public DtoStartExecution createDtoRerunExecution(Integer simulationId){
//        return new DtoStartExecution(createDtoEntitiesPopulationList(simulationId), createDtoEnvironmentInitializeList(simulationId), simulationCount);
//    }

    private List<DtoEnvironmentInitialize> createDtoEnvironmentInitializeList(Integer simulationId) {
        List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList = new ArrayList<>();

        for(PropertyInstance environment : simulations.get(simulationId).getWorldInstance().getEnvironmentVariables().getProperties().values()){
                dtoEnvironmentInitializeList.add(new DtoEnvironmentInitialize(environment.getName(), environment.getCurrValue()));
        }

        return dtoEnvironmentInitializeList;
    }

    private List<DtoEntitiesPopulation> createDtoEntitiesPopulationList(Integer simulationId) {
        List<DtoEntitiesPopulation> dtoEntitiesPopulationList = new ArrayList<>();

        for(EntityDefinition entityDefinition : simulations.get(simulationId).getWorldDefinition().getEntities().values()){
            dtoEntitiesPopulationList.add(new DtoEntitiesPopulation(entityDefinition.getName(), entityDefinition.getPopulation()));
        }

        return dtoEntitiesPopulationList;
    }

    public synchronized DtoStartExecution createNewSimulation(DtoStartExecution dtoSendExecution, Request request) {
        WorldDefinition newWorldDefinition = new WorldDefinition(worldDefinition);
        updateEnvironment(dtoSendExecution.getDtoEnvironmentInitializeList(), newWorldDefinition);
        updateEntitiesPopulation(dtoSendExecution.getDtoEntitiesPopulationList(), newWorldDefinition);
        Simulation simulation = new Simulation(simulationCount, FactoryInstance.createWorldInstance(newWorldDefinition), newWorldDefinition, request.getTermination(), request);
        simulations.add(simulationCount, simulation);
        DtoStartExecution dtoStartExecution = createDtoStartExecution(simulationCount, request.getId());
        simulationCount++;

        return dtoStartExecution;
    }

    public DtoStartExecution createDtoStartExecution(Integer simulationId, Integer requestId){
        return new DtoStartExecution(createDtoEntitiesPopulationList(simulationId), createDtoEnvironmentInitializeList(simulationId), simulationId, requestId);
    }

    public void simulationRun(ThreadPoolExecutor executorService, Integer simulationId){
        Simulation simulation = simulations.get(simulationId);
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

    public DtoSimulationEndedDetails createDtoSimulationEndedDetails(Integer id){
        Simulation simulation = simulations.get(id);
        return new DtoSimulationEndedDetails(id, simulation.getRequest().getId(), simulation.getStartDateFormat(), createDtoEntityMap(simulation));
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

    public DtoSimulationInfo createDtoSimulationInfo(Integer simulationId){
        Simulation simulation = simulations.get(simulationId);
        return new DtoSimulationInfo(simulation.getId(), simulation.getRequest().getId(), simulation.getSimulationMode().toString().toLowerCase());
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

    public void stopSimulation(Integer simulationId) {
        Simulation simulation = simulations.get(simulationId);
        if(simulation.getSimulationMode() == SimulationMode.PAUSE){
            simulations.get(simulationId).setSimulationMode(SimulationMode.ENDED);
            synchronized (simulation){
                simulation.notify();
            }
        } else{
            simulations.get(simulationId).setSimulationMode(SimulationMode.ENDED);
        }
    }

    public void pauseSimulation(Integer simulationId){
        simulations.get(simulationId).setSimulationMode(SimulationMode.PAUSE);
    }

    public void resumeSimulation(Integer simulationId){
        Simulation simulation = simulations.get(simulationId);
        simulation.setSimulationMode(SimulationMode.RUNNING);
        synchronized (simulation){
            simulation.notify();
        }
    }

    public DtoSimulationRunningDetails createDtoSimulationRunningDetails(Integer simulationId) {
        Simulation simulation = simulations.get(simulationId);
        return new DtoSimulationRunningDetails(simulationId, simulation.getRequest().getId(), simulation.getSimulationMode().toString().toLowerCase(),
                simulation.getStartDateFormat(), simulation.getTicks(), simulation.getSeconds(), createDtoSimulationEntityList(simulation));
    }

    public DtoSimulationFailedDetails createDtoSimulationFailedDetails(Integer simulationId) {
        Simulation simulation = simulations.get(simulationId);
        return new DtoSimulationFailedDetails(simulationId, simulation.getRequest().getId(), simulation.getStartDateFormat(), simulation.getFailedReason());
    }
}



