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

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNOperationMacro extends ASNType {
    
    private String argumentIdentifier;
    private ASNType argumentType;
    private String resultIdentifier;
    private ASNType resultType;
    private List<ASNTypeOrValue> errorList = new ArrayList<ASNTypeOrValue>();
    private List<ASNTypeOrValue> linkedOperationList = new ArrayList<ASNTypeOrValue>();

    /** Creates a new instance of ASNOperationMacro */
    public ASNOperationMacro(Context context) {
        super(context, Enum.OPERATIONMACRO);
    }
    
    public void setArgumentIdentifier (String a)
    {
        argumentIdentifier = a;
    }
    
    public String getArgumentIdentifier ()
    {
        return argumentIdentifier;
    }
        
    public void setArgumentType (ASNType t)
    {
        argumentType = t;
    }
    
    public ASNType getArgumentType ()
    {
        return argumentType;
    }
    
    public void setResultIdentifier (String r)
    {
        resultIdentifier = r;
    }
    
    public String getResultIdentifier ()
    {
        return resultIdentifier;
    }
    
    public void setResultType (ASNType r)
    {
        resultType = r;
    }
    
    public ASNType getResultType ()
    {
        return resultType;
    }
    
    public void setErrorList (List<ASNTypeOrValue> e)
    {
        errorList = e;
    }
    
    public List<ASNTypeOrValue> getErrorList ()
    {
        return errorList;
    }
    
    public void setLinkedOperationList (List<ASNTypeOrValue> l)
    {
        linkedOperationList = l;
    }
    
    public List<ASNTypeOrValue> getLinkedOperationList ()
    {
        return linkedOperationList;
    }
    
}
