package com.example.test.GameUtils;

import com.example.test.Pieces.King;
import com.example.test.Pieces.Pawn;
import com.example.test.Pieces.Piece;
import com.example.test.Pieces.Rook;
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
            System.out.println("Drag detected");

            if (t.getTilePiece() != null) {

                if(this.gb.getSelectedPiece() == null){
                    this.gb.setSelectedPiece(t.getTilePiece());
                }

                this.gb.allOff();
                this.gb.setSelectedTile(t);
                this.gb.setSelectedPiece(t.getTilePiece());
                this.gb.drawPathOntoBoard(t.getTilePiece().getAllPath(t.getTileRowPos(),t.getTileColPos(),gb) );

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
                this.gb.drawPathOntoBoard(source.getTilePiece().getAllPath(source.getTileRowPos(),source.getTileColPos(),gb) );
                if(!source.samePieceColor(t)  &&
                        source.getTilePiece().isValidPath(t.getTileRowPos(),t.getTileColPos(),gb)) {

                    t.setPiece((source.getTilePiece()));
                    source.setPiece(null);

                    King x = t.getTilePiece().getPieceType().getColor() ==
                            ColorUtil.BLACK ? (King)this.gb.getBlackPieces().get("K") :(King) this.gb.getWhitePieces().get("K");
                    int colMovement = t.getTileColPos() - source.getTileColPos();

                    if(x.isChecked(x.getPieceRow(),x.getPieceCol(),gb)){
                        source.setPiece(t.getTilePiece());
                        t.setPiece(null);
                        source.setImage();
                        System.out.println("King is checked");
                    }
                    else if(x == t.getTilePiece() && !x.hasMoved() && (colMovement == 2 || colMovement == -2 )){
                        Rook r = null;
//
                        if( (r = x.castle(gb,colMovement,false)) != null ) {
                            int rookRow, rookCol = -1;
                            Tile rookTarget = null, rookSource = null;

                            if (colMovement == 2) {
                                rookTarget = gb.getTiles()[x.getPieceRow()][x.getPieceCol() - 1];
                            } else {
                                rookTarget = gb.getTiles()[x.getPieceRow()][x.getPieceCol() + 1];
                            }

                            rookRow = r.getPieceRow();
                            rookCol = r.getPieceCol();
                            rookSource = gb.getTiles()[rookRow][rookCol];


                            assert rookTarget != null;
                            rookTarget.setPiece(r);
                            rookSource.setPiece(null);
                            rookTarget.setImage();
                            rookSource.setImage();

                            t.setImage();
                            success = true;

                        } else{
                            source.setPiece(t.getTilePiece());
                            t.setPiece(null);
                            source.setImage();
                        }
                    }
                    else{
                        t.setImage();
                        success = true;

                    }

                    if(t.getTilePiece() instanceof  King && !((King) t.getTilePiece()).hasMoved() ){
                        ((King)(t.getTilePiece())).moved();
                    }
                    else if(t.getTilePiece() instanceof Pawn){
                        ((Pawn) t.getTilePiece()).setTwoStep(true);
                    } else if(t.getTilePiece() instanceof Rook && !((Rook) t.getTilePiece()).hasMoved() ){
                        ((Rook)(t.getTilePiece())).moved();
                    }

                    gb.allOff();

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
//                System.out.println("YES");
                t.setPiece(null);
            }
            else {
                t.setPiece(t.getTilePiece());
            }
            t.setImage();

        };

        t.setOnDragDone(onDragDone);
    };




}
