module game.trap_the_cat {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens game.trap_the_cat to javafx.fxml;
    opens control to javafx.fxml;
    opens model.logic to javafx.fxml;
    opens model.entity to javafx.fxml;
    opens model.graph to javafx.fxml;
    opens model.factory;


    exports game.trap_the_cat;
    exports model.logic;
    exports model.entity;
    exports model.graph;
    exports model.factory;
    exports control;
}