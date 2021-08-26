package com.hunt.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Player {
   private int x;
   private int y;
   private int dx;
   private int dy;
   private final int width;
   private final int height;
   private int runRight;
   private int runLeft;
   private int life;
   private boolean isDead;
   private List<BufferedImage> imageList;
   private BufferedImage sprite;
   private long lastTime;
   private long delay;
   private boolean right;
   private boolean left;
   private boolean up;
   private boolean down;
   private boolean rightDirection;
   private boolean leftDirection;
   private List<Bullet> bullets;
   private long currentTime;
   private long previousTime;
   private long shootingDelay;
   private int powerLevel;
   private int bulletRank;
   private long recoveryTimerDiff;
   private long recoveryDelay;
   private long lastRecovery;
   private boolean recovering;

   public Player(List<BufferedImage> imageList) {
      this.imageList = imageList;
      this.bullets = new ArrayList();
      this.x = 512;
      this.y = 542;
      this.dx = 0;
      this.dy = 0;
      this.life = 3;
      this.isDead = false;
      this.width = 64;
      this.height = 64;
      this.runRight = 0;
      this.runLeft = 0;
      this.lastTime = 0L;
      this.delay = 100L;
      this.previousTime = 0L;
      this.shootingDelay = 150L;
      this.recoveryDelay = 1400L;
      this.lastRecovery = 0L;
      this.recovering = false;
      this.powerLevel = 1;
      this.sprite = (BufferedImage)imageList.get(0);
   }

   public void spriteUpdate(long currentTime) {
      if (currentTime - this.lastTime >= this.delay) {
         if (this.right) {
            if (this.runRight == 0) {
               this.sprite = (BufferedImage)this.imageList.get(0);
            } else if (this.runRight == 1) {
               this.sprite = (BufferedImage)this.imageList.get(1);
            } else if (this.runRight == 2) {
               this.sprite = (BufferedImage)this.imageList.get(2);
            } else if (this.runRight == 3) {
               this.sprite = (BufferedImage)this.imageList.get(3);
            } else if (this.runRight == 4) {
               this.sprite = (BufferedImage)this.imageList.get(4);
            } else if (this.runRight == 5) {
               this.sprite = (BufferedImage)this.imageList.get(5);
            } else if (this.runRight == 6) {
               this.sprite = (BufferedImage)this.imageList.get(6);
            } else if (this.runRight == 7) {
               this.sprite = (BufferedImage)this.imageList.get(7);
            } else if (this.runRight == 8) {
               this.sprite = (BufferedImage)this.imageList.get(8);
            }

            ++this.runRight;
            if (this.runRight > 8) {
               this.runRight = 0;
            }
         }

         if (this.left) {
            if (this.runLeft == 0) {
               this.sprite = (BufferedImage)this.imageList.get(9);
            } else if (this.runLeft == 1) {
               this.sprite = (BufferedImage)this.imageList.get(10);
            } else if (this.runLeft == 2) {
               this.sprite = (BufferedImage)this.imageList.get(11);
            } else if (this.runLeft == 3) {
               this.sprite = (BufferedImage)this.imageList.get(12);
            } else if (this.runLeft == 4) {
               this.sprite = (BufferedImage)this.imageList.get(13);
            } else if (this.runLeft == 5) {
               this.sprite = (BufferedImage)this.imageList.get(14);
            } else if (this.runLeft == 6) {
               this.sprite = (BufferedImage)this.imageList.get(15);
            } else if (this.runLeft == 7) {
               this.sprite = (BufferedImage)this.imageList.get(16);
            } else if (this.runLeft == 8) {
               this.sprite = (BufferedImage)this.imageList.get(17);
            }

            ++this.runLeft;
            if (this.runLeft > 8) {
               this.runLeft = 0;
            }
         }

         if (this.up) {
            if (this.rightDirection) {
               this.sprite = (BufferedImage)this.imageList.get(3);
            } else {
               this.sprite = (BufferedImage)this.imageList.get(14);
            }
         }

         if (this.down) {
            if (this.rightDirection) {
               this.sprite = (BufferedImage)this.imageList.get(3);
            } else {
               this.sprite = (BufferedImage)this.imageList.get(14);
            }
         }

         this.lastTime = currentTime;
      }

   }

   public void tick() {
      this.x += this.dx;
      this.y += this.dy;
      if (this.x <= 0) {
         this.x = 0;
      }

      if (this.x > 1024 - this.width) {
         this.x = 1024 - this.width;
      }

      if (this.y < 542) {
         this.y = 542;
      }

      if (this.y > 680 - this.height) {
         this.y = 680 - this.height;
      }

      long elapsed = System.currentTimeMillis() - this.lastRecovery;
      if (elapsed >= this.recoveryDelay) {
         this.recovering = false;
      }

   }

   public void draw(Graphics g) {
      g.drawImage(this.sprite, this.x, this.y, this.width, this.height, (ImageObserver)null);
   }

   public void loseLife() {
      this.recoveryTimerDiff = System.currentTimeMillis() - this.lastRecovery;
      if (this.recoveryTimerDiff >= this.recoveryDelay) {
         --this.life;
         this.recovering = true;
         this.lastRecovery = System.currentTimeMillis();
      }

      if (this.life <= 0) {
         this.isDead = true;
      }

   }

   public void gainLife() {
      ++this.life;
      if (this.life > 4) {
         this.life = 4;
      }

   }

   public void keyPressed(KeyEvent e) {
      int value = e.getKeyCode();
      if (value == 32) {
         this.currentTime = System.currentTimeMillis();
         if (this.currentTime - this.previousTime >= this.shootingDelay) {
            this.fire();
            this.previousTime = this.currentTime;
         }
      }

      if (value == 39) {
         this.right = true;
         this.rightDirection = true;
         this.leftDirection = false;
         this.dx = 3;
      }

      if (value == 37) {
         this.left = true;
         this.leftDirection = true;
         this.rightDirection = false;
         this.dx = -3;
      }

      if (value == 38) {
         this.up = true;
         this.dy = -3;
      }

      if (value == 40) {
         this.down = true;
         this.dy = 3;
      }

   }

   public void keyReleased(KeyEvent e) {
      int value = e.getKeyCode();
      if (value == 39) {
         this.right = false;
         this.dx = 0;
      }

      if (value == 37) {
         this.left = false;
         this.dx = 0;
      }

      if (value == 38) {
         this.up = false;
         this.dy = 0;
      }

      if (value == 40) {
         this.down = false;
         this.dy = 0;
      }

   }

   public void fire() {
      if (this.powerLevel == 1) {
         this.bulletRank = 1;
      } else if (this.powerLevel == 2) {
         this.bulletRank = 2;
      } else if (this.powerLevel == 3) {
         this.bulletRank = 3;
      }

      if (this.rightDirection) {
         this.bullets.add(new Bullet(1, this.bulletRank, this.x + this.width, this.y + this.height / 2));
      } else if (this.leftDirection) {
         this.bullets.add(new Bullet(2, this.bulletRank, this.x, this.y + this.height / 2 + 5));
      }

   }

   public Rectangle getBounds() {
      return new Rectangle(this.x, this.y, this.width, this.height);
   }

   public List<Bullet> getBullets() {
      return this.bullets;
   }

   public int getX() {
      return this.x;
   }

   public int getWidth() {
      return this.width;
   }

   public int getLife() {
      return this.life;
   }

   public boolean getDead() {
      return this.isDead;
   }

   public boolean isRecovering() {
      return this.recovering;
   }

   public void setPowerLevel(int powerLevel) {
      this.powerLevel = powerLevel;
   }
}
