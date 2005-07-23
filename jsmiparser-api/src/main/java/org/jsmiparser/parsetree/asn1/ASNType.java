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
 * @author  nigelss
 */
public class ASNType extends AbstractSymbol {
    
	public enum Enum
	{
		UNKNOWN,
		DEFINED,
		SELECTION,
		ANY,
		BOOLEAN,
		EMBEDDED,
		BITSTRING,
		CHARACTERSTRING,
		CHOICE,
		ENUM,
		EXTERNAL,
		INTEGER,
		NULL,
		OBJECTIDENTIFIER,
		OCTETSTRING,
		REAL,
		RELATIVEOID,
		SEQUENCE,
		SET,
		SEQUENCEOF,
		SETOF,
		TAG,
		OPERATIONMACRO,
		ERRORSMACRO,
		SMITYPE,
		SMIOBJECTTYPEMACRO,
		SMIMODULEIDENTITYMACRO,
		SMIOBJECTIDENTITYMACRO,
		SMINOTIFICATIONTYPEMACRO,
		SMITEXTUALCONVENTIONMACRO,
		SMIOBJECTGROUPMACRO,
		SMINOTIFICATIONGROUPMACRO,
		SMIMODULECOMPLIANCEMACRO,
		SMIAGENTCAPABILITIESMACRO,
		SMITRAPTYPEMACRO
	}
	
    private Enum type_;
    
	/**
	 * @param context
	 * @param type
	 */
	public ASNType(Context context, Enum type) {
		super(context);
		type_ = type;
	}
	
	/**
	 * @return Returns the type.
	 */
	public Enum getType() {
		return type_;
	}
	
	/**
	 * @param type The type to set.
	 */
	public void setType(Enum type) {
		type_ = type;
	}
}
