package manager;

import details.*;
import details.DtoAction.*;
import entity.EntityDefinition;
import entity.EntityInstance;
import entity.EntityManager;
import exceptions.InvalidNameException;
import exceptions.SimulationFailedException;
import factory.FactoryInstance;
import jaxb.LoadXml;
import menuChoice2.*;
import menuChoice3.DtoSimulationDetails;
import menuChoice3.DtoEnvironmentInitialize;
import menuChoice4.*;
import menuChoice5or6.DtoFilePath;
import newExecution.EntityNamesDto;
import newExecution.GridDto;
import property.PropertyDefinition;
import property.Range;
import rule.Activation;
import rule.Rule;
import rule.action.*;
import simulation.Simulation;
import world.WorldDefinition;
import menuChoice1.DtoXmlPath;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicManager {
    private WorldDefinition worldDefinition;
    private List<Simulation> simulations;
    private Integer simulationCount = 0;

    public WorldDefinition getWorldDefinition() {
        return worldDefinition;
    }

    public List<Simulation> getSimulations() {
        return simulations;
    }

    public Integer getSimulationCount() {
        return simulationCount;
    }

    public LogicManager() {
        simulations = new ArrayList<>();
    }

    //choice 1
    public void ReadXmlFile(DtoXmlPath dtoXmlPath){
        LoadXml loadXml = new LoadXml();
        worldDefinition = loadXml.loadAndValidateXml(dtoXmlPath.getPath());
        simulationCount = 0;
        simulations.clear();
    }

    //choice 2
    public DtoWorldInfo1 displayWorld(){
        List<DtoEntityInfo> dtoEntity = createDtoEntityList();
        List<DtoRuleInfo> dtoRules = new ArrayList<>();



        return new DtoWorldInfo1(dtoEntity, dtoRules, createDtoTermination());
    }

    private List<DtoEntityInfo> createDtoEntityList() {
        List<DtoEntityInfo> dtoEntity = new ArrayList<>();
        for (EntityDefinition entity : worldDefinition.getEntities().values()) {
            dtoEntity.add(createDtoEntity(entity));
        }

        return dtoEntity;
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



    // todo change,do func inside each dto
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
                return new DtoKill("Kill",action.getMainEntityName(), action.getSecondaryEntityName());

            case "Replace":
                return new DtoReplace("Replace",action.getMainEntityName(),"Not Exist");

            case "Decrease":
                Decrease decrease = (Decrease) action;
                return new DtoDecrease("Decrease",action.getMainEntityName(), action.getSecondaryEntityName(),
                        decrease.getPropertyName(), decrease.getExpressionString());

            case "Increase":
                Increase increase = (Increase) action;
                return new DtoIncrease("Increase",action.getMainEntityName(), action.getSecondaryEntityName(),
                        increase.getPropertyName(),increase.getExpressionString());

            case "Set":
                Set set = (Set) action;
                return new DtoSet("Set",action.getMainEntityName(), action.getSecondaryEntityName(),
                        set.getExpressionString(), set.getPropertyName());
        }
        return null;
    }

    private DtoActivation createDtoActivation(Activation activation){
        return new DtoActivation(activation.getTicks(), activation.getProbability());
    }



    //choice 3
    public List<DtoEnvironmentInfo> displayEnvironment(){
        List<DtoEnvironmentInfo> environmentDetails = new ArrayList<>();

        for (PropertyDefinition environment : worldDefinition.getEnvironmentVariables().getProperties().values()) {
            if(environment.getRange() != null){
                environmentDetails.add(new DtoEnvironmentInfo(environment.getName(), environment.getType().toString().toLowerCase(),
                        createDtoRange(environment.getRange())));
            } else{
                environmentDetails.add(new DtoEnvironmentInfo(environment.getName(), environment.getType().toString().toLowerCase(),
                        null));
            }
        }

        return environmentDetails;
    }

    public void updateEnvironment(List<DtoEnvironmentInitialize> environments){
        for (DtoEnvironmentInitialize environment : environments) {
            worldDefinition.getEnvironmentVariables().getProperties().get(environment.getName()).setInit(environment.getValue());
            worldDefinition.getEnvironmentVariables().getProperties().get(environment.getName()).setRandomInit(false);
        }
    }

    public DtoSimulationDetails simulationRun(){
        Simulation simulation = new Simulation(simulationCount, FactoryInstance.createWorldInstance(worldDefinition), worldDefinition);
        try {
            simulation.runSimulation();
            for (PropertyDefinition environment : worldDefinition.getEnvironmentVariables().getProperties().values()) {
                environment.setRandomInit(true);
            }
        } catch (Exception e){
            throw new SimulationFailedException(e.getMessage(), simulationCount);
        }
        simulations.add(simulationCount, simulation);
        DtoSimulationDetails simulationDetails = creteSimulationDetails();
        simulationCount++;
        return simulationDetails;
    }

    private DtoSimulationDetails creteSimulationDetails() {
        return new DtoSimulationDetails(simulationCount, simulations.get(simulationCount).getTerminationReason().toString().toLowerCase());
    }

    //choice 4
    public List<DtoPastSimulationDetails> createPastSimulationDetails(){
        List<DtoPastSimulationDetails> pastSimulationDetails = new ArrayList<>();

        for (Simulation simulation : simulations) {
            pastSimulationDetails.add(new DtoPastSimulationDetails(simulation.getStartDateFormat(), simulation.getId()));
        }

        return pastSimulationDetails;
    }

    public List<DtoAmountOfEntity> createAmountOfEntitiesDetails(DtoSimulationIdChoice simulationId){
        List<DtoAmountOfEntity> amountOfEntities = new ArrayList<>();

        for (EntityManager entity : simulations.get(simulationId.getId()).getWorldInstance().getEntities().values()) {
            amountOfEntities.add(new DtoAmountOfEntity(entity.getName(), worldDefinition.getEntities().get(entity.getName()).getPopulation(),
                    entity.getEntityInstance().size()));
        }

        return amountOfEntities;
    }

    public List<DtoEntityName> createEntitiesNamesList() {
        List<DtoEntityName> entityNames = new ArrayList<>();

        for (EntityDefinition entity : worldDefinition.getEntities().values()) {
            entityNames.add(new DtoEntityName(entity.getName()));
        }

        return entityNames;
    }

    public List<DtoPropertyName> createPropertiesNamesListByEntity(DtoEntityName entityName) {
        List<DtoPropertyName> propertyNames = new ArrayList<>();

        for (PropertyDefinition property : worldDefinition.getEntities().get(entityName.getName()).getPropertiesOfAllPopulation().values()) {
            propertyNames.add(new DtoPropertyName(property.getName()));
        }

        return propertyNames;
    }

    public Map<String, DtoPropertyHistogram> createPropertyHistogram(DtoEntityName entityName, DtoPropertyName propertyName,
                                                        DtoSimulationIdChoice simulationId){
        Map<String, DtoPropertyHistogram> amountOfValues = new HashMap<>();
        DtoPropertyHistogram propertyValueDetails;

        for (EntityInstance entity :
                simulations.get(simulationId.getId()).getWorldInstance().getEntities().get(entityName.getName()).getEntityInstance()) {
            String propertyValueName = entity.getProperties().get(propertyName.getName()).getValue().toString();
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

    //choice 5
    public void saveSimulationsToFile(DtoFilePath filePath){
        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             Files.newOutputStream(Paths.get(filePath.getFilePath() + ".txt")))) {
            out.writeObject(worldDefinition);
            out.writeObject(simulations);
            out.writeObject(simulationCount);
            out.flush();
        } catch (Exception e) {
            throw new InvalidNameException("file path not found. ");
        }
    }

    //choice 6
    public void loadSimulationsFromFile(DtoFilePath filePath){
        if(!filePath.getFilePath().endsWith(".txt")){
            throw new InvalidNameException("file name must end with .txt");
        }
        try (ObjectInputStream in =
                     new ObjectInputStream(
                             Files.newInputStream(Paths.get(filePath.getFilePath())))) {
            worldDefinition = (WorldDefinition) in.readObject();
            simulations = (List<Simulation>) in.readObject();
            simulationCount = (Integer) in.readObject();
        } catch (Exception e) {
            throw new InvalidNameException("file path not found. ");
        }
    }

    //----------------------------------------------------------------------------------------

    public DtoEnvironmentInfo getDtoEnvironmentInfo(String name){
        PropertyDefinition environmentDefinition = worldDefinition.getEnvironmentVariables().getProperties().get(name);

        DtoRange dtoRange = new DtoRange(environmentDefinition.getRange().getFrom(), environmentDefinition.getRange().getTo());
        return new DtoEnvironmentInfo(environmentDefinition.getName(), environmentDefinition.getType().toString(), dtoRange) ;
    }


    public DtoWorldInfo getDtoWorldInfo(){
        Map<String, DtoEntityInfo> dtoEntityInfoMap = createDtoEntityMap();
        Map<String, DtoRuleInfo> dtoRuleInfoMap = createDtoRuleMap();
        DtoTermination dtoTermination = createDtoTermination();
        Map<String,DtoEnvironmentInfo>  dtoEnvironmentInfoMap= createDtoEnvironmentInfoMap();
        return new DtoWorldInfo(dtoEntityInfoMap,dtoRuleInfoMap,dtoTermination,dtoEnvironmentInfoMap);
    }


    private Map<String, DtoEntityInfo> createDtoEntityMap() {
        Map<String, DtoEntityInfo> dtoEntityInfoMap = new HashMap<>();
        for (EntityDefinition entity : worldDefinition.getEntities().values()) { // null !
            dtoEntityInfoMap.put(entity.getName() ,createDtoEntity(entity));
        }
        return dtoEntityInfoMap;
    }
    public DtoEntityInfo createDtoEntity(EntityDefinition entityDefinition) {
        return new DtoEntityInfo(entityDefinition.getName(), createDtoEntityPropertyMap(entityDefinition.getPropertiesOfAllPopulation()));
    }

    public DtoRuleInfo createDtoRule(Rule rule){
        Map<String,DtoAction> dtoAction = new HashMap<>();

        for (Action action : rule.getActionList()) {
            dtoAction.put(action.getClass().getSimpleName(),createDtoAction(action));
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

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public EntityNamesDto createEntityNameDto(){
        List<String> entityNames = new ArrayList<>();

        for(EntityDefinition entityDefinition : worldDefinition.getEntities().values()){
            entityNames.add(entityDefinition.getName());
        }

        return new EntityNamesDto(entityNames);
    }

    public GridDto createGridDto(){
        return new GridDto(worldDefinition.getGrid().getRows(), worldDefinition.getGrid().getCols());
    }
}


