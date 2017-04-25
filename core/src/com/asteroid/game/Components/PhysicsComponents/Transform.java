package com.asteroid.game.Components.PhysicsComponents;

import com.asteroid.game.Components.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Jacob on 4/22/2017.
 */
public class Transform extends Component {

  public Vector2 position;
  public Vector2 velocity;
  public Vector2 acceleration;

  public Transform(){
    position = new Vector2();
    velocity = new Vector2();
    acceleration = new Vector2(0,-2);
  }

  public Vector2 getPosition() {
    return position;
  }

  public Vector2 getVelocity() {
    return velocity;
  }

  public Vector2 getAcceleration() {
    return acceleration;
  }

  public void setPosition(Vector2 pos){
    position = pos;
  }

  public void setVelocity(Vector2 vel){
    velocity = vel;
  }

  public void setAcceleration(Vector2 acc){
    acceleration = acc;
  }
}
