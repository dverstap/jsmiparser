/*
 * Copyright 2005 Davy Verstappen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jsmiparser.smi;

import org.jsmiparser.util.token.IdToken;

public class SmiObjectType extends SmiOidMacro {

    protected SmiType m_type;
    private SmiPrimitiveType m_primitiveType;

    public SmiObjectType(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }


    public SmiPrimitiveType getPrimitiveType() {
		if (m_type != null) {
			return m_type.getPrimitiveType();
		}
		else {
			return m_primitiveType;
		}
	}

    public void setPrimitiveType(SmiPrimitiveType primitiveType) {
		m_primitiveType = primitiveType;
	}

    public SmiType getType() {
		return m_type;
	}

    public void setType(SmiType type) {
		m_type = type;
	}

    public void resolveReferences() {
        m_type = m_type.resolveThis();
    }
}
