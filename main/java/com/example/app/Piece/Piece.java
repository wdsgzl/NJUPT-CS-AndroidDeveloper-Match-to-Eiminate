package com.example.app.Piece;

import android.graphics.Point;

import com.example.app.Game.GameConf;

/* 方块对象 */
public class Piece {

    private PieceImage pieceImage;//保存方块对象的所对应的图片
    private int beginX;//该方块的左上角的x坐标
    private int beginY;//该方块的左上角的y座标
    private int indexX;//该对象在Piece[][]数组中第一维的索引值
    private int indexY;//该对象在Piece[][]数组中第二维的索引值
    public Piece(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }
    //获取该Piece的中心位置
    public Point getCenter() {
        return new Point(getBeginX() + GameConf.PIECE_WIDTH / 2, getBeginY() + GameConf.PIECE_HEIGHT / 2);
    }
    //判断图片是否相同
    public boolean isSameImage(Piece otherPieceImage) {
        if (pieceImage == null) {
            if (otherPieceImage.pieceImage != null)
                return false;
        }
        // 当两个Piece封装图片资源ID相同时，即可认为这两个Piece上的图片相同。
        return pieceImage.getImageId() == otherPieceImage.pieceImage
                .getImageId();
    }
     //以下代码分别第一行为获取信息，第二行为赋信息值
    public int getBeginX() {
        return beginX;
    }
    public void setBeginX(int beginX) {
        this.beginX = beginX;
    }

    public int getBeginY() {
        return beginY;
    }
    public void setBeginY(int beginY) {
        this.beginY = beginY;
    }

    public int getIndexX() {
        return indexX;
    }
    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getIndexY() {
        return indexY;
    }
    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }

    public PieceImage getPieceImage() {
        return pieceImage;
    }
    public void setPieceImage(PieceImage pieceImage) {
        this.pieceImage = pieceImage;
    }
}

