package com.example.test.Pieces;

import com.example.test.GameUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class King extends Piece{
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

        ret = ret.stream()
                .filter(l-> (!isChecked(l.getRow(),l.getCol(),gb)) )
                .toList();
        return ret;

    }



    public boolean isChecked(int row ,int col ,GameBoard gb){

        String checkerColor = this.getPieceType().getColor() == ColorUtil.BLACK ?  "W" : "B" ;
        boolean hasPiece = gb.getTiles()[row][col].hasPiece();
        boolean straight = MoveHandler.straight(row,col,this,gb,hasPiece).stream()
                .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
                .filter(Objects::nonNull)
                 .anyMatch(piece -> piece.getKey().equals("Q"+checkerColor + piece.getKeyNum()) ||
                        piece.getKey().equals("R"+ checkerColor + piece.getKeyNum()) );
        boolean diagonal = MoveHandler.diagonal(row,col,this,gb,hasPiece)
                .stream()
                .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
                .filter(Objects::nonNull)
                .anyMatch(piece -> piece.getKey().equals("B"+checkerColor + piece.getKeyNum()) ||
                        piece.getKey().equals("Q"+checkerColor + piece.getKeyNum()) );
        System.out.println(diagonal);
        boolean lHorse = Stream.of(new Location(row+1,col+2),
                new Location(row+1,col-2) ,
                new Location(row+2,col-1),new Location(row+2,col+1),
                new Location(row-2,col-1),new Location(row-2,col+1),
                new Location(row-1,col-2),new Location(row-1,col+2)).filter(l ->
                (l.getRow() >= 0 && l.getRow() < 8) && (l.getCol() >= 0 && l.getCol() < 8) )
                .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
                .filter(Objects::nonNull)
                .anyMatch(piece -> piece.getKey().equals("K"+checkerColor + piece.getKeyNum()));

        Stream<Location>  pawnStream;
        if(this.getPieceType().getColor() == ColorUtil.BLACK){
            pawnStream =Stream.of(new Location(row+1,col+1),
                    new Location(row+1,col-1));
        }else{
            pawnStream =Stream.of(new Location(row-1,col+1),
                    new Location(row-1,col-1));
        }
        boolean pawnCheck = pawnStream
                .filter(l -> (l.getRow() >= 0 && l.getRow() < 8) && (l.getCol() >= 0 && l.getCol() < 8))
                .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
                .filter(Objects::nonNull)
                .anyMatch(piece -> piece.getKey().equals("P" + checkerColor + piece.getKeyNum()));
//
      boolean kingCheck =Stream.of(new Location(row,col+1),
                new Location(row,col-1),new Location(row-1,col),
              new Location(row+1,col),new Location(row-1,col-1),
              new Location(row-1,col+1),new Location(row+1,col-1),
              new Location(row+1,col+1))
              .filter(l ->
                      (l.getRow() >= 0 && l.getRow() < 8) && (l.getCol() >= 0 && l.getCol() < 8) )
              .map(l -> gb.getTiles()[l.getRow()][l.getCol()].getTilePiece())
              .filter(Objects::nonNull)
              .anyMatch(piece -> piece.getKey().equals("Ki" + checkerColor + piece.getKeyNum()));


        return straight || lHorse || diagonal || pawnCheck || kingCheck;

    }

    public boolean isCheckMated(int row, int col, GameBoard gb){
        return getAllPath(row,col,gb).isEmpty();
    }

}
