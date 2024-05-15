package com.example.Proj.Pieces;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import javafx.scene.image.Image;

import java.util.List;

public class Queen extends Piece{
    public Queen( ColorUtil color) {
        super( color);
    }

    @Override
    public List<LocAt.Location> getPossibleMoves(int row, int col , GameBoard gb) {
        List<LocAt.Location> ret = MovementHelper.diagonal(row,col,this,gb);
        ret.addAll(MovementHelper.straight(row,col,this,gb));
        return (ret);
    }
}
