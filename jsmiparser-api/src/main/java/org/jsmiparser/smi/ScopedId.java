package org.jsmiparser.smi;

import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.phase.xref.XRefProblemReporter;

/*
* Copyright 2007 Davy Verstappen.
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
public class ScopedId {

    private final SmiModule m_localModule;
    private final IdToken m_moduleToken;
    private final IdToken m_symbolToken;
    private SmiModule m_module;
    private SmiSymbol m_symbol;

    public ScopedId(SmiModule localModule, IdToken moduleToken, IdToken symbolToken) {
        m_localModule = localModule;
        m_moduleToken = moduleToken;
        m_symbolToken = symbolToken;
    }


    public SmiModule getLocalModule() {
        return m_localModule;
    }

    public IdToken getModuleToken() {
        return m_moduleToken;
    }

    public IdToken getSymbolToken() {
        return m_symbolToken;
    }

    public SmiModule getModule() {
        return m_module;
    }

    public SmiSymbol getSymbol() {
        return m_symbol;
    }

    public void resolveReferences(XRefProblemReporter reporter) {
        if (m_moduleToken != null) {
            m_module = m_localModule.getMib().resolveModule(m_moduleToken, reporter);
        }
        if (m_module != null) {
            m_symbol = m_module.resolveReference(m_symbolToken, reporter);
        } else {
            m_symbol = m_localModule.resolveReference(m_symbolToken, reporter);
        }
    }
}
