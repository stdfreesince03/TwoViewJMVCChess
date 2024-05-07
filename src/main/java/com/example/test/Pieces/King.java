package com.example.test.Pieces;

import com.example.test.GameUtils.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class King extends Piece{
    private boolean hasMoved = false;
    public King(int rowPos, int colPos, ColorUtil color, int keyNum) {
        super(rowPos, colPos);
        super.setPieceType(color.equals(ColorUtil.WHITE) ? PieceType.KINGW : PieceType.KINGB );
        super.setKey(keyNum);
    }


    @Override
    public List<Location> getAllPath(int row, int col, GameBoard gb) {
        List<Location> ret = new ArrayList<>();
        MoveHandler.kingSpecial(ret,-1,0,this,gb);
        MoveHandler.kingSpecial(ret,-1,-1,this,gb);
        MoveHandler.kingSpecial(ret,0,-1,this,gb);
        MoveHandler.kingSpecial(ret,1,-1,this,gb);
        MoveHandler.kingSpecial(ret,1,0,this,gb);
        MoveHandler.kingSpecial(ret,1,1,this,gb);
        MoveHandler.kingSpecial(ret,0,1,this,gb);
        MoveHandler.kingSpecial(ret,-1,1,this,gb);
        if(!hasMoved()){
            if(castle(gb,2,true) != null){
                MoveHandler.kingSpecial(ret,0,2,this,gb);
            }
            if(castle(gb,-2,true) != null){
                MoveHandler.kingSpecial(ret,0,-2,this,gb);
            }

        }



        ret = ret.stream()
                .filter(l-> (!isChecked(l.getRow(),l.getCol(),gb)) )
                .toList();
        return ret;

    }



    public boolean isChecked(int row ,int col ,GameBoard gb){

        String checkerColor = this.getPieceType().getColor() == ColorUtil.BLACK ?  "W" : "B" ;
        boolean straight = MoveHandler.straight(row,col,this,gb).stream()
                .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
                .filter(Objects::nonNull)
                 .anyMatch(piece -> piece.getKey().equals("Q"+checkerColor + piece.getKeyNum()) ||
                        piece.getKey().equals("R"+ checkerColor + piece.getKeyNum()) );
        boolean diagonal = MoveHandler.diagonal(row,col,this,gb)
                .stream()
                .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
                .filter(Objects::nonNull)
                .anyMatch(piece -> piece.getKey().equals("B"+checkerColor + piece.getKeyNum()) ||
                        piece.getKey().equals("Q"+checkerColor + piece.getKeyNum()) );
        boolean lHorse = Stream.of(Location.at(row+1,col+2),
                Location.at(row+1,col-2) ,
                Location.at(row+2,col-1),Location.at(row+2,col+1),
                Location.at(row-2,col-1),Location.at(row-2,col+1),
                Location.at(row-1,col-2),Location.at(row-1,col+2)).filter(l ->
                (l.getRow() >= 0 && l.getRow() < 8) && (l.getCol() >= 0 && l.getCol() < 8) )
                .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
                .filter(Objects::nonNull)
                .anyMatch(piece -> piece.getKey().equals("K"+checkerColor + piece.getKeyNum()));

        Stream<Location>  pawnStream;
        if(this.getPieceType().getColor() == ColorUtil.BLACK){
            pawnStream =Stream.of(Location.at(row+1,col+1),
                    Location.at(row+1,col-1));
        }else{
            pawnStream =Stream.of(Location.at(row-1,col+1),
                    Location.at(row-1,col-1));
        }
        boolean pawnCheck = pawnStream
                .filter(l -> (l.getRow() >= 0 && l.getRow() < 8) && (l.getCol() >= 0 && l.getCol() < 8))
                .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
                .filter(Objects::nonNull)
                .anyMatch(piece -> piece.getKey().equals("P" + checkerColor + piece.getKeyNum()));
//
      boolean kingCheck =Stream.of(Location.at(row,col+1),
                Location.at(row,col-1),Location.at(row-1,col),
              Location.at(row+1,col),Location.at(row-1,col-1),
              Location.at(row-1,col+1),Location.at(row+1,col-1),
              Location.at(row+1,col+1))
              .filter(l ->
                      (l.getRow() >= 0 && l.getRow() < 8) && (l.getCol() >= 0 && l.getCol() < 8) )
              .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
              .filter(Objects::nonNull)
              .anyMatch(piece -> piece.getKey().equals("Ki" + checkerColor + piece.getKeyNum()));


        return straight || lHorse || diagonal || pawnCheck || kingCheck;

    }

    public Rook castle(GameBoard gb,int colMovement,boolean path){
        Rook ret = null;
        int rookAdd = 0;
        if(this.hasMoved()){
            return null;
        }
        if(!path){
            if(colMovement ==2 ){
                rookAdd = 1;
            }else{
                rookAdd = -2;
            }
        }else{
            if(colMovement ==2 ){
                rookAdd = 3;
            }else{
                rookAdd = -4;
            }
        }

        if(!(gb.getTiles()[this.getPieceRow()][this.getPieceCol() + rookAdd ].getTilePiece() instanceof  Rook) ){
                return null;
        }
        ret = (Rook) gb.getTiles()[this.getPieceRow()][this.getPieceCol() + rookAdd ].getTilePiece();
        System.out.println(ret.getKey());

        if(ret.hasMoved()){
            return null;
        }
        return ret;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void moved() {
        this.hasMoved = true;
    }

    public boolean isStaleMate(GameBoard gb){
        Map<String,Piece> check = (this.getPieceType().getColor() == ColorUtil.WHITE) ? gb.getWhitePieces() : gb.getBlackPieces();
        Map<String,Piece> opposite = (this.getPieceType().getColor() == ColorUtil.WHITE) ? gb.getBlackPieces(): gb.getWhitePieces();
        if(opposite.size() == 1 && check.size() ==1 ){
            return true;
        }
        for(Piece p: check.values()){
            if(!p.getAllPath(p.getPieceRow(), p.getPieceCol(), gb).isEmpty()){
                return false;
            }
        }
        return getAllPath(this.getPieceRow(),this.getPieceCol(),gb).isEmpty();
    }

    public boolean isCheckMated(GameBoard gb){
        return isChecked(this.getPieceRow(),this.getPieceCol(),gb)
                && getAllPath(this.getPieceRow(),this.getPieceCol(),gb).isEmpty() ;
    }

}
