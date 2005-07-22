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
public class ASNOidComponent extends AbstractSymbol {
    
	private String name_; // optional
    private long number_;
    
    /** Creates a new instance of ASNOidComponent */
    public ASNOidComponent(Context context, String name, long number) {
    	super(context);
    	name_ = name;
    	number_ = number;
    }

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name_;
	}
	
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		name_ = name;
	}
	
	/**
	 * @return Returns the number.
	 */
	public long getNumber() {
		return number_;
	}
	
	/**
	 * @param number The number to set.
	 */
	public void setNumber(long number) {
		number_ = number;
	}
}
