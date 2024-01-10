package com.example.test.GameUtils;

import com.example.test.Pieces.Piece;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DragAndDropHandler {

    private  GameBoard gb;

    public DragAndDropHandler(GameBoard gb){
        this.gb =  gb;
    }
      Consumer<Tile> onDragDetected = t -> {
        EventHandler<MouseEvent> onDragDetected = e -> {
            Dragboard db = t.startDragAndDrop(TransferMode.ANY);
            if (t.getTilePiece() != null) {
                this.gb.setSelectedTile(t);
                this.gb.setSelectedPiece(t.getTilePiece());
                this.gb.drawPathOntoBoard(t.getTilePiece().getAllPath(t.getTileRowPos(),t.getTileColPos(),gb),true );

                ClipboardContent content = new ClipboardContent();
                content.putString(t.getText());
                content.putImage(t.getTilePiece().getPieceType().getImage());

                db.setDragView(t.getTilePiece().getPieceType().getImage());
                db.setDragViewOffsetX(37);
                db.setDragViewOffsetY(30);

                t.setGraphic(null);
                t.setText("");

                db.setContent(content);
            }
            e.consume();
        };
        t.setOnDragDetected(onDragDetected);
    };



    Consumer<Tile> onDragEntered = t->{

        EventHandler<DragEvent> onDragEntered = e->{
            Tile source = (Tile) e.getGestureSource();
            if(source != t ){
                if(t.hasPiece() & !source.samePieceColor(t) &&
                        source.getTilePiece().isValidPath(t.getTileRowPos(),t.getTileColPos(),gb)){
//                    System.out.println("Drag entered");
                    t.highlight(ColorUtil.RED);
                }
            }
            e.consume();
        };
        t.setOnDragEntered(onDragEntered);
    };

    Consumer<Tile> onDragExited = t->{

        EventHandler<DragEvent> onDragExited = e ->{
            if(t.getHighlighted().equals(ColorUtil.RED) ){
                t.highlight(ColorUtil.ORANGE);
            }
            e.consume();
        };
        t.setOnDragExited(onDragExited);
    };

     Consumer<Tile> onDragOver = t-> {


        EventHandler<DragEvent> onDragOver = e ->{
            Tile source = (Tile) e.getGestureSource();
            if(source != t &&
                    !source.samePieceColor(t)  &&
                    source.getTilePiece().isValidPath(t.getTileRowPos(),t.getTileColPos(),gb)){
                e.acceptTransferModes(TransferMode.MOVE);

            }

            e.consume();
        };
        t.setOnDragOver(onDragOver);
    };

    Consumer<Tile> onDragDropped = t->{

        EventHandler<DragEvent> onDragDropped = e->{
            boolean success = false;
            Tile source = (Tile) e.getGestureSource();

            if(source != t ){
                this.gb.drawPathOntoBoard(source.getTilePiece().getAllPath(source.getTileRowPos(),source.getTileColPos(),gb),false );
                if(!source.samePieceColor(t)  &&
                        source.getTilePiece().isValidPath(t.getTileRowPos(),t.getTileColPos(),gb)){
                    t.setPiece((source.getTilePiece()));
                    success = true;
                }

            }
            e.setDropCompleted(success);

            e.consume();
        };
        t.setOnDragDropped(onDragDropped);

    };

    Consumer<Tile> onDragDone = t ->{

        EventHandler<DragEvent> onDragDone = e->{
            if (e.getTransferMode() == TransferMode.MOVE) {
                System.out.println("YES");
                t.setPiece(null);
            }
            else {
                t.setPiece(t.getTilePiece());
            }

        };

        t.setOnDragDone(onDragDone);
    };

    public static BiFunction<Piece,GameBoard, List<Location>> diagonal = (p,gb) ->{
          List<Location> ret = new ArrayList<>();
          ret.addAll(oneDirection(-1,1,p,gb));
        ret.addAll(oneDirection(1,-1,p,gb));
        ret.addAll(oneDirection(1,1,p,gb));
        ret.addAll(oneDirection(-1,-1,p,gb));


          return ret;
    };

    public static BiFunction<Piece,GameBoard,List<Location>> straight= (p,gb) ->{
        List<Location> ret = new ArrayList<>();
        ret.addAll(oneDirection(0,1,p,gb));
        ret.addAll(oneDirection(0,-1,p,gb));
        ret.addAll(oneDirection(1,0,p,gb));
        ret.addAll(oneDirection(-1,0,p,gb));

        return ret;
    };

    private static List<Location> oneDirection(int rowInc,int colInc,Piece p
            , GameBoard gb){
        List<Location> ret = new ArrayList<>();
        int row = p.getPieceRow();
        int col = p.getPieceCol();
        ColorUtil color = p.getPieceType().getColor();
        Tile[][] tiles = gb.getTiles();

        int i = row + rowInc ;
        int j = col + colInc;
        while( i>=0 && j>= 0&& i < 8 && j < 8 && !tiles[i][j].hasPiece()  ){
            ret.add(new Location(i,j));
            i+= rowInc;
            j+= colInc;
        }
        if( i>=0 && j>= 0&& i < 8 && j < 8 && !tiles[i][j].samePieceColor(tiles[row][col])){
            ret.add(new Location(i,j));
        }
        return ret;
    }



}
