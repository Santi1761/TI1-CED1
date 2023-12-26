package control;

import game.trap_the_cat.GameApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.entity.User;
import model.logic.GameManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    GameController games;
    private Stage st;
    private Stage godParent;
    private Stage mainStage;

    /**
     *
     */
    public StartController() {
    }

    @FXML
    private TextField usernameTextField;

    @FXML
    private ImageView logo;


    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void go(ActionEvent event) throws IOException {
        String res = usernameTextField.getText();

        if (!res.isEmpty()) {
            GameManager.getInstance().setCurrent(new User(res));
            Stage mainStage = ((Stage) usernameTextField.getScene().getWindow());
            GameApplication.hideWindow(mainStage);
            GameApplication.showWindow("game", null, 987.5, 900);

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Username textfield must be not empty!", ButtonType.OK);
            a.setTitle("What?!");
            a.showAndWait();
        }
    }

    /**
     * 
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.logo.setImage(new Image("file:src/assets/Logo.png"));
        this.logo.toFront();

    }
}
