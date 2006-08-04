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

import org.jsmiparser.util.data.LinkedHashMapKeyList;
import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.token.IdToken;

import java.util.LinkedHashMap;
import java.util.List;

// TODO idtokens
public class SmiImports {

    private SmiModule m_importingModule;
    private IdToken m_importedModuleToken;
    private SmiModule m_importedModule;
    private LinkedHashMap<SmiSymbol, IdToken> m_symbolMap = new LinkedHashMap<SmiSymbol,  IdToken>();
    private List<SmiSymbol> m_symbols = new LinkedHashMapKeyList<SmiSymbol, IdToken>(m_symbolMap);

    public SmiImports(SmiModule importingModule, IdToken importedModuleToken, SmiModule importedModule) {
        m_importingModule = importingModule;
        m_importedModuleToken = importedModuleToken;
        m_importedModule = importedModule;
    }

    public SmiModule getImportingModule() {
        return m_importingModule;
    }

    public SmiModule getImportedModule() {
        return m_importedModule;
    }

    public List<SmiSymbol> getSymbols() {
        return m_symbols;
    }

    public IdToken getImportedModuleToken() {
        return m_importedModuleToken;
    }

    public Location getLocation() {
        return m_importedModuleToken.getLocation();
    }

    public void addSymbol(IdToken idToken, SmiSymbol symbol) {
        m_symbolMap.put(symbol, idToken);
    }

    public SmiSymbol find(String id) {
        for (SmiSymbol symbol : m_symbolMap.keySet()) {
            if (symbol.getId().equals(id)) {
                return symbol;
            }
        }
        return null;
    }
}
