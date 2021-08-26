package com.hunt.states;

import com.hunt.game.Asset;
import com.hunt.game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;

public class MenuState extends State implements MouseListener {
   private Rectangle play = new Rectangle(410, 150, 200, 80);
   private Rectangle help = new Rectangle(410, 300, 200, 80);
   private Rectangle quit = new Rectangle(410, 450, 200, 80);

   public void tick() {
   }

   public void draw(Graphics g) {
      g.drawImage(Asset.menu, 0, 0, 1024, 680, (ImageObserver)null);
      g.setColor(Color.decode("#02440A"));
      g.fillRect(0, 0, 1024, 60);
      g.setFont(new Font("Century Gothic", 1, 50));
      g.setColor(Color.WHITE);
      g.drawString("HUNTING FRENZY", 290, 50);
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(Color.decode("#34495E"));
      g2d.draw(this.play);
      g2d.setFont(new Font("Century Gothic", 0, 35));
      g2d.drawString("PLAY", 470, 200);
      g2d.draw(this.help);
      g2d.setFont(new Font("Century Gothic", 0, 35));
      g2d.drawString("HELP", 470, 350);
      g2d.draw(this.quit);
      g2d.setFont(new Font("Century Gothic", 0, 35));
      g2d.drawString("QUIT", 470, 500);
   }

   public void mousePressed(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      Rectangle mouseRect = new Rectangle(x, y, 1, 1);
      if (this.play.intersects(mouseRect)) {
         State.setState(Game.getGameState());
      }

      if (this.quit.intersects(mouseRect)) {
         System.exit(1);
      }

   }

   public void mouseClicked(MouseEvent arg0) {
   }

   public void mouseEntered(MouseEvent arg0) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent arg0) {
   }

   public void keyPressed(KeyEvent e) {
   }

   public void keyReleased(KeyEvent e) {
   }

   public void keyTyped(KeyEvent e) {
   }
}
