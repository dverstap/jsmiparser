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
import org.jsmiparser.phase.xref.XRefProblemReporter;

public class SmiObjectType extends SmiOidMacro {

    protected SmiType m_type;
    private ObjectTypeAccessV1 m_accessV1;
    private ObjectTypeAccessV2 m_accessV2;
    private String m_description;

    public SmiObjectType(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }

    public SmiType getType() {
		return m_type;
	}

    public void setType(SmiType type) {
		m_type = type;
	}

    public void resolveReferences(XRefProblemReporter reporter) {
        m_type = m_type.resolveThis(reporter);
    }

    public String getDescription() {
        return m_description;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public void setStatus(StatusAll status) {
        m_status = status;
    }

    public ObjectTypeAccessV1 getAccessV1() {
        return m_accessV1;
    }

    public void setAccessV1(ObjectTypeAccessV1 accessV1) {
        m_accessV1 = accessV1;
    }

    public ObjectTypeAccessV2 getAccessV2() {
        return m_accessV2;
    }

    public void setAccessV2(ObjectTypeAccessV2 accessV2) {
        m_accessV2 = accessV2;
    }

    public ObjectTypeAccessV2 getMaxAccess() {
        return m_accessV2;
    }

    public void setMaxAccess(ObjectTypeAccessV2 accessV2) {
        m_accessV2 = accessV2;
    }

    public AccessAll getAccessAll() {
        if (m_accessV1 != null) {
            return m_accessV1.getAccessAll();
        } else {
            return m_accessV2.getAccessAll();
        }
    }

}
