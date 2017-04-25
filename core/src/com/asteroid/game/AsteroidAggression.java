package com.asteroid.game;

import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.asteroid.game.Entities.Characters.Player;
import com.asteroid.game.Entities.Games.MinerGame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AsteroidAggression extends ApplicationAdapter {
	SpriteBatch batch;
	public static MinerGame game;
	OrthographicCamera cam;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		game = new MinerGame();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(30, 30 * (h / w));
		cam.setToOrtho(false, 960, 640);
		cam.position.set(0, 0, 0);
		game.setCamera(cam);
		game.getCamera().position.x = game.getChild(Player.class).getComponent(Transform.class).position.x;
		game.getCamera().position.y = game.getChild(Player.class).getComponent(Transform.class).position.y;
		cam.update();
	}

	@Override
	public void render () {
		game.tick();
		Gdx.gl.glClearColor(30f/256f, 30f/256f, 66f/256f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		game.draw(batch);
		batch.end();
	}
}
