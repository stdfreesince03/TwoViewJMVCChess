package com.example.Proj;


import com.example.Proj.Controller.GameController;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Model.GameBoard;
import com.example.Proj.View.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessGame extends Application {

    public void start(Stage stage) throws IOException {
        GameBoard board = new GameBoard();
        GameView view = new GameView(board);
        GameRules rules = new GameRules(board);
        GameController.initialize(board,view);
        GameController controller = GameController.getInstance();


        Scene scene = new Scene(controller.getGameView(), 640, 640);
        stage.setResizable(true);
        stage.setTitle("Chess Game");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}