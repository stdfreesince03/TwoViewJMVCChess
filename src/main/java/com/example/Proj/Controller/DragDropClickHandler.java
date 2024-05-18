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

import java.awt.*;

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
        enableColor(ColorUtil.WHITE);

    }


//
    public static void enableColor(ColorUtil color){
        GameController gc = GameController.getInstance();
        for (int i = 0; i < GameView.ROW_SIZE; i++) {
            for (int j = 0; j < GameView.COL_SIZE; j++) {
                TileView whitetv = gc.getGameView().getWhiteView().getTileView(i,j);
                TileView blacktv = gc.getGameView().getBlackView().getTileView(i,j);

                Tile twhite = gameBoard.getTile(whitetv.getLoc().row(), whitetv.getLoc().col());
                if(twhite.hasPiece() && twhite.getPiece().getColor() == color){
                    enable(whitetv);
                }else{
                    disable(whitetv);
                }

                Tile tblack = gameBoard.getTile(blacktv.getLoc().row(), blacktv.getLoc().col());
                if(tblack.hasPiece() && tblack.getPiece().getColor() == color){
                    enable(blacktv);
                }else{
                    disable(blacktv);
                }
            }
        }
    }

    private static void mouseClicked(MouseEvent event, TileView t1){
        if(t1 != GameController.getInstance().getSelectedTileView()){
            GameController gc = GameController.getInstance();
            gc.setSelectedTileView(t1);
            Tile t = gameBoard.getTile(t1.getLoc().row(),t1.getLoc().col());
            if(t.hasPiece()){
                gc.getGameView().allOff();
                gc.getGameView().path(gc.getSelectedTileView(),gameBoard);
            }else{
                gc.getGameView().allOff();
            }
        }
    }

    private static void enable(TileView tileView){
        tileView.setOnDragDropped(OnDragDropped(tileView));
        tileView.setOnMouseClicked(event -> mouseClicked(event, tileView));
        tileView.setOnDragDetected(OnDragDetected(tileView));
        tileView.setOnDragEntered(OnDragEntered(tileView));
        tileView.setOnDragExited(OnDragExited(tileView));
        tileView.setOnDragOver(OnDragOver(tileView));
        tileView.setOnDragDone(OnDragDone(tileView));

    }
    private static void disable(TileView tileView){
        tileView.setOnMouseClicked(null);
        tileView.setOnDragDetected(null);
        tileView.setOnDragDone(null);
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
                    System.out.println(movement.getPiece().getClass().getSimpleName() + movement.getPiece().getColor());
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
