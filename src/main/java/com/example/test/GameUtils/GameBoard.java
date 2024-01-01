package com.example.test.GameUtils;

import com.example.test.Pieces.Piece;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import static com.example.test.GameUtils.DragAndDropHandler.*;

import java.util.List;

public class GameBoard extends TilePane {
    private final Tile[][] tiles;
    private Piece selectedPiece;

    private Tile selectedTile;

    private final DragAndDropHandler dragAndDropHandler ;

    public GameBoard(){

        this.setWidth(640);
        this.setHeight(640);
        this.dragAndDropHandler = new DragAndDropHandler(this);

        tiles = new Tile[8][8];
        this.selectedTile = new Tile(ColorUtil.BLACK,0,0); // random tile for temp variable

        ColorUtil row = ColorUtil.WHITE;
        ColorUtil col = row;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                tiles[i][j] = new Tile(col, i, j);
                this.getChildren().add(tiles[i][j]);
                col = (col == ColorUtil.WHITE) ? ColorUtil.BLACK : ColorUtil.WHITE;
            }
            row = (row == ColorUtil.WHITE) ? ColorUtil.BLACK : ColorUtil.WHITE;
            col = row;
        }

        enable();


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
        if(t1.hasPiece()){
            if(this.selectedTile != null && this.selectedPiece != null){
                drawPathOntoBoard(this.selectedPiece.getAllPath
                        (this.selectedTile.getTileRowPos(), this.selectedTile.getTileColPos(),this),false);
            }
            this.selectedTile = t1;
            this.selectedPiece = t1.getTilePiece();
            drawPathOntoBoard(this.selectedPiece.getAllPath
                    (this.selectedTile.getTileRowPos(), this.selectedTile.getTileColPos(),this),true);

        } else{
            if(this.selectedPiece != null){
                drawPathOntoBoard(this.selectedPiece.getAllPath
                        (this.selectedTile.getTileRowPos(), this.selectedTile.getTileColPos(),this),false);
                this.selectedTile = t1;
                this.selectedPiece = null;
            }

        }
    }



     void drawPathOntoBoard(List<Location> paths,boolean  x){
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
