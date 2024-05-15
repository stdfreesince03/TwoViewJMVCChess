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
                    GameController.getInstance().controllerUpdate(movement);
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


//
//    Consumer<Tile> onDragDropped = t->{
//
//        EventHandler<DragEvent> onDragDropped = e->{
//            boolean success = false;
//            Tile source = (Tile) e.getGestureSource();
//
//            if(source != t ){
//                this.gb.drawPathOntoBoard(source.getTilePiece().getAllPath(source.getTileRowPos(),source.getTileColPos(),gb) );
//                if(!source.samePieceColor(t)  &&
//                        source.getTilePiece().isValidPath(t.getTileRowPos(),t.getTileColPos(),gb)) {
//
//                    t.setPiece((source.getTilePiece()));
//                    source.setPiece(null);
//
//                    King x = t.getTilePiece().getPieceType().getColor() ==
//                            ColorUtil.BLACK ? (King)this.gb.getBlackPieces().get("K") :(King) this.gb.getWhitePieces().get("K");
//                    int colMovement = t.getTileColPos() - source.getTileColPos();
//
//                    if(x.isChecked(x.getPieceRow(),x.getPieceCol(),gb)){
//                        source.setPiece(t.getTilePiece());
//                        t.setPiece(null);
//                        source.setImage();
//                        System.out.println("King is checked");
//                    }
//                    else if(x == t.getTilePiece() && !x.hasMoved() && (colMovement == 2 || colMovement == -2 )){
//                        Rook r = null;
////
//                        if( (r = x.castle(gb,colMovement,false)) != null ) {
//                            int rookRow, rookCol = -1;
//                            Tile rookTarget = null, rookSource = null;
//
//                            if (colMovement == 2) {
//                                rookTarget = gb.getTiles()[x.getPieceRow()][x.getPieceCol() - 1];
//                            } else {
//                                rookTarget = gb.getTiles()[x.getPieceRow()][x.getPieceCol() + 1];
//                            }
//
//                            rookRow = r.getPieceRow();
//                            rookCol = r.getPieceCol();
//                            rookSource = gb.getTiles()[rookRow][rookCol];
//
//
//                            assert rookTarget != null;
//                            rookTarget.setPiece(r);
//                            rookSource.setPiece(null);
//                            rookTarget.setImage();
//                            rookSource.setImage();
//
//                            t.setImage();
//                            success = true;
//
//                        } else{
//                            source.setPiece(t.getTilePiece());
//                            t.setPiece(null);
//                            source.setImage();
//                        }
//                    }
//                    else{
//                        t.setImage();
//                        success = true;
//
//                    }
//
//                    if(t.getTilePiece() instanceof  King && !((King) t.getTilePiece()).hasMoved() ){
//                        ((King)(t.getTilePiece())).moved();
//                    }
//                    else if(t.getTilePiece() instanceof Pawn){
//                        ((Pawn) t.getTilePiece()).setTwoStep(true);
//                    } else if(t.getTilePiece() instanceof Rook && !((Rook) t.getTilePiece()).hasMoved() ){
//                        ((Rook)(t.getTilePiece())).moved();
//                    }
//
//                    gb.allOff();
//
//                }
//
//            }
//            if(success) gb.playerTurn();
//            e.setDropCompleted(success);
//
//            e.consume();
//        };
//        t.setOnDragDropped(onDragDropped);
//
//    };
//
//    Consumer<Tile> onDragDone = t ->{
//
//        EventHandler<DragEvent> onDragDone = e->{
//            if (e.getTransferMode() == TransferMode.MOVE) {
////                System.out.println("YES");
//                t.setPiece(null);
//            }
//            else {
//                t.setPiece(t.getTilePiece());
//            }
//            t.setImage();
//
//        };
//
//        t.setOnDragDone(onDragDone);
//    };


}
