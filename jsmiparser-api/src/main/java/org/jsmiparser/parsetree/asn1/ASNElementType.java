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

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNElementType {
    
	public enum Type
	{
		UNKNOWN,
		TAGGEDTYPEVALUE,
		COMPONENTSOF
	}
	

    private Type type;
    private String identifier;
    private ASNTag tag;
    private ASNTag.Type tagDefault;
    private ASNType subtype;
    private boolean optional;
    private boolean defaultFlag;
    private ASNValue defaultValue;
   
    /** Creates a new instance of ASNElementType */
    public ASNElementType() {
        setType (Type.UNKNOWN);
    }
    
    public void setType (Type t)
    {
        type = t;
    }
    
    public Type getType ()
    {
        return type;
    }
    
    public void setIdentifier (String i)
    {
        identifier = i;
    }
    
    public String getIdentifier ()
    {
        return identifier;
    }
    
    public void setTag (ASNTag t)
    {
        tag = t;
    }
    
    public ASNTag getTag ()
    {
        return tag;
    }
    
    public void setTagDefault (ASNTag.Type t)
    {
        tagDefault = t;
    }
    
    public ASNTag.Type getTagDefault ()
    {
        return tagDefault;
    }
    
    public void setSubtype (ASNType t)
    {
        subtype = t;
    }
    
    public ASNType getSubtype ()
    {
        return subtype;
    }
    
    public void setOptional (boolean o)
    {
        optional = o;
    }
    
    public boolean isOptional ()
    {
        return optional;
    }
    
    public void setDefault (boolean d)
    {
        defaultFlag = d;
    }
    
    public boolean isDefault ()
    {
        return defaultFlag;
    }
    
    public void setDefaultValue (ASNValue v)
    {
        defaultValue = v;
    }
    
    public ASNValue getDefaultValue ()
    {
        return defaultValue;
    }
}
