package com.asteroid.game.Entities.Worlds;

import com.asteroid.game.AsteroidAggression;
import com.asteroid.game.Components.InputComponents.PlayerInput;
import com.asteroid.game.Components.PhysicsComponents.Transform;
import com.asteroid.game.Entities.Characters.Alien;
import com.asteroid.game.Entities.Characters.Player;
import com.asteroid.game.Entities.Minerals.MineralNode;
import com.asteroid.game.Entities.Projectiles.Laser;
import com.asteroid.game.Entities.Turrets.Turret;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jacob on 4/22/2017.
 */
public class SideScrollerTileWorld extends World{
  public final int worldWidth = 128;
  public final int worldHeight = 64;
  public final int tileSize = 16;

  int[][] tiles = new int[worldWidth][worldHeight];
  int[][] sin = new int[worldWidth][1];
  int[] height = new int[worldWidth];
  Texture asteroid = new Texture("Asteroid.png");

  CopyOnWriteArrayList<MineralNode> mineralNodes = new CopyOnWriteArrayList<MineralNode>();
  public CopyOnWriteArrayList<Turret> turrets = new CopyOnWriteArrayList<Turret>();
  public CopyOnWriteArrayList<Alien> enemies = new CopyOnWriteArrayList<Alien>();
  public CopyOnWriteArrayList<Laser> lasers = new CopyOnWriteArrayList<Laser>();

  public SideScrollerTileWorld(){
    createWorld();
  }

  public void draw(SpriteBatch batch){

    OrthographicCamera cam = AsteroidAggression.game.getCamera();

    float x = cam.position.x;
    float y = cam.position.y;
    float effectiveViewportWidth = cam.viewportWidth*cam.zoom;
    float effectiveViewportHeight = cam.viewportHeight*cam.zoom;

    float furthestLeft = x - effectiveViewportWidth/2;
    float furthestDown = y - effectiveViewportHeight/2;
    for(int i = (int)(furthestLeft/tileSize)-1; i < (int)(furthestLeft/tileSize) + effectiveViewportWidth/tileSize + 1; i++){
      for(int j = (int)(furthestDown/tileSize)-1; j < (int)(furthestDown/tileSize) + effectiveViewportHeight/tileSize + 1; j++){
        if(j < worldHeight) {
          if(i < 0){
              if(tiles[(i+worldWidth)%worldWidth][j] == 1) {
              batch.draw(asteroid,i*tileSize,j*tileSize,tileSize,tileSize);
            }
          } else if (i >= worldWidth) {
            if(tiles[i%worldWidth][j] == 1) {
              batch.draw(asteroid,i*tileSize,j*tileSize,tileSize,tileSize);
            }

          } else {
            if(tiles[i][j]==1){
              batch.draw(asteroid,i*tileSize,j*tileSize,tileSize,tileSize);
            }
          }
        }
      }
    }

    for(MineralNode m : mineralNodes){
      if(AsteroidAggression.game.getCamera().position.x - 960/2 < 0)
      {
        System.out.println("Seam is to the left");
        if(m.getComponent(Transform.class).position.x > 8*128){
          m.draw(batch, m.getComponent(Transform.class).position.x-16*128,m.getComponent(Transform.class).position.y);
          System.out.println("drawing node to left of seam");
        }
        else {
          m.draw(batch);
        }
      }
      else if(AsteroidAggression.game.getCamera().position.x + 960/2 > 16*128){
        System.out.println("Seam is to the right");
        if(m.getComponent(Transform.class).position.x < 8*128){
          m.draw(batch, m.getComponent(Transform.class).position.x+16*128,m.getComponent(Transform.class).position.y);
          System.out.println("drawing node to right of seam");
        }
        else {
          m.draw(batch);
        }
      }
      else{
        m.draw(batch);
      }

    }

    for(Laser l : lasers){
      l.update();
      if(AsteroidAggression.game.getCamera().position.x - 960/2 < 0)
      {
        System.out.println("Seam is to the left");
        if(l.x > 8*128){
          l.draw(batch, l.x-16*128,l.y);
          System.out.println("drawing node to left of seam");
        }
        else {
          l.draw(batch);
        }
      }
      else if(AsteroidAggression.game.getCamera().position.x + 960/2 > 16*128){
        System.out.println("Seam is to the right");
        if(l.x < 8*128){
          l.draw(batch, l.x+16*128,l.y);
          System.out.println("drawing node to right of seam");
        }
        else {
          l.draw(batch);
        }
      }
      else{
        l.draw(batch);
      }
      l.draw(batch);
    }

    for(Turret t : turrets){
      if(AsteroidAggression.game.getCamera().position.x - 960/2 < 0)
      {
        System.out.println("Seam is to the left");
        if(t.tileX*16 > 8*128){
          t.draw(batch, t.tileX*16-16*128,t.tileY*16);
          System.out.println("drawing node to left of seam");
        }
        else {
          t.draw(batch);
        }
      }
      else if(AsteroidAggression.game.getCamera().position.x + 960/2 > 16*128){
        System.out.println("Seam is to the right");
        if(t.tileX*16 < 8*128){
          t.draw(batch, t.tileX*16+16*128,t.tileY*16);
          System.out.println("drawing node to right of seam");
        }
        else {
          t.draw(batch);
        }
      }
      else{
        t.draw(batch);
      }
    }

    for(Alien a : enemies){
      if(AsteroidAggression.game.getCamera().position.x - 960/2 < 0)
      {
        System.out.println("Seam is to the left");
        if(a.getComponent(Transform.class).position.x > 8*128){
          a.draw(batch, a.getComponent(Transform.class).position.x-16*128,a.getComponent(Transform.class).position.y);
          System.out.println("drawing node to left of seam");
        }
        else {
          a.draw(batch);
        }
      }
      else if(AsteroidAggression.game.getCamera().position.x + 960/2 > 16*128){
        System.out.println("Seam is to the right");
        if(a.getComponent(Transform.class).position.x < 8*128){
          a.draw(batch, a.getComponent(Transform.class).position.x+16*128,a.getComponent(Transform.class).position.y);
          System.out.println("drawing node to right of seam");
        }
        else {
          a.draw(batch);
        }
      }
      else{
        a.draw(batch);
      }
    }
  }

  public int[][] getTiles(){
    return tiles;
  }

  private void createWorld(){
    fillSin();
    fillFlat();
    createCraters();
    average();
    placeMineralNodes();
  }

  public void placeMineralNodes(){
    for(int i = 0; i < 3; i++){
      int randX = (int)(Math.random()*worldWidth);
      int y = height[randX]+1;

      Transform t = new Transform();
      t.position = new Vector2(randX*16,y*16);
      MineralNode m = new MineralNode();
      m.getComponent(Transform.class).position = new Vector2(randX*16,y*16);
      mineralNodes.add(m);

    }
  }

  public CopyOnWriteArrayList<MineralNode> getMineralNodes(){
    return mineralNodes;
  }

  public void fillFlat(){
    for(int i = 0; i < worldWidth; i++){
      for(int j = worldHeight/2; j > 0; j--){
        tiles[i][j] = 1;
      }
    }
  }

  public void fillSin(){
    for(int i = 0; i < sin.length; i++){
      sin[i][0] = worldHeight/2 + (int)Math.sin((i/sin.length)*2*Math.PI);
    }
  }

  public void createCraters(){
    for(int i = 0; i < 18; i++){
      int randX = (int)(Math.random()*worldWidth);
      System.out.println("X:"+randX);
      int randWidth = (int)(10.0 + Math.random() * 15);
      int randDepth = (int)(5.0 + Math.random() * 10);
      System.out.println("depth:"+randDepth);

      createCrater(randX,randWidth,randDepth);

    }
  }

  public Alien getClosestEnemy(float x,float y){
    float shortest = Float.MAX_VALUE;
    int index = -1;
    int shortestIndex = -1;
    for(Alien a : enemies){
      index++;
      Point2D p1 = new Point2D.Float(x,y);
      Point2D p2 = new Point2D.Float(a.getComponent(Transform.class).position.x,a.getComponent(Transform.class).position.y);
      if(p1.distance(p2) < shortest){
        shortest = (float)p1.distance(p2);
        shortestIndex = index;
      }
    }
    return enemies.get(shortestIndex);
  }

  public void createCrater(int x, int w, int d){
    int height = worldHeight/2 - w/2;

    for(int y = height; y < worldHeight; y++){
      tiles[x][y] = 0;
    }
    for(int i = 0; i < w/2; i++){
      height = worldHeight/2 - (w/2 - i) ;
      for(int y = height; y < worldHeight; y++){
        tiles[((i+x+1)%worldWidth)][y] = 0;
      }
    }
    for(int i = 0; i < w/2; i++){
      height = worldHeight/2 - (w/2 - i);
      for(int y = height; y < worldHeight; y++){
        if(x - i - 1 < 0){
          tiles[(x - i - 1) + worldWidth][y] = 0;
        }
        else {
          tiles[x - i - 1][y] = 0;
        }
      }
    }
  }

  public void average(){
    for(int i = 0; i < tiles.length; i++){
      int h = tiles[0].length - 1;
      for(;h > 0;h--){
        if(tiles[i][h] == 1){
          break;
        }
      }
      int newHeight = (h + sin[i][0])/2;
      for(int j = newHeight; j > 0; j--){
        tiles[i][j] = 1;
        height[i] = newHeight;
      }
    }
  }

  public Vector2 contains(float x, float y){//We want to know the int representation on the tile map of the float pair

    int intX = (int)(x/(tileSize-1))%worldWidth;
    int intY = (int)(y/(tileSize-1));
    if(tiles[intX][intY] == 1){
      return new Vector2(intX,intY);
    }
    else {
      return null;
    }
  }

  public int getTileAt(float x, float y){
    int xIndex;
    int yIndex;
    if(x < 0){
      xIndex = (int)((x%worldWidth)+worldWidth);
    }
    else if(x >= worldWidth*tileSize){
      xIndex = (int)(x%(worldWidth*tileSize));
    }
    else {
      xIndex = (int)x;
    }

    xIndex /= 16;
    yIndex = (int)y/16;

    return tiles[xIndex][yIndex];

  }

  public Vector2 getIntsFor(Vector2 v){
    return new Vector2((int)v.x/tileSize,(int)v.y/tileSize);
  }





}
