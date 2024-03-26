package com.example.test.GameUtils;

import com.example.test.Pieces.Pawn;
import com.example.test.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class MoveHandler {
    private static boolean validTile(int i , int j){
        return i>=0 && j>= 0 && i < 8 && j < 8;
    }
    public static void kingSpecial (List<Location> ret,int rowInc,int colInc,Piece p, GameBoard gb){
        int zero = 0;
        int negOne = -1;
        int one = 1;

        Tile[][] tiles = gb.getTiles();
        int row = p.getPieceRow();
        int col = p.getPieceCol();

        int i = row + rowInc ;
        int j = col + colInc;
        if(validTile(i,j)  && (!tiles[i][j].hasPiece() || !tiles[i][j].samePieceColor(tiles[row][col]) )){
            ret.add(new Location(i,j));
        }
    }

    public static void pawnSpecial (List<Location> ret, Pawn p, GameBoard gb){
        Tile[][] tiles = gb.getTiles();
        int step = p.getPieceType().getColor() == ColorUtil.BLACK ? 1 : -1;
        int row = p.getPieceRow();
        int col = p.getPieceCol();

        if(!(row + step >= 8 || row+step < 0) && !tiles[row + step][col].hasPiece() ){
            ret.add(new Location(row+step,col));
        }
        if(!(row + (2*step) >= 8 || row + (2*step) < 0) && !(tiles[row + (2*step)][col].hasPiece() || p.hasTwoStepped()) ){
            ret.add(new Location(row+(2*step),col));
        }
        if(!(row + step >= 8 || row+step < 0) && col + 1 < 8 && tiles[row + step][col + 1].hasPiece()){
            if(tiles[row+step][col+1].getTilePiece().getPieceType().getColor() != p.getPieceType().getColor()){
                ret.add(new Location(row+step,col+1));
            }
        }
        if(!(row + step >= 8 || row+step < 0) && col-1 >= 0 && tiles[row + step][col - 1].hasPiece()){
            if(tiles[row+step][col-1].getTilePiece().getPieceType().getColor() != p.getPieceType().getColor()){
                ret.add(new Location(row+step,col-1));
            }
        }
    }

    public static List<Location> diagonal(int row, int col ,Piece p,GameBoard gb){
        List<Location> ret = new ArrayList<>();
        oneDirectionMultiple(ret,row,col,-1,1,p,gb);
        oneDirectionMultiple(ret,row,col,1,-1,p,gb);
        oneDirectionMultiple(ret,row,col,1,1,p,gb);
        oneDirectionMultiple(ret,row,col,-1,-1,p,gb);
        return ret;
    }

    public static List<Location> straight(int row, int col ,Piece p,GameBoard gb){
        List<Location> ret = new ArrayList<>();
        oneDirectionMultiple(ret,row,col,0,1,p,gb);
        oneDirectionMultiple(ret,row,col,0,-1,p,gb);
        oneDirectionMultiple(ret,row,col,1,0,p,gb);
        oneDirectionMultiple(ret,row,col,-1,0,p,gb);
        return ret;
    }

    private static void oneDirectionMultiple(List<Location> ret,
                                             int row, int col, int rowInc,int colInc,
                                             Piece p,GameBoard gb){
        Tile[][] tiles = gb.getTiles();

        int i = row + rowInc;
        int j = col + colInc;

        while(validTile(i,j)  && (!tiles[i][j].hasPiece() || tiles[i][j].getTilePiece() == p)){
            ret.add(new Location(i,j));
            i+= rowInc;
            j+= colInc;
        }
        if(validTile(i,j)){
            if( !tiles[i][j].samePieceColor(tiles[p.getPieceRow()][p.getPieceCol()])){
                    ret.add(new Location(i,j));
            }

        }

    }


}
