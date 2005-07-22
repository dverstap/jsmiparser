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

package org.jsmiparser.antlr.smi;

import java.util.*;

import org.jsmiparser.antlr.asn1.ASNType;

/**
 *
 * @author  nigelss
 */
public class SMISyntax {
    
    private ASNType subtype;
    private List<SMINamedBit> namedBits;
    
    /** Creates a new instance of SMISyntax */
    public SMISyntax() {
        /* Do collections */
        namedBits = new ArrayList<SMINamedBit> ();
    }
    
    public void setSubtype (ASNType s)
    {
        subtype = s;
    }
    
    public ASNType getSubtype ()
    {
        return subtype;
    }
    
    public void setSubtypeNamedBits (List<SMINamedBit> n)
    {
        namedBits = n;
    }
    
    public List<SMINamedBit> getSubtypeNamedBits ()
    {
        return namedBits;
    }
}
