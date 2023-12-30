package com.example.test.GUI;

import com.example.test.GameUtils.Tile;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

public class GameGUI {
    public static void guiUpdate(TilePane pane, Tile[][] tiles) {
        for (int i = 0;i<tiles.length;i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                pane.getChildren().add(tiles[i][j]);
            }
        }
    }
}
