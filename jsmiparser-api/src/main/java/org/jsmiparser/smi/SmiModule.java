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

import org.apache.log4j.Logger;
import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.IdToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SmiModule {

    private static final Logger m_log = Logger.getLogger(SmiModule.class);

    private SmiMib m_mib;
    private IdToken m_idToken;

    private List<SmiImports> m_imports = new ArrayList<SmiImports>();
    private List<SmiSymbol> m_symbols = new ArrayList<SmiSymbol>();

    Map<String, SmiType> m_typeMap = new LinkedHashMap<String, SmiType>();
    Map<String, SmiTextualConvention> m_textualConventionMap = new LinkedHashMap<String, SmiTextualConvention>();
    Map<String, SmiSymbol> m_symbolMap = new LinkedHashMap<String, SmiSymbol>();
    Map<String, SmiVariable> m_variableMap = new LinkedHashMap<String, SmiVariable>();
    Map<String, SmiVariable> m_scalarMap = new LinkedHashMap<String, SmiVariable>();
    Map<String, SmiTable> m_tableMap = new LinkedHashMap<String, SmiTable>();
    Map<String, SmiRow> m_rowMap = new LinkedHashMap<String, SmiRow>();
    Map<String, SmiVariable> m_columnMap = new LinkedHashMap<String, SmiVariable>();
    Map<String, SmiOidValue> m_oidValueMap = new LinkedHashMap<String, SmiOidValue>();
    Map<String, SmiObjectType> m_objectTypeMap = new LinkedHashMap<String, SmiObjectType>();

    private int m_v1Features = 0;
    private int m_v2Features = 0;
    private SmiVersion m_version;
    private boolean m_isSmiDefinitionModule;


    public SmiModule(SmiMib mib, IdToken idToken) {
        m_mib = mib;
        if (idToken == null) {
            throw new IllegalArgumentException();
        }
        setIdToken(idToken);
        m_isSmiDefinitionModule = SmiConstants.SMI_DEFINITION_MODULE_NAMES.contains(idToken.getId());
    }


    public int getV1Features() {
        return m_v1Features;
    }

    public void incV1Features() {
        m_v1Features++;
    }

    public int getV2Features() {
        return m_v2Features;
    }

    // TODO this needs to be applied in more cases, such as SNMPv2-CONF, where the V2 macro's are defined
    public void incV2Features() {
        m_v2Features++;
    }

    public SmiVersion getVersion() {
        if (m_version == null && (m_v1Features != 0 || m_v2Features != 0)) {
            m_version = determineVersion();
        }
        return m_version;
    }

    private SmiVersion determineVersion() {
        if (m_v1Features > m_v2Features) {
            return SmiVersion.V1;
        } else if (m_v1Features < m_v2Features) {
            return SmiVersion.V2;
        }
        m_log.info("interesting mib with equal amount of V1 and V2 features: " + m_v1Features + ": " + getIdToken());
        return null;
    }

    public SmiType findType(String id) {
        return m_typeMap.get(id);
    }

    public Collection<SmiType> getTypes() {
        return m_typeMap.values();
    }

    public SmiTextualConvention findTextualConvention(String id) {
        return m_textualConventionMap.get(id);
    }

    public Collection<SmiTextualConvention> getTextualConventions() {
        return m_textualConventionMap.values();
    }

    public Collection<SmiSymbol> getSymbols() {
        // TODO when the symbols have been resolved, set the m_symbols list to null?
        if (m_symbols != null) {
            return m_symbols;
        } else {
            return m_symbolMap.values();
        }
    }

    public SmiSymbol findSymbol(String id) {
        return m_symbolMap.get(id);
    }

    public SmiVariable findVariable(String id) {
        return m_variableMap.get(id);
    }

    public Collection<SmiVariable> getVariables() {
        return m_variableMap.values();
    }

    public SmiVariable findScalar(String id) {
        return m_scalarMap.get(id);
    }

    public Collection<SmiVariable> getScalars() {
        return m_scalarMap.values();
    }

    public SmiTable findTable(String id) {
        return m_tableMap.get(id);
    }

    public Collection<SmiTable> getTables() {
        return m_tableMap.values();
    }

    public SmiRow findRow(String id) {
        return m_rowMap.get(id);
    }

    public Collection<SmiRow> getRows() {
        return m_rowMap.values();
    }

    public SmiVariable findColumn(String id) {
        return m_columnMap.get(id);
    }

    public Collection<SmiVariable> getColumns() {
        return m_columnMap.values();
    }

    public SmiOidValue findOidValue(String id) {
        return m_oidValueMap.get(id);
    }

    public Collection<SmiOidValue> getOidValues() {
        return m_oidValueMap.values();
    }

    public SmiObjectType findObjectType(String id) {
        return m_objectTypeMap.get(id);
    }

    public Collection<SmiObjectType> getObjectTypes() {
        return m_objectTypeMap.values();
    }

    public void setIdToken(IdToken id) {
        assert (m_idToken == null);
        m_idToken = id;
        m_mib.addModule(id.getId(), this);
    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    public String getId() {
        return m_idToken.getId();
    }

    public SmiMib getMib() {
        return m_mib;
    }

    public SmiType createType(IdToken idToken) {
        SmiType type = new SmiType(idToken, this);
        m_typeMap.put(idToken.getId(), type);
        return type;
    }

/*
    public SmiTextualConvention createTextualConvention(IdToken idToken) {
        SmiTextualConvention tc = new SmiTextualConvention(idToken, this);
        m_typeMap.put(idToken.getId(), tc);
        return tc;
    }
*/

    public String getCodeId() {
        return getMib().getCodeNamingStrategy().getModuleId(this);
    }

    public String getFullCodeId() {
        return getMib().getCodeNamingStrategy().getFullModuleId(this);
    }

    public SmiTable createTable(IdToken idToken) {
        SmiTable table = new SmiTable(idToken, this);
        m_tableMap.put(idToken.getId(), table);
        return table;
    }

    public SmiRow createRow(IdToken idToken) {
        SmiRow row = new SmiRow(idToken, this);
        m_rowMap.put(idToken.getId(), row);
        return row;
    }

    public String getFullVariableOidClassId() {
        return getMib().getCodeNamingStrategy().getFullVariableOidClassId(this);
    }

    public String getVariableOidClassId() {
        return getMib().getCodeNamingStrategy().getVariableOidClassId(this);
    }

    public boolean isSmiDefinitionModule() {
        return m_isSmiDefinitionModule;
    }

    /**
     * @return The list of IMPORTS statements. Note that there may be more than one IMPORTS statement per module,
     *         so this is not guaranteed to be unique.
     */
    public List<SmiImports> getImports() {
        return m_imports;
    }

    /**
     * @return Unique set of imported modules.
     */
    public Set<SmiModule> getImportedModules() {
        Set<SmiModule> result = new HashSet<SmiModule>();
        for (SmiImports anImport : m_imports) {
            result.add(anImport.getModule());
        }
        return result;
    }

    public void fillTables() {
        for (SmiSymbol symbol : m_symbols) {
            put(m_tableMap, SmiTable.class, symbol);
            put(m_variableMap, SmiVariable.class, symbol);
            put(m_typeMap, SmiType.class, symbol);
            put(m_textualConventionMap, SmiTextualConvention.class, symbol);
            put(m_rowMap, SmiRow.class, symbol);
            put(m_oidValueMap, SmiOidValue.class, symbol);
            put(m_objectTypeMap, SmiObjectType.class, symbol);
        }
    }

    public void fillExtraTables() {
        for (SmiVariable variable : m_variableMap.values()) {
            if (variable.isColumn()) {
                m_columnMap.put(variable.getId(), variable);
            } else {
                m_scalarMap.put(variable.getId(), variable);
            }
        }
    }

    private <T extends SmiSymbol> void put(Map<String, T> map, Class<T> clazz, SmiSymbol symbol) {
        if (clazz.isInstance(symbol)) {
            map.put(symbol.getId(), clazz.cast(symbol));
        }
    }

    public void addSymbol(SmiSymbol symbol) {
        m_symbols.add(symbol);
        m_symbolMap.put(symbol.getId(), symbol);
    }

    /**
     * Resolves a reference from within this module to a symbol in the same module, an imported module
     * or in the whole mib
     *
     * @param idToken  Token of the identifier that has to be resolved.
     * @param reporter If not null, the reporter will be used to reporter the not found error message.
     * @return The symbol that was found, or null.
     */
    public SmiSymbol resolveReference(IdToken idToken, XRefProblemReporter reporter) {
// doesn't work anymore with hardcoded missing symbols
//        if (!idToken.getLocation().getSource().equals(getIdToken().getLocation().getSource())) {
//            // note this check is not entirely fool-proof in case multiple modules are located in one file
//            throw new IllegalArgumentException("Resolving references is only allowed from inside the same module");
//        }

        SmiSymbol result = findSymbol(idToken.getId());
        if (result == null) {
            result = findImportedSymbol(idToken.getId());
        }
        if (result == null) {
            List<SmiSymbol> symbols = getMib().getSymbols().findAll(idToken.getId());
            if (symbols.size() == 1) {
                result = symbols.get(0);
            } else if (symbols.size() > 0) {
                result = determineBestMatch(idToken, symbols);
            }
        }
        if (result == null && reporter != null) {
            reporter.reportCannotFindSymbol(idToken);
        }

        return result;
    }

    public <T extends SmiSymbol> T resolveReference(IdToken idToken, Class<T> expectedClass, XRefProblemReporter reporter) {
        SmiSymbol result = resolveReference(idToken, reporter);
        if (result != null) {
            if (expectedClass.isInstance(result)) {
                return expectedClass.cast(result);
            } else {
                reporter.reportFoundSymbolButWrongType(idToken, expectedClass, result.getClass());
            }
        }
        return null;
    }

    private SmiSymbol determineBestMatch(IdToken idToken, List<SmiSymbol> symbols) {
        SmiSymbol result = determineBestMatchBasedOnSnmpVersion(symbols);
        if (result != null) {
            return result;
        }
        result = determineBestMatchBasedOnOtherImports(idToken, symbols);
        if (result != null) {
            return result;
        }
        if (m_log.isDebugEnabled()) {
            m_log.debug("Couldn't choose between " + symbols.size() + " choices for resolving: " + idToken + ":");
            for (SmiSymbol symbol : symbols) {
                m_log.debug(symbol);
            }
        }
        return null;
    }

    private SmiSymbol determineBestMatchBasedOnOtherImports(IdToken idToken, List<SmiSymbol> symbols) {
        for (SmiSymbol symbol : symbols) {
            for (SmiImports imports : m_imports) {
                if (imports.getModule() == symbol.getModule()) {
                    m_log.debug("Determined best match for " + idToken + " based on other imports from " + symbol.getModule().getId());
                    return symbol;
                }
            }
        }
        return null;
    }

    private SmiSymbol determineBestMatchBasedOnSnmpVersion(List<SmiSymbol> symbols) {
        if (symbols.size() == 2) {
            SmiSymbol s0 = symbols.get(0);
            SmiSymbol s1 = symbols.get(1);
            SmiVersion version0 = s0.getModule().getVersion();
            SmiVersion version1 = s1.getModule().getVersion();
            if (version0 != null && version1 != null && version0 != version1) {
                if (getVersion() == version0) {
                    return s0;
                } else if (getVersion() == version1) {
                    return s1;
                }
            }
        }
        return null;
    }

    private SmiSymbol findImportedSymbol(String id) {
        for (SmiImports imports : m_imports) {
            SmiSymbol symbol = imports.find(id);
            if (symbol != null) {
                return symbol;
            }
        }
        return null;
    }

    public void resolveImports(XRefProblemReporter reporter) {
        for (SmiImports imports : m_imports) {
            imports.resolveImports(reporter);
        }
        // TODO check for imports with the same id
    }


    public String toString() {
        return m_idToken.toString();
    }
}
