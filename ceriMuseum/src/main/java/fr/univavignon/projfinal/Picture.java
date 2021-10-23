package fr.univavignon.projfinal;

import android.graphics.Bitmap;

public class Picture {

    private Bitmap bitmap;
    private String description;

    public Picture(Bitmap bitmap, String description){
        this.bitmap = bitmap;
        this.description = description;
    }

    public Bitmap getBitmap(){return bitmap;}
    public String getDescription(){return description;}
}
