/**
  Copyright (c) 2013 Artur Huletski (hatless.fox@gmail.com)
  Permission is hereby granted, free of charge, to any person obtaining a copy 
  of this software and associated documentation files (the "Software"),
  to deal in the Software without restriction, including without limitation
  the rights to use, copy, modify, merge, publish, distribute, sublicense,
  and/or sell copies of the Software, and to permit persons
  to whom the Software is furnished to do so,
  subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included
  in all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
  OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package ru.spbau.sd.model.game;

import java.util.Random;

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.InteractionStrategy;
import ru.spbau.sd.model.framework.MovableObject;
import ru.spbau.sd.model.framework.Point2D;

public class Drinker extends MovableObject {

    private Bottle bottle = new Bottle(-1, -1);
    private Random rnd = new Random();
    
    //drinker's movement strategies
    private enum MovementStrategy {
        SLEEP {
          @Override
          public Point2D makeMove(FieldObject obj) { 
              return new Point2D(obj.getX(), obj.getY());
          }

          @Override public char getSingleCharRepr() { return 'Z'; }
        },
        LAYING {
          @Override
          public Point2D makeMove(FieldObject obj) { 
            return new Point2D(obj.getX(), obj.getY());
          }

          @Override public char getSingleCharRepr() { return '&'; }
        },
        ALIVE {
          private Random rnd = new Random();
          @Override
          public Point2D makeMove(FieldObject obj) {
              boolean isXChanged = rnd.nextBoolean();
              int delta = rnd.nextBoolean() ? 1 : -1;
              
              Point2D newPos = new Point2D(obj.getX(), obj.getY());
              
              if (isXChanged) { newPos.x += delta; }
              else { newPos.y += delta; }
              
              return newPos;
          }
          @Override
          public char getSingleCharRepr() { return 'D'; }
        };
        
        abstract public Point2D makeMove(FieldObject obj);
        abstract public char getSingleCharRepr();
    }
    
    private MovementStrategy mMovementStr = MovementStrategy.ALIVE;
    
    public Drinker(int x, int y) {
        super(x,y);
        registerInteractionHandler(Drinker.class, Column.class,
          new InteractionStrategy<Drinker, Column>() {
            @Override
            public void performInteraction(Drinker obj1, Column obj2) {
                obj1.mMovementStr =  MovementStrategy.SLEEP;
            }
          }
        );
        registerInteractionHandler(Drinker.class, Bottle.class,
           new InteractionStrategy<Drinker, Bottle>() {
             @Override
             public void performInteraction(Drinker obj1, Bottle obj2) {
               mMovementStr = MovementStrategy.LAYING;
             }
           }
        );
        registerInteractionHandler(Drinker.class, Drinker.class,
                new InteractionStrategy<Drinker, Drinker>() {
                  @Override
                  public void performInteraction(Drinker obj1, Drinker buddy) {
                      if (buddy.mMovementStr == MovementStrategy.SLEEP) {
                          mMovementStr = MovementStrategy.SLEEP;
                      }
                      //LAYING -- do nothing
                      //ALIVE -- it's bang, so do nothing
                  }
                }
        );
        //policemen, light -- do nothing so no reasons for register
    }

    
    @Override
    protected Point2D calcNextPos() {
        Point2D newPos = mMovementStr.makeMove(this);
        
        return newPos;
    }
    
    @Override
    public char getSingleCharDescription() {
        return mMovementStr.getSingleCharRepr();
    }
    
    
    @Override
    public void setNewPosition(int x, int y) {
        if (bottle != null && rnd.nextInt(30) == 0) {
            bottle.setNewPosition(getX(), getY());
            Field.getInstance().addStationary(bottle);
            bottle = null;
        }
        
        super.setNewPosition(x,y);
    }
    
}
