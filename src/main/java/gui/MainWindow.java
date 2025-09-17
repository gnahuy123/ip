package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lebron.Lebron;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private static final String welcomeMsg = "Wassup, I'm Lebron. What popping homie?";
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private Lebron lebron;
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image lebronImage = new Image(this.getClass().getResourceAsStream("/images/Lebron.jpg"));
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setDuke(Lebron d) {
        lebron = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = lebron.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, lebronImage)
        );
        userInput.clear();
    }

    /**
     * Called for Lebron to send message when app starts up
     */
    @FXML
    public void startUp() {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcomeMsg, lebronImage)
        );
    }
}
