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
import org.jsmiparser.phase.xref.XRefProblemReporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmiImports {

    private final SmiModule m_importerModule;
    private final IdToken m_moduleToken;
    private final List<IdToken> m_symbolTokens;

    private SmiModule m_module;
    private List<SmiSymbol> m_symbols;

    public SmiImports(SmiModule importerModule, IdToken moduleToken, List<IdToken> symbolTokens) {
        assert(importerModule != null);
        assert(moduleToken != null);
        assert(symbolTokens != null);
        
        m_importerModule = importerModule;

        m_moduleToken = moduleToken;
        m_symbolTokens = Collections.unmodifiableList(symbolTokens);
    }

    public SmiModule getModule() {
        return m_module;
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

    public void resolveImports(XRefProblemReporter reporter) {
        List<SmiSymbol> symbols = new ArrayList<SmiSymbol>();
        m_module = m_importerModule.getMib().findModule(m_moduleToken.getId());
        if (m_module != null) {
            for (IdToken idToken : getSymbolTokens()) {
                SmiSymbol symbol = getModule().findSymbol(idToken.getId());
                if (symbol != null) {
                    symbols.add(symbol);
                } else {
                    reporter.reportCannotFindImportedSymbol(idToken, m_moduleToken);
                }
            }
        } else {
            reporter.reportCannotFindModule(m_moduleToken);
        }
        m_symbols = Collections.unmodifiableList(symbols);
    }
}
