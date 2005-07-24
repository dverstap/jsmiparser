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

/**
 *
 * @author  Nigel Sheridan-Smith
 */
/**
 * @author davy
 *
 */
public class ASNNamedNumber extends AbstractNamedSymbol {
    
    private ASNValue intValue_;
    private ASNValue definedValue_;
    
    /** Creates a new instance of ASNNamedNumber */
    public ASNNamedNumber(Context context, ASNNamedNumberType nnt,
    		IdToken idToken, ASNValue intValue, ASNValue definedValue) {
    	super(context, idToken);
    	
    	intValue_ = intValue;
    	definedValue_ = definedValue;
    	
    	// TODO check unique name and value
    	nnt.getNamedNumbers().add(this);
    }
    

	/**
	 * @return Returns the definedValue.
	 */
	public ASNValue getDefinedValue() {
		return definedValue_;
	}
	
	/**
	 * @param definedValue The definedValue to set.
	 */
	public void setDefinedValue(ASNValue definedValue) {
		definedValue_ = definedValue;
	}
	
	/**
	 * @return Returns the intValue.
	 */
	public ASNValue getIntValue() {
		return intValue_;
	}
	
	/**
	 * @param intValue The intValue to set.
	 */
	public void setIntValue(ASNValue intValue) {
		intValue_ = intValue;
	}
}
