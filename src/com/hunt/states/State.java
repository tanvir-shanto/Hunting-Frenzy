package com.hunt.states;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public abstract class State implements KeyListener, MouseListener {
   private static State currentState = null;

   public static void setState(State state) {
      currentState = state;
   }

   public static State getState() {
      return currentState;
   }

   public abstract void tick();

   public abstract void draw(Graphics var1);
}
