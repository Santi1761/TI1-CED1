package model.entity;

import javafx.scene.image.ImageView;

public class Cat {

    private static Cat cat;
    private ImageView catImage;

    private Cat(){}

    public static Cat getInstance() {
        if(cat == null) cat = new Cat();
        return cat;
    }

    public ImageView getCatImage() {
        return catImage;
    }

    public void setCatImage(ImageView catImage) {
        this.catImage = catImage;
    }
}
