package com.tlongdev.hexle.renderer;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.tlongdev.hexle.controller.GameController;
import com.tlongdev.hexle.view.GameView;

/**
 * @author longi
 * @since 2016.04.10.
 */
public class GameRenderer implements Disposable{

    private ShapeRenderer shapeRenderer;

    private GameController controller;

    private int width;

    private int height;

    public GameRenderer(GameController controller, int width, int height) {
        this.controller = controller;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        shapeRenderer = controller.getShapeRenderer();
    }

    public void render() {
        //Render the game
        GameView view = controller.getGameView();
        view.render();
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        GameView view = controller.getGameView();
        view.setDimensions(width, height);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
