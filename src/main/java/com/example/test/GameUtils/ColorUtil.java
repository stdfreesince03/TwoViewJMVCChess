package com.example.test.GameUtils;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public enum ColorUtil {
    WHITE,BLACK,RED,ORANGE,NORMAL,CIRCLE;

   public static void fillColor(Shape shape, ColorUtil color){
       if(color.equals(WHITE)){
           shape.setFill(Color.rgb(200,200,200));
       }
       if(color.equals(BLACK)){
           shape.setFill(Color.web("#9c6c5c"));
       }
    }
}
