package com.example.Proj;


import com.example.Proj.Controller.GameController;
import com.example.Proj.Controller.GameLoop;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Model.GameBoard;
import com.example.Proj.View.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessGame extends Application {

    public void start(Stage stage) throws IOException {
        GameLoop gameLoop = new GameLoop();

        Scene scene = new Scene(gameLoop.getGameView(), 1200, 640);
        stage.setResizable(true);
        stage.setTitle("Chess Game");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        gameLoop.runGame();

    }

    public static void main(String[] args) {
        launch();
    }
}