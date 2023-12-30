package com.example.test.Pieces;

import com.example.test.GameUtils.ColorUtil;
import com.example.test.GameUtils.Location;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.*;

import java.util.List;

public abstract class Piece extends Node {

    private Location loc;



    private String key;
    private final ColorUtil pieceColor;

    protected PieceType pieceType;


    public Piece(int rowPos, int colPos,
            ColorUtil color, int keyNum){
        this.loc = new Location(rowPos,colPos);
        this.pieceColor = color;
    }


    protected Image getImage(){
        return this.pieceType.getImage();
    }
    public PieceType getPieceType() {
        return pieceType;
    }

    protected int getPieceRowPos() {
        return this.loc.getRow();
    }

    protected int getPieceColPos() {
        return this.loc.getCol();
    }

    protected Location getLoc() {
        return loc;
    }

    protected String getPieceKey() {
        return this.key;
    }

    public void setPieceKey(String key) {
        this.key = key;
    }

    protected ColorUtil getPieceColor() {
        return this.pieceColor;
    }

    protected abstract boolean isValidPath(int row , int col);

    public abstract List<Location> getAllPath(int row , int col );


    protected boolean isValidCapture(Piece capturedPiece){
        return (isValidPath(capturedPiece.loc.getRow(),capturedPiece.loc.getCol()) && capturedPiece.pieceColor != this.pieceColor);
    }

}
