package com.example.test.Pieces;

import com.example.test.GameUtils.*;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(int rowPos, int colPos, ColorUtil color, int keyNum){
        super(rowPos, colPos);
        super.setPieceType(color.equals(ColorUtil.WHITE) ? PieceType.QUEENW: PieceType.QUEENB );
        super.setKey(keyNum);
    }



    @Override
    public List<Location> getAllPath(int row, int col,GameBoard gb) {
        List<Location> ret = new ArrayList<>();
        ret.addAll(MoveHandler.straight(row,col,this,gb));
        ret.addAll(MoveHandler.diagonal(row,col,this,gb));
        return ret;
    }


}
