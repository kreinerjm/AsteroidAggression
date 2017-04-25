package com.asteroid.game.Components.InputComponents;

import com.asteroid.game.AsteroidAggression;
import com.asteroid.game.Components.Component;
import com.asteroid.game.Components.GraphicsComponents.PlayerGraphics;
import com.asteroid.game.Components.GraphicsComponents.PlayerGraphics.State;
import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.asteroid.game.Entities.Characters.Player;
import com.asteroid.game.Entities.Games.Game;
import com.asteroid.game.Entities.Minerals.MineralNode;
import com.asteroid.game.Entities.Turrets.Turret;
import com.asteroid.game.Entities.Worlds.SideScrollerTileWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

/**
 * Created by Jacob on 4/22/2017.
 */
public class PlayerInput extends Component implements InputComponent {
  int currentDirection;
  int jumpTime = 0;
  boolean jump;
  boolean inAir;
  boolean falling;
  boolean wasFalling;
  boolean onGround = false;
  boolean justLanded = false;
  int fallTime = 0;
  int currentTime = 0;
  int maxTime = 6;
  int miningCount = 0;
  Sound jumpLand = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/JumpLand.mp3"));
  Sound jumpWhoosh = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/JumpWhoosh.mp3"));
  Sound construction = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/Construction.mp3"));
  ArrayList<Sound> footstepSounds = new ArrayList<Sound>();
  ArrayList<Sound> miningSounds = new ArrayList<Sound>();
  Sound collectionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/MineralCollection.mp3"));

  public PlayerInput(){
    for(int i = 1; i < 7; i++){
      footstepSounds.add(Gdx.audio.newSound(Gdx.files.internal("sounds/effects/Footsteps"+i+".mp3")));
    }
    for(int i = 1; i < 7; i++){
      miningSounds.add(Gdx.audio.newSound(Gdx.files.internal(("sounds/effects/Mining"+i+".mp3"))));
    }
  }

  @Override
  public void handleInput() {
    Transform t = getParent().getComponent(Transform.class);
    float speed = Player.speed;
    SideScrollerTileWorld world = AsteroidAggression.game.getChild(SideScrollerTileWorld.class);


    if(Gdx.input.isKeyJustPressed(Keys.A)){
      if(currentDirection != Keys.A) {
        currentDirection = Keys.A;
        getParent().getComponent(PlayerGraphics.class).reset();

      }
    }
    if(Gdx.input.isKeyJustPressed(Keys.D)){
      if(currentDirection != Keys.D) {
        currentDirection = Keys.D;
        getParent().getComponent(PlayerGraphics.class).reset();
      }
    }

    boolean right = Gdx.input.isKeyPressed(Keys.D);
    boolean left = Gdx.input.isKeyPressed(Keys.A);
    boolean none = !right && !left;

    float suggestedX;

    if(right){
      suggestedX = t.position.x + speed + 24;
      if(world.getTileAt(suggestedX,t.position.y)==1 || world.getTileAt(suggestedX,t.position.y+16)==1 || world.getTileAt(suggestedX,t.position.y+32)==1){

      }
      else {
        t.position.x += speed;
      }
    }
    if(left){
      suggestedX = t.position.x - speed;
      if(world.getTileAt(suggestedX,t.position.y)==1 || world.getTileAt(suggestedX,t.position.y+16)==1 || world.getTileAt(suggestedX,t.position.y+32)==1){

      }
      else {
        t.position.x -= speed;
      }
    }

    if(none){
      currentTime = 0;
      getParent().getComponent(PlayerGraphics.class).state = State.Idle;
    }
    else if(!falling && !jump){
      getParent().getComponent(PlayerGraphics.class).state = State.Running;

      if(currentTime == 0){
        getRandomWalkSound().play(.5f);
        currentTime++;
      }
      else if(currentTime < maxTime){
        currentTime++;
      }
      else {
        currentTime = 0;
      }
    }

    if(t.position.x > world.worldWidth*world.tileSize){
      t.position.x %= world.worldWidth*world.tileSize;
    }
    if(t.position.x < 0){
      t.position.x = world.worldWidth*world.tileSize + t.position.x;
    }

    if(jump){
      t.position.y += 15 - jumpTime;
      if(jumpTime < 15){
        jumpTime++;
      }
      else{
        jumpTime = 0;
        jump = false;
        falling = true;
      }
    }
    else if(!jump){
      float suggestedY = t.position.y - fallTime;
      if(world.getTileAt(t.position.x,suggestedY)==1 || world.getTileAt(t.position.x + 16,suggestedY) == 1){//on the ground
        //System.out.println("Colliding below");
        if(falling = true){
          falling = false;
          justLanded = true;
         // System.out.println("just landed");
        }

      }
      else {//falling
        falling = true;
       // System.out.println("falling!");
        t.position.y -= fallTime;
      }
      if(falling && fallTime < 10){
        fallTime++;
      }
    }

    if(wasFalling && !falling){
      if(fallTime >= 9){
        jumpLand.play(.5f);
      }
      fallTime = 0;

    }
    wasFalling = falling;

    if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
      if(!jump){
        jump = true;
        jumpWhoosh.play(.25f);
        getParent().getComponent(PlayerGraphics.class).state = State.Jumping;
        getParent().getComponent(PlayerGraphics.class).reset();
      }
    }

    if(Gdx.input.isButtonPressed(Buttons.RIGHT)){

      Game game = AsteroidAggression.game;
      OrthographicCamera cam = game.getCamera();
      int w = Gdx.graphics.getWidth();
      int h = Gdx.graphics.getHeight();
      float x = Gdx.input.getX();
      float y = h - Gdx.input.getY();

      System.out.println("mouse x : "+((cam.position.x) + (x/(float)w)*960f - 960f/2f));
      System.out.println("player x : "+getParent().getComponent(Transform.class).position.x);
      System.out.println("mouse y : "+((cam.position.y) + (y/(float)h)*640f - 640f/2f));
      System.out.println("player y : "+getParent().getComponent(Transform.class).position.y);

      float relativeX = ((cam.position.x) + (x/(float)w)*960f - 960f/2f);
      float relativeY = ((cam.position.y) + (y/(float)h)*640f - 640f/2f);
      boolean hasMineral = false;
      for(MineralNode m : world.getMineralNodes()){
        if(m.contains(relativeX,relativeY)){
          hasMineral = true;
          System.out.println("Mining the node!");
          if(miningCount < 100){
            if(miningCount%50 == 0){
              getRandomMiningSound().play(.5f);
            }
            miningCount++;
          }
          else{
            if(m.metalCount > 1){
              Player.metalCount++;
              miningCount = 0;
               collectionSound.play(.5f);
              m.metalCount--;
            }
            else {
              Player.metalCount++;
              miningCount = 0;
               collectionSound.play(.5f);
              world.getMineralNodes().remove(m);
            }

          }

        }
      }
      if(!hasMineral){
        if(world.getTileAt(relativeX,relativeY) == 0 && world.getTileAt(relativeX+16,relativeY) == 0 && world.getTileAt(relativeX,relativeY-16) == 1 && world.getTileAt(relativeX+16,relativeY-16) == 1){
          Vector2 pos = getIntsFor(new Vector2(relativeX,relativeY));
          if(Player.metalCount >= 5){
            world.turrets.add(new Turret((int)pos.x,(int)pos.y));
            Player.metalCount -= 5;
            construction.play(.5f);
          }
        }
      }

    }
  }

  public int getCurrentDirection(){
    return currentDirection;
  }

  public boolean isLeft(){
    return currentDirection == Keys.A;
  }

  public Sound getRandomWalkSound(){
    int rand = (int)((Math.random()*6));
    return footstepSounds.get(rand);
  }

  public Sound getRandomMiningSound(){
    int rand = (int)((Math.random()*6));
    return miningSounds.get(rand);
  }

  public Vector2 getIntsFor(Vector2 v){
    return new Vector2((int)(v.x/16),(int)(v.y/16));
  }

  public Vector2[][] getOccupyingTiles(){
    Transform t = getParent().getComponent(Transform.class);
    Vector2[][] toRet;
    int l,w;
    l = t.position.x%16 > 8 ? 3 : 2;
    w = t.position.y%16!=0 ? 4 : 3;
    toRet = new Vector2[l][w];
    for(int i = 0; i < l; i++) {
      for(int j = 0; j < w; j++){
        toRet[i][j] = new Vector2(t.position.x + i*16,t.position.y + j * 16);
      }
    }
    return toRet;
  }

  private void getCollisionsBelow(){
    Vector2[][] o = getOccupyingTiles();
  }

  private void getCollisionsLeft(){
    boolean blocked = false;
    SideScrollerTileWorld world = getParent().getParent().getChild(SideScrollerTileWorld.class);
    Transform t = getParent().getComponent(Transform.class);
    Vector2[][] o = getOccupyingTiles();
    for(int i = 0; i < o.length; i++){
      for(int j = 0; j < o[0].length; j++) {
        if (world.getTileAt(o[i][j].x, o[i][j].y) == 1) {
          System.out.println("BLOCKED");
          while (world.getTileAt(o[i][j].x, o[i][j].y) == 1) {
            t.position.x++;
            o = getOccupyingTiles();
          }
          blocked = true;
        }
      }
    }
    if(blocked){

    }
    else {

    }

  }

  private void getCollisionsRight(){
    Transform t = getParent().getComponent(Transform.class);
    Vector2[][] o = getOccupyingTiles();
    float diff = o[o.length-1][0].x+16- t.position.x;
    if(diff <= 5){
      t.position.x += diff;
    }
    else{
      t.position.x += 5;
    }
  }

}
