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

package org.jsmiparser.antlr.asn1;

import java.util.*;



/**
 * Represents all the symbols that are imported from a module.
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNImports extends AbstractNamedSymbol {
    
    private List<String> symbols;
    private ASNOidComponentList assignedIdentifierOid;
    private ASNDefinedValue assignedIdentifierDefined;
    
    /** Creates a new instance of ASNImports */
    public ASNImports(Context context, String name, List<String> symbols) {
    	super(context, name);
    	
        this.symbols = symbols;
    }
    
    public String getModuleName ()
    {
        return getName();
    }
    
    public List<String> getSymbols ()
    {
        return symbols;
    }
    
    public void setAssignedIdentifierOid (ASNOidComponentList o)
    {
        assignedIdentifierOid = o;
    }
    
    public ASNOidComponentList getAssignedIdentifierOid ()
    {
        return assignedIdentifierOid;
    }
    
    public void setAssignedIdentifierDefined (ASNDefinedValue d)
    {
        assignedIdentifierDefined = d;
    }
    
    public ASNDefinedValue getAssignedIdentifierDefined ()
    {
        return assignedIdentifierDefined;
    }
    
}
