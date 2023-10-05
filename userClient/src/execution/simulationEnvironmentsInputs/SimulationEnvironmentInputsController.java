package execution.simulationEnvironmentsInputs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import execution.newExecution.NewExecutionController;
import newExecution.dtoEnvironment.DtoEnvironment;
import execution.simulationEnvironmentsInputs.inputTypes.EnvironmentInputs;
import execution.simulationEnvironmentsInputs.inputTypes.booleanEnvironment.BooleanEnvironmentController;
import execution.simulationEnvironmentsInputs.inputTypes.numberEnvironment.NumberEnvironmentController;
import execution.simulationEnvironmentsInputs.inputTypes.stringEnvironment.StringEnvironmentController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationEnvironmentInputsController {

    @FXML
    public VBox environmentInputsVBox;

    private NewExecutionController newExecutionController;

    private final Map<String, EnvironmentInputs> environmentInputsMap = new HashMap<>();

    @FXML
    private Label titleLabel;

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setEnvironmentInputs(List<DtoEnvironment> environments){
        Platform.runLater(() -> environmentInputsVBox.getChildren().clear());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/execution/simulationEnvironmentsInputs/inputTypes/numberEnvironment/NumberEnvironment.fxml"));
            Parent numberEnvironment = loader.load();
            Platform.runLater(() -> environmentInputsVBox.getChildren().add(numberEnvironment));

            NumberEnvironmentController numberEnvironmentController = loader.getController();
            numberEnvironmentController.setter(newExecutionController, environment);
            newExecutionController.addListenerToStartButton(numberEnvironmentController);
            environmentInputsMap.put(environment.getName(), numberEnvironmentController);
        } catch (IOException ignored) {
        }
    }

    private void setBooleanEnvironment(DtoEnvironment environment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/execution/simulationEnvironmentsInputs/inputTypes/booleanEnvironment/BooleanEnvironment.fxml"));
            Parent booleanEnvironment = loader.load();
            Platform.runLater(() -> environmentInputsVBox.getChildren().add(booleanEnvironment));

            BooleanEnvironmentController booleanEnvironmentController = loader.getController();
            booleanEnvironmentController.setter(newExecutionController, environment);
            newExecutionController.addListenerToStartButton(booleanEnvironmentController);
            environmentInputsMap.put(environment.getName(), booleanEnvironmentController);
        } catch (IOException ignored) {
        }
    }

    private void setStringEnvironment(DtoEnvironment environment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/execution/simulationEnvironmentsInputs/inputTypes/stringEnvironment/StringEnvironment.fxml"));
            Parent stringEnvironment = loader.load();
            Platform.runLater(() -> environmentInputsVBox.getChildren().add(stringEnvironment));

            StringEnvironmentController stringEnvironmentController = loader.getController();
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