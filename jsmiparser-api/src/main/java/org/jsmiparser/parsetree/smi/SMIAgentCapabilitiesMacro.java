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

package org.jsmiparser.parsetree.smi;

import java.util.*;

import org.jsmiparser.parsetree.asn1.ASNType;
import org.jsmiparser.parsetree.asn1.Context;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class SMIAgentCapabilitiesMacro extends ASNType {
    
    private String productRelease;
    private SMIStatus status;
    private String description;
    private String reference;
    private List<SMIAgentCapabilitiesModule> modules = new ArrayList<SMIAgentCapabilitiesModule> ();
    
    /** Creates a new instance of SMIAgentCapabilitiesMacro */
    public SMIAgentCapabilitiesMacro(Context context) {
        super(context, ASNType.Enum.SMIAGENTCAPABILITIESMACRO);
    }
    
    public void setProductRelease (String p)
    {
        productRelease = p;
    }
    
    public String getProductRelease ()
    {
        return productRelease;
    }
    
    public void setStatus (SMIStatus s)
    {
        status = s;
    }
    
    public SMIStatus getStatus ()
    {
        return status;
    }
    
    public void setDescription (String d)
    {
        description = d;
    }
    
    public String getDescription ()
    {
        return description;
    }
    
    public void setReference (String r)
    {
        reference = r;
    }
    
    public String getReference ()
    {
        return reference;
    }
    
    public void setModules (List<SMIAgentCapabilitiesModule> m)
    {
        modules = m;
    }
    
    public List<SMIAgentCapabilitiesModule> getModules ()
    {
        return modules;
    }
}
