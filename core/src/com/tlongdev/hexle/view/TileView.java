package com.tlongdev.hexle.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tlongdev.hexle.model.Tile;
import com.tlongdev.hexle.shape.EquilateralTriangle;

/**
 * @author longi
 * @since 2016.04.10.
 */
public class TileView implements BaseView {

    private EquilateralTriangle triangle;

    private Tile tile;

    private Vector2 center;

    public TileView() {
        triangle = new EquilateralTriangle();
    }

    @Override
    public void render() {
        switch (tile.getOrientation()) {
            case UP:
                triangle.setRotation(MathUtils.PI / 2);
                triangle.setCenter(new Vector2(
                        center.x,
                        center.y - ((float) Math.sqrt(3) * triangle.getSide() / 12.0f)
                ));
                break;
            case DOWN:
                triangle.setRotation(-MathUtils.PI / 2);
                triangle.setCenter(new Vector2(
                        center.x,
                        center.y + ((float) Math.sqrt(3) * triangle.getSide() / 12.0f)
                ));
                break;
        }

        switch (tile.getTileColor()) {
            case RED:
                triangle.setColor(Color.RED);
                break;
            case GREEN:
                triangle.setColor(Color.GREEN);
                break;
            case BLUE:
                triangle.setColor(Color.BLUE);
                break;
            case CYAN:
                triangle.setColor(Color.CYAN);
                break;
            case MAGENTA:
                triangle.setColor(Color.MAGENTA);
                break;
            case YELLOW:
                triangle.setColor(Color.YELLOW);
                break;
        }

        triangle.render();
    }

    @Override
    public void dispose() {
        triangle.dispose();
        tile = null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setSide(float side) {
        triangle.setSide(side);
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }

    public Vector2 getTriangleCenter() {
        return triangle.getCenter();
    }

    public Vector2 getCenter() {
        return center;
    }
}
