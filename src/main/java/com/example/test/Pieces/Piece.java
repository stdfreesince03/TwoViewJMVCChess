package com.example.test.Pieces;

import com.example.test.GameUtils.ColorUtil;
import com.example.test.GameUtils.GameBoard;
import com.example.test.GameUtils.Location;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.*;

import java.util.List;

public abstract class Piece extends Node {

    private Location loc;
    private PieceType pieceType;
    private String key;


    public Piece(int rowPos, int colPos){
        this.loc = new Location(rowPos,colPos);
    }

    public abstract boolean isValidPath(int row , int col, GameBoard gb);
    public abstract List<Location> getAllPath(int row , int col ,GameBoard gb);

    public PieceType getPieceType() {
        return pieceType;
    }
    protected Image getImage(){
        return this.pieceType.getImage();
    }
    protected int getPieceRow() {
        return this.loc.getRow();
    }
    protected int getPieceCol() {
        return this.loc.getCol();
    }


    public void setLoc(int row,int col) {
        this.loc.setRow(row);
        this.loc.setCol(col);
    }

    protected void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public String getKey() {
        return key;
    }

    protected void setKey(int keyNum) {
        this.key = this.pieceType.getKey(keyNum);
    }
}
