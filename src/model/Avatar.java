package model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Klasse Avatar mit den Instanzvariablen und den Methoden zum Lokalisieren und Bewegen des Avatars
public class Avatar extends ImageView {

    private String img_url;
    private double x_coordinate;
    private double y_coordinate;
    private Image img;
    private double speed;

    public Avatar(String img_url){
        this.img_url = img_url;
//        this.x_coordinate = 0;
//        this.y_coordinate = 0;
        this.img = new Image(img_url);
        this.setImage(img);
        this.speed = 55.0;
    }

    public void set_x_coordinate(){
        this.x_coordinate = this.getTranslateX();
    }
    public void set_y_coordinate(){
        this.y_coordinate = this.getTranslateY();
    }

    public void moveRight() {
        // die aktuellen Koordinaten der Obstacles ermitteln
        double x = this.translateXProperty().doubleValue();
        double y = this.translateYProperty().doubleValue();
        System.out.println("X: " + x);
        System.out.println("Y: " + y);

        System.out.println("Aktuelle x-Koordinate: " + this.getTranslateX());
        System.out.println("Aktuelle y-Koordinate: " + this.getTranslateY());
        if (this.getTranslateX() <= 705){
            this.setTranslateX(x_coordinate + speed);
            this.set_x_coordinate();
        }
    }
    public void moveLeft() {
        System.out.println("Aktuelle x-Koordinate: " + this.getTranslateX());
        System.out.println("Aktuelle y-Koordinate: " + this.getTranslateY());
        if (this.getTranslateX() >= 10) {
            this.setTranslateX(x_coordinate - speed);
            this.set_x_coordinate();
        }
    }
    public void moveUp() {


//        System.out.println("LayoutX: " + this.getX());
//        System.out.println("LayoutY: " + this.getY());
        System.out.println("Aktuelle x-Koordinate: " + (0 -this.getTranslateX()));
        System.out.println("Aktuelle y-Koordinate: " + (670 + this.getTranslateY()));
        if (this.getTranslateY() >= -665) {
            this.setTranslateY(y_coordinate - speed);
            this.set_y_coordinate();
        }
    }
    public void moveDown() {
        System.out.println(this.localToScene(this.getBoundsInLocal()));
        System.out.println("Aktuelle x-Koordinate: " + this.getTranslateX());
        System.out.println("Aktuelle y-Koordinate: " + (670 + this.getTranslateY()));
        if (this.getTranslateY() < 0) {
            this.setTranslateY(y_coordinate + speed);
            this.set_y_coordinate();
        }
    }


}
