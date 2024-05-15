package com.example.Proj.Pieces;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Model.Move;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import com.example.Proj.Util.LocAt.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece  {
    private boolean hasMoved;

    public King( ColorUtil color) {
        super( color);
        this.hasMoved = false;
    }

    @Override
    public List<Location> getPossibleMoves(int row, int col , GameBoard gb) {
        List<Location> moves = MovementHelper.kingSpecial(this,row,col,gb);

        if(!this.hasMoved){
            Piece xRight =  gb.getTile(row,col+3).getPiece();
            Piece xLeft = gb.getTile(row,col-4).getPiece();

            if(xRight instanceof Rook && !((Rook) xRight) .hasMoved()){
                if(!GameRules.kingEndangered(LocAt.at(row,col),LocAt.at(row,col+1),gb)){
                    moves.add(LocAt.at(row,col+2));
                }
            }

            if(xLeft instanceof Rook && !((Rook) xLeft).hasMoved()){
                if(!GameRules.kingEndangered(LocAt.at(row,col),LocAt.at(row,col-1),gb)){
                    moves.add(LocAt.at(row,col-2));
                }
            }
        }
       return  moves.stream().filter(l -> {
            try{
                return !GameRules.kingEndangered(LocAt.at(row,col),l,gb);
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }).toList();
    }

    public void setHasMoved(){
        this.hasMoved = true;
    }

    public boolean hasMoved(){
        return this.hasMoved;
    }

}
