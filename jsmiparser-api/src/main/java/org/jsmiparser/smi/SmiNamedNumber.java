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

import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.token.IdToken;

import java.math.BigInteger;

/**
 * Used to represent enum values and bit fields.
 */
public class SmiNamedNumber {

	private SmiType m_type;
	private IdToken m_idToken;
	private BigIntegerToken m_valueToken;

    public SmiNamedNumber(IdToken id, BigIntegerToken value) {
		super();
//		m_type = type;
		m_idToken = id;
		m_valueToken = value;
	}

    public Location getLocation() {
        return m_idToken.getLocation();
    }

	public String getId() {
		return m_idToken.getId();
	}

    public BigInteger getValue() {
		return m_valueToken.getValue();
	}

    public String getCodeId() {
		return m_type.getModule().getMib().getCodeNamingStrategy().getEnumValueId(this);
	}

    public SmiType getType() {
        return m_type;
    }

    public void setType(SmiType type) {
        m_type = type;
    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    public BigIntegerToken getValueToken() {
        return m_valueToken;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (m_idToken != null) {
            sb.append(m_idToken.getId());
        }
        if (m_valueToken != null) {
            sb.append('(').append(m_valueToken.getValue()).append(')');
        }
        return sb.toString();
    }
}
