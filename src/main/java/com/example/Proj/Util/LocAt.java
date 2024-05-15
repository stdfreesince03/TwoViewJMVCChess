package com.example.Proj.Util;

import com.example.Proj.Pieces.MovementHelper;

public class LocAt {
    public record Location(int row , int col){

    }
    static final Location[][] locs = new Location[8][8];
    static{
        for(int i = 0 ; i < 8 ; i++){
            for(int j = 0 ; j < 8 ; j++){
                locs[i][j] = new Location(i,j);
            }
        }
    }

    public static Location at(int row, int col){
        if(!MovementHelper.validTile(row,col)){
            return null;
        }
        return locs[row][col];
    }

}
