package com.hunt.entities;

import com.hunt.game.Asset;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Plane {
   private int x = 1074;
   private int y = 64;
   private int dx = -4;
   private int width = 100;
   private int height = 55;
   private BufferedImage image;
   private MedKit medKit;
   private long timerDiff;
   private long lastTime;
   private long delay;

   public Plane() {
      this.image = Asset.plane;
      this.lastTime = 0L;
      this.delay = 2500L;
   }

   public void tick() {
      if (this.x > -this.width) {
         this.x += this.dx;
         this.timerDiff = System.currentTimeMillis() - this.lastTime;
         if (this.timerDiff >= this.delay) {
            this.medKit = new MedKit(this.x, this.y + this.height);
            this.lastTime = System.currentTimeMillis();
            this.timerDiff = 0L;
         }
      }

   }

   public void draw(Graphics g) {
      if (this.x > -this.width) {
         g.drawImage(this.image, this.x, this.y, this.width, this.height, (ImageObserver)null);
      }

   }

   public MedKit getMedKit() {
      return this.medKit;
   }
}
