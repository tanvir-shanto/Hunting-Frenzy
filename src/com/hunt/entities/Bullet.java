package com.hunt.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {
   private int x;
   private int y;
   private int width;
   private int height;
   private int dx;
   private int rank;
   private Color color;
   private boolean visible = true;

   public Bullet(int type, int rank, int x, int y) {
      this.rank = rank;
      if (type == 1) {
         this.dx = 8;
         this.x = x;
         this.y = y;
      } else if (type == 2) {
         this.dx = -8;
         this.x = x;
         this.y = y;
      }

      if (rank == 1) {
         this.color = Color.BLACK;
         this.width = 10;
         this.height = 1;
      } else if (rank == 2) {
         this.color = Color.BLUE;
         this.width = 12;
         this.height = 1;
      } else if (rank == 3) {
         this.color = Color.ORANGE;
         this.width = 14;
         this.height = 1;
      }

   }

   public void tick() {
      this.x += this.dx;
      if (this.x < 0 || this.x > 1024) {
         this.visible = false;
      }

   }

   public void draw(Graphics g) {
      g.setColor(this.color);
      g.fillRect(this.x, this.y, this.width, this.height);
      g.setColor(Color.BLACK);
      g.drawRect(this.x, this.y, this.width, this.height);
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean b) {
      this.visible = b;
   }

   public Rectangle getBounds() {
      return new Rectangle(this.x, this.y, this.width, this.height);
   }

   public int getRank() {
      return this.rank;
   }
}
