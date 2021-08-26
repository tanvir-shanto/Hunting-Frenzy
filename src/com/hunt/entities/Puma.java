package com.hunt.entities;

import com.hunt.game.Asset;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Puma extends Animal {
   private Random rand = new Random();
   private long lastTime;
   private long delay;
   private int run;

   public Puma(int type) {
      super(type);
      this.width = 128;
      this.height = 64;
      this.health = 8;
      this.lastTime = 0L;
      this.delay = 80L;
      this.run = 0;
      this.dx = 5;
      this.spriteSheet = Asset.pumaSprite;
      this.x = -280 + this.rand.nextInt(200);
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
         } else if (this.run == 4) {
            this.image = (BufferedImage)this.spriteSheet.get(4);
         } else if (this.run == 5) {
            this.image = (BufferedImage)this.spriteSheet.get(5);
         } else if (this.run == 6) {
            this.image = (BufferedImage)this.spriteSheet.get(6);
         } else if (this.run == 7) {
            this.image = (BufferedImage)this.spriteSheet.get(7);
         }

         ++this.run;
         if (this.run > 7) {
            this.run = 0;
         }

         this.lastTime = currentTime;
      }

   }

   public void tick() {
      this.x += this.dx;
      if (this.x > 1024) {
         this.x = -280 + this.rand.nextInt(200);
         this.y = 542 + this.rand.nextInt(80);
      }

   }

   public void draw(Graphics g) {
      g.drawImage(this.image, this.x, this.y, this.width, this.height, (ImageObserver)null);
   }
}
