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

import org.jsmiparser.parsetree.asn1.ASNValue;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class SMIVariation {
    
    private ASNValue variationValue;
    private SMISyntax syntax;
    private SMISyntax writeSyntax;
    private SMIAccess access;
    private List<ASNValue> creationRequires;
    private ASNValue defaultValue;
    private List<String> defaultValueIdentifiers;
    private String description; 
    
    /** Creates a new instance of SMIVariation */
    public SMIVariation() {
        /* Do collections */
        creationRequires = new ArrayList<ASNValue> ();
        defaultValueIdentifiers = new ArrayList<String> ();
    }
    
    public void setVariationValue (ASNValue v)
    {
        variationValue = v;
    }
    
    public ASNValue getVariationValue ()
    {
        return variationValue;
    }
    
    public void setSyntax (SMISyntax s)
    {
        syntax = s;
    }
    
    public SMISyntax getSyntax ()
    {
        return syntax;
    }
    
    public void setWriteSyntax (SMISyntax s)
    {
        writeSyntax = s;
    }
    
    public SMISyntax getWriteSyntax ()
    {
        return writeSyntax;
    }
    
    public void setAccess (SMIAccess a)
    {
        access = a;
    }
    
    public SMIAccess getAccess ()
    {
        return access;
    }
    
    public void setCreationRequires (List<ASNValue> c)
    {
        creationRequires = c;
    }
    
    public List<ASNValue> getCreationRequires ()
    {
        return creationRequires;
    }
    
    public void setDefaultValue (ASNValue v)
    {
        defaultValue = v;
    }
    
    public ASNValue getDefaultValue ()
    {
        return defaultValue;
    }
    
    public void setDefaultValueIdentifiers (List<String> i)
    {
        defaultValueIdentifiers = i;
    }
    
    public List<String> getDefaultValueIdentifiers ()
    {
        return defaultValueIdentifiers;
    }
    
    public void setDescription (String d)
    {
        description = d;
    }
    
    public String getDescription ()
    {
        return description;
    }
}
