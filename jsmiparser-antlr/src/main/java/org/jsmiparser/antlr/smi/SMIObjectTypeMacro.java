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
import org.jsmiparser.antlr.asn1.ASNValue;
import org.jsmiparser.antlr.asn1.Context;

/**
 *
 * @author  nigelss
 */
public class SMIObjectTypeMacro extends SMIStdMacro {
        
    private ASNType syntax;
    private List<SMINamedBit> namedBits = new ArrayList<SMINamedBit>();
    private String units;
    private SMIAccess mibAccess;
    private PIBAccess pibAccess;
    private ASNValue pibReferences;
    private ASNValue pibTag;
    private SMIAccessField accessType;
    private List<SMINamedBit> installErrors = new ArrayList<SMINamedBit>();
    private List<SMIIndexType> mibIndex = new ArrayList<SMIIndexType>();
    private ASNValue mibAugments;
    private ASNValue pibIndex;
    private ASNValue pibExtends;
    private List<ASNValue> uniquenessValues = new ArrayList<ASNValue>();
    private ASNValue defaultValue;
    private List<String> defaultValueBits = new ArrayList<String>();
    
    /** Creates a new instance of SMIObjectTypeMacro */
    public SMIObjectTypeMacro(Context context) {
        super(context, Enum.SMIOBJECTTYPEMACRO);
    }
    
    public void setSyntax (ASNType s)
    {
        syntax = s;
    }
    
    public ASNType getSyntax ()
    {
        return syntax;
    }
    
    public void setNamedBits (List<SMINamedBit> n)
    {
        namedBits = n;
    }
    
    public List<SMINamedBit> getNamedBits ()
    {
        return namedBits;
    }
    
    public void setUnits (String u)
    {
        units = u;
    }
    
    public void setMibAccess (SMIAccess t)
    {
        mibAccess = t;
    }
    
    public SMIAccess getMibAccess ()
    {
        return mibAccess;
    }
    
    public void setPibAccess (PIBAccess t)
    {
        pibAccess = t;
    }
    
    public PIBAccess getPibAccess ()
    {
        return pibAccess;
    }
    
    public void setPibReferences (ASNValue v)
    {
        pibReferences = v;
    }
    
    public ASNValue getPibReferences ()
    {
        return pibReferences;
    }
    
    public void setPibTag (ASNValue p)
    {
        pibTag = p;
    }
    
    public ASNValue getPibTag ()
    {
        return pibTag;
    }
    
    public void setAccessType (SMIAccessField a)
    {
        accessType = a;
    }
    
    public SMIAccessField getAccessType ()
    {
        return accessType;
    }
    
    public void setInstallErrors (List<SMINamedBit> i)
    {
        installErrors = i;
    }
    
    public List<SMINamedBit> getInstallErrors ()
    {
        return installErrors;
    }
    
    public void setMibIndex (List<SMIIndexType> i)
    {
        mibIndex = i;
    }
    
    public List<SMIIndexType> getMibIndex ()
    {
        return mibIndex;
    }
    
    public void setMibAugments (ASNValue a)
    {
        mibAugments = a;
    }
    
    public ASNValue getMibAugments ()
    {
        return mibAugments;
    }
    
    public void setPibIndex (ASNValue v)
    {
        pibIndex = v;
    }
    
    public ASNValue getPibIndex ()
    {
        return pibIndex;
    }
    
    public void setPibExtends (ASNValue v)
    {
        pibExtends = v;
    }
    
    public ASNValue getPibExtends ()
    {
        return pibExtends;
    }

    public void setUniquenessValues (List<ASNValue> u)
    {
        uniquenessValues = u;
    }
    
    public List<ASNValue> getUniquenessValues ()
    {
        return uniquenessValues;
    }
    
    public void setDefaultValue (ASNValue v)
    {
        defaultValue = v;
    }
    
    public ASNValue getDefaultValue ()
    {
        return defaultValue;
    }
    
    public void setDefaultValueBits (List<String> b)
    {
        defaultValueBits = b;
    }
    
    public List<String> getDefaultValueBits ()
    {
        return defaultValueBits;
    }
}
