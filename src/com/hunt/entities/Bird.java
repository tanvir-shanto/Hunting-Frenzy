package com.hunt.entities;

import com.hunt.game.Asset;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Bird extends Fowl {
   private Random rand = new Random();
   private long delay;
   private long lastTime;
   private int run;
   private long eggTimerDiff;
   private long eggDelay;
   private long eggLastTime;

   public Bird(int type) {
      super(type);
      this.width = 64;
      this.height = 64;
      this.health = 3;
      this.lastTime = 0L;
      this.delay = 120L;
      this.eggDelay = 2000L;
      this.eggLastTime = 0L;
      this.run = 0;
      this.dx = 3;
      this.spriteSheet = Asset.birdSprite;
      this.x = -480 + this.rand.nextInt(380);
      this.y = 32 + this.rand.nextInt(96);
   }

   public void imageUpdate(long currentTime) {
      if (currentTime - this.lastTime >= this.delay) {
         if (this.run == 0) {
            this.image = (BufferedImage)this.spriteSheet.get(0);
         } else if (this.run == 1) {
            this.image = (BufferedImage)this.spriteSheet.get(1);
         } else if (this.run == 2) {
            this.image = (BufferedImage)this.spriteSheet.get(2);
         }

         ++this.run;
         if (this.run > 2) {
            this.run = 0;
         }

         this.lastTime = currentTime;
      }

   }

   public void tick() {
      this.x += this.dx;
      if (this.x > 1024 + 2 * this.width) {
         this.isDead = true;
      }

      this.eggTimerDiff = System.currentTimeMillis() - this.eggLastTime;
      if (this.eggTimerDiff >= this.eggDelay) {
         this.eggs.add(new Egg(this.x + this.width, this.y + this.height));
         this.eggLastTime = System.currentTimeMillis();
         this.eggTimerDiff = 0L;
      }

   }

   public void draw(Graphics g) {
      g.drawImage(this.image, this.x, this.y, this.width, this.height, (ImageObserver)null);
   }
}
