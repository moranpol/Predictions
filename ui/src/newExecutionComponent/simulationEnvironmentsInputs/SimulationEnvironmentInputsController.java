package newExecutionComponent.simulationEnvironmentsInputs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import newExecutionComponent.NewExecutionController;
import newExecution.dtoEnvironment.DtoEnvironment;
import newExecutionComponent.simulationEnvironmentsInputs.inputTypes.EnvironmentInputs;
import newExecutionComponent.simulationEnvironmentsInputs.inputTypes.booleanEnvironment.BooleanEnvironmentController;
import newExecutionComponent.simulationEnvironmentsInputs.inputTypes.numberEnvironment.NumberEnvironmentController;
import newExecutionComponent.simulationEnvironmentsInputs.inputTypes.stringEnvironment.StringEnvironmentController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationEnvironmentInputsController {

    @FXML
    public VBox environmentInputsVBox;

    private NewExecutionController newExecutionController;

    private Map<String, EnvironmentInputs> environmentInputsMap = new HashMap<>();

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setEnvironmentInputs(List<DtoEnvironment> environments){
        environmentInputsVBox.getChildren().clear();
        for(DtoEnvironment environment : environments){
            switch (environment.getType()){
                case "string":
                    setStringEnvironment(environment);
                    break;
                case "boolean":
                    setBooleanEnvironment(environment);
                    break;
                default:
                    setNumberEnvironment(environment);
                    break;
            }
        }
    }

    private void setNumberEnvironment(DtoEnvironment environment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecutionComponent/simulationEnvironmentsInputs/inputTypes/numberEnvironment/NumberEnvironment.fxml"));
            Parent numberEnvironment = loader.load();
            NumberEnvironmentController numberEnvironmentController = loader.getController();
            environmentInputsVBox.getChildren().add(numberEnvironment);
            numberEnvironmentController.setter(newExecutionController, environment);
            newExecutionController.addListenerToStartButton(numberEnvironmentController);
            environmentInputsMap.put(environment.getName(), numberEnvironmentController);
        } catch (IOException ignored) {
        }
    }

    private void setBooleanEnvironment(DtoEnvironment environment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecutionComponent/simulationEnvironmentsInputs/inputTypes/booleanEnvironment/BooleanEnvironment.fxml"));
            Parent booleanEnvironment = loader.load();
            BooleanEnvironmentController booleanEnvironmentController = loader.getController();
            environmentInputsVBox.getChildren().add(booleanEnvironment);
            booleanEnvironmentController.setter(newExecutionController, environment);
            newExecutionController.addListenerToStartButton(booleanEnvironmentController);
            environmentInputsMap.put(environment.getName(), booleanEnvironmentController);
        } catch (IOException ignored) {
        }
    }

    private void setStringEnvironment(DtoEnvironment environment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecutionComponent/simulationEnvironmentsInputs/inputTypes/stringEnvironment/StringEnvironment.fxml"));
            Parent stringEnvironment = loader.load();
            StringEnvironmentController stringEnvironmentController = loader.getController();
            environmentInputsVBox.getChildren().add(stringEnvironment);
            stringEnvironmentController.setter(newExecutionController, environment);
            newExecutionController.addListenerToStartButton(stringEnvironmentController);
            environmentInputsMap.put(environment.getName(), stringEnvironmentController);
        } catch (IOException ignored) {
        }
    }

    public void rerunExecutionEnvironments(List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList) {
        for(DtoEnvironmentInitialize dtoEnvironmentInitialize : dtoEnvironmentInitializeList){
            environmentInputsMap.get(dtoEnvironmentInitialize.getName()).rerunExecution(dtoEnvironmentInitialize);
        }
    }
}