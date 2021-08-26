package com.hunt.entities;

import com.hunt.game.Asset;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class MedKit {
   private int x;
   private int y;
   private int dy;
   private int width;
   private int height;
   private BufferedImage image;
   private boolean destroyed;

   public MedKit(int x, int y) {
      this.x = x;
      this.y = y;
      this.width = 32;
      this.height = 32;
      this.dy = 4;
      this.image = Asset.medKit;
   }

   public void tick() {
      this.y += this.dy;
      if (this.y > 680 - 2 * this.height) {
         this.dy = 0;
      }

   }

   public void draw(Graphics g) {
      g.drawImage(this.image, this.x, this.y, this.width, this.height, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle(this.x, this.y, this.width, this.height);
   }

   public void setDestroyed(boolean b) {
      this.destroyed = b;
   }

   public boolean isDestroyed() {
      return this.destroyed;
   }
}
