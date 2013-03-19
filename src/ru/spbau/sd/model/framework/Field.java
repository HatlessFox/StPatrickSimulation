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

package ru.spbau.sd.model.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.spbau.sd.model.framework.movement.MovementCommand;
import ru.spbau.sd.model.framework.movement.MovementManager;

/**
 * Instance of a game field.
 * 
 * @author Artur Huletski (hatless.fox@gmail.com)
 *
 */
public class Field {

    private static Field sInstance;
    public static Field getInstance() { return sInstance; }
    public static void init(int xBound, int yBound) {
        sInstance = new Field(xBound, yBound);
    }
    
    private final int mXBound;
    public int getXBound() { return mXBound; }
    private final int mYBound;
    public int getYBound() { return mYBound; }
    
    private Field(int xBound, int yBound) {
        mXBound = xBound;
        mYBound = yBound;
    }
    
    //object that can move
    private List<MovableObject> mMovableObjects = new ArrayList<MovableObject>();
    //objects that can't move
    private List<FieldObject> mStationaryObjects = new ArrayList<FieldObject>();
    //end turn listeners
    private List<EndTurnListener> mEndTurnListenrs = new ArrayList<>();
    //outside objects
    private List<GameObject> mOutsideObjects = new ArrayList<>();
    
    
    public void addMovable(MovableObject movable) { mMovableObjects.add(movable); }
    public void removeMovable(MovableObject movable) { mMovableObjects.remove(movable); }
    public void addStationary(FieldObject stat) { mStationaryObjects.add(stat); }
    public void addEndTurnListener(EndTurnListener etl) { mEndTurnListenrs.add(etl); }
    public void addOutside(GameObject etl) { mOutsideObjects.add(etl); }
    
    //checks if point lies on field
    public boolean isInsideField(Point2D point) {
        return isInsideField(point.x, point.y);
    }
    public boolean isInsideField(int x, int y) {
        return (0 <= x) && (x < getXBound()) &&
               (0 <= y) && (y < getYBound());
    }
    
    public boolean isPosFree(Point2D pos) {
        for (FieldObject fo : getAllFieldObjects()) {
            if (fo.isOnSamePosition(pos)) { return false; }
        }
        return true;
    }
    
    /**
     * Performs 'time tick'
     */
    public void simulateRound() {
        Map<MovableObject, Point2D> cmds = new HashMap<>(mMovableObjects.size());
        for (MovableObject movable : mMovableObjects) {
            MovementCommand cmd = movable.move();
            if (!cmd.isStationary()) {
                cmds.put(cmd.actor, cmd.newPosition);
            }
        }
        
        //handle movements
        MovementManager.getInstance().handleMovements(cmds);
        //notify listeners
        for (EndTurnListener etl : mEndTurnListenrs) {
            etl.handleEndTurn();
        }
    }
    
    /**
     * Returns list of all object that are on field at a given time<br/>
     * 
     * @return
     */
    public List<FieldObject> getAllFieldObjects() {
        //caching if this is performance bottle neck
        List<FieldObject> result = new ArrayList<FieldObject>();
        result.addAll(mStationaryObjects);
        result.addAll(mMovableObjects);
        return result;
    }
    
    public List<GameObject> getOutsideObjects() {
        return Collections.unmodifiableList(mOutsideObjects);
    }
    
}
