package com.example.Proj.Controller;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.View.GameView;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jdk.swing.interop.SwingInterOpUtils;

public class GameLoop {
    private GameController gc;
    public GameLoop() {
        GameBoard board = new GameBoard();
        GameView view = new GameView(board);
        GameRules rules = new GameRules(board);
        GameController.initialize(board,view);
        this.gc = GameController.getInstance();
    }

    public void runGame(){
        Task<Void> gameTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while(true){
                    if (gc.getWinner() != null) {
                        updateMessage(gc.getWinner() == ColorUtil.BLACK ? "Black Wins" : "White Wins");
                        break;
                    }
                    if (gc.isStalemate()) {
                        System.out.println("STALEMATE MAYNE");
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
        this.gc.getGameBoard().pieceInit();
        this.gc.getGameView().restartUpdate(this.gc.getGameBoard());
        DragDropClickHandler.initialize(this.gc.getGameBoard(), this.getGameView());
    }

    public GameView getGameView(){
        return this.gc.getGameView();
    }
}
