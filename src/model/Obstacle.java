package model;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Klasse Avatar mit den Instanzvariablen und den Methoden zum Lokalisieren und Bewegen des Avatars
public class Obstacle extends ImageView {

    private String img_url;
    private Image img;
    private boolean friendly;
    ColorAdjust colorAdjust;


    public Obstacle(String img_url, boolean friendly){
        this.img_url = img_url;
        this.img = new Image(img_url);
        this.setImage(img);
        this.colorAdjust = new ColorAdjust();
        this.friendly = friendly;
    }

    public void changeColor(double hue){
        this.colorAdjust.setHue(hue);
        this.setEffect(colorAdjust);
    }






}
