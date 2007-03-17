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

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.utils.UserDataContainer;
import org.apache.log4j.Logger;
import org.jsmiparser.exception.SmiException;
import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.token.IntegerToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SmiMib {

    private static final Logger m_log = Logger.getLogger(SmiMib.class);


    private static final UserDataContainer.CopyAction SHARED = new UserDataContainer.CopyAction.Shared();

    private Map<String, SmiModule> m_moduleMap = new LinkedHashMap<String, SmiModule>();
    private SmiCodeNamingStrategy m_codeNamingStrategy;
    private SmiOidValue m_rootNode;

    SmiSymbolMapImpl<SmiType> m_typeMap = new SmiSymbolMapImpl<SmiType>(SmiType.class, m_moduleMap);
    SmiSymbolMapImpl<SmiTextualConvention> m_textualConventionMap = new SmiSymbolMapImpl<SmiTextualConvention>(SmiTextualConvention.class, m_moduleMap);
    SmiSymbolMapImpl<SmiSymbol> m_symbolMap = new SmiSymbolMapImpl<SmiSymbol>(SmiSymbol.class, m_moduleMap);
    SmiSymbolMapImpl<SmiVariable> m_variableMap = new SmiSymbolMapImpl<SmiVariable>(SmiVariable.class, m_moduleMap);
    SmiSymbolMapImpl<SmiTable> m_tableMap = new SmiSymbolMapImpl<SmiTable>(SmiTable.class, m_moduleMap);
    SmiSymbolMapImpl<SmiRow> m_rowMap = new SmiSymbolMapImpl<SmiRow>(SmiRow.class, m_moduleMap);
    SmiSymbolMapImpl<SmiVariable> m_columnMap = new SmiSymbolMapImpl<SmiVariable>(SmiVariable.class, m_moduleMap);
    SmiSymbolMapImpl<SmiVariable> m_scalarMap = new SmiSymbolMapImpl<SmiVariable>(SmiVariable.class, m_moduleMap);
    SmiSymbolMapImpl<SmiOidValue> m_oidValueMap = new SmiSymbolMapImpl<SmiOidValue>(SmiOidValue.class, m_moduleMap);
    SmiSymbolMapImpl<SmiObjectType> m_objectTypesMap = new SmiSymbolMapImpl<SmiObjectType>(SmiObjectType.class, m_moduleMap);

    int m_dummyOidNodesCount;
    private SmiModule m_internalModule;

    public SmiMib(SmiCodeNamingStrategy codeNamingStrategy) {
        //assert(codeNamingStrategy != null);
        m_codeNamingStrategy = codeNamingStrategy;

        SmiModule internalMib = buildInternalMib();
        m_moduleMap.put(internalMib.getId(), internalMib);
    }

    private SmiModule buildInternalMib() {
        Location location = new Location("JSMI_INTERNAL_MIB");
        m_internalModule = new SmiModule(this, new IdToken(location, "JSMI_INTERNAL_MIB"));

        m_rootNode = new SmiOidValue(new IdToken(location, "JSMI_INTERNAL_ROOT_NODE"), m_internalModule);
        m_rootNode.setOidComponents(Collections.<OidComponent>emptyList());

        return m_internalModule;
    }

    public SmiOidValue getRootNode() {
        return m_rootNode;
    }

    public SmiModule getInternalModule() {
        return m_internalModule;
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
        SmiModule oldModule = m_moduleMap.get(idToken.getId());
        if (oldModule != null) {
            // TODO error handling
            // should do this in the XRefPhase
            throw new IllegalStateException("Duplicate module: " + oldModule.getIdToken() + " is already defined when adding: " + idToken);
        }
        SmiModule module = new SmiModule(this, idToken);
        m_moduleMap.put(module.getId(), module);
        return module;
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
                SmiIndex lastIndex = row.getIndexes().get(row.getIndexes().size() - 1);
                SmiRow lastIndexRow = lastIndex.getColumn().getRow();
                if (row != lastIndexRow && row.hasSameIndexes(lastIndexRow)) {
                    row.addParentRow(lastIndexRow);
                }
            }
        }
    }

    void addModule(String id, SmiModule module) {
        SmiModule oldModule = m_moduleMap.get(id);
        if (oldModule != null) {
            throw new IllegalArgumentException("Mib already contains a module: " + oldModule);
        }
        m_moduleMap.put(id, module);
    }

    public void fillTables() {
        // TODO deal with double defines
        for (SmiModule module : m_moduleMap.values()) {
            module.fillTables();
            m_typeMap.putAll(module.m_typeMap);
            m_textualConventionMap.putAll(module.m_textualConventionMap);
            m_variableMap.putAll(module.m_variableMap);
            m_rowMap.putAll(module.m_rowMap);
            m_tableMap.putAll(module.m_tableMap);
            m_symbolMap.putAll(module.m_symbolMap);
            m_oidValueMap.putAll(module.m_oidValueMap);
            m_objectTypesMap.putAll(module.m_objectTypeMap);
        }
    }

    public void fillExtraTables() {
        // TODO deal with double defines
        for (SmiModule module : m_moduleMap.values()) {
            module.fillExtraTables();
            m_scalarMap.putAll(module.m_scalarMap);
            m_columnMap.putAll(module.m_columnMap);
        }
    }


    public int getDummyOidNodesCount() {
        return m_dummyOidNodesCount;
    }

    public DirectedGraph createGraph() {
        Map<SmiModule, Vertex> vertexMap = new HashMap<SmiModule, Vertex>();
        DirectedGraph graph = new DirectedSparseGraph();
        for (SmiModule module : m_moduleMap.values()) {
            Vertex v = new DirectedSparseVertex();
            v.setUserDatum(SmiModule.class, module, SHARED);
            graph.addVertex(v);
            vertexMap.put(module, v);
        }

        for (SmiModule module : m_moduleMap.values()) {
            Vertex v = vertexMap.get(module);
            for (SmiModule importedModule : module.getImportedModules()) {
                Vertex importedVertex = vertexMap.get(importedModule);
                try {
                    m_log.debug("Adding dependency from " + module.getId() + " to " + importedModule.getId());
                    graph.addEdge(new DirectedSparseEdge(v, importedVertex));
                } catch (Exception e) {
                    String msg = "Exception while added dependency from " + module.getId() + " to " + importedModule.getId();
                    throw new SmiException(msg, e);
                }
            }
        }
        return graph;
    }

    public SmiSymbolMap<SmiType> getTypes() {
        return m_typeMap;
    }

    public SmiSymbolMap<SmiTextualConvention> getTextualConventions() {
        return m_textualConventionMap;
    }

    public SmiSymbolMap<SmiSymbol> getSymbols() {
        return m_symbolMap;
    }

    public SmiSymbolMap<SmiVariable> getVariables() {
        return m_variableMap;
    }

    public SmiSymbolMap<SmiTable> getTables() {
        return m_tableMap;
    }

    public SmiSymbolMap<SmiRow> getRows() {
        return m_rowMap;
    }

    public SmiSymbolMap<SmiVariable> getColumns() {
        return m_columnMap;
    }

    public SmiSymbolMap<SmiVariable> getScalars() {
        return m_scalarMap;
    }

    public SmiSymbolMap<SmiOidValue> getOidValues() {
        return m_oidValueMap;
    }

    public SmiSymbolMap<SmiObjectType> getObjectTypes() {
        return m_objectTypesMap;
    }

    /**
     * This method can be used to find the best match for an OID.
     * By comparing the length of the OID of the result and the input OID you can
     * determine how many and which subIds where not matched.
     *
     * @param oid For which the best match is searched.
     * @return Best matching SmiOidValue, or null if none is found.
     */
    public SmiOidValue findByOidPrefix(int[] oid) {
        SmiOidValue parent = getRootNode();
        for (int subId : oid) {
            SmiOidValue result = parent.findChild(subId);
            if (result == null) {
                return parent;
            }
            parent = result;
        }
        return null;
    }

    public Set<SmiModule> findModules(SmiVersion version) {
        Set<SmiModule> result = new HashSet<SmiModule>();
        for (SmiModule module : m_moduleMap.values()) {
            if (module.getVersion() == null || module.getVersion() == version) {
                result.add(module);
            }
        }
        return result;
    }

    public void defineMissingStandardOids() {
        Location location = m_internalModule.getIdToken().getLocation();

        if (m_symbolMap.findAll("itu").isEmpty()) {
            SmiOidValue itu = new SmiOidValue(new IdToken(location, "itu"), m_internalModule);
            ArrayList<OidComponent> ituOid = new ArrayList<OidComponent>();
            ituOid.add(new OidComponent(null, new IntegerToken(location, 0)));
            itu.setOidComponents(ituOid);
            itu.setParent(m_rootNode);
            m_internalModule.addSymbol(itu);
            m_internalModule.m_symbolMap.put(itu.getId(), itu);
            m_symbolMap.put(itu.getId(), itu);
            m_oidValueMap.put(itu.getId(), itu);
        }

        if (m_symbolMap.findAll("iso").isEmpty()) {
            SmiOidValue iso = new SmiOidValue(new IdToken(location, "iso"), m_internalModule);
            ArrayList<OidComponent> isoOid = new ArrayList<OidComponent>();
            isoOid.add(new OidComponent(null, new IntegerToken(location, 1)));
            iso.setOidComponents(isoOid);
            iso.setParent(m_rootNode);
            m_internalModule.addSymbol(iso);
            m_internalModule.m_symbolMap.put(iso.getId(), iso);
            m_symbolMap.put(iso.getId(), iso);
            m_oidValueMap.put(iso.getId(), iso);
        }
    }

    public SmiModule resolveModule(IdToken moduleToken, XRefProblemReporter reporter) {
        SmiModule result = m_moduleMap.get(moduleToken.getId());
        if (result == null) {
            reporter.reportCannotFindModule(moduleToken);
        }
        return result;
    }
}
