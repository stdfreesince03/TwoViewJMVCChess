package com.example.Proj.Pieces;

import com.example.Proj.Model.GameBoard;
import com.example.Proj.Model.GameRules;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import com.example.Proj.Util.LocAt.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Knight extends Piece{
    public Knight( ColorUtil color) {
        super(color);
    }

    @Override
    public List<LocAt.Location> getPossibleMoves(int row, int col , GameBoard gb) {
        return Stream.of(
                        LocAt.at(row + 1, col + 2),
                        LocAt.at(row + 1, col - 2),
                        LocAt.at(row + 2, col - 1), LocAt.at(row + 2, col + 1),
                        LocAt.at(row - 2, col - 1), LocAt.at(row - 2, col + 1),
                        LocAt.at(row - 1, col - 2), LocAt.at(row - 1, col + 2))
                .filter(Objects::nonNull)
                .filter(l -> !GameRules.kingEndangered(LocAt.at(row, col), l, gb))
                .filter(Objects::nonNull)
                .toList();

    }
}
