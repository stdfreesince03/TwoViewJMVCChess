package com.example.Proj.Pieces;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.Move;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import javafx.scene.image.Image;

import java.util.List;

public class Pawn extends Piece  {
    private boolean hasTwoStepped ;

    public Pawn(ColorUtil color) {
        super( color);
        this.hasTwoStepped = false;
    }

    @Override
    public List<LocAt.Location> getPossibleMoves(int row, int col, GameBoard gb) {
        return MovementHelper.pawnSpecial(this,row,col,gb);
    }

    public void twoStep(){
        this.hasTwoStepped = true;
    }

    public boolean hasTwoStepped(){
        return this.hasTwoStepped;
    }



}
