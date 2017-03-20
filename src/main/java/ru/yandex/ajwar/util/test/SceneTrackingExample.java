package ru.yandex.ajwar.util.test;

/**
 * Created by Ajwar on 17.03.2017.
 */
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SceneTrackingExample extends Application {

    int count = 0 ;

    @Override
    public void start(Stage primaryStage) {
        StageFactory factory = StageFactory.INSTANCE ;
        factory.registerStage(primaryStage);

        configureStage(primaryStage);
        primaryStage.show();
    }

    private void configureStage(Stage stage) {
        StageFactory stageFactory = StageFactory.INSTANCE;
        Stage owner = stageFactory.getCurrentStage() ;
        Label ownerLabel = new Label();
        if (owner == null) {
            ownerLabel.setText("No owner");
        } else {
            ownerLabel.setText("Owner: "+owner.getTitle());
            stage.initOwner(owner);
        }
        stage.setTitle("Stage "+(++count));
        Button newStage = new Button("New Stage");
        newStage.setOnAction(e -> {
            Stage s = stageFactory.createStage();
            Stage current = stageFactory.getCurrentStage() ;
            if (current != null) {
                s.setX(current.getX() + 20);
                s.setY(current.getY() + 20);
            }
            configureStage(s);
            s.show();
        });

        VBox root = new VBox(10, ownerLabel, newStage);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root, 360, 150));
    }

    public enum StageFactory {
        INSTANCE ;

        private final ObservableList<Stage> openStages = FXCollections.observableArrayList();

        public ObservableList<Stage> getOpenStages() {
            return openStages ;
        }

        private final ObjectProperty<Stage> currentStage = new SimpleObjectProperty<>(null);
        public final ObjectProperty<Stage> currentStageProperty() {
            return this.currentStage;
        }
        public final javafx.stage.Stage getCurrentStage() {
            return this.currentStageProperty().get();
        }
        public final void setCurrentStage(final javafx.stage.Stage currentStage) {
            this.currentStageProperty().set(currentStage);
        }

        public void registerStage(Stage stage) {
            stage.addEventHandler(WindowEvent.WINDOW_SHOWN, e ->
                    openStages.add(stage));
            stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e ->
                    openStages.remove(stage));
            stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (isNowFocused) {
                    currentStage.set(stage);
                } else {
                    currentStage.set(null);
                }
            });
        }

        public Stage createStage() {
            Stage stage = new Stage();
            registerStage(stage);
            return stage ;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
