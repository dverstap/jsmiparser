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

import org.jsmiparser.util.token.IdToken;

import java.math.BigInteger;

public abstract class SmiAttribute extends SmiSymbol {
	
	private SmiType type_;
	private String oid_;
	private SmiSingleAttributeEnum singleAttributeEnum_;
	private SmiPrimitiveType primitiveType_;


	public SmiAttribute(IdToken idToken, SmiModule module) {
		super(idToken, module);
	}

	public abstract SmiClass getSmiClass();

	public SmiPrimitiveType getPrimitiveType() {
		if (type_ != null) {
			return type_.getPrimitiveType();
		}
		else {
			return primitiveType_;
		}
	}
	
	public void setPrimitiveType(SmiPrimitiveType primitiveType) {
		primitiveType_ = primitiveType;
	}

	public SmiType getType() {
		return type_;
	}

	public void setType(SmiType type) {
		type_ = type;
	}

	public String getOid() {
		return oid_;
	}

	public void setOid(String oid) {
		oid_ = oid;
	}

	public String getCodeConstantId() {
		return getModule().getMib().getCodeNamingStrategy().getCodeConstantId(this);
	}
	
	public String getFullCodeConstantId() {
		return getModule().getMib().getCodeNamingStrategy().getFullCodeConstantId(this);
	}

	public String getCodeOid() {
		return oid_;
	}

	public String getCodeId() {
		return getModule().getMib().getCodeNamingStrategy().getAttributeId(this);
	}
	
	public SmiSingleAttributeEnum getSingleAttributeEnum() {
		return singleAttributeEnum_;
	}

	public SmiEnumValue addEnumValue(String id, BigInteger value) {
		if (singleAttributeEnum_ == null) {
			singleAttributeEnum_ = getModule().createSingleAttributeEnum(this);
			singleAttributeEnum_.setPrimitiveType(SmiPrimitiveType.ENUM);
		}
		type_ = singleAttributeEnum_;
		return singleAttributeEnum_.addEnumValue(id, value);
	}
	
	public String getRequestMethodId() {
		return getModule().getMib().getCodeNamingStrategy().getRequestMethodId(this);
	}

	public String getGetterMethodId() {
		return getModule().getMib().getCodeNamingStrategy().getGetterMethodId(this);
	}

	public String getSetterMethodId() {
		return getModule().getMib().getCodeNamingStrategy().getSetterMethodId(this);
	}




	
}
