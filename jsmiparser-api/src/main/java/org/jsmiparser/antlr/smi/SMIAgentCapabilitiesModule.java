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
public class SMIAgentCapabilitiesModule {
    
    private String moduleName;
    private ASNValue value;
    private List<ASNValue> includes;
    private List<SMIVariation> variations;
    
    /** Creates a new instance of SMIAgentCapabilitiesModule */
    public SMIAgentCapabilitiesModule() {
        /* Do collections */
        includes = new ArrayList<ASNValue> ();
        variations = new ArrayList<SMIVariation> ();
    }
    
    public void setModuleName (String m)
    {
        moduleName = m;
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
    
    public void setIncludes (List<ASNValue> i)
    {
        includes = i;
    }
    
    public List<ASNValue> getIncludes ()
    {
        return includes;
    }
    
    public void setVariations (List<SMIVariation> v)
    {
        variations = v;
    }
    
    public List<SMIVariation> getVariations ()
    {
        return variations;
    }
    
}
