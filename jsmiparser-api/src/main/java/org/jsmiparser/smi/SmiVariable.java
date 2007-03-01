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
import org.jsmiparser.util.token.QuotedStringToken;

import java.util.List;

/**
 * Besides the OBJECT-TYPE fields that are specific to SNMP variable definitions,
 * this class also contains some methods that make it easier to deal with the recursive nature
 * of the SmiType definitions.
 */
public class SmiVariable extends SmiObjectType {

    private QuotedStringToken m_unitsToken;

    public SmiVariable(IdToken idToken, SmiModule module, SmiType type, QuotedStringToken unitsToken) {
		super(idToken, module);
        setType(type);
        m_unitsToken = unitsToken;
    }

    public String getCodeConstantId() {
		return getModule().getMib().getCodeNamingStrategy().getCodeConstantId(this);
	}
	
	public String getFullCodeConstantId() {
		return getModule().getMib().getCodeNamingStrategy().getFullCodeConstantId(this);
	}

	public String getCodeOid() {
		return getOidStr();
	}

	public String getCodeId() {
		return getModule().getMib().getCodeNamingStrategy().getVariableId(this);
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

    public SmiRow getRow() {
        SmiOidValue parent = getParent();
        if (parent instanceof SmiRow) {
            return (SmiRow) parent;
        }
        return null;
    }

    public SmiTable getTable() {
        SmiRow row = getRow();
        if (row != null) {
            return row.getTable();
        }
        return null;
    }

    public boolean isColumn() {
        return getRow() != null;
    }

    public boolean isScalar() {
        return getRow() == null;
    }

    public String getUnits() {
        return m_unitsToken != null ? m_unitsToken.getValue() : null;
    }

    public QuotedStringToken getUnitsToken() {
        return m_unitsToken;
    }

    public SmiTextualConvention getTextualConvention() {
        SmiType type = m_type;
        while (type != null) {
            if (type instanceof SmiTextualConvention) {
                return (SmiTextualConvention) type;
            }
            type = type.getBaseType();
        }
        return null;
    }

    public SmiPrimitiveType getPrimitiveType() {
        return m_type.getPrimitiveType();
    }

    public List<SmiNamedNumber> getEnumValues() {
        SmiType type = m_type;
        while (type != null) {
            if (type.getEnumValues() != null) {
                return type.getEnumValues();
            }
            type = type.getBaseType();
        }
        return null;
    }

    public List<SmiNamedNumber> getBitFields() {
        SmiType type = m_type;
        while (type != null) {
            if (type.getBitFields() != null) {
                return type.getBitFields();
            }
            type = type.getBaseType();
        }
        return null;
    }

    public List<SmiRange> getRangeConstraints() {
        SmiType type = m_type;
        while (type != null) {
            if (type.getRangeConstraints() != null) {
                return type.getRangeConstraints();
            }
            type = type.getBaseType();
        }
        return null;
    }

    public List<SmiRange> getSizeConstraints() {
        SmiType type = m_type;
        while (type != null) {
            if (type.getSizeConstraints() != null) {
                return type.getSizeConstraints();
            }
            type = type.getBaseType();
        }
        return null;
    }
}
