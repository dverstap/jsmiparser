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


/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNChoiceValue extends ASNValue implements NamedSymbol {
    
    private String name_;
    private ASNValue value_;
    private boolean colon_; // DV ?
    
    /** Creates a new instance of ASNChoiceValue */
    public ASNChoiceValue(Context context, String name) {
    	super(context, Type.CHOICEVALUE);
    	name_ = name;
    }

	/* (non-Javadoc)
	 * @see org.parsesmi.asn1.NamedSymbol#getName()
	 */
	public String getName() {
		return name_;
	}
    
	/**
	 * @return Returns the colon.
	 */
	public boolean isColon() {
		return colon_;
	}
	
	/**
	 * @param colon The colon to set.
	 */
	public void setColon(boolean colon) {
		colon_ = colon;
	}
	
	/**
	 * @return Returns the value.
	 */
	public ASNValue getValue() {
		return value_;
	}
	
	/**
	 * @param value The value to set.
	 */
	public void setValue(ASNValue value) {
		value_ = value;
	}
}
