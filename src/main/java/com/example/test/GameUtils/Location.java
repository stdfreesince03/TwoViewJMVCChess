package com.example.test.GameUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Location {
    private static final Map<String, Location> cache = new HashMap<>();
    private final int row;
    private final int col;

    // Private constructor to control instantiation
    private Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Factory method to get Location instances
    public static Location at(int row, int col) {
        String key = row + "," + col;
        // Check if the location is already created
        if (!cache.containsKey(key)) {
            // If not, create a new one and put it into the cache
            cache.put(key, new Location(row, col));
        }
        return cache.get(key);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location other)) {
            return false;
        }
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Location{" + "row=" + row + ", col=" + col + '}';
    }
}
