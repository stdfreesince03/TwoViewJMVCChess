package com.example.test.GameUtils;

import com.example.test.Pieces.Piece;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.util.List;

public class GameBoard extends TilePane {
    private final Tile[][] tiles;
    private Piece selectedPiece;

    private Tile selectedTile;

    public GameBoard(){




        this.setWidth(640);
        this.setHeight(640);

        tiles = new Tile[8][8];

        ColorUtil row = ColorUtil.WHITE;
        ColorUtil col = row;

        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                tiles[i][j] = new Tile(col, i, j);
                col = (col == ColorUtil.WHITE) ? ColorUtil.BLACK : ColorUtil.WHITE;
            }
            row = (row == ColorUtil.WHITE) ? ColorUtil.BLACK : ColorUtil.WHITE;
            col = row;
        }



        this.selectedTile = new Tile(ColorUtil.BLACK,0,0); // random tile for temp variable



        for(Tile[] t0 : this.tiles){
            for(Tile t1 : t0 ){
                t1.setOnMouseClicked((event) -> {
                     if(t1.hasPiece()){
                         if(this.selectedTile != null && this.selectedPiece != null){
                             drawPathOntoBoard(this.selectedPiece.getAllPath
                                     (this.selectedTile.getTileRowPos(), this.selectedTile.getTileColPos()),false);
                         }
                         this.selectedTile = t1;
                         this.selectedPiece = t1.getTilePiece();
                         drawPathOntoBoard(this.selectedPiece.getAllPath
                                 (this.selectedTile.getTileRowPos(), this.selectedTile.getTileColPos()),true);

                     } else{
                         drawPathOntoBoard(this.selectedPiece.getAllPath
                                 (this.selectedTile.getTileRowPos(), this.selectedTile.getTileColPos()),false);
                         this.selectedTile = t1;
                         this.selectedPiece = null;
                     }
                });
            }
        }


    }



    private void drawPathOntoBoard(List<Location> paths,boolean  x){
       if(x){
           this.selectedTile.highlight(false);
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
                   tiles[row][col].highlight(true);

               }else{
                   tiles[row][col].highlight(false);
               }

           }
       }else{
           this.selectedTile.normalColor();
           for(Location l : paths) {


               int row = l.getRow();
               int col = l.getCol();

               if(row >= 8 || col >=8 || col < 0 || row < 0 ){
                   continue;
               }

               if(tiles[row][col] != null){
                   tiles[row][col].normalColor();
               }
           }
       }

    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
