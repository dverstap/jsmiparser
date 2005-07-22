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
 * This identifies each of the primitive types as defined in the SNMPv2-SMI module.
 * Each one of them is also mapped to the field that should be used to send/receive
 * values of this type.
 * 
 * @author davy
 *
 */
public enum SmiPrimitiveType {
	
	// Basic ASN.1 type:
	ENUM(SmiVarBindField.INTEGER_VALUE, ""),
	
	// From the SimpleSyntax choice:
	INTEGER(SmiVarBindField.INTEGER_VALUE, "Int"),
	OCTET_STRING(SmiVarBindField.STRING_VALUE, "Octs"),
	OBJECT_IDENTIFIER(SmiVarBindField.OBJECTID_VALUE, "Oid"),
	
	// Defined ASN.1 type:
	INTEGER_32(SmiVarBindField.INTEGER_VALUE, "Integer32"),
	
	// From the ApplicationSyntax choice:
	IP_ADDRESS(SmiVarBindField.IPADDRESS_VALUE, "IpAddress"),
	COUNTER_32(SmiVarBindField.COUNTER_VALUE, "Counter32"),
	GAUGE_32(SmiVarBindField.UNSIGNED_INTEGER_VALUE, "Gauge32"),
	UNSIGNED_32(SmiVarBindField.UNSIGNED_INTEGER_VALUE, "Unsigned32"),
	TIME_TICKS(SmiVarBindField.TIMETICKS_VALUE, "TimeTicks"),
	OPAQUE(SmiVarBindField.ARBITRARY_VALUE, "Opaque"),
	COUNTER_64(SmiVarBindField.BIG_COUNTER_VALUE, "Counter64"),
	
	// From the TextualConvention macro:
	BITS(SmiVarBindField.STRING_VALUE, "Bits")
	;
	
	//private static Map<String, SmiPrimitiveType> xmlValueMap_;
	private SmiVarBindField varBindField_;
	private String xmlValue_;

	
	private SmiPrimitiveType(SmiVarBindField varBindField, String xmlValue)
	{
//		if (xmlValueMap_ == null) {
//			xmlValueMap_ = new HashMap<String, SmiPrimitiveType>();
//		}
		varBindField_ = varBindField;
		xmlValue_ = xmlValue;
		//xmlValueMap_.put(xmlValue_, this);
	}
	
	public SmiVarBindField getVarBindField() {
		return varBindField_;
	}
	
	public SmiPrimitiveType fromXmlValue(String xmlValue)
	{
		for (SmiPrimitiveType pt : values()) {
			if (pt.xmlValue_.equals(xmlValue)) {
				return pt;
			}
		}
		return null;
	}


}
