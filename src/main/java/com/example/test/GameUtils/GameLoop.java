package com.example.test.GameUtils;

import com.example.test.Pieces.Knight;
import com.example.test.Pieces.Piece;
import javafx.scene.layout.TilePane;

public class GameLoop {
    private final GameBoard gameBoard;

    public GameLoop() {


        gameBoard = new GameBoard();
        Piece knight1 = new Knight(4,4,ColorUtil.WHITE,1);
        gameBoard.getTiles()[4][4].setPiece(knight1);
        gameBoard.getTiles()[3][6].setPiece(new Knight(3,6,ColorUtil.BLACK,1));
        gameBoard.getTiles()[2][5].setPiece(new Knight(2,5,ColorUtil.WHITE,1));

    }

//    public void loop(){
//        GameGUI.guiUpdate(this.gameBoard,gameBoard.getTiles());
//    }

    public TilePane getGameBoard() {
        return gameBoard;
    }

}
