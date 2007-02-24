/*
 * Copyright 2006 Davy Verstappen.
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
import org.jsmiparser.util.token.IntegerToken;

public class OidComponent {

    private final IdToken m_idToken;
    private final IntegerToken m_valueToken;
    private SmiOidValue m_symbol;

    public OidComponent(IdToken idToken, IntegerToken intToken) {
        m_idToken = idToken;
        m_valueToken = intToken;
    }

    public String getId() {
        if (m_idToken != null) {
            return m_idToken.getId();
        } else if (m_symbol != null) {
            return m_symbol.getId();
        }
        return null;
    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    public IntegerToken getValueToken() {
        return m_valueToken;
    }

    public SmiOidValue getSymbol() {
        return m_symbol;
    }

    void setSymbol(SmiOidValue symbol) {
        m_symbol = symbol;
    }

}
