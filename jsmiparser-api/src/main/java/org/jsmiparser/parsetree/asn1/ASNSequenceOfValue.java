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
	/*
 * ASNSequenceOfValue.java
 *
 * Created on 15 January 2005, 13:22
 */

package org.jsmiparser.parsetree.asn1;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNSequenceOfValue extends ASNValue {
    
    private List<ASNValue> values;
    
    /** Creates a new instance of ASNSequenceOfValue */
    public ASNSequenceOfValue(Context context) {
        super(context, Type.SEQUENCEOFVALUES);
        
        /* Do collections */
        values = new ArrayList<ASNValue> ();
    }
    
    public void setValues (List<ASNValue> v)
    {
        values = v;
    }
    
    public List<ASNValue> getValues ()
    {
        return values;
    }
    
}
