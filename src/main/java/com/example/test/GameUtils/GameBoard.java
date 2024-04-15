package com.example.test.GameUtils;

import com.example.test.Pieces.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import static com.example.test.GameUtils.DragAndDropHandler.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard extends GridPane {
    private final Tile[][] tiles;
    private Piece selectedPiece;

    public final static int ROW_SIZE= 8;
    public final static int COL_SIZE= 8;

    private Tile selectedTile;

    private final DragAndDropHandler dragAndDropHandler ;

    private Map<String, Piece> whitePieces = new HashMap<>();
    private Map<String, Piece> blackPieces = new HashMap<>();

    public GameBoard(){

        this.setWidth(640);
        this.setHeight(640);


        this.dragAndDropHandler = new DragAndDropHandler(this);

        tiles = new Tile[ROW_SIZE][COL_SIZE];
        this.selectedTile = new Tile(ColorUtil.BLACK,0,0); // random tile for temp variable

        ColorUtil row = ColorUtil.WHITE;
        ColorUtil col = row;
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                tiles[i][j] = new Tile(col, i, j);
                this.add(tiles[i][j],j,i);
                col = (col == ColorUtil.WHITE) ? ColorUtil.BLACK : ColorUtil.WHITE;
            }
            row = (row == ColorUtil.WHITE) ? ColorUtil.BLACK : ColorUtil.WHITE;
            col = row;
        }

        enable();
        initializePieces();
        placePieces();


    }

    private void initializePieces() {;

        blackPieces.put("K", new King(0, 4, ColorUtil.BLACK, 1));
        blackPieces.put("Q", new Queen(0, 3, ColorUtil.BLACK, 1));
        blackPieces.put("B1", new Bishop(0, 2, ColorUtil.BLACK, 1));
        blackPieces.put("B2", new Bishop(0, 5, ColorUtil.BLACK, 2));
        blackPieces.put("Kn1", new Knight(0, 1, ColorUtil.BLACK, 1));
        blackPieces.put("Kn2", new Knight(0, 6, ColorUtil.BLACK, 2));
        blackPieces.put("R1", new Rook(0, 0, ColorUtil.BLACK, 1));
        blackPieces.put("R2", new Rook(0, 7, ColorUtil.BLACK, 2));
        for (int i = 0; i < 8; i++) {
            blackPieces.put("P" + (i + 1), new Pawn(1, i, ColorUtil.BLACK, 0));
        }

        // Initialize white pieces on rows 7 and 8
        whitePieces.put("K", new King(7, 4, ColorUtil.WHITE, 1));
        whitePieces.put("Q", new Queen(7, 3, ColorUtil.WHITE, 1));
        whitePieces.put("B1", new Bishop(7, 2, ColorUtil.WHITE, 1));
        whitePieces.put("B2", new Bishop(7, 5, ColorUtil.WHITE, 2));
        whitePieces.put("Kn1", new Knight(7, 1, ColorUtil.WHITE, 1));
        whitePieces.put("Kn2", new Knight(7, 6, ColorUtil.WHITE, 2));
        whitePieces.put("R1", new Rook(7, 0, ColorUtil.WHITE, 1));
        whitePieces.put("R2", new Rook(7, 7, ColorUtil.WHITE, 2));
        for (int i = 0; i < 8; i++) {
            whitePieces.put("P" + (i + 1), new Pawn(6, i, ColorUtil.WHITE, 0));
        }
    }

    private void placePieces() {
        // Place white pieces
        for (Piece piece : whitePieces.values()) {
            this.getTiles()[piece.getPieceRow()][piece.getPieceCol()].setPiece(piece);
            this.getTiles()[piece.getPieceRow()][piece.getPieceCol()].setImage();
        }
        // Place black pieces
        for (Piece piece : blackPieces.values()) {
            this.getTiles()[piece.getPieceRow()][piece.getPieceCol()].setPiece(piece);
            this.getTiles()[piece.getPieceRow()][piece.getPieceCol()].setImage();
        }
    }

    private void enable(){
        this.getChildren().forEach(c ->{
             c.setOnMouseClicked(e -> mouseClicked(e,(Tile) c));
             dragAndDropHandler.onDragDetected.andThen(dragAndDropHandler.onDragExited).andThen(dragAndDropHandler.onDragEntered)
                     .andThen(dragAndDropHandler.onDragOver).andThen(dragAndDropHandler.onDragDropped).
                     andThen(dragAndDropHandler.onDragDone).accept((Tile) c);
        });
    }

    private void mouseClicked(MouseEvent event,Tile t1){
        System.out.print("click detected,");
        if(t1.hasPiece()){

            allOff();

            this.selectedTile = t1;
            this.selectedPiece = t1.getTilePiece();

            drawPathOntoBoard(this.selectedPiece.getAllPath
                    (this.selectedTile.getTileRowPos(), this.selectedTile.getTileColPos(),this));

            System.out.println("click1");
        }
        else{
            if(this.selectedPiece != null){
                drawPathOntoBoard(this.selectedPiece.getAllPath
                        (this.selectedTile.getTileRowPos(), this.selectedTile.getTileColPos(),this));
                this.selectedTile = t1;
                this.selectedPiece = null;
            }
            System.out.println("click2");
        }
    }



     void drawPathOntoBoard(List<Location> paths){

           for(Location l : paths){
               int row = l.getRow();
               int col = l.getCol();
               if(row >= 8 || col >=8 || col < 0 || row < 0 ){
                   continue;
               }

               if(tiles[row][col].hasPiece()){
                   if( tiles[row][col].getTilePiece().getPieceType().getColor().
                           equals(this.selectedPiece.getPieceType().getColor())
                           ){
                       continue;
                   }
                   tiles[row][col].highlight(ColorUtil.ORANGE);
               }
               else{
                   tiles[row][col].highlight(ColorUtil.CIRCLE);
               }

           }


    }

    void allOff(){
        for(Tile[] x : tiles){
            for (Tile t : x){
                t.highlight(ColorUtil.NORMAL);
            }
        }
    }


    public  Tile[][] getTiles() {
        return tiles;
    }

    Piece getSelectedPiece(){
        return this.selectedPiece;
    }

     void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

     Map<String, Piece> getWhitePieces() {
        return Map.copyOf(whitePieces);
    }

     Map<String, Piece> getBlackPieces() {
        return Map.copyOf(blackPieces);
    }
}
