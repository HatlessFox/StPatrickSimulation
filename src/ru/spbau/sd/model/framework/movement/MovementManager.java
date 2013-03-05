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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.MovableObject;
import ru.spbau.sd.model.framework.Point2D;

/**
 * Handles of movements, resolves collisions
 * 
 * @author Artur Huletski (hatless.fox@gmail.com)
 */
public class MovementManager {
    private static MovementManager sInstance = new MovementManager();
    private MovementManager() {}
    
    public static MovementManager getInstance() { return sInstance; }
    
    
    //plain implementation. doesn't handle multi-causal rounds of movements (per single time tick)
    public void handleMovements(Map<MovableObject, Point2D> movementInfo) {
        if (movementInfo.size() == 0) { return; }
        
        List<FieldObject> stationaryObjects = new ArrayList<>();
        for (FieldObject fo : Field.getInstance().getAllFieldObjects()) {
          if (!movementInfo.containsKey(fo) || !Field.getInstance().isInsideField(movementInfo.get(fo))) {
            stationaryObjects.add(fo);
            movementInfo.remove(fo);
          }
        }
        
        for (Map.Entry<MovableObject, Point2D> entry : movementInfo.entrySet()) {
            handleCollisions(entry.getKey(), entry.getValue(), stationaryObjects);
            stationaryObjects.add(entry.getKey());
        }
        
    }
    
    //checks and handles all collisions. If there are no collisions --
    //updates actor's position
    private void handleCollisions(MovableObject actor, Point2D newPosition, List<FieldObject> stationaryObjects) {
        for (FieldObject sfo : stationaryObjects) {
            if (sfo.isOnSamePosition(newPosition)) {
                actor.interact(sfo);
                return;
            }
        }
        
        actor.setNewPosition(newPosition.x, newPosition.y);
    }
}
