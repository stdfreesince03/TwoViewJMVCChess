package com.example.Proj.Model;

import com.example.Proj.Pieces.Piece;
import com.example.Proj.Util.LocAt.*;

public class Move {
    private Location from;
    private Location to;
    private Piece piece;

    public Move(Location from, Location to, Piece piece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
