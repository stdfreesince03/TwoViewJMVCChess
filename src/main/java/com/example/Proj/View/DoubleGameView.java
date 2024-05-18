package com.example.Proj.View;

import com.example.Proj.Controller.GameController;
import com.example.Proj.Model.GameBoard;
import javafx.scene.layout.HBox;

public class DoubleGameView extends HBox {
    private final GameView whiteView;
    private final GameView blackView;

    public DoubleGameView(GameBoard gameBoard) {
        whiteView = new GameView(gameBoard, false);
        blackView = new GameView(gameBoard, true);

        this.getChildren().addAll(whiteView, blackView);
    }

//    public void updateViews(GameBoard gameBoard) {
//        whiteView.update(gameBoard);
//        blackView.update(gameBoard);
//    }
//
//    public GameView getWhiteView() {
//        return whiteView;
//    }
//
//    public GameView getBlackView() {
//        return blackView;
//    }

}
