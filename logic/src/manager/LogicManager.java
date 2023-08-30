package manager;

import details.DtoEntityInfo;
import details.DtoRuleInfo;
import details.dtoAction.DtoAction;
import details.dtoAction.DtoCalculation;
import details.dtoAction.DtoMultipleCondition;
import details.dtoAction.DtoSimpleCondition;
import menuChoice2.DtoRange;
import entity.EntityDefinition;
import entity.EntityInstance;
import entity.EntityManager;
import exceptions.InvalidNameException;
import exceptions.SimulationFailedException;
import factory.FactoryInstance;
import jaxb.LoadXml;
import menuChoice2.*;
import details.DtoEnvironmentInfo;
import menuChoice3.DtoSimulationDetails;
import menuChoice3.DtoEnvironmentInitialize;
import menuChoice4.*;
import menuChoice5or6.DtoFilePath;
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

        for (Rule rule : worldDefinition.getRules()) {
            dtoRules.add(createDtoRule(rule));
        }

        return new DtoWorldInfo1(dtoEntity, dtoRules, createDtoTermination());
    }

    private List<DtoEntityInfo> createDtoEntityList() {
        List<DtoEntityInfo> dtoEntity = new ArrayList<>();
        for (EntityDefinition entity : worldDefinition.getEntities().values()) {
            dtoEntity.add(createDtoEntity(entity));
        }

        return dtoEntity;
    }

    private DtoEntityInfo createDtoEntity(EntityDefinition entityDefinition) {
        return new DtoEntityInfo(entityDefinition.getName(), createDtoEntityPropertyMap(entityDefinition.getPropertiesOfAllPopulation()));
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

    private DtoRange createDtoRange(Range range){
        return new DtoRange(range.getFrom(), range.getTo());
    }

    private DtoRuleInfo createDtoRule(Rule rule){
        List<DtoAction> dtoAction = new ArrayList<>();

        for (Action action : rule.getActionList()) {
            dtoAction.add(createDtoAction(action));
        }

        return new DtoRuleInfo(rule.getName(), createDtoActivation(rule.getActivation()), dtoAction);
    }

    private DtoAction createDtoAction(Action action){
        switch (action.getClass().getSimpleName()) {
            case "Calculation":
                Calculation calculation = (Calculation) action;
                DtoCalculation dtoCalculation = new DtoCalculation(calculation.getArg1String(),calculation.getArg2String(), calculation.getArithmeticString());
                return new DtoAction("Calculation", action.getMainEntityName(), action.getSecondaryEntityName(),null,
                        dtoCalculation, null, null, null);
            case "SingleCondition":
                SingleCondition singleCondition = (SingleCondition) action;
                DtoSimpleCondition dtoSimpleCondition = new DtoSimpleCondition(singleCondition.getPropertyString(),
                        singleCondition.getValueString(), singleCondition.getOperatorString());
                return new DtoAction("Single Condition", action.getMainEntityName(), action.getSecondaryEntityName(),null,
                        null,dtoSimpleCondition, null, null);
            case "MultipleCondition":
                MultipleCondition multipleCondition = (MultipleCondition) action;
                DtoMultipleCondition dtoMultipleCondition = new DtoMultipleCondition(multipleCondition.getLogicString(), multipleCondition.getConditionsAmount());
                return new DtoAction("Multiple Condition", action.getMainEntityName(), action.getSecondaryEntityName(),null,
                        null, null, dtoMultipleCondition, null);
            case "Proximity":
                //todo
                break;
            case "Kill":
                return new DtoAction("Kill",action.getMainEntityName(), action.getSecondaryEntityName(),"Not Exist",
                        null, null, null, null);
            case "Replace":
                return new DtoAction("Replace",action.getMainEntityName(), action.getSecondaryEntityName(),"Not Exist",
                        null, null, null, null);
            case "Decrease":
                Decrease decrease = (Decrease) action;
                return new DtoAction("Decrease",action.getMainEntityName(), action.getSecondaryEntityName(),decrease.getExpressionString(),
                        null, null, null, null);
            case "Increase":
                Increase increase = (Increase) action;
                return new DtoAction("Increase",action.getMainEntityName(), action.getSecondaryEntityName(),increase.getExpressionString(),
                        null, null, null, null);
            case "Set":
                Set set = (Set) action;
                return new DtoAction("Set",action.getMainEntityName(), action.getSecondaryEntityName(),set.getExpressionString(),
                        null, null, null, null);
        }
        return null;
    }

    private DtoActivation createDtoActivation(Activation activation){
        return new DtoActivation(activation.getTicks(), activation.getProbability());
    }

    private DtoTermination createDtoTermination(){
        return new DtoTermination(worldDefinition.getTermination().getTicks(), worldDefinition.getTermination().getSeconds());
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




}
