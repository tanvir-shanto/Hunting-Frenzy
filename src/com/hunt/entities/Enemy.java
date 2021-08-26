package com.hunt.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Enemy {
   protected int x;
   protected int y;
   protected int dx;
   protected int width;
   protected int height;
   protected int type;
   protected List<BufferedImage> spriteSheet;
   protected BufferedImage image;
   protected int health;
   protected boolean isDead;

   public Enemy(int type) {
      this.type = type;
   }

   public Rectangle getBounds() {
      return new Rectangle(this.x, this.y, this.width, this.height);
   }

   public int getType() {
      return this.type;
   }

   public void hit(int num) {
      this.health -= num;
      if (this.health <= 0) {
         this.isDead = true;
      }

   }

   public boolean isVisible() {
      return this.isDead;
   }

   public abstract void imageUpdate(long var1);

   public abstract void tick();

   public abstract void draw(Graphics var1);
}
