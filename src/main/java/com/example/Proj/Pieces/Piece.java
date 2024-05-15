package com.example.Proj.Pieces;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;

public abstract class Piece {
    protected Image image;
    protected ColorUtil color;

    public Piece( ColorUtil color) {
        String colorName = color.name().substring(0,1).toUpperCase() + color.name().substring(1).toLowerCase();
        String imagePath = "src/main/java/com/example/Proj/ImageRes/"
                + this.getClass().getSimpleName() + "-" + colorName + ".png";
        this.image = new Image(new File(imagePath).toURI().toString());
        this.color = color;
    }

    public Image getImage() {
        return image;
    }

    public ColorUtil getColor() {
        return color;
    }

    public boolean possibleMovesContains(int rowOrg, int colOrg,int rowDest, int colDest,GameBoard gameBoard) {
        return getPossibleMoves(rowOrg,colOrg,gameBoard).contains(LocAt.at(rowDest,colDest));
    }

    public abstract List<LocAt.Location> getPossibleMoves(int row, int col, GameBoard gb);
}
