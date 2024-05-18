package com.example.Proj.View;

import com.example.Proj.Controller.GameController;
import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Model.Move;
import com.example.Proj.Model.Tile;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.List;

public class DoubleGameView extends HBox {
    private final GameView whiteView;
    private final GameView blackView;

    public DoubleGameView(GameBoard gameBoard) {
        whiteView = new GameView(gameBoard, false);
        blackView = new GameView(gameBoard, true);

        this.getChildren().addAll(whiteView,createSeparator() ,blackView);
    }

    private Pane createSeparator(){
        Pane separator = new Pane();
        separator.setPrefWidth(2);
        separator.setStyle("-fx-background-color: darkkhaki;");
        return separator;
    }

    public void path(TileView tv,GameBoard gameBoard){
        whiteView.path(tv, gameBoard);
        blackView.path(tv, gameBoard);
    }

    public void restartUpdate(GameBoard gameBoard){
        whiteView.restartUpdate(gameBoard);
        blackView.restartUpdate(gameBoard);
    }

    public void allOff(){
        whiteView.allOff();
        blackView.allOff();
    }

    public void updateAfterMove(Move move, GameBoard gameBoard){
        whiteView.updateAfterMove(move, gameBoard);
        blackView.updateAfterMove(move, gameBoard);
    }


    public GameView getWhiteView() {
        return whiteView;
    }

    public GameView getBlackView() {
        return blackView;
    }
}
