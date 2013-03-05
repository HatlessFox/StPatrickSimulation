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

package ru.spbau.sd.view;

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.GameObject;

/**
 * Prints field to console
 * 
 * @author Artur Huletski (hatless.fox@gmail.com)
 */
public class FileldConsoleWriter {

    public static void printField() {
        char filedProg[][] = new char[Field.getInstance().getXBound()+2][Field.getInstance().getYBound()+2]; 
        
        //round rectangle is for outer objects
        for (int i = -1; i < Field.getInstance().getXBound() + 1; i++) {
            for (int j = -1; j < Field.getInstance().getYBound() + 1; j ++) {
                filedProg[i+1][j+1] = Field.getInstance().isInsideField(i, j) ? '.' : ' ';
                
            }
        }
        
        
        for (FieldObject fo : Field.getInstance().getAllFieldObjects()) {
            filedProg[fo.getX()+1][fo.getY()+1] = fo.getSingleCharDescription();
        }
        for (GameObject go : Field.getInstance().getOutsideObjects()) {
            filedProg[go.getX()+1][go.getY()+1] = go.getSingleCharDescription();
        }
        
        for (int i = 0; i < Field.getInstance().getXBound()+2; i++) {
            for (int j = 0; j < Field.getInstance().getYBound()+2; j ++) {
                System.out.print(filedProg[i][j]);
            }
            System.out.println();
        }
    }
    
}
