import manager.LogicManager;
import menuChoice1.DtoXmlPath;
import menuChoice2.DtoWorldInfo;

import java.util.Scanner;

public class Manu {
    private static final Scanner scanner = new Scanner(System.in);
    private Boolean isSuccessLoad;
    private Boolean isSimulationRun;
    private final LogicManager logicManager;

    public Manu(){
        isSuccessLoad = false;
        isSimulationRun = false;
        logicManager = new LogicManager();
    }

    public void startManu(){
        printManu();
        boolean stop = false;

        while(!stop){
            Integer choice = getMenuChoice();
            choice = validateChoice(choice);
            switch (choice) {
                case 1:
                    readXml();
                    break;
                case 2:
                    displaySimulation();
                    break;
                case 3:
                    //runningSimulation();
                    break;
                case 4:
                    //displayPastActivation();
                    break;
                case 5:
                    stop = true;
                    break;
                default:
                    System.out.println("ERROR:\n    Must enter number from 1-5.\n   try again");
                    break;
            }
            printManu();
        }
    }

    private Integer getMenuChoice() {
        boolean stop = false;
        Integer choice = null;

        while (!stop) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                stop = true;
            } catch (NumberFormatException e) {
                System.out.println("ERROR:\n    Must enter number from 1-5.\n   try again\n");
                printManu();
            }
        }

        return choice;
    }

    private Integer validateChoice(Integer choice){
        boolean stop = false;

        while (!stop){
            if(!isSuccessLoad && choice != 1 && choice != 5){
                System.out.println("ERROR:\n    There is no Xml file for simulation load, choose 1 or 5.\n   try again\n");
                printManu();
                choice = getMenuChoice();
            } else if (!isSimulationRun && choice == 4) {
                System.out.println("ERROR:\n    You must run simulation for display simulation details.\n   try again\n");
                printManu();
                choice = getMenuChoice();
            }else {
                stop = true;
            }
        }

        return choice;
    }

    private void readXml(){
        System.out.print("Enter full path of XML file:\n------>");
        String path = scanner.nextLine();
        DtoXmlPath dtoXmlPath = new DtoXmlPath(path);
        try {
            logicManager.ReadXmlFile(dtoXmlPath);
            isSuccessLoad = true;
            System.out.println("XML file loaded successfully.\n");
        } catch (Exception e) {
            System.out.println("ERROR:\n    Cant load file.\n   " + e.getMessage() + "\n");
        }
    }

    private void printManu(){
        System.out.print("Select an action:\n" +
                "   #1 - Read system information file from XML.\n" +
                "   #2 - Display simulation details.\n" +
                "   #3 - Running a simulation.\n" +
                "   #4 - Display full details of past activation.\n" +
                "   #5 - Exit.\n" +
                "------>");
    }

    private void displaySimulation(){
        DtoWorldInfo dtoWorldInfo = logicManager.displayWorld();
        
    }
}


