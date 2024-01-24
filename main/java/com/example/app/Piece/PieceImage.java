package com.example.app.Piece;

import android.graphics.Bitmap;

//定义图片类
public class PieceImage {
    private Bitmap image;
    private int imageId;
    //定义构造函数
    public PieceImage(Bitmap image, int imageId) {
        super();
        this.image = image;
        this.imageId = imageId;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

