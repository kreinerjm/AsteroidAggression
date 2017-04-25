package com.asteroid.game.Entities.Projectiles;

import com.asteroid.game.AsteroidAggression;
import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.asteroid.game.Entities.Characters.Alien;
import com.asteroid.game.Entities.Entity;
import com.asteroid.game.Entities.Worlds.SideScrollerTileWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Point;

/**
 * Created by Jacob on 4/24/2017.
 */
public class Laser extends Entity{
  Texture laser = new Texture(Gdx.files.internal("laser.png"));
  Alien target;
  public float x, y;
  float dx, dy;

  public Laser(float x, float y, Alien target){
    this.x = x + 8;
    this.y = y + 8;
    this.target = target;
    float angle = getAngle(new Point.Float(target.getComponent(Transform.class).position.x + 32,target.getComponent(Transform.class).position.y + 32)) - (float)Math.PI/2f;
    dx = (float)Math.cos(Math.toRadians(angle))*5f;
    dy = (float)Math.sin(Math.toRadians(angle))*5f;
  }

  public void update(){
    this.x += dx;
    if(this.x > 128*16){
      x = x%(128*16);
    }
    else if(this.x < 0){
      this.x += (128*16);
    }
    this.y += dy;
    for(Alien a : AsteroidAggression.game.getChild(SideScrollerTileWorld.class).enemies){
      if(this.x < a.getComponent(Transform.class).position.x + 64 && this.y < a.getComponent(Transform.class).position.y + 64 && this.y > a.getComponent(Transform.class).position.y && this.x > a.getComponent(Transform.class).position.x){
        a.hitpoints--;
        if(a.hitpoints <= 0){
          AsteroidAggression.game.getChild(SideScrollerTileWorld.class).enemies.remove(a);
        }
        AsteroidAggression.game.getChild(SideScrollerTileWorld.class).lasers.remove(this);
      }
    }
  }

  @Override
  public void draw(SpriteBatch b) {
    b.draw(laser,x,y,16,16);
  }
  public void draw(SpriteBatch b,float tox, float toy) {
    b.draw(laser,tox,toy,16,16);
  }

  public float getAngle(Point.Float target) {
    float angle = (float) Math.toDegrees(Math.atan2(target.y - y, target.x - x));

    if(angle < 0){
      angle += 360;
    }

    return angle;
  }
}
