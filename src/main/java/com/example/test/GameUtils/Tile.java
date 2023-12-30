package com.example.test.GameUtils;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import  com.example.test.Pieces.*;

import java.io.File;

public class Tile extends Button {
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

    public Tile(ColorUtil color, int rowPos, int colPos,Piece piece){
        this(color,rowPos,colPos);
        setPiece(piece);
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

    public void setPiece(Piece piece){
        ImageView img = new ImageView(piece.getPieceType().getImage());
        img.setFitWidth(60);
        img.setFitHeight(60);

        this.piece = piece;

        this.setGraphic(img);
    }

}
