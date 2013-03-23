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
import java.util.List;

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.Point2D;

public class Light extends FieldObject {

    public Light(int x, int y) {
        super(x, y);
    }
  
    /*
     * Light cover
     * 
     * ---X---
     * -XXXXX-
     * -XXXXX-
     * XXXLXXX
     * -XXXXX-
     * -XXXXX-
     * ---X---
     * 
     */
    public List<FieldObject> getLightedFieldObjects() {
        List<FieldObject> lightedObjects = new ArrayList<>();
        for (FieldObject fo : Field.getInstance().getAllFieldObjects()) {
            if (Math.abs(getX() - fo.getX()) <= 2 &&
                Math.abs(getY() - fo.getY()) <= 2) {
                lightedObjects.add(fo);
                continue;
            }
               
            if (fo.isOnSamePosition(new Point2D(getX(), getY() - 3)) ||
                fo.isOnSamePosition(new Point2D(getX(), getY() + 3)) ||
                fo.isOnSamePosition(new Point2D(getX() - 3, getY())) ||
                fo.isOnSamePosition(new Point2D(getX() + 3, getY()))) {
                lightedObjects.add(fo);
                continue;
            }
        }
        return lightedObjects;
    }
    
    @Override public char getSingleCharDescription() { return 'L'; }

}
