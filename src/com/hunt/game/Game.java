package com.hunt.game;

import com.hunt.states.GameState;
import com.hunt.states.MenuState;
import com.hunt.states.State;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable {
   private static final long serialVersionUID = 1L;
   public static final int WIDTH = 1024;
   public static final int HEIGHT = 680;
   private boolean running = false;
   private Thread thread;
   private static State gameState;
   private State menuState;

   public Game() {
      this.setPreferredSize(new Dimension(1024, 680));
      this.setMaximumSize(new Dimension(1024, 680));
      this.setMinimumSize(new Dimension(1024, 680));
      this.setFocusable(true);
      this.requestFocus();
      Asset.init();
      gameState = new GameState();
      this.menuState = new MenuState();
      this.addKeyListener(gameState);
      this.addMouseListener(this.menuState);
      State.setState(this.menuState);
      this.start();
   }

   public synchronized void start() {
      if (!this.running) {
         this.running = true;
         this.thread = new Thread(this);
         this.thread.start();
      }
   }

   public synchronized void stop() {
      if (this.running) {
         this.running = false;

         try {
            this.thread.join();
         } catch (InterruptedException var2) {
            var2.printStackTrace();
            System.exit(1);
         }

      }
   }

   public void run() {
      int fps = 60;
      double timePerTick = (double)(1000000000 / fps);
      double delta = 0.0D;
      long lastTime = System.nanoTime();
      long timer = 0L;
      int ticks = 0;

      while(this.running) {
         long now = System.nanoTime();
         delta += (double)(now - lastTime);
         timer += now - lastTime;
         lastTime = now;
         if (delta >= timePerTick) {
            this.tick();
            ++ticks;
            delta -= timePerTick;
         }

         this.repaint();
         if (timer >= 1000000000L) {
            System.out.println("The fps is: " + ticks);
            ticks = 0;
            timer = 0L;
         }
      }

   }

   public void tick() {
      if (State.getState() != null) {
         State.getState().tick();
      }

   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (State.getState() != null) {
         State.getState().draw(g);
      }

   }

   public static State getGameState() {
      return gameState;
   }
}
