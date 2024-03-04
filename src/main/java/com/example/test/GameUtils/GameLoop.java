package com.example.test.GameUtils;

import com.example.test.Pieces.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

public class GameLoop {

    private final GameBoard gameBoard = new GameBoard();

    public GameLoop() {


        Piece knight1 = new Knight(4,4,ColorUtil.WHITE,1);
        gameBoard.getTiles()[4][4].setPiece(knight1);
        gameBoard.getTiles()[3][6].setPiece(new Knight(3,6,ColorUtil.BLACK,1));
        gameBoard.getTiles()[2][5].setPiece(new Knight(2,5,ColorUtil.WHITE,1));
        gameBoard.getTiles()[0][0].setPiece(new Queen(0,0,ColorUtil.BLACK,1));
        gameBoard.getTiles()[0][7].setPiece(new Rook(0,7,ColorUtil.BLACK,1));
        gameBoard.getTiles()[0][6].setPiece(new Bishop(0,6,ColorUtil.WHITE,1));
        gameBoard.getTiles()[0][1].setPiece(new King(0,1,ColorUtil.WHITE,1));
        gameBoard.getTiles()[6][6].setPiece(new Pawn(6,6,ColorUtil.WHITE,1));

    }

//    public void loop(){
//        GameGUI.guiUpdate(this.gameBoard,gameBoard.getTiles());
//    }

    public GridPane getGameBoard() {
        return gameBoard;
    }

}
