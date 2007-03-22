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

import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.IdToken;

import java.util.ArrayList;
import java.util.List;

public class SmiType extends SmiSymbol {

    public final static SmiPrimitiveType[] APPLICATION_TYPES = {
            SmiPrimitiveType.IP_ADDRESS,
            SmiPrimitiveType.COUNTER_32,
            SmiPrimitiveType.GAUGE_32,
            // SmiPrimitiveType.UNSIGNED_32, has the same application tag as GAUGE_32
            SmiPrimitiveType.TIME_TICKS,
            SmiPrimitiveType.OPAQUE,
            null, // tag 5 is undefined
            SmiPrimitiveType.COUNTER_64
    };

    private SmiType m_baseType;
    private final SmiPrimitiveType m_primitiveType;
    private List<SmiNamedNumber> m_enumValues;
    private List<SmiNamedNumber> m_bitFields;
    private List<SmiRange> m_rangeConstraints;
    private List<SmiRange> m_sizeConstraints;
    private List<SmiField> m_fields;
    private IdToken m_elementTypeToken;
    private SmiType m_elementType;

    public SmiType(IdToken idToken, SmiModule module, SmiPrimitiveType primitiveType) {
        super(idToken, module);
        m_primitiveType = primitiveType;
    }

    public SmiType(IdToken idToken, SmiModule module, int applicationTag) {
        super(idToken, module);
        if (applicationTag >= 0) {
            if (applicationTag >= APPLICATION_TYPES.length) {
                throw new IllegalArgumentException("Application tag " + applicationTag + " is invalid at: " + idToken);
            }
            if (APPLICATION_TYPES[applicationTag] == SmiPrimitiveType.GAUGE_32
                    && "Unsigned32".equals(getId())) {
                m_primitiveType = SmiPrimitiveType.UNSIGNED_32;
            } else {
                m_primitiveType = APPLICATION_TYPES[applicationTag];
            }
        } else {
            m_primitiveType = null;
        }
    }

    public SmiType(IdToken idToken, SmiModule module) {
        super(idToken, module);
        m_primitiveType = null;

/*
        if (idToken != null) {
            String id = idToken.getId();
            if (id.equals("Integer32")) {
                m_primitiveType = SmiPrimitiveType.INTEGER_32;
            } else if (id.equals("")) {
                m_primitiveType = SmiPrimitiveType.;
            } else if (id.equals("")) {
                m_primitiveType = SmiPrimitiveType.;
            } else if (id.equals("")) {
                m_primitiveType = SmiPrimitiveType.;
            } else if (id.equals("")) {
                m_primitiveType = SmiPrimitiveType.;
            } else if (id.equals("")) {
                m_primitiveType = SmiPrimitiveType.;
            } else if (id.equals("")) {
                m_primitiveType = SmiPrimitiveType.;
            } else if (id.equals("")) {
                m_primitiveType = SmiPrimitiveType.;
            }
        }
*/
    }

    /**
     * The base type from which this type is derived (by giving it named numbers, constraints, a name...).
     * All types have a base type, except INTEGER, OCTET STRING, OBJECT IDENTIFIER and BITS.
     */
    public SmiType getBaseType() {
        return m_baseType;
    }

    public void setBaseType(SmiType baseType) {
        m_baseType = baseType;
    }

    public SmiPrimitiveType getPrimitiveType() {
        if (m_enumValues != null) {
            return SmiPrimitiveType.ENUM;
        }
        if (m_bitFields != null) {
            return SmiPrimitiveType.BITS;
        }

        // TODO fix this hack
        // m_baseType != null && m_baseType == SmiConstants.INTEGER_TYPE
        if ("Integer32".equals(getId()) && "SNMPv2-SMI".equals(getModule().getId())) {
            return SmiPrimitiveType.INTEGER_32;
        }

        if (m_primitiveType == null && m_baseType != null) {
            return m_baseType.getPrimitiveType();
        }

        return m_primitiveType;
    }

    public SmiVarBindField getVarBindField() {
        return getPrimitiveType().getVarBindField();
    }

    public List<SmiNamedNumber> getEnumValues() {
        return m_enumValues;
    }

    public void setEnumValues(List<SmiNamedNumber> enumValues) {
        if (enumValues != null) {
            setType(enumValues);
        }
        m_enumValues = enumValues;
    }

    private void setType(List<SmiNamedNumber> enumValues) {
        for (SmiNamedNumber namedNumber : enumValues) {
            namedNumber.setType(this);
        }
    }

    public List<SmiNamedNumber> getBitFields() {
        return m_bitFields;
    }

    public void setBitFields(List<SmiNamedNumber> bitFields) {
        if (bitFields != null) {
            setType(bitFields);
        }
        m_bitFields = bitFields;
    }

    public String getCodeId() {
        return getModule().getMib().getCodeNamingStrategy().getTypeId(this);
    }

    public SmiNamedNumber getBiggestEnumValue() {
        int currentBiggest = Integer.MIN_VALUE;
        SmiNamedNumber result = null;
        for (SmiNamedNumber ev : m_enumValues) {
            if (ev.getValue().intValue() > currentBiggest) {
                currentBiggest = ev.getValue().intValue();
                result = ev;
            }
        }
        return result;
    }

    public SmiNamedNumber getSmallestEnumValue() {
        int currentSmallest = Integer.MAX_VALUE;
        SmiNamedNumber result = null;

        for (SmiNamedNumber ev : m_enumValues) {
            if (ev.getValue().intValue() < currentSmallest) {
                currentSmallest = ev.getValue().intValue();
                result = ev;
            }
        }
        return result;
    }

    public SmiNamedNumber findEnumValue(int i) {
        for (SmiNamedNumber ev : m_enumValues) {
            if (ev.getValue().intValue() == i) {
                return ev;
            }
        }
        return null;
    }

    public SmiNamedNumber findEnumValue(String id) {
        for (SmiNamedNumber ev : m_enumValues) {
            if (ev.getId().equals(id)) {
                return ev;
            }
        }
        return null;
    }

    public List<SmiRange> getRangeConstraints() {
        return m_rangeConstraints;
    }

    public void setRangeConstraints(List<SmiRange> rangeConstraints) {
        m_rangeConstraints = rangeConstraints;
    }

    public List<SmiRange> getSizeConstraints() {
        return m_sizeConstraints;
    }

    public void setSizeConstraints(List<SmiRange> sizeConstraints) {
        m_sizeConstraints = sizeConstraints;
    }

    public void addField(IdToken col, SmiType fieldType) {
        if (m_fields == null) {
            m_fields = new ArrayList<SmiField>();
        }
        m_fields.add(new SmiField(this, col, fieldType));
    }

    public List<SmiField> getFields() {
        return m_fields;
    }

    public IdToken getElementTypeToken() {
        return m_elementTypeToken;
    }

    public void setElementTypeToken(IdToken elementTypeToken) {
        m_elementTypeToken = elementTypeToken;
    }

    public SmiType getElementType() {
        return m_elementType;
    }

    public void setElementType(SmiType elementType) {
        // TODO set primitive type
        m_elementType = elementType;
    }


    public SmiType resolveThis(XRefProblemReporter reporter, SmiType ignored) {
        if (m_baseType != null) {
            m_baseType = m_baseType.resolveThis(reporter, this);
        }
        return this;
    }

    public void resolveReferences(XRefProblemReporter reporter) {
        assert (getIdToken() != null);
        assert (!(this instanceof SmiReferencedType));

        if (m_baseType != null) {
            m_baseType = m_baseType.resolveThis(reporter, this);
        }

        if (m_elementTypeToken != null) {
            m_elementType = getModule().resolveReference(m_elementTypeToken, SmiType.class, reporter);
        }
        if (m_fields != null) {
            for (SmiField field : m_fields) {
                field.resolveReferences(reporter);
            }
        }
    }

}
