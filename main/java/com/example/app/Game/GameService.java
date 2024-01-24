package com.example.app.Game;

import com.example.app.Utils.LinkInfo;
import com.example.app.Piece.Piece;

/* 游戏的服务接口 */
public interface GameService {
	public void start();
	public Piece[][] getPieces();
	public boolean hasPieces();
	public void shuffle();
	public Piece findPiece(float touchX, float touchY);
	public LinkInfo link(Piece p1, Piece p2);
}
