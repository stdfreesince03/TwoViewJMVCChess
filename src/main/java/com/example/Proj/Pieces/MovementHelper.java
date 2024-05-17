package com.example.Proj.Pieces;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import com.example.Proj.Util.LocAt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovementHelper {
    public static List<Location> kingSpecial (King p,int row, int col ,GameBoard gb){
        List<Location> ret = new ArrayList<>();
        oneDirectionSingle(ret,row ,col ,1,0,p,gb);
        oneDirectionSingle(ret,row ,col ,1,-1,p,gb);
        oneDirectionSingle(ret,row ,col ,1,1,p,gb);
        oneDirectionSingle(ret,row ,col ,-1,1,p,gb);
        oneDirectionSingle(ret,row ,col ,-1,0,p,gb);
        oneDirectionSingle(ret,row ,col ,-1,-1,p,gb);
        oneDirectionSingle(ret,row ,col ,0,-1,p,gb);
        oneDirectionSingle(ret,row ,col ,0,1,p,gb);

        return ret;

    }

    public static List<Location> pawnAttacks(int row, int col, ColorUtil color) {
        List<Location> attacks = new ArrayList<>();
        int direction = color == ColorUtil.WHITE ? -1 : 1;
        if (validTile(row + direction, col - 1)) {
            attacks.add(LocAt.at(row + direction, col - 1));
        }
        if (validTile(row + direction, col + 1)) {
            attacks.add(LocAt.at(row + direction, col + 1));
        }
        return attacks;
    }


    public static List<Location> pawnSpecial (Pawn p , int row,int col , GameBoard gb){
        int step = (p.getColor() == ColorUtil.BLACK) ? 1 : -1;
        List<Location > ret = new ArrayList<>();
        oneDirectionSingle(ret,row,col,step,0,p,gb);
        if(!p.hasTwoStepped()){
            oneDirectionSingle(ret,row + step,col,step,0,p,gb);
        }
        //for entpassant path
        if(validTile(row,col+1) && gb.getTile(row,col+1).hasPiece()){
            if(  gb.getTile(row,col+1).getPiece() instanceof Pawn){
                Pawn nextDoor = (Pawn) gb.getTile(row,col+1).getPiece();
                if(nextDoor.isEntPassantProne() && nextDoor == gb.getMoveLog().getLast().getPiece()){
                    ret.add(LocAt.at(row + (p.getColor() == ColorUtil.BLACK ? 1 : -1),col+1));

                }
            }

        }
        if(validTile(row,col-1) && gb.getTile(row,col-1).hasPiece()){
            if(  gb.getTile(row,col-1).getPiece() instanceof Pawn){
                Pawn nextDoor = (Pawn) gb.getTile(row,col-1).getPiece();
                if(nextDoor.isEntPassantProne() && nextDoor == gb.getMoveLog().getLast().getPiece()){
                    ret.add(LocAt.at(row + (p.getColor() == ColorUtil.BLACK ? 1 : -1),col-1));

                }
            }

        }
        //for entpassant path
        oneDirectionSingle(ret,row ,col ,step,-1,p,gb);
        oneDirectionSingle(ret,row ,col ,step,1,p,gb);
        return ret;
    }
    public static List<Location> diagonal(int row, int col , Piece p, GameBoard gb){
        List<Location> ret = new ArrayList<>();
        oneDirectionMultiple(ret,row,col,-1,1,p,gb);
        oneDirectionMultiple(ret,row,col,1,-1,p,gb);
        oneDirectionMultiple(ret,row,col,1,1,p,gb);
        oneDirectionMultiple(ret,row,col,-1,-1,p,gb);
        return ret;
    }

    public static List<Location> knightSpecial(int row, int col) {
        List<Location> moves = new ArrayList<>();
        int[][] directions = {
                {1, 2}, {1, -2}, {2, 1}, {2, -1},
                {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (validTile(newRow, newCol)) {
                moves.add(LocAt.at(newRow, newCol));
            }
        }
        return moves;
    }



    public static List<Location> straight(int row, int col ,Piece p,GameBoard gb){
        List<Location> ret = new ArrayList<>();
        oneDirectionMultiple(ret,row,col,0,1,p,gb);
        oneDirectionMultiple(ret,row,col,0,-1,p,gb);
        oneDirectionMultiple(ret,row,col,1,0,p,gb);
        oneDirectionMultiple(ret,row,col,-1,0,p,gb);
        return ret;
    }
    public static boolean validTile(int i, int j){
        return i>=0 && j>= 0 && i < 8 && j < 8;
    }

    private static void oneDirectionSingle(List<Location> ret,
                                           int row, int col, int rowInc,int colInc,
                                           Piece p,GameBoard gb){
        int i = row + rowInc;
        int j = col + colInc;

        if(validTile(i,j) ){
            if(p instanceof  King){
                if(gb.getTile(i,j).hasPiece() && gb.getTile(i,j).getPiece().getColor() != p.color){
                    ret.add(new Location(i,j));
                }
                if(!gb.getTile(i,j).hasPiece())ret.add(new Location(i,j));
            }else if (p instanceof  Pawn){
                if(colInc == 0 ){
                    if(gb.getTile(row,col).hasPiece() && gb.getTile(row,col).getPiece() != p) return;
                    else{
                        if(!gb.getTile(i,j).hasPiece())ret.add(new Location(i,j));
                    }

                }else if (gb.getTile(i,j).hasPiece() && gb.getTile(i,j).getPiece().getColor() != p.getColor()){
                    ret.add(new Location(i,j));
                }
            }
        }

    }

    private static void oneDirectionMultiple(List<Location> ret,
                                             int row, int col, int rowInc,int colInc,
                                             Piece p,GameBoard gb){
        int i = row + rowInc;
        int j = col + colInc;

        while(validTile(i,j)  && (!gb.getTile(i,j).hasPiece()|| gb.getTile(i,j).getPiece() == p)){
            ret.add(LocAt.at(i,j));
            i+= rowInc;
            j+= colInc;
        }

        if(validTile(i,j)){
            Piece added = gb.getTile(i,j).getPiece();
            if( added != null && added.color != p.color){
                ret.add(LocAt.at(i,j));
            }

        }
  }



}
