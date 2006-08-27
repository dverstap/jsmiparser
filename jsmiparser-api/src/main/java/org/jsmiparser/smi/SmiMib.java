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
import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.location.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class SmiMib extends SmiSymbolContainer {

    private Map<String, SmiModule> m_moduleMap = new HashMap<String, SmiModule>();
    private SmiCodeNamingStrategy m_codeNamingStrategy;
    private SmiOidValue m_rootNode;

    int m_dummyOidNodesCount;

    public SmiMib(SmiCodeNamingStrategy codeNamingStrategy) {
        super(null);
        //assert(codeNamingStrategy != null);
        m_codeNamingStrategy = codeNamingStrategy;

        SmiModule internalMib = buildInternalMib();
        m_moduleMap.put(internalMib.getId(), internalMib);
    }

    private SmiModule buildInternalMib() {
        Location location = new Location("JSMI_INTERNAL_MIB");
        SmiModule module = new SmiModule(this, new IdToken(location, "JSMI_INTERNAL_MIB"));

        m_rootNode = new SmiOidValue(new IdToken(location, "JSMI_INTERNAL_ROOT_NODE"), module);
        m_rootNode.setOidComponents(Collections.<OidComponent>emptyList());

        SmiOidValue itu = new SmiOidValue(new IdToken(location, "itu"), module);
        ArrayList<OidComponent> ituOid = new ArrayList<OidComponent>();
        ituOid.add(new OidComponent(null, new BigIntegerToken(location, false, "0")));
        itu.setOidComponents(ituOid);
        itu.setParent(m_rootNode);
        module.addSymbol(itu);

        SmiOidValue iso = new SmiOidValue(new IdToken(location, "iso"), module);
        ArrayList<OidComponent> isoOid = new ArrayList<OidComponent>();
        isoOid.add(new OidComponent(null, new BigIntegerToken(location, false, "1")));
        iso.setOidComponents(isoOid);
        iso.setParent(m_rootNode);
        module.addSymbol(iso);

        return module;
    }

    public SmiOidValue getRootNode() {
        return m_rootNode;
    }

    public SmiModule findModule(String id) {
        return m_moduleMap.get(id);
    }

    public Collection<SmiModule> getModules() {
        return m_moduleMap.values();

    }

    public SmiCodeNamingStrategy getCodeNamingStrategy() {
        return m_codeNamingStrategy;
    }

    public void setCodeNamingStrategy(SmiCodeNamingStrategy codeNamingStrategy) {
        m_codeNamingStrategy = codeNamingStrategy;
    }

    public SmiModule createModule(IdToken idToken) {
        return new SmiModule(this, idToken);
    }

    public SmiModule createModule() {
        // used to create class definition module from XML using the ReflectContentHandler.
        return new SmiModule(this, null);
    }

    public void determineInheritanceRelations() {
        for (SmiRow row : m_rowMap.values()) {
            if (row.getAugments() != null) {
                row.addParentRow(row.getAugments());
            } else if (row.getIndexes().size() == 1) {
                SmiIndex index = row.getIndexes().get(0);
                SmiRow indexRow = index.getColumn().getRow();
                if (row != indexRow) {
                    row.addParentRow(indexRow);
                }
            } else if (row.getIndexes().size() > 1) {
                SmiIndex lastIndex = row.getIndexes().get(row.getIndexes().size()-1);
                SmiRow lastIndexRow = lastIndex.getColumn().getRow();
                if (row != lastIndexRow && row.hasSameIndexes(lastIndexRow)) {
                    row.addParentRow(lastIndexRow);
                }
            }
        }
    }

    void addModule(String id, SmiModule module) {
        // TODO unique id check
        m_moduleMap.put(id, module);
    }

    public void fillTables() {
        for (SmiModule module : m_moduleMap.values()) {
            m_typeMap.putAll(module.m_typeMap);
            m_attributeMap.putAll(module.m_attributeMap);
            m_rowMap.putAll(module.m_rowMap);
            m_tableMap.putAll(module.m_tableMap);
            m_scalarMap.putAll(module.m_scalarMap);
            m_columnMap.putAll(module.m_columnMap);
            m_classMap.putAll(module.m_classMap);
            m_symbolMap.putAll(module.m_symbolMap);
        }
    }

    public SmiTextualConvention findTextualConvention(String id) {
        SmiType type = findType(id);
        if (type instanceof SmiTextualConvention) {
            return (SmiTextualConvention) type;
        }
        return null;
    }

    public int getDummyOidNodesCount() {
        return m_dummyOidNodesCount;
    }
}
