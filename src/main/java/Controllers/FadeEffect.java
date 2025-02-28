package Controllers;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeEffect {
    public void fadeIn(Node node) {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setNode(node);
        fadeIn.setDuration(Duration.seconds(1));
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public void fadeOut(Node node) {
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setNode(node);
        fadeOut.setDuration(Duration.seconds(1));
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.play();
    }
}
