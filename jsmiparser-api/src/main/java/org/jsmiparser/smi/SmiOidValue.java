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

import org.jsmiparser.phase.oid.OidNode;
import org.jsmiparser.util.token.IdToken;

import java.util.List;

public class SmiOidValue extends SmiValue {

    private OidNode m_oidComponent;

    public SmiOidValue(IdToken idToken, SmiModule module) {
        super(idToken, module);
    }

    public OidNode getOidComponent() {
        return m_oidComponent;
    }

    public void setOidComponent(OidNode oidComponent) {
        m_oidComponent = oidComponent;
    }

    public String getOid() {
        if (m_oidComponent == null) {
            return null;
            // TODO throw new IllegalStateException("oidComponent not set on " + getIdToken());
        }

        return m_oidComponent.getDecimalDottedStr();
    }

    public SmiOidValue getParent() {
        return null; // TODO
    }

    public List<? extends SmiOidValue> getChildren() {
        return null; // TODO
    }
}
