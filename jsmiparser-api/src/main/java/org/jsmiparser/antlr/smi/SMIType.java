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
import org.jsmiparser.antlr.asn1.Context;

/**
 *
 * @author  nigelss
 */
public class SMIType extends ASNType {
    
	public enum SMIEnum
	{
		SMI_UNKNOWN,
		SMI_BITS,
		SMI_INTEGER,
		SMI_OCTETSTRING,
		SMI_OID,
		SMI_SUBTYPE
	}
	
    private SMIEnum smiType;
    private String subtype;
    private List<SMIRange> ranges  = new ArrayList<SMIRange>();
    private boolean sizeConstraint;
    
    // TODO check constructors
    
    public SMIType (Context context, SMIEnum t)
    {
        super(context, Enum.SMITYPE);
        setSmiType (t);
    }
    
    public SMIType (Context context, String t)
    {
    	super(context, Enum.SMITYPE);
        setSmiType (SMIEnum.SMI_SUBTYPE);
        setSubtype (t);
    }
    
    public void setSmiType (SMIEnum t)
    {
        smiType = t;
    }
    
    public SMIEnum getSmiType ()
    {
        return smiType;
    }
    
    public void setSubtype (String t)
    {
        subtype = t;
    }
    
    public String getSubtype ()
    {
        return subtype;
    }
    
    public void setRanges (List<SMIRange> r)
    {
        ranges = r;
    }
    
    public List<SMIRange> getRanges ()
    {
        return ranges;
    }
    
    public void setSizeConstraint (boolean s)
    {
        sizeConstraint = s;
    }
    
    public boolean isSizeConstraint ()
    {
        return sizeConstraint;
    }
}
