package com.example.test;

import com.example.test.GUI.GameGUI;
import com.example.test.GameUtils.GameBoard;
import com.example.test.GameUtils.GameLoop;
import com.example.test.GameUtils.Tile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        GameLoop gl = new GameLoop();
        gl.loop();

        Scene scene = new Scene(gl.getGameBoard(), 640, 640);

        stage.setResizable(false);
        stage.setTitle("Chess Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}