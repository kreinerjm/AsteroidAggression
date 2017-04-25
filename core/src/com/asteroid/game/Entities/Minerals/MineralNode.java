package com.asteroid.game.Entities.Minerals;

import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.asteroid.game.Entities.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Jacob on 4/24/2017.
 */
public class MineralNode extends Entity{

  Texture t = new Texture("MetalNode.png");

  public int metalCount = 5;

  public MineralNode(){
    addComponent(new Transform());
  }

  public boolean contains(float x, float y){
    Vector2 pos = getComponent(Transform.class).position;
    return x > pos.x && x < pos.x + 16 && y > pos.y && y < pos.y + 32;
  }

  @Override
  public void draw(SpriteBatch b) {
    Vector2 pos = getComponent(Transform.class).position;
    b.draw(t,pos.x,pos.y,16,32);
    //System.out.println("drawing metal node at "+pos.x+","+pos.y);
  }
  public void draw(SpriteBatch b, float x, float y) {
    b.draw(t,x,y,16,32);
    System.out.println("drawing metal node at "+x+","+y);
  }

}
