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

import org.jsmiparser.util.token.IdToken;

import java.io.PrintWriter;

import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNModule extends AbstractNamedSymbol {
    
	public enum Type
	{
		ASN1,
		SMIv1,
		SMIv2,
		SPPI
	}
	
    private Type type;
    private boolean explicitTags;
    private boolean implicitTags;
    private boolean automaticTags;
    private boolean extensibilityImplied;
    private ASNOidComponentList definitiveIdentifier;
    private ASNExports exports;
    private List<ASNImports> imports = new ArrayList<ASNImports>();
    private List<ASNAssignment> assignments = new ArrayList<ASNAssignment>();
    
    
    /** Creates a new instance of ASNModule */
    public ASNModule(Context context, IdToken idToken)
    {
    	super(context, idToken);
        type = Type.ASN1;
    }
    
    public void setType (Type t)
    {
        type = t;
    }
    
    public Type getType ()
    {
        return type;
    }
    
    public void setExplicitTags (boolean t)
    {
        explicitTags = t;
    }
    
    public boolean getExplicitTags ()
    {
        return explicitTags;
    }

    public void setImplicitTags (boolean t)
    {
        implicitTags = t;
    }
    
    public boolean getImplicitTags ()
    {
        return implicitTags;
    }

    public void setAutomaticTags (boolean t)
    {
        automaticTags = t;
    }
    
    public boolean getAutomaticTags ()
    {
        return automaticTags;
    }
    
    public void setExtensibilityImplied (boolean e)
    {
        extensibilityImplied = e;
    }

    public boolean getExtensibilityImplied ()
    {
        return extensibilityImplied;
    }
    
    public String getModuleName ()
    {
        return getName();
    }
    
    public void setDefinitiveIdentifier (ASNOidComponentList l)
    {
        definitiveIdentifier = l;
    }
    
    public ASNOidComponentList getDefinitiveIdentifier ()
    {
        return definitiveIdentifier;
    }
    
    public void setExports (ASNExports e)
    {
        exports = e;
    }
    
    public ASNExports getExports ()
    {
        return exports;
    }
    
    public void setImports (List<ASNImports> i)
    {
        imports = i;
    }
    
    public List<ASNImports> getImports ()
    {
        return imports;
    }
    
    public void setAssignments (List<ASNAssignment> a)
    {
        assignments = a;
    }
    
    public List<ASNAssignment> getAssignments ()
    {
        return assignments;
    }
    
    public ASNAssignment findAssignment(String name)
    {
    	for (ASNAssignment a : assignments)
    	{
    		if (a.getName().equals(name))
    		{
    			return a;
    		}
    	}
    	return null;
    }
	
	public void print(PrintWriter out)
	{
		out.println(getModuleName());
		if (type.equals(Type.SPPI))
			out.println("PIB-DEFINITIONS ::= BEGIN");
		else
			out.println("DEFINITIONS ::= BEGIN");
		out.println();
		
		out.println("END");
		out.flush();
	}
}
