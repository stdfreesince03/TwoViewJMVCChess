package com.example.test.GameUtils;

import com.example.test.Pieces.*;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    public void runGame(){
       Task<Void> gameTask = new Task<Void>() {

           @Override
           protected Void call() throws Exception {
               while(true){
                   if (gameBoard.playerCheckMated()) {
                       updateMessage(gameBoard.getCurrentPlayer() == ColorUtil.WHITE ? "Black Wins" : "White Wins");
                       break;
                   }
                   if (gameBoard.tie()) {
                       updateMessage("It's a draw");
                       break;
                   }
                   try {
                       Thread.sleep(500); // Add a small delay to reduce CPU usage, if appropriate
                   } catch (InterruptedException e) {
                       if (isCancelled()) {
                           updateMessage("Game interrupted");
                           break;
                       }
                   }
               }
               return null;
           }
       };

        gameTask.messageProperty().addListener((obs, oldMessage, newMessage) -> {
            showGameEndDialog(newMessage);
        });

        Thread gameThread = new Thread(gameTask);
        gameThread.setDaemon(true);
        gameThread.start();




    }

    private void showGameEndDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,message, ButtonType.OK);
        alert.setTitle("Fucked Up Chess Game");
        alert.setHeaderText("Game Over");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                restartGame();
            }
        });
    }

    private void restartGame(){
        for(Tile[] ts : gameBoard.getTiles()){
            for(Tile t : ts){
                t.setPiece(null);
                t.setImage();;
            }
        }
        gameBoard.initializePieces();
        gameBoard.placePieces();
        gameBoard.enable();
    }

    public GridPane getGameBoard() {
        return gameBoard;
    }
}