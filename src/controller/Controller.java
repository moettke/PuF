package controller;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Avatar;
import model.Background;
import model.Obstacle;

public class Controller {

    // String Array mit den Bildernamen für den Hintergrund
    private String[] images = {"bg13.png", "bg12.jpg", "bg11.jpg", "bg10.jpg", "bg9.jpg", "bg8.jpg", "bg7.png",
                "bg6.jpg", "bg5.jpg", "bg4.jpg", "bg3.jpg", "bg2.jpg", "bg1.png"};
    // Spielfigur
    private Avatar avatar;
    private Obstacle car1;
    private Obstacle car2;

    @FXML
    private GridPane Game;

    public void initialize() {
        Game.setStyle("-fx-background-image: url('images/bg_gesamt.png')");
        //GridPane wird mit 1 Spalte und 13 Zeilen befüllt
        for(int row=0;row<1;row++){
            for(int col=1;col<14;col++){
                //Zeilenhöhe auf 55px setzen
                RowConstraints rc = new RowConstraints();
                rc.setMinHeight(55);
                rc.setPrefHeight(55);
                Game.getRowConstraints().addAll(rc);
                // Bildernamen aus dem Array holen und neues Bildobjekt erzeugen
//                Image img = new Image("images/" + images[col-1]);
                //Bilder dem Grid hinzufügen
//                Game.add(new Background(img), row, col);
            }
        }
        // Spielfigur erzeugen und Link zum Bild übergeben
        avatar = new Avatar("images/frog_50_38_lila.png");
        // Spielfigur dem Grid hinzufügen in Spalte 0, Zeile 13
        Game.add(avatar, 0, 13);

        // Hindernis erzeugen und Link zum Bild übergeben
        car1 = new Obstacle("images/car_red_40.png");
        car2 = new Obstacle("images/car_red_40.png");
        // Spielfigur dem Grid hinzufügen in Spalte 0, Zeile 13
        Game.add(car1, 0, 12);
        Game.add(car2, 0, 11);

        Line line = new Line();
        line.setStartX(820);
        line.setStartY(20);
        line.setEndX(-100);
        line.setEndY(20);

        Line line2 = new Line();
        line2.setStartX(820);
        line2.setStartY(0);
        line2.setEndX(-100);
        line2.setEndY(0);
//        Rectangle r = new Rectangle(400,400);
//        Circle circle = new Circle(100);
        PathTransition t = new PathTransition();
        t.setNode(car1);
        t.setDuration(Duration.seconds(7));
        t.setPath(line);
        t.setCycleCount(PathTransition.INDEFINITE);
        t.play();

        PathTransition t2 = new PathTransition();
        t2.setNode(car2);
        t2.setDuration(Duration.seconds(6));
        t2.setPath(line2);
        t2.setCycleCount(PathTransition.INDEFINITE);
        t2.play();


    }

    // Welche Tasten wurden gedrückt, Aufruf der Bewegungsmethoden des Avatar-Objekts
    public void handleOnKeyPressed(KeyEvent event)    {
        //System.out.println("Pressed key text: " + event.getCode());
        if (event.getCode() == KeyCode.RIGHT){
            avatar.moveRight();
            car1.moveRight();
        }
        if (event.getCode() == KeyCode.LEFT){
            avatar.moveLeft();
        }
        if (event.getCode() == KeyCode.UP){
            avatar.moveUp();
        }
        if (event.getCode() == KeyCode.DOWN){
            avatar.moveDown();
        }

    }




}
