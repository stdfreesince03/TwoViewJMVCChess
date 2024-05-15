package com.example.Proj.View;

import com.example.Proj.Model.GameRules;
import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.Move;
import com.example.Proj.Model.Tile;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.util.List;

public class GameView  extends GridPane {
    public final static int ROW_SIZE= 8;
    public final static int COL_SIZE= 8;
    private TileView[][] tileViews ;

    public GameView(GameBoard gameBoard){
        this.setWidth(640);
        this.setHeight(640);

        this.tileViews =  new TileView[8][8];

        ColorUtil row = ColorUtil.WHITE;
        ColorUtil col = row;
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                tileViews[i][j] = new TileView(col,i,j);
                this.add(tileViews[i][j],j,i);
                Tile tile = gameBoard.getTile(i, j);
                if(tile.hasPiece()){
                    tileViews[i][j].setImage(tile.getPiece().getImage());
                }
                col = (col == ColorUtil.WHITE) ? ColorUtil.BLACK : ColorUtil.WHITE;
            }
            row = (row == ColorUtil.WHITE) ? ColorUtil.BLACK : ColorUtil.WHITE;
            col = row;
        }

    }
    public void path(TileView tv,GameBoard gameBoard){
        Tile curr = gameBoard.getTile(tv.getLoc().row(),tv.getLoc().col());
        if(curr.hasPiece()){
            drawPathOntoBoard(curr.getPiece().getPossibleMoves(tv.getLoc().row(),tv.getLoc().col()
                    ,gameBoard),tv,gameBoard);
        }
    }



    private void drawPathOntoBoard(List<Location> paths,TileView current,GameBoard gameBoard) {
        Tile curr = gameBoard.getTile(current.getLoc().row(),current.getLoc().col());
        for (Location l : paths) {
            Tile now = gameBoard.getTile(l.row(),l.col());
            if(GameRules.validEnemy(curr,now)){
                tileViews[l.row()][l.col()].highlight(ColorUtil.ORANGE);
            }else if(GameRules.validEmpty(curr,now)){
                tileViews[l.row()][l.col()].highlight(ColorUtil.CIRCLE);
            }

        }
    }
    public void allOff(){
        for(TileView[] x : this.tileViews){
            for (TileView tv : x){
                if(tv.getGraphic() instanceof Circle){
                    tv.setGraphic(null);
                }
                tv.highlight(ColorUtil.NORMAL);
            }
        }
    }

    public void update(Move move,GameBoard gameBoard){
        System.out.println("Executed");
        Tile dest = gameBoard.getTile(move.getTo().row(),move.getTo().col());
        tileViews[move.getTo().row()][move.getTo().col()].setImage(dest.getPiece().getImage());
    }

    public TileView getTileView(int row, int col) {
        return tileViews[row][col];
    }
}
