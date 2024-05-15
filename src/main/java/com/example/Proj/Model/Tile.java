package com.example.Proj.Model;

import com.example.Proj.Pieces.Piece;
import com.example.Proj.Util.LocAt;

public class Tile {
    private Piece piece ;
    private LocAt.Location location ;

    public Tile(int row, int col) {
        this.location = LocAt.at(row,col);
        this.piece = null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean inPiecePath(Tile dest,GameBoard gameBoard){
        if(this.piece != null){
            return this.piece.possibleMovesContains(this.getLocation().row(), this.getLocation().col()
                    , dest.getLocation().row(),dest.getLocation().col(),gameBoard);
        }
        return false;

    }

    public boolean hasPiece(){
        return this.piece != null;
    }
    public Piece getPiece() {
        return this.piece;
    }

    public LocAt.Location getLocation() {
        return this.location;
    }




}
