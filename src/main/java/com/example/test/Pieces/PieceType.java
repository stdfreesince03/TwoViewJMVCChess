package com.example.test.Pieces;

import com.example.test.GameUtils.ColorUtil;
import javafx.scene.image.Image;

import java.io.File;

public enum PieceType {
    KNIGHTB("src/main/java/com/example/test/Images/Knight-Black.png"),
    KINGB("src/main/java/com/example/test/Images/King-Black.png"),
    QUEENB("src/main/java/com/example/test/Images/Queen-Black.png"),
    ROOKB("src/main/java/com/example/test/Images/Rook-Black.png"),
    BISHOPB("src/main/java/com/example/test/Images/Bishop-Black.png"),
    PAWNB("src/main/java/com/example/test/Images/Pawn-Black.png"),
    KNIGHTW("src/main/java/com/example/test/Images/Knight-White.png"),
    KINGW("src/main/java/com/example/test/Images/King-White.png"),
    QUEENW("src/main/java/com/example/test/Images/Queen-White.png"),
    ROOKW("src/main/java/com/example/test/Images/Rook-White.png"),
    BISHOPW("src/main/java/com/example/test/Images/Bishop-White.png"),
    PAWNW("src/main/java/com/example/test/Images/Pawn-White.png");


    private String image;
    private String key;
    PieceType(String image){
        this.image = image;
    }

    public Image getImage(){
        return new Image(new File(image).toURI().toString());
    }

   public  String getKey(int keyNum){
        return  switch(this){
            case BISHOPB -> "BB";
            case BISHOPW -> "BW";
            case KNIGHTB -> "KB";
            case KNIGHTW -> "KW";
            case ROOKB -> "RB";
            case ROOKW -> "RW";
            case KINGB -> "KiB";
            case KINGW -> "KiW";
            case PAWNB -> "PB";
            case PAWNW -> "PW";
            default -> "";
        } + keyNum ;
    }

    public ColorUtil getColor(){
        return this.toString().charAt(this.toString().length()-1) == 'W' ? ColorUtil.WHITE : ColorUtil.BLACK;
    }




}
