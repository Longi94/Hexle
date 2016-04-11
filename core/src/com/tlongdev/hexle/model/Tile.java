package com.tlongdev.hexle.model;

/**
 * A single tile.
 *
 * @author longi
 * @since 2016.04.09.
 */
public class Tile {

    private int posX;

    private int posY;

    /**
     * These variables determine index of the 3 possible rows the tile is in
     */
    private int horizontalRowIndex;
    private int leftDiagonalIndex;
    private int rightDiagonalIndex;

    private TileColor tileColor;

    private TileOrientation orientation;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public TileColor getTileColor() {
        return tileColor;
    }

    public void setTileColor(TileColor tileColor) {
        this.tileColor = tileColor;
    }

    public TileOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(TileOrientation orientation) {
        this.orientation = orientation;
    }

    public int getHorizontalRowIndex() {
        return horizontalRowIndex;
    }

    public int getLeftDiagonalIndex() {
        return leftDiagonalIndex;
    }

    public int getRightDiagonalIndex() {
        return rightDiagonalIndex;
    }

    public boolean isAffectedBySlide(Tile selectedTile, SlideDirection slideDirection) {
        if (selectedTile == null || slideDirection == null) {
            return false;
        }
        switch (slideDirection) {
            case EAST:
                //Sliding sideways, tile is affected if the Y coordinate is the same
                return horizontalRowIndex == selectedTile.getHorizontalRowIndex();
            case NORTH_EAST:
                //Magic to determine whether the tile is in the same right diagonal
                return rightDiagonalIndex == selectedTile.getRightDiagonalIndex();
            case NORTH_WEST:
                //Magic to determine whether the tile is in the same left diagonal
                return leftDiagonalIndex == selectedTile.getLeftDiagonalIndex();
        }
        return false;
    }

    public void updateIndices() {
        //Black magic (not really) to determine which rows the tile is in
        horizontalRowIndex = posY;
        rightDiagonalIndex = (posX - posY + 7) / 2;
        leftDiagonalIndex = (posX + posY) / 2;
    }

    public enum TileOrientation {
        UP, DOWN
    }
}