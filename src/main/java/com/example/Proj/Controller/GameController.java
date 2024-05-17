package com.example.Proj.Controller;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Model.Move;
import com.example.Proj.Model.Tile;
import com.example.Proj.Pieces.King;
import com.example.Proj.Pieces.Pawn;
import com.example.Proj.Pieces.Piece;
import com.example.Proj.Pieces.Rook;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import com.example.Proj.View.GameView;
import com.example.Proj.View.TileView;
import javafx.scene.input.MouseEvent;
import jdk.swing.interop.SwingInterOpUtils;

public class GameController {
    private GameBoard gameBoard;
    private GameView gameView;
    private TileView selectedTileView;
    private static GameController instance;
    private ColorUtil winner;
    private boolean stalemate = false;

    private GameController(GameBoard gameBoard, GameView gameView) {
        this.gameBoard = gameBoard;
        this.gameView = gameView;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                TileView tileView = gameView.getTileView(i, j);
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

        if (piece instanceof Pawn  ) {
            if(!((Pawn) piece).hasTwoStepped()) ((Pawn) piece).twoStep();
            if(src.col() == dest.col() && Math.abs(src.row() - dest.row()) == 2){
                ((Pawn)piece).setEntPassantProne(true);
            }else if(((Pawn) piece).isEntPassantProne()){
                ((Pawn)piece).setEntPassantProne(false);
            }
        }
        if (piece instanceof King) {
            //for castling
            if (!((King) piece).hasMoved()) {
                if(dest.col() - src.col() == 2){
                    Rook right = (Rook) gameBoard.getTile(src.row(),7).getPiece();
                    Move rookMove = new Move(LocAt.at(src.row(),7), LocAt.at(src.row(),dest.col()-1), right);
                    this.gameBoard.addMove(rookMove);
                    this.gameView.update(rookMove,gameBoard);
                }else if(dest.col() -src.col() == -2){
                    Rook left= (Rook) gameBoard.getTile(src.row(),0).getPiece();
                    Move rookMove = new Move(LocAt.at(src.row(),0), LocAt.at(src.row(),dest.col()+1), left);
                    this.gameBoard.addMove(rookMove);
                    this.gameView.update(rookMove,gameBoard);
                }
                ((King) piece).setHasMoved();
            }
            //for castling
        }
        if (piece instanceof Rook) {
            ((Rook) piece).setHasMoved();
        }

        //for ent passant
        if(piece instanceof Pawn && Math.abs(dest.col() - src.col()) == 1 &&
                !gameBoard.getTile(dest.row(), dest.col()).hasPiece() ) {
                int rowInc = piece.getColor() == ColorUtil.WHITE ? -1 : 1;
                Move move1 = new Move(src, dest, piece);
                this.gameBoard.addMove(move1);
                this.gameView.update(move1, gameBoard);
                this.gameBoard.getTile(dest.row() - rowInc, dest.col()).setPiece(null);
                this.gameView.getTileView(dest.row() - rowInc, dest.col()).setImage(null);
                this.gameView.allOff();
        //for ent passant
        }else{
            this.gameBoard.addMove(movement);
            this.gameView.update(movement,gameBoard);
            this.gameView.allOff();
        }


        DragDropClickHandler.enableColor(movement.getPiece().getColor() == ColorUtil.BLACK ?
                ColorUtil.WHITE : ColorUtil.BLACK);

        if((this.winner = GameRules.checkMate(movement)) != null ){
            return;
        }
        if(GameRules.stalemate(movement)){
            this.stalemate = true;
        }

    }

    public static GameController getInstance() {
        return instance;
    }

    public TileView getSelectedTileView() {
        return selectedTileView;
    }


    public void setSelectedTileView(TileView selectedTileView) {
        this.gameView.allOff();
        this.selectedTileView = selectedTileView;
        this.gameView.path(this.selectedTileView,gameBoard);

    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public GameView getGameView() {
        return this.gameView;
    }

    public boolean isStalemate() {
        return stalemate;
    }

    public ColorUtil getWinner() {
        return winner;
    }
}
