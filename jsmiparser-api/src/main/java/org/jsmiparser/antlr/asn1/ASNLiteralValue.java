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

package org.jsmiparser.antlr.asn1;

import java.math.BigInteger;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNLiteralValue extends ASNValue {
    
	public enum LType
	{
		UNKNOWN,
		TRUE,
		FALSE,
		NULL,
		STRING,
		PLUS_INFINITY,
		MINUS_INFINITY,
		NUMBER,
		BSTRING,
		HSTRING
	}
	
    
    private LType literalType;
    private String stringValue;
    private BigInteger numberValue;
    
    private boolean minus;
    
    /** Creates a new instance of ASNLiteralValue */
    public ASNLiteralValue(Context context) {
        super(context, Type.LITERAL);
        setLiteralType (LType.UNKNOWN);
    }
    
    public ASNLiteralValue (Context context, LType literalType)
    {
        super(context, Type.LITERAL);
        this.literalType = literalType;
    }
    
    public ASNLiteralValue (Context context, String s)
    {
        super(context, Type.LITERAL);
        setLiteralType (LType.STRING);
        stringValue = s;
    }
    
    public ASNLiteralValue (Context context, LType t, String s)
    {
        super (context, Type.LITERAL);
        setLiteralType (t);
        setStringValue (s);
    }
    
    public void setLiteralType (LType t)
    {
        literalType = t;
    }
    
    public LType getLiteralType ()
    {
        return literalType;
    }
    
    public void setStringValue (String s)
    {
        stringValue = s;
    }
    
    public String getStringValue ()
    {
        return stringValue;
    }
    
    public void setNumberValue (BigInteger n)
    {
        if (minus)
        {
            numberValue = n.negate();
            
        } else {
            numberValue = n;
        }
    }
    
    public BigInteger getNumberValue ()
    {
        return numberValue;
    }
    
    public void setMinus (boolean m)
    {
        minus = m;
    }
    
    public boolean isMinus ()
    {
        return minus;
    }
}
