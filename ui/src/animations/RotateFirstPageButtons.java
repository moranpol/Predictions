package animations;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class RotateFirstPageButtons {
    private final Button gridButton;
    private final Button entityButton;
    private final Button environmentButton;
    private final Button ruleButton;
    private final Button terminationButton;
    private ParallelTransition parallelTransition;

    public RotateFirstPageButtons(Button gridButton, Button entityButton, Button environmentButton, Button ruleButton, Button terminationButton) {
        this.gridButton = gridButton;
        this.entityButton = entityButton;
        this.environmentButton = environmentButton;
        this.ruleButton = ruleButton;
        this.terminationButton = terminationButton;

        initializeAnimation();
    }

    private void initializeAnimation() {
        // Create a RotateTransition for each button
        RotateTransition gridButtonRotation = createRotateTransition(gridButton);
        RotateTransition entityButtonRotation = createRotateTransition(entityButton);
        RotateTransition environmentButtonRotation = createRotateTransition(environmentButton);
        RotateTransition ruleButtonRotation = createRotateTransition(ruleButton);
        RotateTransition terminationButtonRotation = createRotateTransition(terminationButton);

        // Create a ParallelTransition to run all rotations simultaneously
        parallelTransition = new ParallelTransition(
                gridButtonRotation,
                entityButtonRotation,
                environmentButtonRotation,
                ruleButtonRotation,
                terminationButtonRotation
        );

        parallelTransition.setCycleCount(RotateTransition.INDEFINITE); // Repeat indefinitely
        parallelTransition.setAutoReverse(true); // Reverse the animation for a smooth transition back
    }

    private RotateTransition createRotateTransition(Button button) {
        RotateTransition rotation = new RotateTransition(Duration.seconds(2), button);
        rotation.setByAngle(360); // Rotate by 360 degrees (one full rotation)
        return rotation;
    }

    public void startRotationAnimation() {
        parallelTransition.play();
    }

    public void stopRotationAnimation() {
        parallelTransition.stop();
        // Reset the button rotations
        gridButton.setRotate(0);
        entityButton.setRotate(0);
        environmentButton.setRotate(0);
        ruleButton.setRotate(0);
        terminationButton.setRotate(0);
    }
}
