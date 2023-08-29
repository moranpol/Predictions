package manager;

import entity.EntityDefinition;
import entity.EntityInstance;
import entity.EntityManager;
import exceptions.InvalidNameException;
import exceptions.SimulationFailedException;
import factory.FactoryInstance;
import jaxb.LoadXml;
import menuChoice2.*;
import menuChoice3.DtoEnvironmentDetails;
import menuChoice3.DtoSimulationDetails;
import menuChoice3.DtoEnvironmentInitialize;
import menuChoice4.*;
import menuChoice5or6.DtoFilePath;
import property.PropertyDefinition;
import property.Range;
import rule.Activation;
import rule.Rule;
import rule.action.Action;
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
    public DtoWorldInfo displayWorld(){
        List<DtoEntity> dtoEntity = createDtoEntityList();
        List<DtoRule> dtoRules = new ArrayList<>();

        for (Rule rule : worldDefinition.getRules()) {
            dtoRules.add(createDtoRule(rule));
        }

        return new DtoWorldInfo(dtoEntity, dtoRules, createDtoTermination());
    }

    private List<DtoEntity> createDtoEntityList() {
        List<DtoEntity> dtoEntity = new ArrayList<>();
        for (EntityDefinition entity : worldDefinition.getEntities().values()) {
            dtoEntity.add(createDtoEntity(entity));
        }

        return dtoEntity;
    }

    private DtoEntity createDtoEntity(EntityDefinition entityDefinition) {
        return new DtoEntity(entityDefinition.getName(), entityDefinition.getPopulation(),
                createDtoEntityPropertyList(entityDefinition.getPropertiesOfAllPopulation()));
    }

    private List<DtoProperty> createDtoEntityPropertyList(Map<String, PropertyDefinition> propertyDefinitionMap) {
        List<DtoProperty> dtoEntity = new ArrayList<>();
        for (PropertyDefinition prop: propertyDefinitionMap.values()) {
            dtoEntity.add(createEntityProperty(prop));
        }

        return dtoEntity;
    }

    private DtoProperty createEntityProperty(PropertyDefinition prop) {
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

    private DtoRule createDtoRule(Rule rule){
        List<String> actionNames = new ArrayList<>();

        for (Action action : rule.getActionList()) {
            actionNames.add(action.getClass().getSimpleName());
        }

        return new DtoRule(rule.getName(), createDtoActivation(rule.getActivation()), rule.getActionList().size(),
                actionNames);
    }

    private DtoActivation createDtoActivation(Activation activation){
        return new DtoActivation(activation.getTicks(), activation.getProbability());
    }

    private DtoTermination createDtoTermination(){
        return new DtoTermination(worldDefinition.getTermination().getTicks(), worldDefinition.getTermination().getSeconds());
    }

    //choice 3
    public List<DtoEnvironmentDetails> displayEnvironment(){
        List<DtoEnvironmentDetails> environmentDetails = new ArrayList<>();

        for (PropertyDefinition environment : worldDefinition.getEnvironmentVariables().getProperties().values()) {
            if(environment.getRange() != null){
                environmentDetails.add(new DtoEnvironmentDetails(environment.getName(), environment.getType().toString().toLowerCase(),
                        createDtoRange(environment.getRange())));
            } else{
                environmentDetails.add(new DtoEnvironmentDetails(environment.getName(), environment.getType().toString().toLowerCase(),
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
}
