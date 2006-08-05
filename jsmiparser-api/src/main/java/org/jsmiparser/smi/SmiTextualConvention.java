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

public class SmiTextualConvention extends SmiType {

    private String m_displayHint;
    private StatusV2 m_statusV2;
    private String m_description;
    private String m_reference;

    public SmiTextualConvention(IdToken idToken, SmiModule module, String displayHint, StatusV2 statusV2, String description, String reference) {
        super(idToken, module);
        m_displayHint = displayHint;
        m_statusV2 = statusV2;
        m_description = description;
        m_reference = reference;
    }

    public String getDisplayHint() {
        return m_displayHint;
    }

    public void setDisplayHint(String displayHint) {
        m_displayHint = displayHint;
    }

    public StatusV2 getStatusV2() {
        return m_statusV2;
    }

    public void setStatusV2(StatusV2 statusV2) {
        m_statusV2 = statusV2;
    }

    public String getDescription() {
        return m_description;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public String getReference() {
        return m_reference;
    }

    public void setReference(String reference) {
        m_reference = reference;
    }

}
