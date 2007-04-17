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
import org.jsmiparser.phase.xref.XRefProblemReporter;

import java.util.List;

/**
 * Besides the OBJECT-TYPE fields that are specific to SNMP variable definitions,
 * this class also contains some methods that make it easier to deal with the recursive nature
 * of the SmiType definitions.
 */
public class SmiVariable extends SmiObjectType {

    private final QuotedStringToken m_unitsToken;
    private final SmiDefaultValue m_defaultValue;

    public SmiVariable(IdToken idToken, SmiModule module, SmiType type, QuotedStringToken unitsToken, SmiDefaultValue defaultValue) {
		super(idToken, module);
        setType(type);
        m_unitsToken = unitsToken;
        m_defaultValue = defaultValue;
        if (m_defaultValue != null) {
            m_defaultValue.m_variable = this;
        }
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
        if (getNode() != null && getNode().getParent() != null) {
            SmiOidValue oidValue = getNode().getParent().getSingleValue(SmiOidValue.class, getModule());
            if (oidValue instanceof SmiRow) {
                return (SmiRow) oidValue;
            }
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

    public SmiDefaultValue getDefaultValue() {
        return m_defaultValue;
    }

    public SmiNamedNumber resolveBitField(IdToken idToken, XRefProblemReporter reporter) {
        for (SmiNamedNumber nn : getBitFields()) {
            if (nn.getId().equals(idToken.getId())) {
                return nn;
            }
        }
        reporter.reportCannotFindBitField(idToken);
        return null;
    }

    public SmiNamedNumber resolveEnumConstant(IdToken idToken, XRefProblemReporter reporter) {
        for (SmiNamedNumber nn : getEnumValues()) {
            if (nn.getId().equals(idToken.getId())) {
                return nn;
            }
        }
        reporter.reportCannotFindEnumConstant(idToken);
        return null;
    }
}
