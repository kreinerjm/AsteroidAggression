package com.asteroid.game.Entities.Characters;

import com.asteroid.game.AsteroidAggression;
import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Jacob on 4/22/2017.
 */
public class Alien extends Character{
  public int hitpoints = 25;
  public static final float speed = 4f;
  static Texture alienTexture = new Texture(Gdx.files.internal("mostro.png"));
  public Alien(){
    Transform t = new Transform();
    t.position.x = (float)Math.random()*128f*16f;
    t.position.y = 48*16;
    addComponent(t);
  }

  @Override
  public void draw(SpriteBatch b) {
    Player player = AsteroidAggression.game.getChild(Player.class);
    moveTowards(player);
    b.draw(alienTexture, getComponent(Transform.class).position.x,getComponent(Transform.class).position.y,64,64);
  }

  public void draw(SpriteBatch b, float x, float y) {
    Player player = AsteroidAggression.game.getChild(Player.class);
    moveTowards(player);
    b.draw(alienTexture, x,y,64,64);
  }

  public void moveTowards(Player p){
    Vector2 positionThis = getComponent(Transform.class).position;
    Vector2 positionTarget = p.getComponent(Transform.class).position;
    if(positionThis.x < positionTarget.x){
      positionThis.x++;
    }
    else if(positionThis.x > positionTarget.x) {
      positionThis.x--;
    }

    if(positionThis.y < positionTarget.y){
      positionThis.y++;
    }
    else if(positionThis.y > positionTarget.y) {
      positionThis.y--;
    }
  }
}
