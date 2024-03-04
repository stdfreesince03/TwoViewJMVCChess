package com.example.test.Pieces;

import com.example.test.GameUtils.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(int rowPos, int colPos, ColorUtil color, int keyNum){
        super(rowPos, colPos);
        super.setPieceType(color.equals(ColorUtil.WHITE) ? PieceType.ROOKW: PieceType.ROOKB );
        super.setKey(keyNum);
    }

    @Override
    public boolean isValidPath(int row, int col, GameBoard gb) {
        return (getAllPath(row,col,gb).contains(new Location(row,col)));
    }

    @Override
    public List<Location> getAllPath(int row, int col,GameBoard gb) {
        return new ArrayList<>(MoveHandler.straight.apply(this, gb));
    }
}
