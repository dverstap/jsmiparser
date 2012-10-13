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

import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.IdToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmiNotificationType extends SmiOidMacro implements Notification {

    private List<IdToken> m_objectTokens;
    private List<SmiVariable> m_objects = new ArrayList<SmiVariable>();
    private StatusV2 m_statusV2;
    private String m_description;
    private String m_reference;

    public SmiNotificationType(IdToken idToken, SmiModule module, List<IdToken> objectTokens, StatusV2 statusV2, String description, String reference) {
        super(idToken, module);
        m_objectTokens = objectTokens;
        if (m_objectTokens == null) {
            m_objectTokens = Collections.emptyList();
        }
        m_statusV2 = statusV2;
        m_description = description;
        m_reference = reference;
    }

    public void resolveReferences(XRefProblemReporter reporter) {
        for (IdToken objectToken : m_objectTokens) {
            SmiVariable variable = getModule().resolveReference(objectToken, SmiVariable.class, reporter);
            if (variable != null) {
                m_objects.add(variable);
            }
        }
    }

    public List<SmiVariable> getObjects() {
        return m_objects;
    }

    public List<IdToken> getObjectTokens() {
    	return m_objectTokens;
    }

    public StatusV2 getStatusV2() {
        return m_statusV2;
    }

    public String getDescription() {
        return m_description;
    }

    public String getReference() {
        return m_reference;
    }

}
