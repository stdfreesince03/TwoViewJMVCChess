package com.example.test.GameUtils;

import com.example.test.Pieces.Piece;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class DragAndDropHandler {

    static Consumer<Tile> onDragDetected = t -> {
        EventHandler<MouseEvent> onDragDetected = e -> {
            Dragboard db = t.startDragAndDrop(TransferMode.ANY);
            if (t.getTilePiece() != null) {
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



    static Consumer<Tile> onDragEntered = t->{
        System.out.println(1);
        EventHandler<DragEvent> onDragEntered = e->{
            Tile source = (Tile) e.getGestureSource();
            if(source != t ){
                if(t.hasPiece() & !source.samePieceColor(t) &&
                        source.getTilePiece().isValidPath(t.getTileRowPos(),t.getTileColPos())){
                    t.highlight(true);
                }
            }
            e.consume();
        };
        t.setOnDragEntered(onDragEntered);
    };

    static Consumer<Tile> onDragExited = t->{
        System.out.println(2);
        EventHandler<DragEvent> onDragExited = e ->{
            t.normalColor();
            e.consume();
        };
        t.setOnDragExited(onDragExited);
    };

    static Consumer<Tile> onDragOver = t-> {
        System.out.println(3);

        EventHandler<DragEvent> onDragOver = e ->{
            Tile source = (Tile) e.getGestureSource();
            if(source != t &&
                    !source.samePieceColor(t)  && source.getTilePiece().isValidPath(t.getTileRowPos(),t.getTileColPos())){
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        };
        t.setOnDragOver(onDragOver);
    };

    static Consumer<Tile> onDragDropped = t->{

        System.out.println(4);

        EventHandler<DragEvent> onDragDropped = e->{
            boolean success = false;
            Tile source = (Tile) e.getGestureSource();

            if(source != t ){
                if(!source.samePieceColor(t)  && source.getTilePiece().isValidPath(t.getTileRowPos(),t.getTileColPos())){
                    t.setPiece((source.getTilePiece()));
                    success = true;
                }
            }

            e.setDropCompleted(success);
            e.consume();
        };
        t.setOnDragDropped(onDragDropped);

    };

    static Consumer<Tile> onDragDone = t ->{
        System.out.println(5);
        EventHandler<DragEvent> onDragDone = e->{
            if (e.getTransferMode() == TransferMode.MOVE) {
                System.out.println("YES");
                t.setPiece(null);

            }
            else{
                t.setPiece(t.getTilePiece());
            }
        };
        t.setOnDragDone(onDragDone);
    };



}
