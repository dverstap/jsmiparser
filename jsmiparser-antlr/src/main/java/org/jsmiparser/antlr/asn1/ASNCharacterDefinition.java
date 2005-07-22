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
public class ASNCharacterDefinition {
    
	public enum Type
	{
		STRING,
		TUPLEQUAD,
		DEFINEDVALUE
	}

    private Type type_;
    private String stringValue_;
    private ASNTupleQuadValue tupleQuadValue_;
    private ASNDefinedValue definedValue_;
    
    /** Creates a new instance of ASNCharacterDefinition */
    public ASNCharacterDefinition() {
    }

    
	/**
	 * @return Returns the definedValue.
	 */
	public ASNDefinedValue getDefinedValue() {
		return definedValue_;
	}
	
	/**
	 * @param definedValue The definedValue to set.
	 */
	public void setDefinedValue(ASNDefinedValue definedValue) {
		type_ = Type.DEFINEDVALUE;
		definedValue_ = definedValue;
	}
	
	/**
	 * @return Returns the stringValue.
	 */
	public String getStringValue() {
		return stringValue_;
	}
	
	/**
	 * @param stringValue The stringValue to set.
	 */
	public void setStringValue(String stringValue) {
		type_ = Type.STRING;
		stringValue_ = stringValue;
	}
	
	/**
	 * @return Returns the tupleQuad.
	 */
	public ASNTupleQuadValue getTupleQuadValue() {
		return tupleQuadValue_;
	}
	
	/**
	 * @param tupleQuad The tupleQuad to set.
	 */
	public void setTupleQuadValue(ASNTupleQuadValue tupleQuad) {
		type_ = Type.TUPLEQUAD;
		tupleQuadValue_ = tupleQuad;
	}
	
	/**
	 * @return Returns the type.
	 */
	public Type getType() {
		return type_;
	}
}
