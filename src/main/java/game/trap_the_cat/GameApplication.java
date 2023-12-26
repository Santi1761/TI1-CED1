package game.trap_the_cat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        showWindow("start", stage, 600, 500);
    }

    public static void hideWindow(Stage window) {
        window.hide();
    }

    public static void showWindow(String fxml, Stage stage, double width, double height){
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource(fxml+".fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(stage == null) stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Trap the Cat!");
        stage.setResizable(false);
        stage.show();
    }
}
