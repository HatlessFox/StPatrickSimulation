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

import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.InteractionStrategy;
import ru.spbau.sd.model.framework.MovableObject;
import ru.spbau.sd.model.framework.Point2D;

public class Drinker extends MovableObject {

    
    //drinker's movement strategies
    private enum MovementStrategy {
        SLEEP {
          @Override
          public Point2D makeMove(FieldObject obj) { 
              return new Point2D(obj.getX(), obj.getY());
          }
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
        };
        
        abstract public Point2D makeMove(FieldObject obj);
    }
    
    private MovementStrategy mMovementStr = MovementStrategy.ALIVE;
    private int mTimeToSleep = 0;
    
    public Drinker(int x, int y) {
        super(x,y);
        registerInteractionHandler(Drinker.class, Column.class,
          new InteractionStrategy<Drinker, Column>() {
            @Override
            public void performInteraction(Drinker obj1, Column obj2) {
                obj1.interactWithColumn(obj2);
            }
          }
        );
    }
    
    private void interactWithColumn(Column obj2) {
        mTimeToSleep = 5;
        mMovementStr = MovementStrategy.SLEEP;
    }
    
    @Override
    protected Point2D calcNextPos() {
        Point2D newPos = mMovementStr.makeMove(this);
        if (mMovementStr == MovementStrategy.SLEEP) {
            if (--mTimeToSleep == 0) {
                mMovementStr = MovementStrategy.ALIVE;
            }
        }
        
        return newPos;
    }
    
    @Override
    public char getSingleCharDescription() {
        return (mMovementStr == MovementStrategy.ALIVE) ? 'D' : 'Z';
    }
    
}
