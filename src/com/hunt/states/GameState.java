package com.hunt.states;

import com.hunt.entities.Animal;
import com.hunt.entities.Bird;
import com.hunt.entities.Bullet;
import com.hunt.entities.Bunny;
import com.hunt.entities.Egg;
import com.hunt.entities.Fowl;
import com.hunt.entities.Frog;
import com.hunt.entities.MedKit;
import com.hunt.entities.Piggy;
import com.hunt.entities.Plane;
import com.hunt.entities.Player;
import com.hunt.entities.Puma;
import com.hunt.game.Asset;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class GameState extends State implements KeyListener {
   public Player player;
   private List<Animal> animals;
   private List<Fowl> fowls;
   private long waveStartTimer;
   private long waveStartTimerDiff;
   private int waveNumber;
   private boolean waveStart;
   private int waveDelay;
   private boolean inGame;
   private Plane plane;

   public GameState() {
      this.player = new Player(Asset.playerSprite);
      this.waveDelay = 2000;
      this.inGame = true;
      this.waveStartTimer = 0L;
      this.waveStartTimerDiff = 0L;
      this.waveStart = true;
      this.waveNumber = 0;
      this.animals = new ArrayList();
      this.fowls = new ArrayList();
   }

   public void tick() {
      if (this.inGame) {
         this.createWave();
         this.player.spriteUpdate(System.currentTimeMillis());
         this.player.tick();
         this.bulletUpdate();
         this.checkCollisions();
         this.animalSpriteUpdate();
         this.animalTick();
         this.fowlSpriteUpdate();
         this.fowlTick();
         this.planeTick();
      }

   }

   public void checkCollisions() {
      this.playerAnimalCollision();
      this.bulletAnimalCollision();
      this.playerEggCollision();
      this.playerMedCollision();
   }

   public void playerAnimalCollision() {
      Rectangle r1 = this.player.getBounds();

      for(int i = 0; i < this.animals.size(); ++i) {
         Animal e = (Animal)this.animals.get(i);
         Rectangle r2 = e.getBounds();
         if (r1.intersects(r2)) {
            this.player.loseLife();
            if (this.player.getDead()) {
               this.inGame = false;
            }
         }
      }

   }

   public void bulletAnimalCollision() {
      List<Bullet> bullets = this.player.getBullets();

      for(int i = 0; i < bullets.size(); ++i) {
         Bullet b = (Bullet)bullets.get(i);
         Rectangle r1 = b.getBounds();

         for(int j = 0; j < this.animals.size(); ++j) {
            Animal e = (Animal)this.animals.get(j);
            Rectangle r2 = e.getBounds();
            if (r1.intersects(r2)) {
               e.hit(b.getRank());
               if (e.isVisible()) {
                  this.animals.remove(j);
                  --j;
               }

               b.setVisible(false);
            }
         }
      }

   }

   public void playerEggCollision() {
      Rectangle r1 = this.player.getBounds();

      for(int i = 0; i < this.fowls.size(); ++i) {
         Fowl f = (Fowl)this.fowls.get(i);
         List<Egg> eggs = f.getEggs();

         for(int j = 0; j < eggs.size(); ++j) {
            Egg e = (Egg)eggs.get(j);
            Rectangle r2 = e.getBounds();
            if (r1.intersects(r2)) {
               e.setDead(true);
               this.player.loseLife();
            }
         }
      }

   }

   public void playerMedCollision() {
      if (this.plane != null) {
         Rectangle r1 = this.player.getBounds();
         if (this.plane.getMedKit() != null) {
            Rectangle r2 = this.plane.getMedKit().getBounds();
            if (r1.intersects(r2)) {
               MedKit med = this.plane.getMedKit();
               if (!med.isDestroyed()) {
                  med.setDestroyed(true);
                  this.player.gainLife();
                  med = null;
               }
            }
         }
      }

   }

   public void createWave() {
      if (this.waveStartTimer == 0L && this.animals.size() == 0 && this.fowls.size() == 0) {
         ++this.waveNumber;
         this.waveStart = false;
         this.waveStartTimer = System.nanoTime();
      } else {
         this.waveStartTimerDiff = (System.nanoTime() - this.waveStartTimer) / 1000000L;
         if (this.waveStartTimerDiff > (long)this.waveDelay) {
            this.waveStart = true;
            this.waveStartTimer = 0L;
            this.waveStartTimerDiff = 0L;
         }
      }

      if (this.waveStart && this.animals.size() == 0 && this.fowls.size() == 0) {
         this.createEnemies();
      }

   }

   public void createEnemies() {
      this.animals.clear();
      this.fowls.clear();
      int i;
      if (this.waveNumber == 1) {
         for(i = 0; i < 4; ++i) {
            this.animals.add(new Frog(1));
         }
      }

      if (this.waveNumber == 2) {
         for(i = 0; i < 3; ++i) {
            this.animals.add(new Frog(1));
         }

         for(i = 0; i < 3; ++i) {
            this.animals.add(new Frog(2));
         }
      }

      if (this.waveNumber == 3) {
         for(i = 0; i < 3; ++i) {
            this.animals.add(new Bunny(1));
         }

         for(i = 0; i < 2; ++i) {
            this.animals.add(new Frog(1));
         }

         for(i = 0; i < 3; ++i) {
            this.animals.add(new Frog(2));
         }

         for(i = 0; i < 1; ++i) {
            this.fowls.add(new Bird(1));
         }

         this.player.setPowerLevel(2);
      }

      if (this.waveNumber == 4) {
         for(i = 0; i < 3; ++i) {
            this.animals.add(new Bunny(2));
         }

         for(i = 0; i < 4; ++i) {
            this.animals.add(new Bunny(1));
         }

         for(i = 0; i < 1; ++i) {
            this.animals.add(new Frog(1));
         }

         for(i = 0; i < 1; ++i) {
            this.animals.add(new Frog(2));
         }
      }

      if (this.waveNumber == 5) {
         for(i = 0; i < 3; ++i) {
            this.animals.add(new Bunny(2));
         }

         for(i = 0; i < 4; ++i) {
            this.animals.add(new Bunny(1));
         }

         for(i = 0; i < 2; ++i) {
            this.animals.add(new Piggy(1));
         }

         for(i = 0; i < 1; ++i) {
            this.animals.add(new Frog(2));
         }

         for(i = 0; i < 2; ++i) {
            this.fowls.add(new Bird(1));
         }

         this.plane = new Plane();
      }

      if (this.waveNumber == 6) {
         this.plane = null;

         for(i = 0; i < 4; ++i) {
            this.animals.add(new Bunny(2));
         }

         for(i = 0; i < 3; ++i) {
            this.animals.add(new Bunny(1));
         }

         for(i = 0; i < 4; ++i) {
            this.animals.add(new Piggy(1));
         }

         for(i = 0; i < 2; ++i) {
            this.animals.add(new Frog(2));
         }

         this.player.setPowerLevel(3);
         this.plane = new Plane();
      }

      if (this.waveNumber == 7) {
         this.plane = null;

         for(i = 0; i < 3; ++i) {
            this.animals.add(new Bunny(2));
         }

         for(i = 0; i < 2; ++i) {
            this.animals.add(new Puma(1));
         }

         for(i = 0; i < 4; ++i) {
            this.animals.add(new Piggy(1));
         }

         for(i = 0; i < 3; ++i) {
            this.fowls.add(new Bird(1));
         }
      }

      if (this.waveNumber == 8) {
         this.inGame = false;
      }

   }

   public void bulletUpdate() {
      List<Bullet> bullets = this.player.getBullets();

      for(int i = 0; i < bullets.size(); ++i) {
         Bullet b = (Bullet)bullets.get(i);
         if (b.isVisible()) {
            b.tick();
         } else {
            bullets.remove(i);
            --i;
         }
      }

   }

   public void animalSpriteUpdate() {
      for(int i = 0; i < this.animals.size(); ++i) {
         Animal a = (Animal)this.animals.get(i);
         a.imageUpdate(System.currentTimeMillis());
      }

   }

   public void animalTick() {
      for(int i = 0; i < this.animals.size(); ++i) {
         Animal e = (Animal)this.animals.get(i);
         if (e.isVisible()) {
            this.animals.remove(i);
            --i;
         } else {
            e.tick();
         }
      }

   }

   public void fowlSpriteUpdate() {
      for(int i = 0; i < this.fowls.size(); ++i) {
         Fowl f = (Fowl)this.fowls.get(i);
         f.imageUpdate(System.currentTimeMillis());
      }

   }

   public void fowlTick() {
      int i;
      Fowl f;
      for(i = 0; i < this.fowls.size(); ++i) {
         f = (Fowl)this.fowls.get(i);
         if (f.isVisible()) {
            this.fowls.remove(i);
            --i;
         } else {
            f.tick();
         }
      }

      for(i = 0; i < this.fowls.size(); ++i) {
         f = (Fowl)this.fowls.get(i);
         List<Egg> eggs = f.getEggs();

         for(int j = 0; j < eggs.size(); ++j) {
            Egg e = (Egg)eggs.get(j);
            if (e.getDead()) {
               eggs.remove(j);
               --j;
            } else {
               e.tick();
            }
         }
      }

   }

   public void planeTick() {
      if (this.plane != null) {
         this.plane.tick();
         if (!this.plane.getMedKit().isDestroyed()) {
            this.plane.getMedKit().tick();
         }
      }

   }

   public void draw(Graphics g) {
      if (this.inGame) {
         this.drawStuffs(g);
      } else {
         this.drawGameOver(g);
      }

   }

   public void drawStuffs(Graphics g) {
      g.drawImage(Asset.background, 0, 0, 1024, 680, (ImageObserver)null);
      this.drawWave(g);
      this.player.draw(g);
      this.drawLife(g);
      this.bulletDraw(g);
      this.drawEnemy(g);
      this.drawPlane(g);
      this.drawRecoveryStatus(g);
   }

   public void drawGameOver(Graphics g) {
      if (this.waveNumber == 8) {
         g.drawImage(Asset.gg, 0, 0, 1024, 680, (ImageObserver)null);
      } else {
         g.drawImage(Asset.gameOver, 0, 0, 1024, 680, (ImageObserver)null);
      }

   }

   public void drawLife(Graphics g) {
      int value = this.player.getLife();

      for(int i = 1; i <= value; ++i) {
         g.drawImage(Asset.life, i * 30, 20, 25, 25, (ImageObserver)null);
      }

   }

   public void bulletDraw(Graphics g) {
      List<Bullet> bullets = this.player.getBullets();

      for(int i = 0; i < bullets.size(); ++i) {
         Bullet b = (Bullet)bullets.get(i);
         if (b.isVisible()) {
            b.draw(g);
         } else {
            bullets.remove(i);
            --i;
         }
      }

   }

   public void drawWave(Graphics g) {
      if (this.waveStartTimer != 0L && this.waveNumber <= 7) {
         g.setFont(new Font("Century Gothic", 0, 50));
         String s = "- W A V E " + this.waveNumber + " -";
         int length = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
         g.setColor(Color.decode("#0A3C47"));
         g.drawString(s, 512 - length / 2, 340);
      }

   }

   public void drawEnemy(Graphics g) {
      for(int i = 0; i < this.animals.size(); ++i) {
         Animal a = (Animal)this.animals.get(i);
         a.draw(g);
      }

      this.drawFowls(g);
   }

   public void drawFowls(Graphics g) {
      int i;
      Fowl f;
      for(i = 0; i < this.fowls.size(); ++i) {
         f = (Fowl)this.fowls.get(i);
         f.draw(g);
      }

      for(i = 0; i < this.fowls.size(); ++i) {
         f = (Fowl)this.fowls.get(i);
         List<Egg> eggs = f.getEggs();

         for(int j = 0; j < eggs.size(); ++j) {
            Egg e = (Egg)eggs.get(j);
            e.draw(g);
         }
      }

   }

   public void drawPlane(Graphics g) {
      if (this.plane != null) {
         this.plane.draw(g);
         if (!this.plane.getMedKit().isDestroyed()) {
            this.plane.getMedKit().draw(g);
         }
      }

   }

   public void drawRecoveryStatus(Graphics g) {
      if (this.player.isRecovering()) {
         g.setColor(Color.decode("#F7490D"));
         g.setFont(new Font("Century Gothic", 1, 24));
         g.drawString("RECOVERING!!", 447, 40);
      }

   }

   public void keyPressed(KeyEvent e) {
      this.player.keyPressed(e);
   }

   public void keyReleased(KeyEvent e) {
      this.player.keyReleased(e);
   }

   public void keyTyped(KeyEvent e) {
   }

   public void mouseClicked(MouseEvent arg0) {
   }

   public void mouseEntered(MouseEvent arg0) {
   }

   public void mouseExited(MouseEvent arg0) {
   }

   public void mousePressed(MouseEvent arg0) {
   }

   public void mouseReleased(MouseEvent arg0) {
   }
}
