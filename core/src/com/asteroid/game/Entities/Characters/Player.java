package com.asteroid.game.Entities.Characters;

import com.asteroid.game.Components.GraphicsComponents.PlayerGraphics;
import com.asteroid.game.Components.InputComponents.PlayerInput;
import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Jacob on 4/22/2017.
 */
public class Player extends Character {

  public static final float speed = 3f;
  public static final float fallSpeed = 1f;
  public static int metalCount = 0;

  public Player(){
    addComponent(new Transform());
    getComponent(Transform.class).setPosition(new Vector2(100,33*16));
    addComponent(new PlayerGraphics());
    addComponent(new PlayerInput());
  }

  @Override
  public void draw(SpriteBatch b) {
    getComponent(PlayerGraphics.class).draw(b);
  }

}
