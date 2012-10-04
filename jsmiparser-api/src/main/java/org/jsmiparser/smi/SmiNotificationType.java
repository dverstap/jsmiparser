/*
 * Copyright 2012 The OpenNMS Group, Inc.
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

import java.util.List;

import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.IdToken;

public class SmiNotificationType extends SmiOidMacro {

    protected SmiType m_type;
    private List<IdToken> m_objectTokens;
    private String m_description;

    public SmiNotificationType(IdToken idToken, SmiModule module) {
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

    public List<IdToken> getObjectTokens() {
        return m_objectTokens;
    }

    public void setAccessToken(List<IdToken> objectTokens) {
        m_objectTokens = objectTokens;
    }

}
