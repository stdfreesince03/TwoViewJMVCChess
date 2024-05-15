package com.example.Proj.View;


import com.example.Proj.Model.Tile;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class TileView extends Label {
    private final ColorUtil color;
    private ColorUtil highlighted;
    private final LocAt.Location loc;

    public TileView( ColorUtil color,int row,int col) {
        this.color = color;
        loc = LocAt.at(row,col);
        normalColor();
    }

    public void normalColor(){
        if(this.color == ColorUtil.WHITE){
            this.setStyle("-fx-background-radius: 0;-fx-max-height: 80;" +
                    "-fx-max-width: 80;-fx-pref-height: 80;-fx-pref-width: 80;-fx-background-color: white");
        }
        if(this.color == ColorUtil.BLACK){
            this.setStyle("-fx-background-radius: 0;-fx-max-height: 80;" +
                    "-fx-max-width: 80;-fx-pref-height: 80;-fx-pref-width: 80;-fx-background-color: gray");
        }
        this.highlighted = this.color;
    }

    public void highlight(ColorUtil colorParam){
        if(colorParam.equals(ColorUtil.CIRCLE) ){
            Circle circle = new Circle(5);
            circle.setFill(Color.AQUA);
            this.setGraphic(circle);
            this.setAlignment(Pos.CENTER);
        }
        if (colorParam.equals(ColorUtil.NORMAL)) {
            normalColor();
        }
        if ( colorParam.equals(ColorUtil.ORANGE)  ){
            this.setStyle("-fx-background-radius: 0;-fx-max-height: 80;" +
                    "-fx-max-width: 80;-fx-pref-height: 80;-fx-pref-width: 80;-fx-background-color: orange;");

        }if (colorParam.equals(ColorUtil.RED)  ){
            this.setStyle("-fx-background-radius: 0;-fx-max-height: 80;" +
                    "-fx-max-width: 80;-fx-pref-height: 80;-fx-pref-width: 80;-fx-background-color: red;");
        }
        this.highlighted = colorParam;
    }

    public void setImage(Image image){
        ImageView img = new ImageView(image);
        img.setFitWidth(60);
        img.setFitHeight(60);

        this.setGraphic(img);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }


    public ColorUtil getHighlighted() {
        return highlighted;
    }

    public LocAt.Location getLoc() {
        return loc;
    }
}
