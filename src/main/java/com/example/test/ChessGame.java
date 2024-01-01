package com.example.test;

import com.example.test.GameUtils.GameLoop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        GameLoop gl = new GameLoop();
//        gl.loop();

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