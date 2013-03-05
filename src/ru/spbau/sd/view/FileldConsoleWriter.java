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

/**
 * Prints field to console
 * 
 * @author Artur Huletski (hatless.fox@gmail.com)
 */
public class FileldConsoleWriter {

    public static void printField() {
        char filedProg[][] = new char[Field.getInstance().getXBound()][Field.getInstance().getYBound()]; 
        
        for (int i = 0; i < Field.getInstance().getXBound(); i++) {
            for (int j = 0; j < Field.getInstance().getYBound(); j ++) {
                filedProg[i][j] = '.';
            }
        }
        
        for (FieldObject fo : Field.getInstance().getAllObjects()) {
            filedProg[fo.getX()][fo.getY()] = fo.getSingleCharDescription();
        }
        
        for (int i = 0; i < Field.getInstance().getXBound(); i++) {
            for (int j = 0; j < Field.getInstance().getYBound(); j ++) {
                System.out.print(filedProg[i][j]);
            }
            System.out.println();
        }
    }
    
}
