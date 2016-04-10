package com.tlongdev.hexle.view;

import com.badlogic.gdx.math.Vector2;
import com.tlongdev.hexle.controller.GameController;

/**
 * @author longi
 * @since 2016.04.10.
 */
public class GameView implements BaseView {

    private int screenWidth;
    private int screenHeight;

    private TileView[][] tileViews;

    @Override
    public void render() {
        float width = (float) (screenWidth / Math.ceil(GameController.TILE_COLUMNS / 2.0));
        float height = width * (float) Math.sqrt(3) / 2.0f;
        float offsetY = (screenHeight - (GameController.TILE_ROWS - 1) * height) / 2.0f;

        for (int i = 0; i < GameController.TILE_ROWS; i++) {
            for (int j = 0; j < GameController.TILE_COLUMNS; j++) {
                TileView view = tileViews[i][j];
                view.setSide(width * 0.9f);
                view.setCenter(new Vector2(
                        (j + 1) * width / 2.0f,
                        offsetY + i * height
                ));
                view.render();
            }
        }
    }

    @Override
    public void dispose() {
        for (int i = 0; i < GameController.TILE_ROWS; i++) {
            for (int j = 0; j < GameController.TILE_COLUMNS; j++) {
                tileViews[i][j].dispose();
            }
        }

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
}
