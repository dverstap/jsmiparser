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
public class ASNCharacterStringType extends ASNType {

	public enum Type
	{
		ANY,
		BMP,
		GENERALIZEDTIME,
		GENERAL,
		GRAPHIC,
		IA5,
		ISO646,
		NUMERIC,
		PRINTABLE,
		TELETEX,
		T61,
		UNIVERSAL,
		UTF8,
		UTC_TIME,
		VIDEOTEX,
		VISIBLE
	}

    
    private Type characterSet_;
    private ASNConstraint constraint_;
    
    /** Creates a new instance of ASNCharacterStringType */
    public ASNCharacterStringType(Context context) 
    {
        super(context, Enum.CHARACTERSTRING);
        characterSet_ = Type.ANY;
    }
    
	/**
	 * @return Returns the characterSet.
	 */
	public Type getCharacterSet() {
		return characterSet_;
	}
	
	/**
	 * @param characterSet The characterSet to set.
	 */
	public void setCharacterSet(Type characterSet) {
		characterSet_ = characterSet;
	}
	
	/**
	 * @return Returns the constraint.
	 */
	public ASNConstraint getConstraint() {
		return constraint_;
	}
	
	/**
	 * @param constraint The constraint to set.
	 */
	public void setConstraint(ASNConstraint constraint) {
		constraint_ = constraint;
	}
}
