/*
 * Copyright 2005 Davy Verstappen.
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
package org.jsmiparser.smi;


/**
 * This class represents an enum type that is not defined as an ASN.1 type assignment,
 * nor defined as a TextualConvention. Instead it is generated from the enum value
 * in the single scalar or column that uses these.
 * 
 * 
 * @author davy
 *
 */
public class SmiSingleAttributeEnum extends SmiType {

	private SmiAttribute singleAttribute_;
	
	public SmiSingleAttributeEnum(SmiAttribute attr) {
		super(attr.getModule().getMib().getCodeNamingStrategy().getSingleAttributeEnumId(attr),
				attr.getModule());
		singleAttribute_ = attr;
	}

	public SmiAttribute getSingleAttribute() {
		return singleAttribute_;
	}
}
