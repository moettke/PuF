package controller;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Avatar;
import model.Obstacle;


public class Controller {
    // Spielfigur erzeugen und Link zum Bild übergeben
    private Avatar frog;
    private ImageView[] flies = new ImageView[3];
    private ImageView[] hearts = new ImageView[3];
    private int numberObstacles = 5;
    private Path[] paths = new Path[numberObstacles];
    private Path[] pathsFriendly = new Path[5];
    private Obstacle[] obstacles = new Obstacle[numberObstacles];
    private Obstacle[] friendlyObstacles = new Obstacle[5];
    private PathTransition[] transitions = new PathTransition[numberObstacles];
    private PathTransition[] friendlyTransitions = new PathTransition[5];

    //Random random = new Random();
    double hue[] = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
    EventHandler<KeyEvent> myHandler;

    private int countPot = 1;
    private int countDied = 0;
    private int points = 0;


    @FXML
    private Pane Game, danger, field;
    @FXML
    private ImageView pot, heart1, heart2, heart3, fly1, fly2, fly3;
    @FXML
    private Label potLabel, gameOverLabel, pointsLabel;

    //    der Controller kann eine initialize()-Methode definieren, die einmal auf einem
//    implementierenden Controller aufgerufen wird, wenn der Inhalt des zugehörigen fxml
//    Dokuments  vollständig geladen wurde Dies ermöglicht der implementierenden Klasse,
//    alle notwendigen Nachbearbeitungen am Inhalt durchzuführen.
    @FXML
    private void initialize() {

        Game.setStyle("-fx-background-image: url('images/bg_gesamt_2.png')");
        pointsLabel.setText(String.valueOf(points));
        //pot = new ImageView("images/pot_50.png");
        frog = new Avatar("images/frog_50_38_lila.png");
        frog.setFocusTraversable(true);
        frog.relocate(0, 725);
        // Fliegenarray befüllen

        flies[0] = fly1;
        flies[0].relocate(200, 65);
        flies[1] = fly2;
        flies[1].relocate(400, 65);
        flies[2] = fly3;
        flies[2].relocate(600, 65);


        // Herzen in Array packen
        hearts[0] = heart3;
        hearts[1] = heart2;
        hearts[2] = heart1;
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
//        EventHandler für Avatar
        myHandler =
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
                    touchFly();
                    touchPot();
                    touchDanger();
                    checkCollisionBorder();


                };
        // EventHandler wird Avatar hinzugefügt
        frog.addEventHandler(KeyEvent.KEY_PRESSED, myHandler);


        // Changelistener auf die translateXProperty von Obstacles
        for (int i = 0; i < numberObstacles; i++) {
            obstacles[i].translateXProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        checkCollision();
                        // System.out.println("test");

                    }
            );
        }

        // Changelistener auf die translateXProperty von FriendlyObstacles
        for (int i = 0; i < 5; i++) {
            friendlyObstacles[i].translateXProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        checkCollisionFriendly();
                        checkCollisionBorder();
                        // System.out.println("test");

                    }
            );
        }


    }

    public void checkCollision() {
        boolean collision = false;
        for (int i = 0; i < numberObstacles; i++) {
            if (obstacles[i].getBoundsInParent().intersects(frog.getBoundsInParent())) {
                collision = true;
                avatarDied();
            }
        }

    }

    public boolean checkCollisionBorder() {
        boolean collision = false;
        if (field.intersects(frog.getBoundsInParent())) {

        } else {
            collision = true;
            avatarDied();

        }
        return collision;


    }

    public boolean checkCollisionFriendly() {
        boolean collision = false;
        for (int i = 0; i < numberObstacles; i++) {
            if (friendlyObstacles[i].getBoundsInParent().intersects(frog.getBoundsInParent())) {
                collision = true;
                // fahre auf den freundlichen Hindernissen mit und springe auf die Mitte
                double obstWidth = friendlyObstacles[i].boundsInParentProperty().get().getWidth();
                frog.setTranslateX(friendlyObstacles[i].getTranslateX() + (obstWidth / 2));
            }

        }
        return collision;
    }

    public void touchDanger() {
        if (frog.getBoundsInParent().intersects(danger.getBoundsInParent()) && !checkCollisionFriendly() && !checkCollisionBorder()) {
            avatarDied();

        }

    }

    public void touchFly() {
        for (int i = 0; i < flies.length; i++) {
            if (frog.getBoundsInParent().intersects(flies[i].getBoundsInParent()) && frog.isTarget() == false) {
                frog.setImgUrl("images/frog_50_38_turkis_fly.png");
                frog.setTarget(true);
                // Hat nicht funktioniert, Fliege war zwar nicht mehr zu sehen, aber trotzdem noch da
                //Game.getChildren().remove(flies[i]);
                flies[i].relocate(0, 0);
                calcPoints();
                pointsLabel.setText(String.valueOf(points));
                System.out.println("gefressen");
            }
        }

    }

    public void calcPoints() {
        points += 10;

    }

    public void touchPot() {
        if (frog.getBoundsInParent().intersects(pot.getBoundsInParent()) && frog.isTarget() == true) {

            frog.setImgUrl("images/frog_50_38_lila.png");
            frog.setTarget(false);
            Image img = new Image("images/pot_fly_50.png");
            pot.setImage(img);
            potLabel.setText(String.valueOf(countPot));
            calcPoints();
            pointsLabel.setText(String.valueOf(points));
            System.out.println("abgelegt" + countPot);
            countPot++;
            if (countPot == 4) {
                win();
            }
        }

    }

    // alle Hindernisse erstellen
    private void createObstacle() {
        for (int i = 0; i < numberObstacles; i++) {
            int randomIndex = (int) (Math.random() * (9 - 0)) + 0;
            if (i < 3) {
                obstacles[i] = new Obstacle("images/car_green_40_r.png", false);
            } else {
                obstacles[i] = new Obstacle("images/car_red_40.png", false);
            }
            obstacles[i].changeColor(hue[randomIndex]);
        }
    }

    // alle Hindernisse erstellen
    private void createFriendlyObstacle() {
        for (int i = 0; i < 5; i++) {
            int randomIndex = (int) (Math.random() * (9 - 0)) + 0;
            if (i == 1 || i == 3) {
                friendlyObstacles[i] = new Obstacle("images/tree_150.png", true);
            } else {
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
            if (i == 1 || i == 3) {
                path.getElements().add(new MoveTo(0, y));
                // Endpunkt
                path.getElements().add(new HLineTo(700));

            } else {
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
            int randomSeconds = (int) (Math.random() * (7 - 3)) + 3;
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
            int randomSeconds = (int) (Math.random() * (7 - 5)) + 5;
            //System.out.println(seconds);
            PathTransition t = new PathTransition();
            t.setNode(friendlyObstacles[i]);
            t.setDuration(Duration.seconds(randomSeconds));
            t.setPath(pathsFriendly[i]);
            //t.setCycleCount(2);
            t.setCycleCount(PathTransition.INDEFINITE);
            if (i == 1 || i == 3) {
                t.setAutoReverse(true);
            }

            friendlyTransitions[i] = t;
        }
    }

    private void avatarDied() {
        // Wenn Frosch Fliege hat
        if (frog.isTarget() == true) {
            frog.setImgUrl("images/frog_50_38_lila.png");
            frog.setTarget(false);
        }
        // Leben entfernen
        Game.getChildren().remove(hearts[countDied]);
        // Avatar zurück zum Start
        frog.setTranslateX(0);
        frog.setTranslateY(0);
        // EventHandler entfernen und nach 1.5 Sekunde wieder hinzufügen
        frog.removeEventHandler(KeyEvent.KEY_PRESSED, myHandler);
        // Pathtransition pausieren und nach 1.5 Sekunden weiterlaufen lassen
        for (PathTransition elem : transitions) {
            elem.pause();
        }
        for (PathTransition elem : friendlyTransitions) {
            elem.pause();
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(event -> {
            frog.addEventHandler(KeyEvent.KEY_PRESSED, myHandler);
            for (PathTransition elem : transitions) {
                elem.play();
            }
            for (PathTransition elem : friendlyTransitions) {
                elem.play();
            }
        });
        pause.play();

        countDied++;

        // System.out.println(countDied);
        if (countDied == 3) {
            // Gameover bekommt das PauseTransition-OBjekt übergeben
            gameOver(pause);

        }


    }

    private void gameOver(PauseTransition pause) {
        System.out.println("Gameover");
        for (PathTransition elem : transitions) {
            elem.stop();

        }
        for (PathTransition elem : friendlyTransitions) {
            elem.stop();
        }
        gameOverLabel.setText("Game Over!\nPoints: " + points);
        // PauseTranstition wird gestoppt
        pause.stop();
        Game.getChildren().removeAll();


//        frog.removeEventHandler(KeyEvent.KEY_PRESSED, myHandler);

    }

    public void win() {
        gameOverLabel.setText("YOU WIN!\nPoints: " + points);
        frog.removeEventHandler(KeyEvent.KEY_PRESSED, myHandler);
    }
}

