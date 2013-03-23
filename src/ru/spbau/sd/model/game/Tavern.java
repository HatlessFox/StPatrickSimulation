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

import ru.spbau.sd.model.framework.EndTurnListener;
import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.GameObject;
import ru.spbau.sd.model.framework.Point2D;

public class Tavern extends GameObject implements EndTurnListener {
    
    public Tavern(int x, int y, int genInt) {
        super(x, y);
        mEntryX = Math.min(Math.max(x, 0), Field.getInstance().getXBound() - 1);
        mEntryY = Math.min(Math.max(y, 0), Field.getInstance().getYBound() - 1);
        mGenInt = genInt;
        
        Field.getInstance().addEndTurnListener(this);
    }
    
    private int mEntryX;
    private int mEntryY;
    private int mGenInt;
    private int mElapsed;
    
    @Override
    public void handleEndTurn() {
        mElapsed += 1;
        if (mGenInt == mElapsed) {
            mElapsed = 0;
            if (Field.getInstance().isPosFree(new Point2D(mEntryX, mEntryY))) {
                Field.getInstance().addMovable(new Drinker(mEntryX, mEntryY));
            }
        }
    }

    @Override
    public char getSingleCharDescription() {
        return 'T';
    }

}
