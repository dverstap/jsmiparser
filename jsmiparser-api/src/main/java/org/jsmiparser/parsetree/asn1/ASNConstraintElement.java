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

import java.util.*;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNConstraintElement {
    
	public enum Type
	{
		UNKNOWN,
		OPERATION,
		VALUE,
		VALUERANGE,
		SIZECONSTRAINT,
		FROMCONSTRAINT,
		ELEMENTSET,
		INCLUDETYPE,
		PATTERN,
		WITHCOMPONENT,
		WITHCOMPONENTS,
		NAMEDCONSTRAINT
	}

	public enum Operator
	{
		UNION,
		INTERSECTION
	}
    
    
    private Type type;
    private Operator operator;
    private ASNConstraintElement operand;
    private List operations;
    private boolean allExceptConstraint;
    private ASNConstraint constraint;
    private ASNConstraintElement constraintElement;
    private ASNValue value;
    private ASNValueRange valueRange;
    private ASNConstraintElement elementSet;
    private boolean includes;
    private ASNType constraintType;
    private boolean ellipsis;
    private List typeConstraintList;
    private String constraintName;
    private boolean present;
    private boolean absent;
    private boolean optional;
    
    /** Creates a new instance of ASNConstraintElement */
    public ASNConstraintElement() {
        setType (Type.UNKNOWN);
        
        /* Do collections */
        operations = new Vector ();
        typeConstraintList = new Vector ();
    }
    
    public ASNConstraintElement (Type t)
    {
        setType (t);
        
        /* Do collections */
        operations = new Vector ();
    }
    
    public void setType (Type t)
    {
        type = t;
    }
    
    public Type getType ()
    {
        return type;
    }
    
    public void setOperand (ASNConstraintElement o)
    {
        operand = o;
    }
    
    public ASNConstraintElement getOperand ()
    {
        return operand;
    }
    
    public void setOperator (Operator o)
    {
        operator = o;
    }
    
    public Operator getOperator()
    {
    	return operator;
    }
    
    public void setOperations (List o)
    {
        operations = o;
    }
    
    public List getOperations ()
    {
        return operations;
    }
    
    public void setAllExceptConstraint (boolean a)
    {
        allExceptConstraint = a;
    }
    
    public boolean isAllExceptConstraint ()
    {
        return allExceptConstraint;
    }
    
    public void setConstraintElement (ASNConstraintElement c)
    {
        constraintElement = c;
    }
    
    public ASNConstraintElement getConstraintElement ()
    {
        return constraintElement;
    }
    
    public void setConstraint (ASNConstraint c)
    {
        constraint = c;
    }
    
    public ASNConstraint getConstraint ()
    {
        return constraint;
    }

    public void setValue (ASNValue v)
    {
        value = v;
    }
    
    public ASNValue getValue ()
    {
        return value;
    }
    
    public void setValueRange (ASNValueRange r)
    {
        valueRange = r;
    }
    
    public ASNValueRange getValueRange ()
    {
        return valueRange;
    }

    public void setElementSet (ASNConstraintElement e)
    {
        elementSet = e;
    }
    
    public ASNConstraintElement getElementSet ()
    {
        return elementSet;
    }

    public void setIncludes (boolean i)
    {
        includes = i;
    }
    
    public boolean isIncludes ()
    {
        return includes;
    }
    
    public void setConstraintType (ASNType t)
    {
        constraintType = t;
    }
    
    public ASNType getConstraintType ()
    {
        return constraintType;
    }

    public void setEllipsis (boolean e)
    {
        ellipsis = e;
    }
    
    public boolean isEllipsis ()
    {
        return ellipsis;
    }
    
    public void setTypeConstraintList (List l)
    {
        typeConstraintList = l;
    }     
    
    public List getTypeConstraintList ()
    {
        return typeConstraintList;
    }
    
    public void setConstraintName (String name)
    {
        constraintName = name;
    }
    
    public String getConstraintName ()
    {
        return constraintName;
    }
    
    public void setPresent (boolean p)
    {
        present = p;
    }
    
    public boolean isPresent ()
    {
        return present;
    }
    
    public void setAbsent (boolean a)
    {
        absent = a;
    }
    
    public boolean isAbsent ()
    {
        return absent;
    }
    
    public void setOptional (boolean o)
    {
        optional = o;
    }
    
    public boolean isOptional ()
    {
        return optional;
    }
}
