package com.asteroid.game.Entities.Games;

import com.asteroid.game.Entities.Characters.Alien;
import com.asteroid.game.Entities.Characters.Player;
import com.asteroid.game.Entities.Worlds.SideScrollerTileWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import javafx.geometry.Side;

/**
 * Created by Jacob on 4/22/2017.
 */
public class MinerGame extends Game{

  Player player;
  SideScrollerTileWorld world;
  Texture metalIcon = new Texture("Metal Icon.png");
  Music explore, backgroundTexture, fight;
  Music currentSong;
  Sound alienWarning1;
  Sound alienWarning2;
  boolean shouldSpawnEnemies = false;
  int numEnemies = 5;

  public MinerGame(){

    alienWarning1 = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/AlienWarning1.mp3"));
    alienWarning2 = Gdx.audio.newSound(Gdx.files.internal("sounds/effects/AlienWarning2.mp3"));
    explore = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/Asteroid_Gameplay-Explore-1.mp3"));
    explore.setVolume(.5f);
    fight = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/Asteroid_Gameplay-Fight.mp3"));
    fight.setVolume(.5f);
    backgroundTexture = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/Background Texture.mp3"));
    backgroundTexture.setVolume(.5f);
    currentSong = explore;
    currentSong.play();
    world = new SideScrollerTileWorld();
    player = new Player();
    addChild(world);
    addChild(player);
  }

  @Override
  public void draw(SpriteBatch batch) {
    if(!currentSong.isPlaying()){
      if(currentSong.equals(explore)){
        currentSong = backgroundTexture;
      }
      else if(currentSong.equals(backgroundTexture)){
        currentSong = fight;
        boolean r = Math.random() > .5f;
        if(r){
          alienWarning1.play();
        }
        else {
          alienWarning2.play();
        }
        shouldSpawnEnemies = true;
      }
      else {
        currentSong = explore;
      }
      currentSong.play();
    }
    else if(currentSong.equals(explore) || currentSong.equals(backgroundTexture)){
      if(world.getMineralNodes().size() == 0){
        boolean r = Math.random() > .5f;
        if(r){
          alienWarning1.play();
        }
        else {
          alienWarning2.play();
        }
        currentSong = fight;
        currentSong.play();
        for(int i = 0; i < numEnemies; i++){
          getChild(SideScrollerTileWorld.class).enemies.add(new Alien());
        }
      }
    }
    //batch.draw(background,-500,-500);
    world.draw(batch);
    player.draw(batch);
    BitmapFont font = new BitmapFont();
    batch.draw(metalIcon,getCamera().position.x - 960/2,getCamera().position.y + 640/2 - 16,16,16);
    font.draw(batch,"= "+Player.metalCount,getCamera().position.x - 960/2 + 20,getCamera().position.y + 640/2 - 2);
    updateCamera();
  }

  @Override
  public void tick() {
  }

  private float peak(float n){
    if(n < -10f) return -10f;
    else return n;
  }
}
