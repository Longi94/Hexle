package com.tlongdev.hexle.view;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tlongdev.hexle.controller.GameController;
import com.tlongdev.hexle.model.SlideDirection;

/**
 * @author longi
 * @since 2016.04.10.
 */
public class FieldView implements BaseView {

    private ShapeRenderer shapeRenderer;

    private int screenWidth;
    private int screenHeight;

    private TileView[][] tileViews;
    private TileView[] fillerTileViews;

    private TileView selectedTile;
    private SlideDirection slideDirection;
    private float slideDistance;

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;

        //Get the maximum width the tile can fit in the screen
        float tileWidth = (float) (screenWidth / Math.ceil(GameController.TILE_COLUMNS / 2.0));

        //Calculate the height from the width (equilateral triangle height from side)
        float tileHeight = tileWidth * (float) Math.sqrt(3) / 2.0f;

        //Calculate the vertical offset, so the triangles are in the middle of the screen
        float offsetY = (screenHeight - (GameController.TILE_ROWS - 1) * tileHeight) / 2.0f;

        //The vector that will translate all the affected tiles
        Vector2 slideVector = new Vector2(slideDistance, 0);

        //Iterate through the tiles
        for (int i = 0; i < GameController.TILE_ROWS; i++) {
            for (int j = 0; j < GameController.TILE_COLUMNS; j++) {
                TileView view = tileViews[i][j];

                //Set the center
                view.setCenter(new Vector2(
                        (j + 1) * tileWidth / 2.0f,
                        offsetY + i * tileHeight
                ));

                view.setSide(tileWidth * 0.9f);

                //If slideDirection is not null the a slide is currently happening
                if (slideDirection != null && view.isAffectedBySlide(selectedTile, slideDirection)) {
                    switch (slideDirection) {
                        case EAST:
                            slideVector.setAngleRad(0);
                            break;
                        case NORTH_EAST:
                            slideVector.setAngleRad(MathUtils.PI / 3.0f);
                            break;
                        case NORTH_WEST:
                            slideVector.setAngleRad(2.0f * MathUtils.PI / 3.0f);
                            break;
                    }

                    //Because setting the length of the vector will always make if face in the
                    //positive direction no matter the distance being negative. Dumb.
                    if (slideDistance < 0) {
                        slideVector.rotateRad(MathUtils.PI);
                    }
                    view.getCenter().add(slideVector);

                    renderDuplicates(view, slideDirection, tileWidth);
                }

                view.render(shapeRenderer);
            }
        }

        renderFillers(tileWidth);
    }

    private void renderFillers(float tileWidth) {
        //Draw the filler tiles if needed
        if (slideDirection != null && selectedTile != null) {
            int leftFillerIndex = -1;
            int rightFillerIndex = -1;
            float leftFillerX = 0;
            float leftFillerY = 0;
            float rightFillerX = 0;
            float rightFillerY = 0;
            switch (slideDirection) {
                case EAST:
                    leftFillerIndex = selectedTile.getTile().getHorizontalRowIndex();
                    rightFillerIndex = selectedTile.getTile().getHorizontalRowIndex();
                    leftFillerX = selectedTile.getCenter().x -
                            (selectedTile.getTile().getPosX() + 1) * tileWidth / 2.0f;
                    rightFillerX = selectedTile.getCenter().x +
                            (9 - selectedTile.getTile().getPosX()) * tileWidth / 2.0f;

                    leftFillerY = rightFillerY = selectedTile.getCenter().y;
                    break;
                case NORTH_EAST:
                    break;
                default:
                    break;
            }

            TileView leftFiller = fillerTileViews[leftFillerIndex];
            leftFiller.setSide(tileWidth * 0.9f);
            leftFiller.setCenter(new Vector2(leftFillerX, leftFillerY));
            leftFiller.render(shapeRenderer);

            TileView rightFiller = fillerTileViews[rightFillerIndex];
            rightFiller.setSide(tileWidth * 0.9f);
            rightFiller.setCenter(new Vector2(rightFillerX, rightFillerY));
            rightFiller.render(shapeRenderer);
        }
    }

    /**
     * This will render duplicates of triangles which are currently sliding creating an illusion of
     * a looped shift register.
     *
     * @param original  the original tile view
     * @param direction the direction the sliding is going on
     * @param side      the (full) size of the triangles side
     */
    private void renderDuplicates(TileView original, SlideDirection direction, float side) {
        Vector2 slideVector = new Vector2(slideDistance, 0);
        Vector2 originalVector = original.getCenter();
        float distance;
        switch (direction) {
            case EAST:
                distance = side * 5.0f;
                slideVector.setAngleRad(0);
                break;
            case NORTH_EAST:
                int rightIndex = original.getTile().getRightDiagonalIndex();
                distance = (1 + Math.min(rightIndex, 7 - rightIndex)) * 2.0f * side;
                slideVector.setAngleRad(MathUtils.PI / 3.0f);
                break;
            default:
                int leftIndex = original.getTile().getLeftDiagonalIndex();
                distance = (1 + Math.min(leftIndex, 7 - leftIndex)) * 2.0f * side;
                slideVector.setAngleRad(2.0f * MathUtils.PI / 3.0f);
                break;
        }
        slideVector.setLength(distance);
        original.setCenter(originalVector.cpy().add(slideVector));
        original.render(shapeRenderer);

        slideVector.rotateRad(MathUtils.PI);
        original.setCenter(originalVector.cpy().add(slideVector));
        original.render(shapeRenderer);
        original.setCenter(originalVector);
    }

    public void setDimensions(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    public TileView[][] getTileViews() {
        return tileViews;
    }

    public void setTileViews(TileView[][] tileViews) {
        this.tileViews = tileViews;
    }

    public void touchDown(int x, int y) {
        Vector2 touchDown = new Vector2(x, y);
        float minDist = screenHeight;

        //Find the closest tile and mark it as selected
        for (int i = 0; i < GameController.TILE_ROWS; i++) {
            for (int j = 0; j < GameController.TILE_COLUMNS; j++) {
                float dist = touchDown.dst(tileViews[i][j].getTriangleCenter());
                if (minDist > dist) {
                    minDist = dist;
                    selectedTile = tileViews[i][j];
                }
            }
        }
    }

    public void touchUp(int screenX, int screenY) {
        selectedTile = null;
        slideDirection = null;
    }

    public void setSlide(SlideDirection direction, float dst) {
        this.slideDirection = direction;
        this.slideDistance = dst;
    }

    public void setFillerTileViews(TileView[] fillerTileViews) {
        this.fillerTileViews = fillerTileViews;
    }
}
