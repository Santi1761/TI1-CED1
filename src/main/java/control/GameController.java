package control;

import game.trap_the_cat.GameApplication;
import javafx.animation.*;
import javafx.stage.Stage;
import model.logic.GameManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import model.graph.Vertex;

import java.net.URL;
import java.util.*;

public class GameController implements Initializable {

    static final Paint BLOCKED = Color.rgb(84, 99, 17);
    static final Paint OPEN = Color.rgb(218, 247, 12);
    static final Paint BILAIASE = Color.RED;

    @FXML
    private AnchorPane floorPane;

    private List<Polygon> polygons;

    @FXML
    private ImageView cat;
    private boolean canClick = true;


    /**
     *
     * @param event
     */
    //TODO ckeck transition
    @FXML
    private void onMouseClicked(MouseEvent event) {
        Polygon polygon = (Polygon) event.getSource();

        if (!canClick) return;

        if (polygon.getFill().equals(OPEN)) {
            canClick = false;
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(polygon.fillProperty(), OPEN)),
                    new KeyFrame(Duration.seconds(3), new KeyValue(polygon.fillProperty(), BLOCKED))
            );

            timeline.play();

            int id = Integer.parseInt(polygon.getId().substring(4));
            GameManager.getInstance().getGraph().getVertex(id).setClosed(true);
            GameManager.getInstance().getGraph().deleteFromNeighbours(id);


            Vertex catPosition = GameManager.getInstance().getGraph().getCatPosition();
            List<Vertex> adjacentVertices = GameManager.getInstance().getGraph().getAdjacentVertices(catPosition);


            Vertex randomVertex = getRandomAdjacentVertex(adjacentVertices);

            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));

            pause.setOnFinished(event1 -> cat.setImage(new Image("file:src/assets/GatoAbajo.png")));

                if(randomVertex.getPolygon().getLayoutY() < catPosition.getPolygon().getLayoutY()) {
                    if(randomVertex.getPolygon().getLayoutX() < catPosition.getPolygon().getLayoutX())
                        cat.setImage(new Image("file:src/assets/GatoDUIzquierda.png"));
                    else if(randomVertex.getPolygon().getLayoutX() > catPosition.getPolygon().getLayoutX())
                        cat.setImage(new Image("file:src/assets/GatoDUDerecha.png"));
                    else cat.setImage(new Image("file:src/assets/GatoArriba.png"));
                } else if(randomVertex.getPolygon().getLayoutY() > catPosition.getPolygon().getLayoutY()) {
                    if(randomVertex.getPolygon().getLayoutX() < catPosition.getPolygon().getLayoutX())
                        cat.setImage(new Image("file:src/assets/GatoDDIzquierda.png"));
                    else if(randomVertex.getPolygon().getLayoutX() > catPosition.getPolygon().getLayoutX())
                        cat.setImage(new Image("file:src/assets/GatoDDDerecha.png"));
                    else cat.setImage(new Image("file:src/assets/GatoAbajo.png"));
                } else if(randomVertex.getPolygon().getLayoutX() < catPosition.getPolygon().getLayoutX()) {
                    cat.setImage(new Image("file:src/assets/GatoIzquierda.png"));
                } else if(randomVertex.getPolygon().getLayoutX() > catPosition.getPolygon().getLayoutX()) {
                    cat.setImage(new Image("file:src/assets/GatoDerecha.png"));
                }

            pause.play();


            GameManager.getInstance().getGraph().setCatPosition(randomVertex);
            moveCatImage(randomVertex.getPolygon());

            if(GameManager.getInstance().isGameFinished()) {
                canClick = false;
                cat.setVisible(false);

                Timeline timeline2 = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(floorPane.opacityProperty(), 1)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(floorPane.opacityProperty(), 0.6)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(floorPane.opacityProperty(), 0.4)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(floorPane.opacityProperty(), 0.25)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(floorPane.opacityProperty(), 0.15)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(floorPane.opacityProperty(), 0.05))
                );

                timeline2.play();

                PauseTransition wait = new PauseTransition(Duration.seconds(2));
                wait.setOnFinished(e -> {
                    GameApplication.hideWindow((Stage) floorPane.getScene().getWindow());
                    GameApplication.showWindow("leaderboard", null, 610, 610);
                });
                wait.play();
            }

            PauseTransition wait = new PauseTransition(Duration.seconds(0.35));
            wait.setOnFinished(e -> canClick = true);
            wait.play();
        } else {
            polygon.setFill(BILAIASE);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(polygon.fillProperty(), BILAIASE)),
                    new KeyFrame(Duration.seconds(3), new KeyValue(polygon.fillProperty(), BLOCKED))
            );

            timeline.play();
        }
    }

    /**
     *
     * @param vertices
     * @return
     */
    private Vertex getRandomAdjacentVertex(List<Vertex> vertices) {

        int randomIndex = new Random().nextInt(vertices.size());
        return vertices.get(randomIndex);
    }


    /**
     *
     * @param polygon
     */
    private void moveCatImage(Polygon polygon) {
        double x = polygon.getLayoutX() - 36.5;
        double y = polygon.getLayoutY() - 26;
        cat.setLayoutX(x);
        cat.setLayoutY(y);
        cat.toFront();
    }


    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < floorPane.getChildren().size(); i++) {
            if(floorPane.getChildren().get(i) instanceof ImageView) continue;
            Polygon polygon = (Polygon) floorPane.getChildren().get(i);
            polygon.setFill(OPEN);
            polygon.toBack();
            int id = Integer.parseInt(polygon.getId().substring(4));
            GameManager.getInstance().getGraph().setPolygon(id, polygon);
        }

        this.cat.setImage(new Image("file:src/assets/GatoAbajo.png"));
        moveCatImage(GameManager.getInstance().getGraph().getVertex(61).getPolygon());
        System.out.println(this.cat.getLayoutX() + " " + this.cat.getLayoutY());
        this.cat.toFront();
    }
}