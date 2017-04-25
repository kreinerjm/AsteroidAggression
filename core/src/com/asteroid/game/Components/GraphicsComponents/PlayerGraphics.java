package com.asteroid.game.Components.GraphicsComponents;


import com.asteroid.game.Components.Component;
import com.asteroid.game.Components.InputComponents.PlayerInput;
import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Jacob on 4/22/2017.
 */
public class PlayerGraphics extends Component implements GraphicsComponent {

  public enum State {Idle,Running,Jumping};
  public State state = State.Idle;
  int runningTime = 0;
  int maxTime = 20;

  Texture currentTexture;
  Texture idleR = new Texture("animations/player idle.png");
  Texture idleL = new Texture("animations/player idleL.png");
  Texture[] runningR = new Texture[6];
  Texture[] runningL = new Texture[6];
  Texture[] jumpingR = new Texture[]{new Texture("animations/jump 1.png"),new Texture("animations/jump 2.png")};
  Texture[] jumpingL = new Texture[]{new Texture("animations/jump 1L.png"),new Texture("animations/jump 2L.png")};

  public PlayerGraphics(){
    currentTexture = idleR;
    for(int i = 0; i < 6; i++){
      runningR[i] = new Texture("animations/player frame "+(i+1)+".png");
      runningL[i] = new Texture("animations/player frame "+(i+1)+"L.png");
    }

  }

  @Override
  public void draw(SpriteBatch batch) {
    getParent().getComponent(PlayerInput.class).handleInput();
    Vector2 pos = getParent().getComponent(Transform.class).position;
    switch(state){
      case Idle:{
        if(getParent().getComponent(PlayerInput.class).getCurrentDirection() == Keys.A){
          currentTexture = idleL;
        }
        else {
          currentTexture = idleR;
        }
        break;
      }
      case Running:{
        if(getParent().getComponent(PlayerInput.class).getCurrentDirection() == Keys.A){
          currentTexture = runningL[(runningTime/4)];
        }
        else {
          currentTexture = runningR[runningTime/4];
        }

        if(runningTime < maxTime){
          runningTime++;
        }
        else {
          runningTime = 0;
        }
        break;
      }
      case Jumping:{
        if(getParent().getComponent(PlayerInput.class).getCurrentDirection() == Keys.A){
          currentTexture = jumpingL[(int)(runningTime/10f)];
        }
        else {
          currentTexture = jumpingR[(int)(runningTime/10f)];
        }
      }
    }
    batch.draw(currentTexture,pos.x,pos.y,24,48);
  }

  public void reset(){
    runningTime = 0;
  }

}
