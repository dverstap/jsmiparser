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
import java.util.ArrayList;
import java.util.List;

public class SmiType extends SmiSymbol {

    private SmiPrimitiveType m_primitiveType;
    private List<SmiEnumValue> m_enumValues = new ArrayList<SmiEnumValue>();


    public SmiType(IdToken idToken, SmiModule module) {
        super(idToken, module);
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

    public SmiEnumValue addEnumValue(String id, BigInteger value) {
        SmiEnumValue ev = new SmiEnumValue(this, id, value);
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

    // TODO ranges
    // TODO size restrictions
}
