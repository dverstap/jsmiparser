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

import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.token.IdToken;

import java.util.List;
import java.util.ArrayList;

// TODO idtokens
public class SmiImports {

    private IdToken m_moduleToken;
    private SmiModule m_module;
    private List<IdToken> m_symbolTokens;
    private List<SmiSymbol> m_symbols = new ArrayList<SmiSymbol>();

    public SmiImports(IdToken moduleToken, List<IdToken> symbolTokens) {
        m_moduleToken = moduleToken;
        m_symbolTokens = symbolTokens;
    }

    public SmiModule getModule() {
        return m_module;
    }

    public void setModule(SmiModule module) {
        m_module = module;
    }

    public List<SmiSymbol> getSymbols() {
        return m_symbols;
    }

    public IdToken getModuleToken() {
        return m_moduleToken;
    }

    public List<IdToken> getSymbolTokens() {
        return m_symbolTokens;
    }

    public Location getLocation() {
        return m_moduleToken.getLocation();
    }

    public SmiSymbol find(String id) {
        for (SmiSymbol symbol : m_symbols) {
            if (symbol.getId().equals(id)) {
                return symbol;
            }
        }
        return null;
    }
}
