package animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ColorAnimationSecondPage {
    private final Label environmentTitleLabel;
    private final Label entitiesTitleLabel;
    private final Label maxPopulationLabel;
    private final Label maxPopulationCountLabel;
    private final Label currPopulationLabel;
    private final Label currPopulationCountLabel;
    private Timeline colorChangeTimeline;

    public ColorAnimationSecondPage(Label environmentTitleLabel, Label entitiesTitleLabel, Label maxPopulationLabel, Label maxPopulationCountLabel, Label currPopulationLabel, Label currPopulationCountLabel) {
        this.environmentTitleLabel = environmentTitleLabel;
        this.entitiesTitleLabel = entitiesTitleLabel;
        this.maxPopulationLabel = maxPopulationLabel;
        this.maxPopulationCountLabel = maxPopulationCountLabel;
        this.currPopulationLabel = currPopulationLabel;
        this.currPopulationCountLabel = currPopulationCountLabel;
    }

    private void changeLabelColor() {
        // Set label colors randomly
        environmentTitleLabel.setTextFill(getRandomColor());
        entitiesTitleLabel.setTextFill(getRandomColor());
        maxPopulationLabel.setTextFill(getRandomColor());
        maxPopulationCountLabel.setTextFill(getRandomColor());
        currPopulationLabel.setTextFill(getRandomColor());
        currPopulationCountLabel.setTextFill(getRandomColor());
    }

    private Color getRandomColor() {
        // Generate a random color
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();
        return new Color(red, green, blue, 1);
    }

    public void startColorChange(){
        // Create a timeline for color changes
        colorChangeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> changeLabelColor()),
                new KeyFrame(Duration.seconds(1))
        );
        colorChangeTimeline.setCycleCount(Timeline.INDEFINITE);
        colorChangeTimeline.play();
    }

    public void stopColorChange() {
        // Stop the color change timeline and reset labels to black
        colorChangeTimeline.stop();
        resetLabelsColor();
    }

    private void resetLabelsColor() {
        environmentTitleLabel.setTextFill(Color.BLACK);
        entitiesTitleLabel.setTextFill(Color.BLACK);
        maxPopulationLabel.setTextFill(Color.BLACK);
        maxPopulationCountLabel.setTextFill(Color.BLACK);
        currPopulationLabel.setTextFill(Color.BLACK);
        currPopulationCountLabel.setTextFill(Color.BLACK);
    }
}
