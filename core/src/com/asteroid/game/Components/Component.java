package com.asteroid.game.Components;

import com.asteroid.game.Entities.Entity;

/**
 * Created by Jacob on 4/22/2017.
 */
public abstract class Component {

  Entity parent;

  public Entity getParent() {
    return parent;
  }

  public void setParent(Entity set) {
    parent = set;
  }
}

