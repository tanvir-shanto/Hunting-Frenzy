package com.hunt.entities;

import com.hunt.game.Asset;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Egg {
   private int x;
   private int y;
   private int width;
   private int height;
   private BufferedImage image;
   private boolean isDead;
   private int dy;

   public Egg(int x, int y) {
      this.x = x;
      this.y = y;
      this.width = 16;
      this.height = 16;
      this.isDead = false;
      this.dy = 4;
      this.image = Asset.egg;
   }

   public void tick() {
      this.y += this.dy;
      if (this.y > 680 - this.height) {
         this.isDead = true;
      }

   }

   public void draw(Graphics g) {
      g.drawImage(this.image, this.x, this.y, this.width, this.height, (ImageObserver)null);
   }

   public boolean getDead() {
      return this.isDead;
   }

   public Rectangle getBounds() {
      return new Rectangle(this.x, this.y, this.width, this.height);
   }

   public void setDead(boolean b) {
      this.isDead = b;
   }
}
