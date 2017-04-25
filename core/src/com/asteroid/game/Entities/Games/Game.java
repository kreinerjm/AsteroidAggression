package com.asteroid.game.Entities.Games;

import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.asteroid.game.Entities.Characters.Player;
import com.asteroid.game.Entities.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jacob on 4/22/2017.
 */
public abstract class Game extends Entity {

  private OrthographicCamera camera;

  public void setCamera(OrthographicCamera camera) {
    this.camera = camera;
  }

  public OrthographicCamera getCamera() {
    return camera;
  }

  public abstract void draw(SpriteBatch batch);

  public abstract void tick();

  public void updateCamera() {

    float x = getChild(Player.class).getComponent(Transform.class).getPosition().x + 32;
    float y = getChild(Player.class).getComponent(Transform.class).getPosition().y + 32;
    //System.out.println(x+","+y);
    camera.position.set(x, y, 0);
    camera.update();

  }

}
