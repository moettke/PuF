package model;

import controller.Controller;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Avatar extends ImageView {

    // Eigenschaften
    private String imgUrl;
    private Bounds bounds;
    private Image img;
    private double speed;

    public Avatar(String img_url){
        this.imgUrl = img_url;
        this.img = new Image(img_url);
        this.setImage(img);
        this.speed = 55.0;
    }
    // Methoden
    public void moveRight() {
//        bounds = this.getBoundsInParent();
//        System.out.println(bounds.getMinX()+55);
        this.setTranslateX(this.getTranslateX() + 55);
    }

    public void moveLeft() {
//        bounds = this.getBoundsInParent();
//        System.out.println(bounds.getMinX()-55);
        this.setTranslateX(this.getTranslateX() - 55);

    }

    public void moveUp() {
//        bounds = this.getBoundsInParent();
//        System.out.println(bounds.getMinX());
//        System.out.println(bounds.getMinY()-55);
//        System.out.println(bounds.getMaxX());
//        System.out.println(bounds.getMaxY());
//        System.out.println(bounds.getWidth());
//        System.out.println(bounds.getHeight());
//        System.out.println("x");
//        System.out.println(frog.getLayoutX());
//        System.out.println("y");
//        System.out.println(frog.getTranslateY());
        //frog.relocate(frog.getLayoutX(), frog.getLayoutY()-55);
        this.setTranslateY(this.getTranslateY() - 55);
    }

    public void moveDown() {
//        bounds = this.getBoundsInParent();
//        System.out.println(bounds.getMinY()+55);
        this.setTranslateY(this.getTranslateY() + 55);
    }
}
