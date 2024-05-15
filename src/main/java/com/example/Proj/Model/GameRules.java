package com.example.Proj.Model;

import com.example.Proj.Pieces.*;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import com.example.Proj.Util.LocAt.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GameRules {
    private static GameBoard gameBoard;

    public GameRules(GameBoard gb) {
        gameBoard = gb;
    }

    public static boolean validMove(Tile src,Tile tgt){
       return  src.inPiecePath(tgt,gameBoard)  && !kingEndangered(src.getLocation(),tgt.getLocation(),gameBoard);
    }

    public static boolean kingEndangered(Location curr, Location next, GameBoard gameBoard) {
        Piece srcPiece = gameBoard.getTile(curr.row(), curr.col()).getPiece();
        Piece destPiece = gameBoard.getTile(next.row(), next.col()).getPiece();


        if (srcPiece == null) {
            System.out.println("YOU DUMB BITCH");
            return true;
        }

        // Perform the move
        gameBoard.getTile(curr.row(), curr.col()).setPiece(null);
        gameBoard.getTile(next.row(), next.col()).setPiece(srcPiece);

        boolean isSafe = !isKingInCheck(srcPiece.getColor());

        // Revert the move
        gameBoard.getTile(curr.row(), curr.col()).setPiece(srcPiece);
        gameBoard.getTile(next.row(), next.col()).setPiece(destPiece);


        return !isSafe;
    }

    public static boolean isKingInCheck(ColorUtil color) {
        // Get the king of the specified color
        King king = (color==ColorUtil.BLACK) ? gameBoard.getBlackKing() : gameBoard.getWhiteKing();
        int row = gameBoard.getKingLocation(color).row();
        int col = gameBoard.getKingLocation(color).col();

        // Check straight threats
        if (checkStraightThreats(king, row, col))return true;

        // Check diagonal threats
        if (checkDiagonalThreats(king, row, col)) return true;

        // Check knight threats
        if (checkKnightThreats(row, col,color)) return true;

        // Check pawn attacks
        if (checkPawnAttacks( row, col,color))return true;

        // Check king threats
        if (checkKingThreats(king,row, col,color )) return true;

        return false;
    }

    private static boolean checkStraightThreats(Piece king, int row, int col) {
        List<Location> straightThreats = MovementHelper.straight(row, col, king,gameBoard);
        for (Location loc : straightThreats) {
            Piece piece = gameBoard.getTile(loc.row(), loc.col()).getPiece();
            if (piece != null && piece.getColor() != king.getColor() &&
                    (piece instanceof Queen || piece instanceof Rook)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonalThreats(Piece king, int row, int col) {
        List<Location> diagonalThreats = MovementHelper.diagonal(row, col, king, gameBoard);
        for (Location loc : diagonalThreats) {
            Piece piece = gameBoard.getTile(loc.row(), loc.col()).getPiece();
            if (piece != null && piece.getColor() != king.getColor() &&
                    (piece instanceof Queen || piece instanceof  Bishop )) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkKnightThreats(int row, int col,ColorUtil color) {
        List<Location> knightMoves = MovementHelper.knightSpecial(row, col);
        for (Location loc : knightMoves) {
            Piece piece = gameBoard.getTile(loc.row(),loc.col()).getPiece();
            if (piece != null && color != piece.getColor() &&
                    piece instanceof  Knight) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkPawnAttacks( int row, int col,ColorUtil color) {
        List<Location> pawnAttacks = MovementHelper.pawnAttacks(row, col,color);
        for (Location loc : pawnAttacks) {
            Piece piece = gameBoard.getTile(loc.row(),loc.col()).getPiece();
            if (piece != null && piece.getColor() != color && piece instanceof Pawn) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkKingThreats(King king , int row, int col,ColorUtil color) {
        List<Location> kingMoves = MovementHelper.kingSpecial(king ,row,col,gameBoard);
        for (Location loc : kingMoves) {
            Piece piece = gameBoard.getTile(loc.row(),loc.col()).getPiece();
            if (piece != null && color != piece.getColor()
                    && piece instanceof  King) {
                return true;
            }
        }
        return false;
    }


  public static boolean validEnemy(Tile  src, Tile tgt){
        if(src == tgt){
            return false;
        }
       return tgt.hasPiece() &&
               tgt.getPiece().getColor() != src.getPiece().getColor() &&
               src.inPiecePath(tgt,gameBoard);
   }

   public static boolean validEmpty(Tile src, Tile tgt){
        if(src == tgt){
            return false;
        }
        return !tgt.hasPiece() && src.inPiecePath(tgt,gameBoard);
   }
}
