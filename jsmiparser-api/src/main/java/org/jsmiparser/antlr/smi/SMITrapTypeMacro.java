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



import java.util.ArrayList;
import java.util.List;

import org.jsmiparser.antlr.asn1.ASNValue;
import org.jsmiparser.antlr.asn1.Context;

/**
 *
 * @author  nigelss
 */
public class SMITrapTypeMacro extends SMIReferenceMacro {
    
    private ASNValue enterprise;
    private List<ASNValue> variables = new ArrayList<ASNValue>();
    
    /** Creates a new instance of SMITrapTypeMacro */
    public SMITrapTypeMacro(Context context) {
        super(context, Enum.SMITRAPTYPEMACRO);
    }
    
    public void setEnterprise (ASNValue v)
    {
        enterprise = v;
    }
    
    public ASNValue getEnterprise ()
    {
        return enterprise;
    }
    
    public void setVariables (List<ASNValue> v)
    {
        variables = v;
    }
    
    public List<ASNValue> getVariables ()
    {
        return variables;
    }

    
}
