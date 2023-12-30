package com.example.test.GameUtils;

import com.example.test.GUI.GameGUI;
import com.example.test.Pieces.Knight;
import com.example.test.Pieces.Piece;
import com.example.test.Pieces.PieceType;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.io.File;

public class GameLoop {
    private final GameBoard gameBoard;

    public GameLoop() {


        gameBoard = new GameBoard();
        Piece knight1 = new Knight(4,4,ColorUtil.WHITE,1);
//        knight1.makeDraggable();
        gameBoard.getTiles()[4][4].setPiece(knight1);
        gameBoard.getTiles()[3][6].setPiece(new Knight(3,6,ColorUtil.BLACK,1));
        gameBoard.getTiles()[2][5].setPiece(new Knight(2,5,ColorUtil.WHITE,1));





    }

    private double mouseAnchorY;
    private double mouseAnchorX;

    public void makeDraggable(Node node){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
            System.out.println(mouseAnchorX);
            System.out.println(mouseAnchorY);
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }

    public void loop(){
        GameGUI.guiUpdate(this.gameBoard,gameBoard.getTiles());
    }

    public TilePane getGameBoard() {
        return gameBoard;
    }

}
