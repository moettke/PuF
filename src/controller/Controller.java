package controller;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Avatar;
import model.Obstacle;

import java.util.Random;


public class Controller {
    // Spielfigur erzeugen und Link zum Bild übergeben
    private Avatar frog;
    private int numberObstacles = 5;
    Path[] paths = new Path[numberObstacles];
    Path[] pathsFriendly = new Path[5];
//    PathTransition t = new PathTransition();
//    PathTransition t2 = new PathTransition();
    private Obstacle[] obstacles = new Obstacle[numberObstacles];
    private Obstacle[] friendlyObstacles = new Obstacle[5];
    private PathTransition[] transitions = new PathTransition[numberObstacles];
    private PathTransition[] friendlyTransitions = new PathTransition[5];
//    private ImageView car;
//    private ImageView car2;
    Random random = new Random();
    double hue[] = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9 };



    @FXML
    private Pane Game;



    //    der Controller kann eine initialize()-Methode definieren, die einmal auf einem
//    implementierenden Controller aufgerufen wird, wenn der Inhalt des zugehörigen fxml
//    Dokuments  vollständig geladen wurde Dies ermöglicht der implementierenden Klasse,
//    alle notwendigen Nachbearbeitungen am Inhalt durchzuführen.
    @FXML
    private void initialize() {

        Game.setStyle("-fx-background-image: url('images/bg_gesamt.png')");
        frog = new Avatar("images/frog_50_38_lila.png");
        //car = new ImageView("images/car_green_40_r.png");
//        int randomIndex = (int) (Math.random()*(9 - 0)) + 0;
//        System.out.println(randomIndex);
//        obstacles[0] = new Obstacle("images/car_green_40_r.png");
//        obstacles[0].changeColor(hue[randomIndex]);
//        obstacles[1] = new Obstacle("images/car_green_40_r.png");
//        obstacles[1].changeColor(hue[randomIndex]);
//        obstacles[2] = new Obstacle("images/car_red_40.png");
//        obstacles[3] = new Obstacle("images/car_red_40.png");
        // car.relocate(500, 500);
        frog.setFocusTraversable(true);
        frog.relocate(0, 725);

        //Game.getChildren().add(car);
        // Pfade und Transitions erstellen
        createObstacle();
        createFriendlyObstacle();
        createPathLeftToRight();
        createPathRightToLeft();
        createPathLeftToRightFriendly();
        createTransition();
        createTransitionFriendly();
        // Hindernisse hinzufügen
        for (int i = 0; i < numberObstacles; i++) {
            Game.getChildren().add(obstacles[i]);
        }

        // freundliche Hindernisse hinzufügen
        for (int i = 0; i < 5; i++) {
            Game.getChildren().add(friendlyObstacles[i]);
        }
        // Avatar hinzufügen
        Game.getChildren().add(frog);

        // Starte alle Transitions
        for (int i = 0; i < numberObstacles; i++) {
            transitions[i].play();
        }

        // Starte alle friendlyTransitions
        for (int i = 0; i < 5; i++) {
            friendlyTransitions[i].play();
        }







//        Path path = new Path();
//        path.getElements().add(new MoveTo(-90, 690));
//        path.getElements().add(new HLineTo(900));


//        t.setNode(car);
//        t.setDuration(Duration.seconds(5));
//        t.setPath(path);
//        t.setCycleCount(PathTransition.INDEFINITE);
//        t.play();

//        t.setNode(cars[0]);
//        t.setDuration(Duration.seconds(5));
//        t.setPath(createPath()[0]);
//        t.setCycleCount(PathTransition.INDEFINITE);
//        t.play();
//
//        t2.setNode(cars[1]);
//        t2.setDuration(Duration.seconds(6));
//        t2.setPath(createPath()[1]);
//        t2.setCycleCount(PathTransition.INDEFINITE);
//        t2.play();





        // EventHandler für die Bewegung des Avatars
        frog.addEventHandler(KeyEvent.KEY_PRESSED,
                keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case UP:
                            frog.moveUp();
                            break;
                        case DOWN:
                            frog.moveDown();
                            break;
                        case LEFT:
                            frog.moveLeft();
                            break;
                        case RIGHT:
                            frog.moveRight();
                            break;
                    }
                    //checkCollision();

                });

        // Changelistener auf die translateXProperty von cars[]
        for (int i = 0; i < numberObstacles; i++) {
            obstacles[i].translateXProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        checkCollision();
                       // System.out.println("hahaha");

                    }
            );
        }

        // Changelistener auf die translateXProperty von cars[]
        for (int i = 0; i < 5; i++) {
            friendlyObstacles[i].translateXProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        checkCollision();
                        // System.out.println("hahaha");

                    }
            );
        }


        // andere Schreibweise
//        car.translateXProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> property, Number oldValue, Number newValue) {
//                System.out.println(oldValue + " " + newValue);
//            }
//        });


    }

    public void checkCollision() {
        boolean collision = false;
        for (int i = 0; i < numberObstacles; i++) {
            if (obstacles[i].getBoundsInParent().intersects(frog.getBoundsInParent())) {
                collision = true;
                transitions[i].stop();
//                System.out.println("if " + i);

            }
            if (friendlyObstacles[i].getBoundsInParent().intersects(frog.getBoundsInParent())) {
                collision = true;
                // fahre auf den freundlichen Hindernissen mit
                double obstWidth = friendlyObstacles[i].boundsInParentProperty().get().getWidth();

                frog.setTranslateX(friendlyObstacles[i].getTranslateX() + (obstWidth/2));
//                System.out.println("if friendly " + i);

            }
//            else {
//                System.out.println("TOT!");
//            }

        }
        if (collision) {
//            System.out.println("Getroffen");

        } else {

           // System.out.println("nicht Getroffen");
        }
    }

    // alle Hindernisse erstellen
    private void createObstacle() {
        for (int i = 0; i < numberObstacles; i++) {
            int randomIndex = (int) (Math.random()*(9 - 0)) + 0;
            if (i < 3) {
                obstacles[i] = new Obstacle("images/car_green_40_r.png", false);
            }
            else {
                obstacles[i] = new Obstacle("images/car_red_40.png", false);
            }
            obstacles[i].changeColor(hue[randomIndex]);
        }
    }

    // alle Hindernisse erstellen
    private void createFriendlyObstacle() {
        for (int i = 0; i < 5; i++) {
            int randomIndex = (int) (Math.random()*(9 - 0)) + 0;
            if(i == 1 || i == 3) {
                friendlyObstacles[i] = new Obstacle("images/tree_150.png", true);
            }
            else{
                friendlyObstacles[i] = new Obstacle("images/tree_300.png", true);
            }

            friendlyObstacles[i].changeColor(hue[randomIndex]);
        }
    }

    // erstelle alle Pfade für die Pfandtransition
    private void createPathLeftToRight() {
        int position = 0;
        for (int i = 0; i < 3; i++) {
            Path path = new Path();
            // eine Ebene nach oben rücken
            int y = 690 - position;
            //System.out.println(y);
            // MoveTo(Startpunkt x und y)
            path.getElements().add(new MoveTo(-90, y));
            // Endpunkt
            path.getElements().add(new HLineTo(900));
            paths[i] = path;
            position = position + 60;
        }
    }

    // erstelle alle Pfade für die Pfandtransition
    private void createPathRightToLeft() {
        int position = 0;
        for (int i = 3; i < numberObstacles; i++) {
            Path path = new Path();
            // eine Ebene nach oben rücken
            int y = 525 - position;
            //System.out.println(y);
            // MoveTo(Startpunkt x und y)
            path.getElements().add(new MoveTo(900, y));
            // Endpunkt
            path.getElements().add(new HLineTo(-90));
            paths[i] = path;
            position = position + 60;
        }
    }

    // erstelle alle Pfade für die Pfandtransition
    private void createPathLeftToRightFriendly() {
        int position = 0;
        for (int i = 0; i < 5; i++) {
            Path path = new Path();
            // eine Ebene nach oben rücken
            int y = 360 - position;
            //System.out.println(y);
            // MoveTo(Startpunkt x und y)
            if(i == 1 || i == 3){
                path.getElements().add(new MoveTo(0, y));
                // Endpunkt
                path.getElements().add(new HLineTo(700));

            }
            else{
                path.getElements().add(new MoveTo(-150, y));
                // Endpunkt
                path.getElements().add(new HLineTo(1150));
            }



            pathsFriendly[i] = path;
            position = position + 55;
        }
    }

    // erstelle alle Pfade für die Pfandtransition
    private void createTransition() {

        for (int i = 0; i < numberObstacles; i++) {
            // zufällige Geschwindigkeit der Hindernisse
            int randomSeconds = (int) (Math.random()*(7 - 3)) + 3;
            //System.out.println(seconds);
            PathTransition t = new PathTransition();
            t.setNode(obstacles[i]);
            t.setDuration(Duration.seconds(randomSeconds));
            t.setPath(paths[i]);
            //t.setCycleCount(2);
            t.setCycleCount(PathTransition.INDEFINITE);
            transitions[i] = t;
        }
    }

    // erstelle alle Pfade für die Pfandtransition
    private void createTransitionFriendly() {

        for (int i = 0; i < 5; i++) {
            // zufällige Geschwindigkeit der Hindernisse
            int randomSeconds = (int) (Math.random()*(7 - 5)) + 5;
            //System.out.println(seconds);
            PathTransition t = new PathTransition();
            t.setNode(friendlyObstacles[i]);
            t.setDuration(Duration.seconds(randomSeconds));
            t.setPath(pathsFriendly[i]);
            //t.setCycleCount(2);
            t.setCycleCount(PathTransition.INDEFINITE);
            if(i == 1 || i == 3){
                t.setAutoReverse(true);
            }

            friendlyTransitions[i] = t;
        }
    }
}
