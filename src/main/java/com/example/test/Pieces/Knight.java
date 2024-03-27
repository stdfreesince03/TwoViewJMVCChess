package com.example.test.Pieces;

import com.example.test.GameUtils.ColorUtil;
import com.example.test.GameUtils.GameBoard;
import com.example.test.GameUtils.Location;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(int rowPos, int colPos, ColorUtil color, int keyNum) {
        super(rowPos, colPos);
        super.setPieceType(color.equals(ColorUtil.WHITE) ? PieceType.KNIGHTW : PieceType.KNIGHTB );
        super.setKey(keyNum);
    }

    @Override
    public boolean isValidPath(int row, int col, GameBoard gb) {
        int r0 = super.getPieceRow();
        int c0 = super.getPieceCol();
        return (Math.abs(row-r0) == 1 && Math.abs(col-c0) ==2) || (Math.abs(row-r0) == 2 && Math.abs(col -c0) == 1 );
    }

    @Override
    public List<Location> getAllPath(int row, int col,GameBoard gb) {
         return new ArrayList<Location>(List.of(Location.at(row+1,col+2),
                 Location.at(row+1,col-2) ,
                 Location.at(row+2,col-1),Location.at(row+2,col+1),
                 Location.at(row-2,col-1),Location.at(row-2,col+1),
                 Location.at(row-1,col-2),Location.at(row-1,col+2)));
    }
}
