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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.spbau.sd.model.framework.EndTurnListener;
import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.GameObject;
import ru.spbau.sd.model.framework.Point2D;

public class PoliceStation extends GameObject implements EndTurnListener {

    private List<Policeman> waitingOfficers = new ArrayList<>();
    private List<Policeman> walkingOfficers = new ArrayList<>();
    private Set<Drinker> drinkersToBeCaught = new HashSet<>();
    
    private int mEntryX;
    public int getEntryX() { return mEntryX; }
    private int mEntryY;
    public int getEntryY() { return mEntryY; }
    private Point2D mEntryPoint;

    
    public PoliceStation(int x, int y, int officersCnt) {
        super(x, y);
        mEntryX = Math.min(Math.max(x, 0), Field.getInstance().getXBound() - 1);
        mEntryY = Math.min(Math.max(y, 0), Field.getInstance().getYBound() - 1);
        mEntryPoint = new Point2D(mEntryX, mEntryY);
        for (int i = 0; i < officersCnt; i++) {
            waitingOfficers.add(new Policeman(-1, -1, this));
        }
        
        Field.getInstance().addEndTurnListener(this);
    }
    
    private void processReturnedOfficers() {
        for (Policeman p : walkingOfficers) {
            if (!p.isOnSamePosition(mEntryPoint)) { continue; }
            if (!p.isGoingHome()) { break; }
            
            //remove policeman and drinker from the field
            if (p.isBadGuyCaught()) {
                Field.getInstance().removeMovable(p.getCatchedBadGuy());
            }
            Field.getInstance().removeMovable(p);
            
            //policeman is waiting for next drinker
            walkingOfficers.remove(p);
            waitingOfficers.add(p);
            break;
        }
    }
    
    private boolean drinkerShouldBeCaught(Drinker d) {
        if (drinkersToBeCaught.contains(d)) { return false; }
        return d.getMovementStrategy() == Drinker.MovementStrategy.LAYING ||
               d.getMovementStrategy() == Drinker.MovementStrategy.SLEEP;
    }
    
    private boolean drinkerIsReachable(Drinker d) {
        return !mEntryPoint.equals(GameUtils.lookUpNextStep(mEntryPoint, new Point2D(d.getX(), d.getY())));
    }
    
    private void processUncaughtDrinkers() {
        if (waitingOfficers.size() == 0) { return; }
        if (!Field.getInstance().isPosFree(mEntryPoint)) { return; }
        
        Drinker drinkerToBeCaught = null;
        for (FieldObject fo_stat : Field.getInstance().getAllFieldObjects()) {
            //our informer is light. If other object will be informers -- create interface
            if (!fo_stat.getClass().equals(Light.class)) { continue; }
            
            Light l = (Light) fo_stat; 
            for (FieldObject fo : l.getLightedFieldObjects()) {
                if (!fo.getClass().equals(Drinker.class)) { continue; }
                Drinker d = (Drinker) fo;
                
                if (drinkerShouldBeCaught(d) && drinkerIsReachable(d)) {
                    drinkerToBeCaught = d;
                    break;
                }
            }
        }
        if (drinkerToBeCaught == null) { return; }
        
        Policeman p = waitingOfficers.remove(waitingOfficers.size() - 1);
        
        drinkersToBeCaught.add(drinkerToBeCaught);
        p.recieveCatchOrder(drinkerToBeCaught);
        p.setNewPosition(mEntryX, mEntryY);
        Field.getInstance().addMovable(p);
        walkingOfficers.add(p);
    }
    
    
    @Override
    public void handleEndTurn() {
        processReturnedOfficers();
        processUncaughtDrinkers();
    }

    @Override
    public char getSingleCharDescription() { return 'S'; }

}
