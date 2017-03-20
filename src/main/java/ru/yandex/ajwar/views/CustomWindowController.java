package ru.yandex.ajwar.views;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.yandex.ajwar.MainApp;
//import ru.yandex.ajwar.util.test.EffectUtilities;
import ru.yandex.ajwar.util.test.UndecoratedHelper;

import static ru.yandex.ajwar.util.Constant.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ajwar on 28.02.17.
 */
public class CustomWindowController implements Initializable{

    private static double xOffset = 0;
    private static double yOffset = 0;
    private static double yStage = 0;
    private static double heightImg=0;
    private Stage primaryStage;
    private MainApp mainApp;


    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView mainImg;
    @FXML
    private ImageView maxImg;
    @FXML
    private ImageView minImg;
    @FXML
    private ImageView restoreImg;
    @FXML
    private ImageView closeImg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Platform.runLater(this::listenerPrimaryStageMousePressedAndDragged);
        //Platform.runLater(()->EffectUtilities.makeDraggable(primaryStage,pane));
        //Platform.runLater(()->UndecoratedHelper.makeDraggable(primaryStage,pane));
        pane.setStyle("-fx-background-color: #2d56cb;");
        heightImg=mainImg.getFitHeight();
        listenerImageViewAll();

    }

    @FXML
    private void minimize(){
        if (!primaryStage.isIconified()){
            primaryStage.setIconified(true);
        }
    }
    @FXML
    private void maximize(){
        AnchorPane.setTopAnchor(mainImg,0.0D);
        if (primaryStage.isMaximized()) {
            maxImg.setVisible(true);
            restoreImg.setVisible(false);
            AnchorPane.setTopAnchor(pane,heightImg/2);
            AnchorPane.setTopAnchor(pane,heightImg/2);
            primaryStage.setMaximized(false);
            if (primaryStage.getY()<-heightImg/2 ) AnchorPane.setTopAnchor(mainImg,heightImg/2);
            else if (primaryStage.getY()>heightImg/2){
                fullImageSize(mainImg,heightImg,true);
            }else resizeImage();
        } else {
            maxImg.setVisible(false);
            restoreImg.setVisible(true);
            fullImageSize(mainImg,heightImg,false);

            AnchorPane.setTopAnchor(pane,0.0D);
            primaryStage.setMaximized(true);
        }
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void listenerPrimaryStageMousePressedAndDragged(){
        //UndecoratedHelper.addListenerDragAndPressNode(pane,primaryStage);
/*        pane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        pane.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
            if (!primaryStage.isMaximized()) resizeImage();
        });*/
        pane.setOnMouseClicked(event -> {
            if (event.getClickCount()==2 && event.getButton()== MouseButton.PRIMARY){
                maximize();
            }
        });
    }
    private void resizeImage(){
        yStage=primaryStage.getY();
        if (yStage>-heightImg/2 && yStage<heightImg/2 /*&& !primaryStage.isMaximized()*/) {
            mainImg.setFitWidth(heightImg*3/4+yStage/2);
            mainImg.setFitHeight(heightImg*3/4+yStage/2);
            AnchorPane.setTopAnchor(mainImg,heightImg/4-yStage/2);
        }
    }
    private void listenerImageViewAll(){
        listenerHover(closeImg,closeImgPathResource,closeImgBackPathResource);
        listenerHover(minImg,minImgPathResource,minImgBackPathResource);
        listenerHover(maxImg,maxImgPathResource,maxImgBackPathResource);
        listenerHover(restoreImg,restoreImgPathResource,restoreImgBackPathResource);
        closeImg.setOnMouseClicked(event -> System.exit(0));
        Platform.runLater(()->{primaryStage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    Platform.runLater(()->{
                        primaryStage.hide();
                        minImg.setImage(new Image(getClass().getResourceAsStream(minImgPathResource)));
                        primaryStage.show();
                    });
                }
            }
        });
        });
    }

    private void listenerHover(ImageView img,String pathImg,String pathImgHover){
        img.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                img.setImage(new Image(getClass().getResourceAsStream(pathImgHover)));
            }
        });
        img.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                img.setImage(new Image(getClass().getResourceAsStream(pathImg)));
            }
        });
    }
    private void fullImageSize(ImageView img,double height,boolean bool){
        if (bool){
            img.setFitWidth(height);
            img.setFitHeight(height);
        }else {
            img.setFitWidth(height/2);
            img.setFitHeight(height/2);
        }
    }
    public  Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ImageView getMinImg() {
        return minImg;
    }

    public void setMinImg(ImageView minImg) {
        this.minImg = minImg;
    }

    public AnchorPane getPane() {
        return pane;
    }

    public void setPane(AnchorPane pane) {
        this.pane = pane;
    }
}
