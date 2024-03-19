package com.example.test.GameUtils;

import com.example.test.Pieces.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GameLoop {

    private final GameBoard gameBoard = new GameBoard();
    private final Stage stage;
    private Map<String, Piece> whitePieces = new HashMap<>();
    private Map<String, Piece> blackPieces = new HashMap<>();

    public GameLoop(Stage stage) {
        this.stage = stage;
        initializePieces();
        placePieces();
    }

    private void initializePieces() {;

        blackPieces.put("K", new King(0, 4, ColorUtil.BLACK, 1));
        blackPieces.put("Q", new Queen(0, 3, ColorUtil.BLACK, 1));
        blackPieces.put("B1", new Bishop(0, 2, ColorUtil.BLACK, 1));
        blackPieces.put("B2", new Bishop(0, 5, ColorUtil.BLACK, 2));
        blackPieces.put("Kn1", new Knight(0, 1, ColorUtil.BLACK, 1));
        blackPieces.put("Kn2", new Knight(0, 6, ColorUtil.BLACK, 2));
        blackPieces.put("R1", new Rook(0, 0, ColorUtil.BLACK, 1));
        blackPieces.put("R2", new Rook(0, 7, ColorUtil.BLACK, 2));
        for (int i = 0; i < 8; i++) {
            blackPieces.put("P" + (i + 1), new Pawn(1, i, ColorUtil.BLACK, 0));
        }

        // Initialize white pieces on rows 7 and 8
        whitePieces.put("K", new King(7, 4, ColorUtil.WHITE, 1));
        whitePieces.put("Q", new Queen(7, 3, ColorUtil.WHITE, 1));
        whitePieces.put("B1", new Bishop(7, 2, ColorUtil.WHITE, 1));
        whitePieces.put("B2", new Bishop(7, 5, ColorUtil.WHITE, 2));
        whitePieces.put("Kn1", new Knight(7, 1, ColorUtil.WHITE, 1));
        whitePieces.put("Kn2", new Knight(7, 6, ColorUtil.WHITE, 2));
        whitePieces.put("R1", new Rook(7, 0, ColorUtil.WHITE, 1));
        whitePieces.put("R2", new Rook(7, 7, ColorUtil.WHITE, 2));
        for (int i = 0; i < 8; i++) {
            whitePieces.put("P" + (i + 1), new Pawn(6, i, ColorUtil.WHITE, 0));
        }
    }

    private void placePieces() {
        // Place white pieces
        for (Piece piece : whitePieces.values()) {
            gameBoard.getTiles()[piece.getPieceRow()][piece.getPieceCol()].setPiece(piece);
        }
        // Place black pieces
        for (Piece piece : blackPieces.values()) {
            gameBoard.getTiles()[piece.getPieceRow()][piece.getPieceCol()].setPiece(piece);
        }
    }

    public void loop(){
        // Game loop logic here
    }

    public GridPane getGameBoard() {
        return gameBoard;
    }
}