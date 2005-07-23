/*
 * Copyright 2005 Nigel Sheridan-Smith, Davy Verstappen.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jsmiparser.parsetree.asn1;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNTupleQuadValue extends ASNValue {

    
    private ASNValue number1;
    private ASNValue number2;
    private ASNValue number3;
    private ASNValue number4;
    private boolean quad;
    
    /** Creates a new instance of ASNTupleQuadValue */
    public ASNTupleQuadValue(Context context) {
        super(context, Type.TUPLEQUADVALUE);
    }
    
    public void setNumber1 (ASNValue n)
    {
        number1 = n;
    }
    
    public ASNValue getNumber1 ()
    {
        return number1;
    }
    
    public void setNumber2 (ASNValue n)
    {
        number2 = n;
    }
    
    public ASNValue getNumber2 ()
    {
        return number2;
    }
    
    public void setNumber3 (ASNValue n)
    {
        number3 = n;
    }
    
    public ASNValue getNumber3 ()
    {
        return number3;
    }
    
    public void setNumber4 (ASNValue n)
    {
        number4 = n;
    }
    
    public ASNValue getNumber4 ()
    {
        return number4;
    }
    
    public void setQuad (boolean q)
    {
        quad = q;
    }
    
    public boolean isQuad ()
    {
        return quad;
    }
}
