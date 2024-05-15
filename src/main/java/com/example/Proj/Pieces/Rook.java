package com.example.Proj.Pieces;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;

import java.util.List;

public class Rook extends Piece {
    private boolean hasMoved;
    public Rook(ColorUtil color) {
        super(color);
        this.hasMoved = false;
    }

    @Override
    public List<LocAt.Location> getPossibleMoves(int row, int col, GameBoard gb) {
        return MovementHelper.straight(row,col,this,gb) .stream()
                .filter(l -> !GameRules.kingEndangered(LocAt.at(row, col), l, gb)).toList();
    }

    public void setHasMoved(){
        this.hasMoved = true;
    }

    public boolean hasMoved(){
        return this.hasMoved;
    }
}
