package com.example.test.GameUtils;

import com.example.test.Pieces.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GameLoop {

    private final GameBoard gameBoard = new GameBoard();
    private final Stage stage;

    public GameLoop(Stage stage) {
        this.stage = stage;

    }

    public void loop(){
        // Game loop logic here
    }

    public GridPane getGameBoard() {
        return gameBoard;
    }
}