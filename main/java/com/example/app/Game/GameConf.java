package com.example.app.Game;

import android.content.Context;

/* 游戏设置 */
public class GameConf {
	public static int PIECE_WIDTH = 45;
	public static int PIECE_HEIGHT = 45;
	public static int DEFAULT_TIME = 30;
	private int xSize;
	private int ySize;
	private int beginImageX;
	private int beginImageY;
	private long gameTime;
	private Context context;
	private int screenWidth;
	private int screenHeight;

	public GameConf(int xSize, int ySize, int screenWidth, int screenHeight,
			long gameTime, Context context) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.gameTime = gameTime;
		this.context = context;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
        float scale = context.getResources().getDisplayMetrics().density;
		//定义图片大小
        PIECE_WIDTH = (int) (60 * scale + 0.5f);
        PIECE_HEIGHT = (int) (60 * scale + 0.5f);
        setBeginImage();
	}
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}
	public void setySize(int ySize) {
		this.ySize = ySize;
	}
	public void setBeginImage(){
        this.beginImageX = (screenWidth-PIECE_WIDTH*xSize)/2;
		this.beginImageY = (screenHeight-500-PIECE_HEIGHT*ySize)/2;
    }
	public long getGameTime() {
		return gameTime;
	}//返回游戏剩余时间
	public int getXSize() {
		return xSize;
	}//返回列数
	public int getYSize() {
		return ySize;
	}//返回行数
	//获取图片的坐标
	public int getBeginImageX() {
		return beginImageX;
	}
	public int getBeginImageY() {
		return beginImageY;
	}
	public Context getContext() {
		return context;
	}
}
