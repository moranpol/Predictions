import java.util.Scanner;

public class Manu {
    static Scanner scanner = new Scanner(System.in);
    boolean isSuccessLoad;

    //LogicManager logicManager;


    public Manu() {
        this.isSuccessLoad = false;
        //logicManager = new LogicManager();
    }

    private void StartManu(){

        PrintManu();
        int choice = scanner.nextInt();
        while(choice != 5){
        switch (choice) {
            case 1:
                ReadXml();
                break;
            case 2:
                //DisplaySimulation();
                break;
            case 3:
                //RunningSimulation();
                break;
            case 4:
                //DisplayPastActivation();
                break;
        }
        PrintManu();
        choice = scanner.nextInt();

        }
    }


    private void ReadXml(){
        System.out.println("Enter full path of XML file:\n");
        String path = scanner.nextLine();
        try {
            // this.isSuccessLoad = logicManager.ReadWml(path)
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            System.out.println("Cant load file, " + errorMessage);

        }

    }

    private void PrintManu(){
        System.out.println("Select an action:\n" +
                "#1 - Read system information file from XML.\n" +
                "#2 - Display simulation details.\n" +
                "#3 - Running a simulation.\n" +
                "#4 - Display full details of past activation.\n" +
                "#5 - Exit\n ");
    }
}


