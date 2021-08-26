package com.hunt.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
   public static BufferedImage loadImage(String path) {
      try {
         return ImageIO.read(ImageLoader.class.getResource(path));
      } catch (IOException var2) {
         var2.printStackTrace();
         return null;
      }
   }
}
