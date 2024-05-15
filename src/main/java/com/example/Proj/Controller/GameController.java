package com.example.Proj.Controller;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.Move;
import com.example.Proj.Model.Tile;
import com.example.Proj.Pieces.King;
import com.example.Proj.Pieces.Pawn;
import com.example.Proj.Pieces.Piece;
import com.example.Proj.Pieces.Rook;
import com.example.Proj.Util.LocAt;
import com.example.Proj.View.GameView;
import com.example.Proj.View.TileView;
import javafx.scene.input.MouseEvent;

public class GameController {
    private GameBoard gameBoard;
    private GameView gameView;
    private TileView selectedTileView;
    private static GameController instance;

    private GameController(GameBoard gameBoard, GameView gameView) {
        this.gameBoard = gameBoard;
        this.gameView = gameView;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                TileView tileView = gameView.getTileView(i, j);
                tileView.setOnMouseClicked(event -> mouseClicked(event, tileView));
            }
        }
    }

    public static void initialize(GameBoard gameBoard, GameView gameView) {
        if (instance == null) {
            instance = new GameController(gameBoard, gameView);
            DragDropClickHandler.initialize(gameBoard, gameView);
        }
    }

    public void handleMovement(Move movement) {
        Piece piece = movement.getPiece();
        LocAt.Location src = movement.getFrom();
        LocAt.Location dest = movement.getTo();

        if (piece instanceof Pawn && !((Pawn) piece).hasTwoStepped()) {
            ((Pawn) piece).twoStep();
        }
        if (piece instanceof King) {
            if (!((King) piece).hasMoved()) {
                if(dest.col() - src.col() == 2){
                    Rook right = (Rook) gameBoard.getTile(src.row(),7).getPiece();
                    Move rookMove = new Move(LocAt.at(src.row(),7), LocAt.at(src.row(),dest.col()-1), right);
                    this.gameBoard.addMove(rookMove);
                    this.gameView.update(rookMove,gameBoard);
                    System.out.println(gameBoard.getPieceLocation(right));
                }else if(dest.col() -src.col() == -2){
                    Rook right = (Rook) gameBoard.getTile(src.row(),0).getPiece();
                    Move rookMove = new Move(LocAt.at(src.row(),0), LocAt.at(src.row(),dest.col()+1), right);
                    this.gameBoard.addMove(rookMove);
                    this.gameView.update(rookMove,gameBoard);
                }
                ((King) piece).setHasMoved();
            }
        }
        if (piece instanceof Rook) {
            ((Rook) piece).setHasMoved();
        }

        this.gameBoard.addMove(movement);
        this.gameView.update(movement,gameBoard);
        this.gameView.allOff();

    }

    public static GameController getInstance() {
        return instance;
    }

    public TileView getSelectedTileView() {
        return selectedTileView;
    }

    private void mouseClicked(MouseEvent event, TileView t1){
        if(t1 != this.selectedTileView){
            System.out.println(gameBoard.getTile(t1.getLoc().row(),t1.getLoc().col()).hasPiece());

            this.selectedTileView = t1;
            Tile t = gameBoard.getTile(t1.getLoc().row(),t1.getLoc().col());
            if(t.hasPiece()){
                gameView.allOff();
                gameView.path(this.selectedTileView,gameBoard);
            }else{
                gameView.allOff();
            }
        }
    }


    public void setSelectedTileView(TileView selectedTileView) {
        this.gameView.allOff();
        this.selectedTileView = selectedTileView;
        this.gameView.path(this.selectedTileView,gameBoard);


    }

    public GameView getGameView() {
        return this.gameView;
    }
}
