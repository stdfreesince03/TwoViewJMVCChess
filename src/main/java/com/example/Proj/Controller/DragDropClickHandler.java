package com.example.Proj.Controller;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Model.Move;
import com.example.Proj.Model.Tile;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.View.GameView;
import com.example.Proj.View.TileView;
import javafx.event.EventHandler;
import javafx.scene.input.*;

public class DragDropClickHandler {
    private static GameBoard gameBoard ;

    public static void initialize(GameBoard board, GameView gameView){
        gameBoard = board;
        for (int i = 0; i < GameView.ROW_SIZE; i++) {
            for (int j = 0; j < GameView.COL_SIZE; j++) {
                TileView tileView = gameView.getTileView(i, j);
                enable(tileView);
            }
        }

    }

    public static void enable(TileView tileView){
        tileView.setOnDragDetected(OnDragDetected(tileView));
        tileView.setOnDragEntered(OnDragEntered(tileView));
        tileView.setOnDragExited(OnDragExited(tileView));
        tileView.setOnDragOver(OnDragOver(tileView));
        tileView.setOnDragDropped(OnDragDropped(tileView));
        tileView.setOnDragDone(OnDragDone(tileView));

    }
    public static void disable(TileView tileView){

    }


    private static EventHandler<MouseEvent> OnDragDetected(TileView tv){
        return mouseEvent -> {
            Tile src = gameBoard.getTile(tv.getLoc().row(),tv.getLoc().col());
            Dragboard db = tv.startDragAndDrop(TransferMode.ANY);

            if (src.getPiece() != null) {
                GameController.getInstance().setSelectedTileView(tv);
                ClipboardContent content = new ClipboardContent();
                content.putImage(src.getPiece().getImage());

                db.setDragView(src.getPiece().getImage());
                db.setDragViewOffsetX(37);
                db.setDragViewOffsetY(30);

                tv.setGraphic(null);
                tv.setText("");

                db.setContent(content);

            }
            mouseEvent.consume();
        };
    }


    private static EventHandler<DragEvent> OnDragEntered(TileView tv){
        return dragEvent -> {
            TileView srcV  = (TileView) dragEvent.getGestureSource();
            Tile src = gameBoard.getTile(srcV.getLoc().row(),srcV.getLoc().col());
            Tile target = gameBoard.getTile(tv.getLoc().row(),tv.getLoc().col());

            if(GameRules.validEnemy( src,target)){
                tv.highlight(ColorUtil.RED);
            }
            dragEvent.consume();
        };
    }


    private static EventHandler<DragEvent> OnDragExited(TileView tv){
        return dragEvent -> {
            if(tv.getHighlighted().equals(ColorUtil.RED)){
                tv.highlight(ColorUtil.ORANGE);
            }
            dragEvent.consume();
        };
    }

    private static EventHandler<DragEvent> OnDragOver(TileView tv) {
        return dragEvent -> {
            TileView srcV = (TileView) dragEvent.getGestureSource();
            Tile src = gameBoard.getTile(srcV.getLoc().row(),srcV.getLoc().col());
            Tile target = gameBoard.getTile(tv.getLoc().row(),tv.getLoc().col());

            if(GameRules.validEnemy(src,target) || GameRules.validEmpty(src,target)){
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }

            dragEvent.consume();
        };
    }

    private static EventHandler<DragEvent> OnDragDropped(TileView tv){
        return dragEvent -> {
            TileView src = (TileView) dragEvent.getGestureSource();
            Tile srcTile = gameBoard.getTile(src.getLoc().row(),src.getLoc().col());
            Tile destTile = gameBoard.getTile(tv.getLoc().row(), tv.getLoc().col());
            Move movement = new Move(src.getLoc(),tv.getLoc(), srcTile.getPiece());

            boolean success = false;
            if(src != tv){
                if( GameRules.validMove(srcTile,destTile)){
                    GameController.getInstance().handleMovement(movement);
                    success = true;
                }
            }
            dragEvent.setDropCompleted(success);
            dragEvent.consume();
        };

    }

    private static EventHandler<DragEvent> OnDragDone(TileView tv){
        return dragEvent -> {
            if (!(dragEvent.getTransferMode() == TransferMode.MOVE)) {
                TileView src = (TileView) dragEvent.getGestureSource();
                Tile srcTile = gameBoard.getTile(src.getLoc().row(), src.getLoc().col());
                src.setImage(srcTile.getPiece().getImage());
            }
        };

    }


}
