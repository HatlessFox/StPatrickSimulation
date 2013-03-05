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

package ru.spbau.sd.model.framework.movement;

import ru.spbau.sd.model.framework.MovableObject;
import ru.spbau.sd.model.framework.Point2D;

/**
 * Command that represents a movement action
 * 
 * @author Artur Huletski (hatless.fox@gmail.com)
 */
public class MovementCommand {
    
    public MovementCommand(MovableObject actor, Point2D pos) {
        this.actor = actor;
        newPosition = pos;
    }
    
    /** object that is moves */
    public MovableObject actor;
    /** new actor's position */
    public Point2D newPosition;
    
    /**
     * Tests if movement doesn't change current actor position
     * 
     * @return
     */
    public boolean isStationary() {
        return actor.getX() == newPosition.x && actor.getY() == newPosition.y;
    }
}
