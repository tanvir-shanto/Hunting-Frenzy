package com.hunt.entities;

import com.hunt.game.Asset;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Frog extends Animal {
   private Random rand;
   private long lastTime;
   private long delay;
   private int run_1;
   private int run_2;

   public Frog(int type) {
      super(type);
      this.width = 64;
      this.height = 64;
      this.rand = new Random();
      this.lastTime = 0L;
      this.delay = 100L;
      this.run_1 = 0;
      this.run_2 = 0;
      this.health = 2;
      if (type == 2) {
         this.x = 1024 + this.rand.nextInt(280);
         this.y = 542 + this.rand.nextInt(80);
         this.dx = -2;
         this.spriteSheet = Asset.frogSprite_2;
      } else if (type == 1) {
         this.x = -280 + this.rand.nextInt(200);
         this.y = 542 + this.rand.nextInt(80);
         this.dx = 2;
         this.spriteSheet = Asset.frogSprite_1;
      }

   }

   public void imageUpdate(long currentTime) {
      if (currentTime - this.lastTime >= this.delay) {
         if (this.type == 1) {
            if (this.run_1 % 3 != 0 && this.run_1 % 5 != 0) {
               this.image = (BufferedImage)this.spriteSheet.get(1);
            } else {
               this.image = (BufferedImage)this.spriteSheet.get(0);
            }

            ++this.run_1;
         } else if (this.type == 2) {
            if (this.run_2 % 3 != 0 && this.run_2 % 5 != 0) {
               this.image = (BufferedImage)this.spriteSheet.get(1);
            } else {
               this.image = (BufferedImage)this.spriteSheet.get(0);
            }

            ++this.run_2;
         }

         this.lastTime = currentTime;
      }

   }

   public void tick() {
      this.x += this.dx;
      if (this.type == 2) {
         if (this.x < 0) {
            this.x = 1024 + this.rand.nextInt(280);
         }
      } else if (this.type == 1 && this.x > 1024) {
         this.x = -280 + this.rand.nextInt(200);
      }

   }

   public void draw(Graphics g) {
      g.drawImage(this.image, this.x, this.y, this.width, this.height, (ImageObserver)null);
   }
}
