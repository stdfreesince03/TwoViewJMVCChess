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
    public boolean isValidPath(int row, int col, GameBoard gb) {
      return (getAllPath(row,col,gb).contains(new Location(row,col)));
    }

    @Override
    public List<Location> getAllPath(int row, int col,GameBoard gb) {
        List<Location> ret = new ArrayList<>();
        ret.addAll(DragAndDropHandler.straight.apply(this,gb));
        ret.addAll(DragAndDropHandler.diagonal.apply(this,gb));
        return ret;
    }
}
