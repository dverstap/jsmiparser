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

import org.jsmiparser.parsetree.asn1.ASNValue;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class SMICompliance {
    
	public enum Type
	{
		GROUP,
		OBJECT
	}
    
    private Type complianceType;
    private ASNValue group;
    private ASNValue object;
    private String description;
    private SMISyntax syntax;
    private SMISyntax writeSyntax;
    private SMIAccess mibMinAccess;
    private PIBAccess pibMinAccess;
    
    /** Creates a new instance of SMICompliance */
    public SMICompliance() {
    }
    
    public void setComplianceType (Type t)
    {
        complianceType = t;
    }
    
    public Type getComplianceType ()
    {
        return complianceType;
    }
    
    public void setGroup (ASNValue v)
    {
        group = v;
    }
    
    public ASNValue getGroup ()
    {
        return group;
    }
    
    public void setObject (ASNValue v)
    {
        object = v;
    }
    
    public ASNValue getObject ()
    {
        return object;
    }
    
    public void setDescription (String d)
    {
        description = d;
    }
    
    public String getDescription ()
    {
        return description;
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
    
    public void setMinAccess (SMIAccess a)
    {
        mibMinAccess = a;
    }
    
    public SMIAccess getMinAccess ()
    {
        return mibMinAccess;
    }
        
    public void setPibMinAccess (PIBAccess a)
    {
        pibMinAccess = a;
    }
    
    public PIBAccess getPibMinAccess ()
    {
        return pibMinAccess;
    }
}
