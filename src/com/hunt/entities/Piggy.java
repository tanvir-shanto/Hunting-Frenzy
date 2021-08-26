package com.hunt.entities;

import com.hunt.game.Asset;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Piggy extends Animal {
   private Random rand = new Random();
   private long delay;
   private long lastTime;
   private int run;

   public Piggy(int type) {
      super(type);
      this.width = 64;
      this.height = 48;
      this.health = 5;
      this.lastTime = 0L;
      this.delay = 80L;
      this.run = 0;
      this.dx = -3;
      this.spriteSheet = Asset.piggySprite;
      this.x = 1024 + this.rand.nextInt(280);
      this.y = 542 + this.rand.nextInt(80);
   }

   public void imageUpdate(long currentTime) {
      if (currentTime - this.lastTime >= this.delay) {
         if (this.run == 0) {
            this.image = (BufferedImage)this.spriteSheet.get(0);
         } else if (this.run == 1) {
            this.image = (BufferedImage)this.spriteSheet.get(1);
         } else if (this.run == 2) {
            this.image = (BufferedImage)this.spriteSheet.get(2);
         } else if (this.run == 3) {
            this.image = (BufferedImage)this.spriteSheet.get(3);
         }

         ++this.run;
         if (this.run > 3) {
            this.run = 0;
         }

         this.lastTime = currentTime;
      }

   }

   public void tick() {
      this.x += this.dx;
      if (this.x <= -1 * this.width) {
         this.x = 1024 + this.rand.nextInt(280);
         this.y = 542 + this.rand.nextInt(80);
      }

   }

   public void draw(Graphics g) {
      g.drawImage(this.image, this.x, this.y, this.width, this.height, (ImageObserver)null);
   }
}
