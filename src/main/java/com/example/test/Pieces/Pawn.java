package com.example.test.Pieces;

import com.example.test.GameUtils.*;

import java.util.ArrayList;
import java.util.List;



public class Pawn extends Piece{

    private boolean twoStep = false;

    public Pawn(int rowPos, int colPos, ColorUtil color, int keyNum) {
        super(rowPos, colPos);
        super.setPieceType(color.equals(ColorUtil.WHITE) ? PieceType.PAWNW : PieceType.PAWNB );

        super.setKey(keyNum);
//        this.twoStep = false;
    }

    @Override
    public boolean isValidPath(int row, int col, GameBoard gb) {
        return getAllPath(row,col,gb).contains(new Location(row,col));
    }

    @Override
    public List<Location> getAllPath(int row, int col, GameBoard gb) {
        List<Location> ret = new ArrayList<>();
        DragAndDropHandler.pawnSpecial(ret,this,gb);
//        DragAndDropHandler.oneDirectionSingle(ret,-1,0,this,gb,true);
//        DragAndDropHandler.oneDirectionSingle(ret,-1,1,this,gb,false);
//        DragAndDropHandler.oneDirectionSingle(ret,-1,-1,this,gb,false);
//        if(!twoStep){
//            if((row-2 >=0 ) &&
//                    (!gb.getTiles()[row-2][col].hasPiece() )){
//                    ret.add(new Location(row-2,col));
//            }
//        }
        return ret;

    }

    public boolean isTwoStep() {
        return twoStep;
    }
}
