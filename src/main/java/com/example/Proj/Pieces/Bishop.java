package com.example.Proj.Pieces;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import javafx.scene.image.Image;

import java.util.List;

public class Bishop extends Piece {
    public Bishop( ColorUtil color) {
        super( color);
    }

    @Override
    public List<LocAt.Location> getPossibleMoves(int row, int col, GameBoard gb) {
        return (MovementHelper.diagonal(row,col,this,gb).stream().filter(l-> !GameRules.kingEndangered(LocAt.at(row,col),
                l,gb)).toList());
    }
}
