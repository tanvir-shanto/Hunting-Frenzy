package com.hunt.game;

import java.awt.Component;
import javax.swing.JFrame;

public class Main {
   private JFrame frame = new JFrame();

   public Main() {
      Game game = new Game();
      this.frame.setTitle("Hunting Frenzy");
      this.frame.setDefaultCloseOperation(3);
      this.frame.add(game);
      this.frame.pack();
      this.frame.setLocationRelativeTo((Component)null);
      this.frame.setVisible(true);
   }

   public static void main(String[] args) {
      new Main();
   }
}
