package com.hunt.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class Fowl extends Enemy {
   protected List<Egg> eggs = new ArrayList();

   public Fowl(int type) {
      super(type);
   }

   public List<Egg> getEggs() {
      return this.eggs;
   }
}
