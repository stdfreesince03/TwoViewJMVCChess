package GameLog;

import com.example.Proj.Model.Move;
import com.example.Proj.Pieces.*;
import com.example.Proj.Util.ColorUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GameLogger {
    static int gameCount = 0;
    private static final String GAME_FOLDER= "src/main/java/GameLog/Log";
    public static void saveGameMoves(List<Move> moveLog) {
        String fileName = GAME_FOLDER + "/Game" + getLatestFilefromDir() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < moveLog.size(); i += 2) {
                String move1 = formatMove(moveLog.get(i));
                String move2 = (i + 1 < moveLog.size()) ? formatMove(moveLog.get(i + 1)) : "";
                writer.write(move1 + " " + move2);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static  String formatMove(Move move) {
        String pieceSymbol = getPieceSymbol(move.getPiece());
        String from =  ((char)(97 + move.getFrom().col()))+ String.valueOf(8-move.getFrom().row());
        String to=  ((char)(97 + move.getTo().col())) + String.valueOf(8-move.getTo().row());
        return pieceSymbol + ": " + from + "->" + to;
    }

    private static String getPieceSymbol(Piece piece) {
        if (piece instanceof King) return piece.getColor() == ColorUtil.WHITE ? "♔(KiW)" : "♚(KiB)";
        if (piece instanceof Queen) return piece.getColor() == ColorUtil.WHITE ? "♕(QW)" : "♛(QB)";
        if (piece instanceof Rook) return piece.getColor() == ColorUtil.WHITE ? "♖(RW)" : "♜(RB)";
        if (piece instanceof Bishop) return piece.getColor() == ColorUtil.WHITE ? "♗(BiW)" : "♝(BiB)";
        if (piece instanceof Knight) return piece.getColor() == ColorUtil.WHITE ? "♘(KW)" : "♞(KB)";
        if (piece instanceof Pawn) return piece.getColor() == ColorUtil.WHITE ? "♙(PW)" : "♟(PB)";
        return "?"; // Fallback symbol if the piece is unknown
    }

    private static int getLatestFilefromDir(){
        File dir = new File(GAME_FOLDER);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return 1;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }

        String ret = lastModifiedFile.getName();
        ret = ret.replace(".txt","");
        ret = (ret.replace(GAME_FOLDER ,""));
        ret = ret.replace("Game","");
        System.out.println(ret);

        return Integer.valueOf(ret) + 1;
    }

    public static void clearSavedGames() {
        Path path = Paths.get(GAME_FOLDER);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path filePath : directoryStream) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
