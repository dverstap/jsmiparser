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
import org.jsmiparser.util.multimap.GenMultiMap;
import org.jsmiparser.util.token.BigIntegerToken;
import org.jsmiparser.util.token.IdToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SmiMib implements SmiSymbolContainer {

    private static final Logger m_log = Logger.getLogger(SmiMib.class);


    private static final UserDataContainer.CopyAction SHARED = new UserDataContainer.CopyAction.Shared();

    private Map<String, SmiModule> m_moduleMap = new LinkedHashMap<String, SmiModule>();
    private SmiCodeNamingStrategy m_codeNamingStrategy;
    private SmiOidValue m_rootNode;

    GenMultiMap<String, SmiType> m_typeMap = GenMultiMap.hashMap();
    GenMultiMap<String, SmiSymbol> m_symbolMap = GenMultiMap.hashMap();
    GenMultiMap<String, SmiVariable> m_variableMap = GenMultiMap.hashMap();
    GenMultiMap<String, SmiVariable> m_scalarMap = GenMultiMap.hashMap();
    GenMultiMap<String, SmiTable> m_tableMap = GenMultiMap.hashMap();
    GenMultiMap<String, SmiRow> m_rowMap = GenMultiMap.hashMap();
    GenMultiMap<String, SmiVariable> m_columnMap = GenMultiMap.hashMap();

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
            m_variableMap.putAll(module.m_variableMap);
            m_rowMap.putAll(module.m_rowMap);
            m_tableMap.putAll(module.m_tableMap);
            m_symbolMap.putAll(module.m_symbolMap);
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

    /**
     * @param id The required id of the symbols.
     * @return All the SmiSymbol with the required id, or an empty list if none are found.
     */
    public List<SmiSymbol> findSymbols(String id) {
        return m_symbolMap.getAll(id);
    }

    /**
     * @param id The required id of the unique symbol.
     * @return The unique symbol with the required id, or null if it is not found.
     * @throws IllegalArgumentException if there is more than one symbol with the required id.
     */
    public SmiSymbol findSymbol(String id) throws IllegalArgumentException {
        return m_symbolMap.getOne(id);
    }

    /**
     * @param moduleId The module where you want to look for the symbol, or null if you want to look through the whole mib.
     * @param id The required id of the unique symbol.
     * @return The unique symbol with the required id, or null if it is not found.
     * @throws IllegalArgumentException if the module is not found or if there is more than one symbol with the required id.
     */
    public SmiSymbol findSymbol(String moduleId, String id) throws IllegalArgumentException {
        if (moduleId != null) {
            SmiModule module = findModule(moduleId);
            if (module == null) {
                throw new IllegalArgumentException("Module " + moduleId + " could not be found.");
            }
            return module.findSymbol(id);
        } else {
            return m_symbolMap.getOne(id);
        }
    }

    public Collection<SmiSymbol> getSymbols() {
        return m_symbolMap.values();
    }

    public List<SmiType> findTypes(String id) {
        return m_typeMap.getAll(id);
    }

    public SmiType findType(String id) {
        return m_typeMap.getOne(id);
    }

    public Collection<SmiType> getTypes() {
        return m_typeMap.values();
    }

    public List<SmiVariable> findVariables(String id) {
        return m_variableMap.getAll(id);
    }

    public SmiVariable findVariable(String id) {
        return m_variableMap.getOne(id);
    }

    public Collection<SmiVariable> getVariables() {
        return m_variableMap.values();
    }

    public List<SmiVariable> findScalars(String id) {
        return m_scalarMap.getAll(id);
    }

    public SmiVariable findScalar(String id) {
        return m_scalarMap.getOne(id);
    }

    public Collection<SmiVariable> getScalars() {
        return m_scalarMap.values();
    }

    public List<SmiTable> findTables(String id) {
        return m_tableMap.getAll(id);
    }

    public SmiTable findTable(String id) {
        return m_tableMap.getOne(id);
    }

    public Collection<SmiTable> getTables() {
        return m_tableMap.values();
    }

    public List<SmiRow> findRows(String id) {
        return m_rowMap.getAll(id);
    }

    public SmiRow findRow(String id) {
        return m_rowMap.getOne(id);
    }

    public Collection<SmiRow> getRows() {
        return m_rowMap.values();
    }

    public List<SmiVariable> findColumns(String id) {
        return m_columnMap.getAll(id);
    }

    public SmiVariable findColumn(String id) {
        return m_columnMap.getOne(id);
    }

    public Collection<SmiVariable> getColumns() {
        return m_columnMap.values();
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

        if (findSymbols("itu").isEmpty()) {
            SmiOidValue itu = new SmiOidValue(new IdToken(location, "itu"), m_internalModule);
            ArrayList<OidComponent> ituOid = new ArrayList<OidComponent>();
            ituOid.add(new OidComponent(null, new BigIntegerToken(location, false, "0")));
            itu.setOidComponents(ituOid);
            itu.setParent(m_rootNode);
            m_internalModule.addSymbol(itu);
            m_internalModule.m_symbolMap.put(itu.getId(), itu);
            m_symbolMap.put(itu.getId(), itu);
        }

        if (findSymbols("iso").isEmpty()) {
            SmiOidValue iso = new SmiOidValue(new IdToken(location, "iso"), m_internalModule);
            ArrayList<OidComponent> isoOid = new ArrayList<OidComponent>();
            isoOid.add(new OidComponent(null, new BigIntegerToken(location, false, "1")));
            iso.setOidComponents(isoOid);
            iso.setParent(m_rootNode);
            m_internalModule.addSymbol(iso);
            m_internalModule.m_symbolMap.put(iso.getId(), iso);
            m_symbolMap.put(iso.getId(), iso);
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
