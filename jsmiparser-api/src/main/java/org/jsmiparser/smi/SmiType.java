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

import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.token.IdToken;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO it should be possible to split this up into SmiSequenceType, SmiSequenceOfType and SmiAttributeType
 */
public class SmiType extends SmiSymbol {

    private SmiType m_baseType;
    private SmiPrimitiveType m_primitiveType;
    private List<SmiEnumValue> m_enumValues = new ArrayList<SmiEnumValue>();
    private List<SmiRange> m_rangeConstraint;
    private List<SmiRange> m_sizeConstraint;
    private List<SmiField> m_fields;
    private SmiType m_elementType;

    public SmiType(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }

    public SmiType getBaseType() {
        return m_baseType;
    }

    public void setBaseType(SmiType baseType) {
        m_baseType = baseType;
    }

    public SmiPrimitiveType getPrimitiveType() {
        if (!m_enumValues.isEmpty()) {
            return SmiPrimitiveType.ENUM;
        }
        return m_primitiveType;
    }


    public void setPrimitiveType(SmiPrimitiveType primitiveType) {
        m_primitiveType = primitiveType;
    }

    public SmiVarBindField getVarBindField() {
        return m_primitiveType.getVarBindField();
    }

    public List<SmiEnumValue> getEnumValues() {
        return m_enumValues;
    }

    public String getCodeId() {
        return getModule().getMib().getCodeNamingStrategy().getTypeId(this);
    }

    public SmiEnumValue addEnumValue(IdToken idToken, BigIntegerToken valueToken) {
        SmiEnumValue ev = new SmiEnumValue(this, idToken, valueToken);
        m_enumValues.add(ev);
        return ev;
    }

    public SmiEnumValue getBiggestEnumValue() {
        int currentBiggest = Integer.MIN_VALUE;
        SmiEnumValue result = null;
        for (SmiEnumValue ev : m_enumValues) {
            if (ev.getValue().intValue() > currentBiggest) {
                currentBiggest = ev.getValue().intValue();
                result = ev;
            }
        }
        return result;
    }

    public SmiEnumValue getSmallestEnumValue() {
        int currentSmallest = Integer.MAX_VALUE;
        SmiEnumValue result = null;

        for (SmiEnumValue ev : m_enumValues) {
            if (ev.getValue().intValue() < currentSmallest) {
                currentSmallest = ev.getValue().intValue();
                result = ev;
            }
        }
        return result;
    }

    public SmiEnumValue findEnumValue(int i) {
        for (SmiEnumValue ev : m_enumValues) {
            if (ev.getValue().intValue() == i) {
                return ev;
            }
        }
        return null;
    }

    public SmiEnumValue findEnumValue(String id) {
        for (SmiEnumValue ev : m_enumValues) {
            if (ev.getId().equals(id)) {
                return ev;
            }
        }
        return null;
    }

    public List<SmiRange> getRangeConstraint() {
        return m_rangeConstraint;
    }

    public void setRangeConstraint(List<SmiRange> rangeConstraint) {
        m_rangeConstraint = rangeConstraint;
    }

    public List<SmiRange> getSizeConstraint() {
        return m_sizeConstraint;
    }

    public void setSizeConstraint(List<SmiRange> sizeConstraint) {
        m_sizeConstraint = sizeConstraint;
    }

    public void addField(SmiAttribute col, SmiType fieldType) {
        if (m_fields == null) {
            m_fields = new ArrayList<SmiField>();
        }
        m_fields.add(new SmiField(col, fieldType));
    }

    public List<SmiField> getFields() {
        return m_fields;
    }

    public SmiType getElementType() {
        return m_elementType;
    }

    public void setElementType(SmiType elementType) {
        // TODO set primitive type
        m_elementType = elementType;
    }
}
