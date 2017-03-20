package ru.yandex.ajwar.util;

import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by Ajwar on 16.03.2017.
 */
public class ResizeListener implements EventHandler<MouseEvent> {
    double dx;
    double dy;
    double deltaX;
    double deltaY;
    double border = 5;
    boolean moveH;
    boolean moveV;
    boolean resizeH = false;
    boolean resizeV = false;
    Dimension2D minSize=new Dimension2D(400,400);
    Scene scene;
    Stage stage;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent t) {
        if(MouseEvent.MOUSE_MOVED.equals(t.getEventType())){
            if(t.getX() < border && t.getY() < border){
                scene.setCursor(Cursor.NW_RESIZE);
                resizeH = true;
                resizeV = true;
                System.out.println("1");
                moveH = true;
                moveV = true;
            }
            else if(t.getX() < border && t.getY() > scene.getHeight() -border){
                scene.setCursor(Cursor.SW_RESIZE);
                System.out.println("2");
                resizeH = true;
                resizeV = true;
                moveH = true;
                moveV = false;
            }
            else if(t.getX() > scene.getWidth() -border && t.getY() < border){
                scene.setCursor(Cursor.NE_RESIZE);
                resizeH = true;
                resizeV = true;
                System.out.println("3");
                moveH = false;
                moveV = true;
            }
            else if(t.getX() > scene.getWidth() -border && t.getY() > scene.getHeight() -border){
                scene.setCursor(Cursor.SE_RESIZE);
                System.out.println("4");
                resizeH = true;
                resizeV = true;
                moveH = false;
                moveV = false;
            }
            else if(t.getX() < border || t.getX() > scene.getWidth() -border){
                scene.setCursor(Cursor.E_RESIZE);
                System.out.println("5");
                resizeH = true;
                resizeV = false;
                moveH = (t.getX() < border);
                System.out.println(moveH);
                moveV = false;
            }
            else if(t.getY() < border || t.getY() > scene.getHeight() -border){
                scene.setCursor(Cursor.N_RESIZE);
                System.out.println("6");
                resizeH = false;
                resizeV = true;
                moveH = false;
                moveV = (t.getY() < border);
                System.out.println(moveV);
            }
            else{
                scene.setCursor(Cursor.DEFAULT);
                System.out.println("7");
                resizeH = false;
                resizeV = false;
                moveH = false;
                moveV = false;
            }
        }
        else if(MouseEvent.MOUSE_PRESSED.equals(t.getEventType())){
            dx = stage.getWidth() - t.getX();
            dy = stage.getHeight() - t.getY();
        }
        else if(MouseEvent.MOUSE_DRAGGED.equals(t.getEventType())){
            /*if (resizeH && !resizeV){
                if (stage.getWidth()>=minSize.getWidth()){

                }
            }*/
            if(resizeH){
                if(stage.getWidth() <= minSize.getWidth()){
                    if(moveH){
                        /*System.out.println("5555");
                        deltaX = stage.getX()-t.getScreenX();
                        if(t.getX() < 0){// if new > old, it's permitted
                            stage.setWidth(deltaX+stage.getWidth());
                            System.out.println("2222");
                            stage.setX(t.getScreenX());
                        }*/
                    }
                    else{
                        if(t.getX()+dx - stage.getWidth() > 0){
                            System.out.println("tut");
                            stage.setWidth(t.getX()+dx);
                            if (resizeV) {
                                System.out.println("t.getY="+t.getY()+"         dy="+dy);
                                stage.setHeight(t.getY()+dy);
                            }
                        }
                    }
                }
                else if(stage.getWidth() > minSize.getWidth()){
                    if(moveH){
                        System.out.println("6666");
                        deltaX = stage.getX()-t.getScreenX();
                        stage.setWidth(deltaX+stage.getWidth());
                        stage.setX(t.getScreenX());
                        if (resizeV) {
                            stage.setHeight(deltaY+stage.getHeight());
                            stage.setHeight(t.getY()+dy);
                            System.out.println("я туттатутатуа!!!!!");
                        }
                    }
                    else{
                        //тут все правильно
                        System.out.println("tutfjkhjk");
                        stage.setWidth(t.getX()+dx);
                        if (resizeV && !moveV) stage.setHeight(t.getY()+dy);
                        if (resizeV && moveV) {
                            deltaY = stage.getY()-t.getScreenY();
                            stage.setHeight(deltaY+stage.getHeight());
                            stage.setY(t.getScreenY());
                        }
                    }
                }
            } else {
                if(stage.getHeight() <= minSize.getHeight()){
                    if(moveV){
                        deltaY = stage.getY()-t.getScreenY();
                        if(t.getY() < 0){// if new > old, it's permitted
                            stage.setHeight(deltaY+stage.getHeight());
                            System.out.println("я 0");
                            stage.setY(t.getScreenY());
                        }
                    }
                    else{
                        if(t.getY()+dy - stage.getHeight() > 0){
                            System.out.println("я 2323");
                            stage.setHeight(t.getY()+dy);
                        }
                    }
                }
                else if(stage.getHeight() > minSize.getHeight()){
                    if(moveV){
                        deltaY = stage.getY()-t.getScreenY();
                        stage.setHeight(deltaY+stage.getHeight());
                        System.out.println("я 235235вапвавап");
                        stage.setY(t.getScreenY());
                    }
                    else{
                        System.out.println("я тутаорпалыоап");
                        stage.setHeight(t.getY()+dy);
                    }
                }
            }
        }
    }
}
