package com.example.test.GameUtils;

import com.example.test.Pieces.Pawn;
import com.example.test.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class MoveHandler {
    public static BiFunction<Piece,GameBoard, List<Location>> diagonal = (p, gb) ->{
        List<Location> ret = new ArrayList<>();
        oneDirectionMultiple(ret,-1,1,p,gb);
        oneDirectionMultiple(ret,1,-1,p,gb);
        oneDirectionMultiple(ret,1,1,p,gb);
        oneDirectionMultiple(ret,-1,-1,p,gb);
        return ret;
    };


    public static BiFunction<Piece,GameBoard,List<Location>> straight= (p,gb) ->{
        List<Location> ret = new ArrayList<>();
        oneDirectionMultiple(ret,0,1,p,gb);
        oneDirectionMultiple(ret,0,-1,p,gb);
        oneDirectionMultiple(ret,1,0,p,gb);
        oneDirectionMultiple(ret,-1,0,p,gb);
        return ret;
    };

    public static void kingSpecial (List<Location> ret,int rowInc,int colInc,Piece p
            , GameBoard gb){

        Tile[][] tiles = gb.getTiles();
        int row = p.getPieceRow();
        int col = p.getPieceCol();
        int i = row + rowInc ;
        int j = col + colInc;



        if(i>=0 && j>= 0&& i < 8 && j < 8  && (!tiles[i][j].hasPiece() ||
                !tiles[i][j].samePieceColor(tiles[row][col]) )){
            ret.add(new Location(i,j));
        }

    }

    public static void pawnSpecial (List<Location> ret, Pawn p, GameBoard gb){
        Tile[][] tiles = gb.getTiles();
        int row = p.getPieceRow();
        int col = p.getPieceCol();

        if(row-1 >= 0 && col+1 < 8 && tiles[row-1][col+1].hasPiece() &&
                !tiles[row-1][col+1].samePieceColor(tiles[row][col]) ){
            ret.add(new Location(row-1,col+1));
        }
        if(row-1 >= 0 && col-1 >= 0 && tiles[row-1][col-1].hasPiece() &&
                !tiles[row-1][col-1].samePieceColor(tiles[row][col]) ){
            ret.add(new Location(row-1,col-1));
        }
        if(row-1 >= 0 && !tiles[row-1][col].hasPiece() ){
            ret.add(new Location(row-1,col));
        }
        if(!p.isTwoStep()){
            if((row-2>=0 ) &&
                    (!gb.getTiles()[row-2][col].hasPiece() )){
                ret.add(new Location(row-2,col));
            }
        }
    }


    private static void  oneDirectionMultiple(List<Location> ret, int rowInc,int colInc,Piece p
            , GameBoard gb){
        int row = p.getPieceRow();
        int col = p.getPieceCol();
        ColorUtil color = p.getPieceType().getColor();
        Tile[][] tiles = gb.getTiles();

        int i = row + rowInc ;
        int j = col + colInc;
        while( i>=0 && j>= 0&& i < 8 && j < 8 && !tiles[i][j].hasPiece()  ){
            ret.add(new Location(i,j));
            i+= rowInc;
            j+= colInc;
        }
        if( i>=0 && j>= 0&& i < 8 && j < 8 && !tiles[i][j].samePieceColor(tiles[row][col])){
            ret.add(new Location(i,j));
        }
    }



    public static List<Location> straight(int row, int col ,Piece p,GameBoard gb){
        List<Location> ret = new ArrayList<>();
        oneDirectionMultipleNew(ret,row,col,0,1,p,gb);
        oneDirectionMultipleNew(ret,row,col,0,-1,p,gb);
        oneDirectionMultipleNew(ret,row,col,1,0,p,gb);
        oneDirectionMultipleNew(ret,row,col,-1,0,p,gb);
        return ret;
    }

    private static void  oneDirectionMultipleNew(List<Location> ret,int row, int col, int rowInc,int colInc,Piece p
            , GameBoard gb){

        ColorUtil color = p.getPieceType().getColor();
        Tile[][] tiles = gb.getTiles();

        int i = row + rowInc;
        int j = col + colInc;
        while( i>=0 && j>= 0&& i < 8 && j < 8 && (!tiles[i][j].hasPiece() || tiles[i][j].getTilePiece() == p)  ){
            ret.add(new Location(i,j));
            i+= rowInc;
            j+= colInc;
        }
        if( i>=0 && j>= 0&& i < 8 && j < 8 && !tiles[i][j].samePieceColor(tiles[row][col])){
            ret.add(new Location(i,j));
        }

    }







}
