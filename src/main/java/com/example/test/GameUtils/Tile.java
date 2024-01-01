package com.example.test.GameUtils;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import  com.example.test.Pieces.*;

public class Tile extends Label {
    private ColorUtil color;
    private int rowPos;
    private int colPos;

    private Piece piece;


    public Tile(ColorUtil color, int rowPos, int colPos){
        this.color = color;
        this.rowPos =rowPos;
        this.colPos = colPos;
        normalColor();


    }


    public boolean hasPiece(){
        return this.piece != null;
    }

    public ColorUtil getTileColor() {
        return color;
    }

    public int getTileRowPos() {
        return rowPos;
    }

    public int getTileColPos() {
        return colPos;
    }

    public Piece getTilePiece() {
        return piece;
    }

    public boolean samePieceColor(Tile t ){
        if(t.getTilePiece() == null) return false;
        return this.piece.getPieceType().getColor() ==
                t.getTilePiece().getPieceType().getColor();
    }

    public void normalColor(){
        if(color.equals(ColorUtil.WHITE)){
            this.setStyle("-fx-background-radius: 0;-fx-max-height: 80;" +
                    "-fx-max-width: 80;-fx-pref-height: 80;-fx-pref-width: 80;-fx-background-color: white");
        }else{
            this.setStyle("-fx-background-radius: 0;-fx-max-height: 80;" +
                    "-fx-max-width: 80;-fx-pref-height: 80;-fx-pref-width: 80;-fx-background-color: gray");
        }
    }
    public void highlight(boolean capture){
        if(!capture){
            this.setStyle("-fx-background-radius: 0;-fx-max-height: 80;" +
                    "-fx-max-width: 80;-fx-pref-height: 80;-fx-pref-width: 80;-fx-background-color: lightblue");
        }else{
            this.setStyle("-fx-background-radius: 0;-fx-max-height: 80;" +
                    "-fx-max-width: 80;-fx-pref-height: 80;-fx-pref-width: 80;-fx-background-color: orange");
        }

    }

    public void setPiece(Piece p){

        this.piece = p;


        if(this.piece != null){
            this.piece.setLoc(this.rowPos,this.colPos);
            ImageView img = new ImageView(p.getPieceType().getImage());
            img.setFitWidth(60);
            img.setFitHeight(60);



            this.setText(this.piece.getKey());
            this.setGraphic(img);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }


    }

}
