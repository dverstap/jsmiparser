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

import org.jsmiparser.antlr.asn1.ASNValue;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class SMIModuleCompliance {

    private String moduleName;
    private ASNValue value;
    private List<ASNValue> mandatoryGroups;
    private List<SMICompliance> compliances;
    
    /** Creates a new instance of SMIModuleCompliance */
    public SMIModuleCompliance() {
        /* Do collections */
        mandatoryGroups = new ArrayList<ASNValue> ();
        compliances = new ArrayList<SMICompliance> ();
    }
    
    public void setModuleName (String n)
    {
        moduleName = n;
    }
    
    public String getModuleName ()
    {
        return moduleName;
    }
    
    public void setValue (ASNValue v)
    {
        value = v;
    }
    
    public ASNValue getValue ()
    {
        return value;
    }
    
    public void setMandatoryGroups (List<ASNValue> m)
    {
        mandatoryGroups = m;
    }
    
    public List<ASNValue> getMandatoryGroups ()
    {
        return mandatoryGroups;
    }
    
    public void setCompliances (List<SMICompliance> c)
    {
        compliances = c;
    }
    
    public List<SMICompliance> getCompliances ()
    {
        return compliances;
    }
    
}
