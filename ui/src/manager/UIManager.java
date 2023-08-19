package manager;

import menuChoice1.DtoXmlPath;
import menuChoice2.*;
import menuChoice3.DtoEnvironmentDetails;
import menuChoice3.DtoEnvironmentInitialize;
import menuChoice3.DtoSimulationDetails;
import menuChoice4.*;
import menuChoice5or6.DtoFilePath;

import java.util.*;

public class UIManager {
    private static final Scanner scanner = new Scanner(System.in);
    private Boolean isSuccessLoad;
    private Boolean isSimulationRun;
    private final LogicManager logicManager;

    public UIManager(){
        isSuccessLoad = false;
        isSimulationRun = false;
        logicManager = new LogicManager();
    }

    //choice 6
    private void loadSimulationsFromFile(){
        System.out.print("Enter file full path to load old system:\n------> ");
        DtoFilePath filePath = new DtoFilePath(scanner.nextLine());
        try{
            logicManager.loadSimulationsFromFile(filePath);
            isSuccessLoad = true;
            isSimulationRun = true;
            System.out.println("System loaded successfully.\n");
        } catch (Exception e){
            System.out.println("ERROR:\n    Cant load from file.\n    " + e.getMessage() + "\n");
        }
    }

    //choice 5
    private void saveSimulationsToFile(){
        System.out.print("Enter file full path including the file name (without the extension) to save the system:\n------> ");
        DtoFilePath filePath = new DtoFilePath(scanner.nextLine());
        try{
            logicManager.saveSimulationsToFile(filePath);
            System.out.println("System saved successfully.\n");
        } catch (Exception e){
            System.out.println("ERROR:\n    Cant save to file.\n    " + e.getMessage() + "\n");
        }
    }

    //choice 4
    private void displayPastActivation() {
        List<DtoPastSimulationDetails> pastSimulationsDetails = logicManager.createPastSimulationDetails();
        Integer simulationId = getPastSimulationIdChoice(pastSimulationsDetails);
        DtoSimulationIdChoice simulationIdChoice = new DtoSimulationIdChoice(simulationId);

        switch (getSimulationDisplayMode()){
            case "amount":
                List<DtoAmountOfEntity> amountOfEntities =
                        logicManager.createAmountOfEntitiesDetails(simulationIdChoice);
                printAmountOfEntities(amountOfEntities);
                break;
            case "property histogram":
                List<DtoEntityName> entitiesNames = logicManager.createEntitiesNamesList();
                DtoEntityName entityNameChoice = getEntityName(entitiesNames);
                List<DtoPropertyName> propertyNames =
                        logicManager.createPropertiesNamesListByEntity(entityNameChoice);
                DtoPropertyName propertyNameChoice = getPropertyName(propertyNames);
                Map<String, DtoPropertyHistogram> propertyHistogram = logicManager.createPropertyHistogram(entityNameChoice,
                        propertyNameChoice, simulationIdChoice);
                printPropertyHistogramByEntity(propertyHistogram, propertyNameChoice, entityNameChoice);
                break;
        }

        System.out.println();
    }

    private void printAmountOfEntities(List<DtoAmountOfEntity> amountOfEntities){
        System.out.println("Entities amounts:");
        System.out.println("=================");

        for (DtoAmountOfEntity entity : amountOfEntities){
            System.out.println("  # Entity name - " + entity.getName());
            System.out.println("    Start amount - " + entity.getStartAmount());
            System.out.println("    End amount - " + entity.getEndAmount());
        }
    }

    private void printPropertyHistogramByEntity(Map<String, DtoPropertyHistogram> propertyHistogram, DtoPropertyName propertyNameChoice,
                                                DtoEntityName entityNameChoice){
        System.out.println("Histogram for property " + propertyNameChoice.getName() + " in " +entityNameChoice.getName() +
                " entity:");
        System.out.println("================================================");

        if(propertyHistogram.isEmpty()){
            System.out.println("  Oh no, all entities are dead :(");
        } else{
            for (DtoPropertyHistogram propertyValue : propertyHistogram.values()){
                System.out.println("  # " + propertyNameChoice.getName() + " " + propertyValue.getValueOfProperty() +
                        " amount - " + propertyValue.getAmount());
            }
        }
    }

    private DtoPropertyName getPropertyName(List<DtoPropertyName> propertiesNames){
        printPropertiesNames(propertiesNames);
        Integer choice = getIntegerChoice();
        choice = validatePropertyNameChoice(choice, propertiesNames);

        return new DtoPropertyName(propertiesNames.get(choice - 1).getName());
    }

    private void printPropertiesNames(List<DtoPropertyName> propertiesNames){
        System.out.println("Select a property to display its histogram:");
        System.out.println("===========================================");

        for (int i = 0; i < propertiesNames.size(); i++){
            System.out.println("  #" + (i + 1) + " " + propertiesNames.get(i).getName());
        }
        System.out.print("------> ");
    }

    private Integer validatePropertyNameChoice(Integer choice, List<DtoPropertyName> propertiesNames){
        boolean stop = false;

        while (!stop){
            if(choice < 1 || choice > propertiesNames.size()){
                System.out.println("ERROR:\n    Must enter number from 1-" + propertiesNames.size() + ".\n    try again\n");
                printPropertiesNames(propertiesNames);
                choice = getIntegerChoice();
            } else {
                stop = true;
            }
        }

        return choice;
    }

    private DtoEntityName getEntityName(List<DtoEntityName> entitiesNames){
        printEntitiesNames(entitiesNames);
        Integer choice = getIntegerChoice();
        choice = validateEntityNameChoice(choice, entitiesNames);

        return new DtoEntityName(entitiesNames.get(choice - 1).getName());
    }

    private void printEntitiesNames(List<DtoEntityName> entitiesNames){
        System.out.println("Select an entity to display its property histogram:");
        System.out.println("===================================================");

        for (int i = 0; i < entitiesNames.size(); i++){
            System.out.println("  #" + (i + 1) + " " + entitiesNames.get(i).getName());
        }
        System.out.print("------> ");
    }

    private Integer validateEntityNameChoice(Integer choice, List<DtoEntityName> entitiesNames){
        boolean stop = false;

        while (!stop){
            if(choice < 1 || choice > entitiesNames.size()){
                System.out.println("ERROR:\n    Must enter number from 1-" + entitiesNames.size() + ".\n    try again\n");
                printEntitiesNames(entitiesNames);
                choice = getIntegerChoice();
            } else {
                stop = true;
            }
        }

        return choice;
    }

    private String getSimulationDisplayMode(){
        printSimulationDisplayMode();
        Integer choice = getIntegerChoice();
        choice = validateDisplayMode(choice);

        if(choice == 1){
            return "amount";
        } else{
            return "property histogram";
        }
    }

    private void printSimulationDisplayMode(){
        System.out.println("Select Simulation display mode:");
        System.out.println("===============================");
        System.out.println("  #1 Amount of entities");
        System.out.println("  #2 Property histogram by entity");
        System.out.print("------> ");
    }

    private Integer validateDisplayMode(Integer choice){
        boolean stop = false;

        while (!stop){
            if(choice != 1 && choice != 2){
                System.out.println("ERROR:\n    Must enter number from 1-2.\n    try again\n");
                printSimulationDisplayMode();
                choice = getIntegerChoice();
            } else {
                stop = true;
            }
        }

        return choice;
    }

    private Integer getPastSimulationIdChoice(List<DtoPastSimulationDetails> pastSimulationsDetails){
        printPastSimulationDetails(pastSimulationsDetails);
        Integer choice = getIntegerChoice();
        choice = validatePastSimulationChoice(choice, pastSimulationsDetails);

        return pastSimulationsDetails.get(choice - 1).getId();
    }

    private Integer validatePastSimulationChoice(Integer choice, List<DtoPastSimulationDetails> pastSimulationsDetails) {
        boolean stop = false;

        while (!stop){
            if(choice < 0 || choice > pastSimulationsDetails.size()){
                System.out.println("ERROR:\n    Must enter number from 1-" + pastSimulationsDetails.size() +
                        ".\n    try again\n");
                printPastSimulationDetails(pastSimulationsDetails);
                choice = getIntegerChoice();
            } else {
                stop = true;
            }
        }

        return choice;
    }

    private void printPastSimulationDetails(List<DtoPastSimulationDetails> pastSimulationsDetails) {
        System.out.println("Past simulations details:");
        System.out.println("=========================");

        for (int i = 0; i < pastSimulationsDetails.size(); i++){
            System.out.println("  #" + (i + 1) + " Run date - " + pastSimulationsDetails.get(i).getRunDate());
            System.out.println("     Id - " + pastSimulationsDetails.get(i).getId());
        }
        System.out.println("Select a number of past simulations details:");
        System.out.print("------> ");
    }

    //choice 3
    private void runningSimulation() {
        List<DtoEnvironmentDetails> environmentDetails = logicManager.displayEnvironment();
        List<DtoEnvironmentInitialize> environmentInitializes = environmentInitializes(environmentDetails);
        logicManager.updateEnvironment(environmentInitializes);
        try {
            DtoSimulationDetails simulationDetails = logicManager.simulationRun();
            isSimulationRun = true;
            printSimulationDetails(simulationDetails);
        } catch(Exception e){
            System.out.println("ERROR:\n    " + e.getMessage());
        }
    }

    private void printSimulationDetails(DtoSimulationDetails simulationDetails) {
        System.out.println("Simulation ended successfully.\n  Simulation details:");
        System.out.println("    Id - " + simulationDetails.getId());
        System.out.println("    Termination reason - arrived to maximum " + simulationDetails.getTermination() + "\n");
    }

    private void printEnvironments(List<DtoEnvironmentDetails> environmentDetails) {
        System.out.println("Environment details:");
        System.out.println("====================");
        for (int i = 0; i < environmentDetails.size(); i++) {
            System.out.println("  #" + (i + 1) + " Environment variable name - " + environmentDetails.get(i).getName());
            System.out.println("     Type - " + environmentDetails.get(i).getType());
            if(environmentDetails.get(i).getRange() != null){
                System.out.println("     Range - from " + environmentDetails.get(i).getRange().getFrom() + " to "
                + environmentDetails.get(i).getRange().getTo());
            }
        }
        System.out.println("  #0 Start simulation");
        System.out.println("Select a number of environment variable to initialize or 0 to start the simulation:");
        System.out.print("------> ");
    }

    private List<DtoEnvironmentInitialize> environmentInitializes(List<DtoEnvironmentDetails> environmentDetails){
        List<DtoEnvironmentInitialize> environmentInitializes = new ArrayList<>();
        boolean stop = false;

        while (!stop){
            printEnvironments(environmentDetails);
            Integer choice = getIntegerChoice();
            choice = validateEnvironmentChoice(choice, environmentDetails);

            if(choice == 0){
                stop = true;
            } else{
                switch (environmentDetails.get(choice - 1).getType()){
                    case "string":
                        environmentInitializes.add(new DtoEnvironmentInitialize(environmentDetails.get(choice - 1).getName(),
                                getStringEnvironment()));
                        break;
                    case "decimal":
                        environmentInitializes.add(new DtoEnvironmentInitialize(environmentDetails.get(choice - 1).getName(),
                                getIntegerEnvironment(environmentDetails.get(choice - 1).getRange())));
                        break;
                    case "boolean":
                        environmentInitializes.add(new DtoEnvironmentInitialize(environmentDetails.get(choice - 1).getName(),
                                getBooleanEnvironment()));
                        break;
                    case "float":
                        environmentInitializes.add(new DtoEnvironmentInitialize(environmentDetails.get(choice - 1).getName(),
                                getFloatEnvironment(environmentDetails.get(choice - 1).getRange())));
                        break;
                }
            }
        }

        return environmentInitializes;
    }

    private Boolean getBooleanEnvironment(){
        System.out.print("Choose your boolean value to initialize the environment variable.\n  #1 true\n  #2 false\n"
        + "------> ");
        return getBoolean();
    }

    private Boolean getBoolean() {
        while (true){
            Integer choice = getIntegerChoice();
            if(choice == 1){
                return true;
            } else if (choice == 2) {
                return false;
            } else {
                System.out.print("ERROR:\n    Must enter a number from 1-2.\n    try again\n------> ");
            }
        }
    }

    private String getStringEnvironment(){
        System.out.print("Enter a string to initialize the environment variable.\n------> ");
        return scanner.nextLine();
    }

    private Integer getIntegerEnvironment(DtoRange range){
        System.out.print("Enter a decimal number in range " + range.getFrom() + "-" + range.getTo() +
                " to initialize the environment variable.\n------> ");
        Integer init = getIntegerChoice();
        init = validateNumberEnvironmentChoice(init.floatValue(), range).intValue();
        return init;
    }

    private Float getFloatEnvironment(DtoRange range){
        System.out.print("Enter a float number in range " + range.getFrom() + "-" + range.getTo() +
                " to initialize the environment variable.\n------> ");
        Float init = getFloatChoice();
        init = validateNumberEnvironmentChoice(init, range);
        return init;
    }

    private Float getFloatChoice() {
        boolean stop = false;
        Float choice = null;

        while (!stop) {
            try {
                choice = Float.parseFloat(scanner.nextLine());
                stop = true;
            } catch (NumberFormatException e) {
                System.out.print("ERROR:\n    Must enter a float number.\n    try again\n------> ");
            }
        }

        return choice;
    }

    private Float validateNumberEnvironmentChoice(Float init, DtoRange range){
        boolean stop = false;

        while (!stop){
            if(init < range.getFrom() || init > range.getTo()){
                System.out.print("ERROR:\n    Must enter number in range " + range.getFrom() + "-" + range.getTo() +
                        ".\n    try again\n------> ");
                init = getIntegerChoice().floatValue();
            } else {
                stop = true;
            }
        }

        return init;
    }

    private Integer validateEnvironmentChoice(Integer choice, List<DtoEnvironmentDetails> environmentDetails) {
        boolean stop = false;

        while (!stop){
            if(choice < 0 || choice > environmentDetails.size()){
                System.out.println("ERROR:\n    Must enter number from 0-" + environmentDetails.size() +
                        ".\n    try again\n");
                printEnvironments(environmentDetails);
                choice = getIntegerChoice();
            } else {
                stop = true;
            }
        }

        return choice;
    }

    //choice 2
    private void displaySimulation(){
        DtoWorldInfo dtoWorldInfo = logicManager.displayWorld();
        System.out.println("Simulation details:");
        System.out.println("====================");
        System.out.println("  Entities:");
        for (DtoEntity entity : dtoWorldInfo.getDtoEntityList()) {
            printEntity(entity);
        }
        System.out.println("  Rules:");
        for (DtoRule rule : dtoWorldInfo.getRules()) {
            printRule(rule);
        }
        System.out.println("  Termination:");
        printTermination(dtoWorldInfo.getTermination());
    }

    private void printTermination(DtoTermination termination) {
        if (termination.getTicks() != null){
            System.out.println("    # Maximum ticks - " + termination.getTicks());
        }
        if (termination.getSeconds() != null){
            System.out.println("    # Maximum seconds - " + termination.getSeconds() + "\n");
        }
    }

    private void printRule(DtoRule rule) {
        System.out.println("    # Rule name - " + rule.getName());
        System.out.println("      Activation - ticks " + rule.getActivation().getTicks() + ", probability " + rule.getActivation().getProbability());
        System.out.println("      Amount of Actions: " + rule.getNumOfActions());
        System.out.println("      Action names:");
        for (String actionName : rule.getActionNames()) {
            if(Objects.equals(actionName, "SingleCondition") || Objects.equals(actionName, "MultipleCondition")){
                System.out.println("        ~ Condition");
            } else{
                System.out.println("        ~ " + actionName);
            }
        }
    }

    private void printEntity(DtoEntity entity) {
        System.out.println("    # Entity name - " + entity.getName());
        System.out.println("      Population - " + entity.getPopulation());
        System.out.println("      Properties:");
        for (DtoProperty prop : entity.getPropertyDefinitions()) {
            printProperty(prop);
        }
    }

    private void printProperty(DtoProperty property){
        System.out.println("        ~ Property name - " + property.getName());
        System.out.println("          Type - " + property.getType());
        if(property.getRange() != null){
            System.out.println("          Range - from " + property.getRange().getFrom() + " to " + property.getRange().getTo());
        }
        System.out.println("          Is random initialized - " + property.isRandomInitialized());
    }

    //choice 1
    private void readXml(){
        System.out.print("Enter full path of XML file:\n------> ");
        String path = scanner.nextLine();
        DtoXmlPath dtoXmlPath = new DtoXmlPath(path);
        try {
            logicManager.ReadXmlFile(dtoXmlPath);
            isSimulationRun = false;
            isSuccessLoad = true;
            System.out.println("XML file loaded successfully.\n");
        } catch (Exception e) {
            System.out.println("ERROR:\n    Cant load file.\n    " + e.getMessage() + "\n");
        }
    }

    //general
    private void printMenu(){
        System.out.print("Select an action:\n" +
                "   #1 - Read system information file from XML.\n" +
                "   #2 - Display simulation details.\n" +
                "   #3 - Running a simulation.\n" +
                "   #4 - Display full details of past activation.\n" +
                "   #5 - Save system (past simulations) to file.\n" +
                "   #6 - Load system (past simulations) from file.\n" +
                "   #7 - Exit.\n" +
                "------> ");
    }

    private Integer getIntegerChoice() {
        boolean stop = false;
        Integer choice = null;

        while (!stop) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                stop = true;
            } catch (NumberFormatException e) {
                System.out.print("ERROR:\n    Must enter a decimal number.\n    try again\n------> ");
            }
        }

        return choice;
    }

    private Integer validateMenuChoice(Integer choice){
        boolean stop = false;

        while (!stop){
            if(choice < 1 || choice > 7){
                System.out.println("ERROR:\n    Must enter number from 1-7.\n    try again\n");
                printMenu();
                choice = getIntegerChoice();
            } else if(!isSuccessLoad && choice != 1 && choice != 7 && choice != 6){
                System.out.println("ERROR:\n    There is no Xml file for simulation load, choose 1, 6 or 7.\n    try again\n");
                printMenu();
                choice = getIntegerChoice();
            } else if (!isSimulationRun && (choice == 4 || choice == 5)) {
                System.out.println("ERROR:\n    You must run simulation (choose 3) for this choice.\n    try again\n");
                printMenu();
                choice = getIntegerChoice();
            }else {
                stop = true;
            }
        }

        return choice;
    }

    public void startMenu(){
        System.out.println("Welcome to Predictions:");
        System.out.println("=========================\n");
        boolean stop = false;

        while(!stop){
            printMenu();
            Integer choice = getIntegerChoice();
            choice = validateMenuChoice(choice);
            switch (choice) {
                case 1:
                    readXml();
                    break;
                case 2:
                    displaySimulation();
                    break;
                case 3:
                    runningSimulation();
                    break;
                case 4:
                    displayPastActivation();
                    break;
                case 5:
                    saveSimulationsToFile();
                    break;
                case 6:
                    loadSimulationsFromFile();
                    break;
                case 7:
                    System.out.println("Good bye :)");
                    System.out.println("Press any key to end...");
                    scanner.nextLine();
                    scanner.close();
                    stop = true;
                    break;
            }
        }
    }
}