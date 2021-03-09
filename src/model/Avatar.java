package model;


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
        this.setTranslateX(this.getTranslateX() + 55);
    }

    public void moveLeft() {
        this.setTranslateX(this.getTranslateX() - 55);
    }

    public void moveUp() {
        this.setTranslateY(this.getTranslateY() - 55);
    }

    public void moveDown() {
        this.setTranslateY(this.getTranslateY() + 55);
    }
}
