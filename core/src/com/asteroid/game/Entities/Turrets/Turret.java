package com.asteroid.game.Entities.Turrets;

import com.asteroid.game.AsteroidAggression;
import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.asteroid.game.Entities.Characters.Alien;
import com.asteroid.game.Entities.Entity;
import com.asteroid.game.Entities.Worlds.SideScrollerTileWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Created by Jacob on 4/24/2017.
 */
public class Turret extends Entity {

  Texture turretBase = new Texture("turretBase.png");
  Texture turretGun = new Texture("turretGun.png");

  int tileX, tileY;
  float currentDirection = 0;

  boolean shot = false;
  int shotTime = 0;

  public Turret(int x, int y){
    tileX = x;
    tileY = y;
  }

  @Override
  public void draw(SpriteBatch b) {
    updateRotation();
    b.draw(turretBase,tileX*16,tileY*16,32,16);
    //SpriteBatch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    b.draw(turretGun, tileX*16,tileY*16+16,16,8,32,16,1,1,currentDirection,0,0,32,16,false,false);
    //currentDirection += 2*Math.PI/50;
  }

  public void updateRotation(){
    SideScrollerTileWorld world = AsteroidAggression.game.getChild(SideScrollerTileWorld.class);
    if(world.enemies.size()>0){
      Alien target = world.getClosestEnemy(tileX*16,tileY*16);
      Point.Float p1 = new Point.Float(tileX*16,tileY*16);
      Point.Float p2 = new Point.Float(target.getComponent(Transform.class).position.x,target.getComponent(Transform.class).position.y);
      currentDirection = getAngle(p2);
      if(!shot){

      }
    }
  }

  public float getAngle(Point.Float target) {
    float angle = (float) Math.toDegrees(Math.atan2(target.y - tileY*16, target.x - tileX*16));

    if(angle < 0){
      angle += 360;
    }

    return angle;
  }
}
