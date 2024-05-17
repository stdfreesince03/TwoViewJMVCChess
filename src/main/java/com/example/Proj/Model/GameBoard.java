package com.example.Proj.Model;

import com.example.Proj.Pieces.*;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameBoard {
    private Tile[][] tiles = new Tile[8][8];
    private LinkedList<Move> moveLog = new LinkedList<>();
    private King whiteKing;
    private King blackKing;

    public GameBoard() {
        tileInit();
        pieceInit();
    }

    private void tileInit() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    public void addMove(Move movement) {

        moveLog.add(movement);

        LocAt.Location src = movement.getFrom();
        LocAt.Location dest = movement.getTo();
        Piece piece = movement.getPiece();
        tiles[dest.row()][dest.col()].setPiece(piece);
        tiles[src.row()][src.col()].setPiece(null);

    }

    private void pieceInit() {
        // Initialize black pieces
        blackKing = new King(ColorUtil.BLACK);
        tiles[0][0].setPiece(new Rook(ColorUtil.BLACK));
        tiles[0][1].setPiece(new Knight(ColorUtil.BLACK));
        tiles[0][2].setPiece(new Bishop(ColorUtil.BLACK));
        tiles[0][3].setPiece(new Queen(ColorUtil.BLACK));
        tiles[0][4].setPiece(blackKing);
        tiles[0][5].setPiece(new Bishop(ColorUtil.BLACK));
        tiles[0][6].setPiece(new Knight(ColorUtil.BLACK));
        tiles[0][7].setPiece(new Rook(ColorUtil.BLACK));
        for (int i = 0; i < 8; i++) {
            tiles[1][i].setPiece(new Pawn(ColorUtil.BLACK));
        }

        // Initialize white pieces
        whiteKing = new King(ColorUtil.WHITE);
        tiles[7][0].setPiece(new Rook(ColorUtil.WHITE));
        tiles[7][1].setPiece(new Knight(ColorUtil.WHITE));
        tiles[7][2].setPiece(new Bishop(ColorUtil.WHITE));
        tiles[7][3].setPiece(new Queen(ColorUtil.WHITE));
        tiles[7][4].setPiece(whiteKing);
        tiles[7][5].setPiece(new Bishop(ColorUtil.WHITE));
        tiles[7][6].setPiece(new Knight(ColorUtil.WHITE));
        tiles[7][7].setPiece(new Rook(ColorUtil.WHITE));
        for (int i = 0; i < 8; i++) {
            tiles[6][i].setPiece(new Pawn(ColorUtil.WHITE));
        }
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public King getWhiteKing() {
        return whiteKing;
    }

    public King getBlackKing() {
        return blackKing;
    }

    public LinkedList<Move> getMoveLog() {
        return moveLog;
    }

    public LocAt.Location getPieceLocation(Piece p ) {
        for(int i = 0;i<8;i++){
            for(int j = 0;j<8;j++){
               Tile tile = tiles[i][j];
               if(tile.hasPiece() && tile.getPiece() == p){
                   return tile.getLocation();
               }
            }
        }
        return null;
    }

}
