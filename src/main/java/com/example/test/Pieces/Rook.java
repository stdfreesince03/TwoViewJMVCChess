package com.example.test.Pieces;

import com.example.test.GameUtils.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private boolean hasMoved = false;
    public Rook(int rowPos, int colPos, ColorUtil color, int keyNum){
        super(rowPos, colPos);
        super.setPieceType(color.equals(ColorUtil.WHITE) ? PieceType.ROOKW: PieceType.ROOKB );
        super.setKey(keyNum);
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void moved() {
        this.hasMoved = true;
    }
    @Override
    public List<Location> getAllPath(int row, int col,GameBoard gb) {
        return new ArrayList<>(MoveHandler.straight(row,col,this, gb));
    }
}
