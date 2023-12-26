package control;

import game.trap_the_cat.GameApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.logic.GameManager;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    @FXML
    private TableColumn<User, Integer> posTc;
    @FXML
    private TableColumn<User, String> nameTc;
    @FXML
    private TableColumn<User, Integer> scoreTc;
    @FXML
    private Label msgLb;
    @FXML
    private TableView<User> tv;
    @FXML
    private Button exitButton;
    @FXML
    private Button newGameButton;
    @FXML
    private Button deleteScoreButton;

    GameManager manager;

    /**
     *
     * @param manager
     */
    public LeaderboardController(GameManager manager) {
        this.manager = manager;
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void startNewGame(ActionEvent event) throws IOException {
        //GameManager.getInstance().exportData();
        GameApplication.hideWindow((Stage) exitButton.getScene().getWindow());
        GameApplication.showWindow("start", null, 600, 500);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void deleteCurrentScore(ActionEvent event) {

    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void exitGame(ActionEvent event) throws IOException {
        //GameManager.getInstance().exportData();
        GameApplication.hideWindow((Stage) exitButton.getScene().getWindow());
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    private void setLeaderBoardTV() {

        ArrayList<User> a = (ArrayList<User>) manager.getUsersList();
        ArrayList<User> b = new ArrayList<>();

        if (a.size() < 5) {

            b = a;
        }else {

            for (int i = 0; i < 5; i++) {

                a.add(a.get(i));
            }
        }


        ObservableList<User> observableList;
        observableList = FXCollections.observableArrayList(b);

        tv.setItems(observableList);
        nameTc.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        scoreTc.setCellValueFactory(new PropertyValueFactory<User,Integer>("score"));
        posTc.setCellValueFactory(new PropertyValueFactory<User,Integer>("position"));
    }

}
