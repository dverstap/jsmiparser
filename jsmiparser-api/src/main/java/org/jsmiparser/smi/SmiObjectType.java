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

import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.IdToken;

public class SmiObjectType extends SmiOidMacro {

    protected SmiType m_type;
    private IdToken m_accessToken;
    private IdToken m_maxAccessToken;
    private String m_description;

    private ObjectTypeAccessV1 m_accessV1;
    private ObjectTypeAccessV2 m_accessV2;
    private AccessAll m_accessAll;

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
        m_type = m_type.resolveThis(reporter, null);

        if (m_accessToken != null) {
            m_accessV1 = ObjectTypeAccessV1.find(m_accessToken.getId(), false);
            if (m_accessV1 != null) {
                m_accessAll = m_accessV1.getAccessAll();
            } else {
                reporter.reportInvalidAccess(m_accessToken);
                m_accessAll = AccessAll.find(m_accessToken.getId(), false);
            }
        } else {
            m_accessV2 = ObjectTypeAccessV2.find(m_maxAccessToken.getId(), false);
            if (m_accessV2 != null) {
                m_accessAll = m_accessV2.getAccessAll();
            } else {
                reporter.reportInvalidMaxAccess(m_maxAccessToken);
                m_accessAll = AccessAll.find(m_maxAccessToken.getId(), false);
            }
        }
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

    public IdToken getAccessToken() {
        return m_accessToken;
    }

    public void setAccessToken(IdToken accessToken) {
        m_accessToken = accessToken;
    }

    public IdToken getMaxAccessToken() {
        return m_maxAccessToken;
    }

    public void setMaxAccessToken(IdToken maxAccessToken) {
        m_maxAccessToken = maxAccessToken;
    }

    public ObjectTypeAccessV1 getAccessV1() {
        return m_accessV1;
    }

    public ObjectTypeAccessV2 getAccessV2() {
        return m_accessV2;
    }

    public ObjectTypeAccessV2 getMaxAccess() {
        return m_accessV2;
    }

    public AccessAll getAccessAll() {
        return m_accessAll;
    }

}
