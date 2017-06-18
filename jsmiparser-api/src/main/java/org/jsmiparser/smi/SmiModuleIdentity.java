/*
 * Copyright 2017 Leo Bayer.
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

import java.util.List;

public class SmiModuleIdentity {
    private final String m_lastUpdated;
    private final String m_organization;
    private final String m_contactInfo;
    private final String m_description;

    private final List<SmiModuleRevision> m_revisions;

    public SmiModuleIdentity(String lastUpdated, String organization, String contactInfo, String description, List<SmiModuleRevision> revisions) {
        m_lastUpdated = lastUpdated;
        m_organization = organization;
        m_contactInfo = contactInfo;
        m_description = description;
        m_revisions = revisions;
    }

    public String getLastUpdated() {
        return m_lastUpdated;
    }

    public String getOrganization() {
        return m_organization;
    }

    public String getContactInfo() {
        return m_contactInfo;
    }

    public String getDescription() {
        return m_description;
    }

    public List<SmiModuleRevision> getRevisions() {
        return m_revisions;
    }
}
