package ru.yandex.ajwar.util.test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Ajwar on 16.03.2017.
 */
public class Test extends Application {
    Scene scene;
    Stage stage;
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        scene = new Scene(root, 300, 250);
        root.setStyle("-fx-background-color: red;"); // just to see the limit of the window ;-)
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED); // remove the "three top buttons on the window"
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        UndecoratedHelper.addResizeListener(stage);
        stage.setMinWidth(200);
        stage.setMinHeight(200);
        /*ResizeListener listener = new ResizeListener();
        listener.setScene(scene);
        listener.setStage(primaryStage);
        scene.setOnMouseMoved(listener);
        scene.setOnMousePressed(listener);
        scene.setOnMouseDragged(listener);*/
    }
}
