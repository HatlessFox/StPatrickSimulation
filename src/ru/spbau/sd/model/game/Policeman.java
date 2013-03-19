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

import ru.spbau.sd.model.framework.MovableObject;
import ru.spbau.sd.model.framework.Point2D;

public class Policeman extends MovableObject {

    private Drinker mBadGuy;
    private boolean mBadGuyIsCaught;
    private PoliceStation mPoliceStation;
    
    public Policeman(int x, int y, PoliceStation ps) {
        super(x, y);
        mPoliceStation = ps;
    }

    public boolean isBadGuyCaught() { return mBadGuyIsCaught; }
    public Drinker getCatchedBadGuy() { return mBadGuy; }
    
    public void recieveCatchOrder(Drinker newBadGuy) {
        mBadGuy = newBadGuy;
        mBadGuyIsCaught = false;
    }

    
    @Override
    protected Point2D calcNextPos() {
        Point2D destPoint = isBadGuyCaught() ?
          new Point2D(mPoliceStation.getEntryX(), mPoliceStation.getEntryY()) :
          new Point2D(mBadGuy.getX(), mBadGuy.getY());

        return GameUtils.lookUpNextStep(new Point2D(getX(), getY()), destPoint);
    }
    
    @Override
    public void setNewPosition(int x, int y) {
        if (isBadGuyCaught()) {
            mBadGuy.setNewPosition(getX(), getY());
        }
        super.setNewPosition(x, y);
    }
    
    @Override
    public char getSingleCharDescription() { return 'P'; }

}
