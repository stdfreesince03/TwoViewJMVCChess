package com.example.trials;

import com.example.test.GameUtils.ColorUtil;
import com.example.test.Pieces.Knight;
import com.example.test.Pieces.Piece;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class MainTrial extends Application {

    public void start(Stage stage) {
        stage.setTitle("Hello Drag And Drop");

        Group root = new Group();
        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.LIGHTGREEN);

        ImageView imgV = new ImageView(new Image((new File("src/main/java/com/example/test/Images/Knight-Black.png").toURI().toString())));

//        StackPane knightPane  = new StackPane();
//        Piece knight = new Knight(0,0, ColorUtil.BLACK,1);

        final Label source = new Label("DRAG ME",imgV);
        source.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

//        source.setScaleX(1.0);
//        source.setScaleY(1.0);

        final Label target = new Label( "DROP HERE");
        target.setTranslateX(250);
        target.setTranslateY(100);
//        target.setScaleX(2.0);
//        target.setScaleY(2.0);

       source.setOnDragDetected(event -> {
            /* drag was detected, start drag-and-drop gesture */
            System.out.println("onDragDetected");

            /* allow any transfer mode */
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);

            Image img = new Image(new File("src/main/java/com/example/test/Images/Knight-Black.png").toURI().toString());
            db.setDragView(img);
            db.setDragViewOffsetX(37);
            db.setDragViewOffsetY(30);

            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(source.getText());
            content.putImage(img);

            db.setContent(content);

            source.setGraphic(null);
            source.setText("");

            event.consume();
        });

        target.setOnDragOver(event -> {
            /* data is dragged over the target */
            System.out.println("onDragOver");

            /*
             * accept it only if it is not dragged from the same node and if it
             * has a string data
             */
            if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        target.setOnDragEntered(event -> {
            /* the drag-and-drop gesture entered the target */
            System.out.println("onDragEntered");
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                target.setTextFill(Color.GREEN);
            }



            event.consume();
        });

        target.setOnDragExited(event -> {
            /* mouse moved away, remove the graphical cues */
            target.setTextFill(Color.BLACK);

            event.consume();
        });

        target.setOnDragDropped(event -> {
            /* data dropped */
            System.out.println("onDragDropped");
            /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                target.setText(db.getString());
                target.setGraphic(new ImageView(db.getImage()));
                target.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                success = true;
            }
            /*
             * let the source know whether the string was successfully
             * transferred and used
             */
            event.setDropCompleted(success);

            event.consume();
        });

        source.setOnDragDone(event -> {
            /* the drag-and-drop gesture ended */
            System.out.println("onDragDone");
            /* if the data was successfully moved, clear it */
            if (event.getTransferMode() == TransferMode.MOVE) {
                source.setText("");
                source.setGraphic(null);
            }else{
                source.setText(event.getDragboard().getString());
                source.setGraphic(new ImageView(event.getDragboard().getImage()));
            }

            event.consume();
        });
//        root.getChildren().add(new ImageView(knight.getPieceType().getImage()));
        root.getChildren().add(source);
        root.getChildren().add(target);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
