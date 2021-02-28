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
    private boolean target;

    public Avatar(String imgUrl){
        this.imgUrl = imgUrl;
        this.img = new Image(imgUrl);
        this.setImage(img);
        this.speed = 55.0;
        this.target = false;
    }
    // Methoden

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
        this.img = new Image(imgUrl);
        this.setImage(img);
    }
    public void setTarget(boolean target){
        this.target = target;
    }
    public boolean isTarget(){
        return this.target;
    }
    // Movement
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
