package com.example.test.Pieces;

import com.example.test.GameUtils.ColorUtil;
import com.example.test.GameUtils.DragAndDropHandler;
import com.example.test.GameUtils.GameBoard;
import com.example.test.GameUtils.Location;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(int rowPos, int colPos, ColorUtil color, int keyNum) {
        super(rowPos, colPos);
        super.setPieceType(color.equals(ColorUtil.WHITE) ? PieceType.KINGW : PieceType.KINGB );
        super.setKey(keyNum);
    }

    @Override
    public boolean isValidPath(int row, int col, GameBoard gb) {
        return getAllPath(row,col,gb).contains(new Location(row,col));
    }

    @Override
    public List<Location> getAllPath(int row, int col, GameBoard gb) {
        List<Location> ret = new ArrayList<>();
        DragAndDropHandler.kingSpecial(ret,-1,0,this,gb);
        DragAndDropHandler.kingSpecial(ret,-1,-1,this,gb);
        DragAndDropHandler.kingSpecial(ret,0,-1,this,gb);
        DragAndDropHandler.kingSpecial(ret,1,-1,this,gb);
        DragAndDropHandler.kingSpecial(ret,1,0,this,gb);
        DragAndDropHandler.kingSpecial(ret,1,1,this,gb);
        DragAndDropHandler.kingSpecial(ret,0,1,this,gb);
        DragAndDropHandler.kingSpecial(ret,-1,1,this,gb);
        return ret;

    }
}
