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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SmiModule extends SmiSymbolContainer {

    private SmiMib m_mib;
    private IdToken m_idToken;

    private SmiScalarsClass m_scalarsClass;
    private List<SmiImports> m_imports = new ArrayList<SmiImports>();
    private List<SmiSymbol> m_symbols = new ArrayList<SmiSymbol>();

    public SmiModule(SmiMib mib, IdToken idToken) {
        super(mib);
        m_mib = mib;

        /*
        m_typeMap = new ContextMap<String, SmiType>(mib.m_typeMap);
        m_symbolMap = new ContextMap<String, SmiSymbol>(mib.m_symbolMap);
        m_classMap = new ContextMap<String, SmiClass>(mib.m_classMap);
        m_attributeMap = new ContextMap<String, SmiAttribute>(mib.m_attributeMap);
        m_scalarMap = new ContextMap<String, SmiScalar>(mib.m_scalarMap);
        m_tableMap = new ContextMap<String, SmiTable>(mib.m_tableMap);
        m_rowMap = new ContextMap<String, SmiRow>(mib.m_rowMap);
        m_columnMap = new ContextMap<String, SmiColumn>(mib.m_columnMap);
        */
        if (idToken != null) {
            setIdToken(idToken);

            // TODO  remove this ScalarsClass thing
            if (m_mib.getCodeNamingStrategy() != null) {
                String classId = m_mib.getCodeNamingStrategy().getModuleId(this) + "Scalars"; //TextUtil.makeTypeName(id);
                IdToken classIdToken = new IdToken(idToken.getLocation(), classId);
                // TODO move to ConceptualModelBuilderPhase
                m_scalarsClass = new SmiScalarsClass(classIdToken, this);
                m_classMap.put(classId, m_scalarsClass);
            }
        }
    }

    public void setIdToken(IdToken id) {
        assert(m_idToken == null);
        m_idToken = id;
        m_mib.addModule(id.getId(), this);
    }

    public IdToken getIdToken() {
        return m_idToken;
    }

    public String getId() {
        return m_idToken.getId();
    }

    @Override
    public Collection<SmiSymbol> getSymbols() {
        // TODO when the symbols have been resolved, set the m_symbols list to null?
        if (m_symbols != null) {
            return m_symbols;
        } else {
            return super.getSymbols();
        }
    }

    public SmiMib getMib() {
        return m_mib;
    }

    public SmiScalarsClass getScalarsClass() {
        return m_scalarsClass;
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

    public SmiSingleAttributeEnum createSingleAttributeEnum(SmiAttribute attribute) {
        SmiSingleAttributeEnum result = new SmiSingleAttributeEnum(attribute);
        m_typeMap.put(result.getId(), result);
        return result;
    }

    public SmiScalar createScalar(IdToken idToken) {
        SmiScalar scalar = new SmiScalar(idToken, m_scalarsClass);
        m_scalarMap.put(idToken.getId(), scalar);
        m_attributeMap.put(idToken.getId(), scalar);
        return scalar;
    }

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
        m_classMap.put(idToken.getId(), row);
        return row;
    }

    public SmiColumn createColumn(IdToken idToken) {
        SmiColumn col = new SmiColumn(idToken, this);
        m_columnMap.put(idToken.getId(), col);
        m_attributeMap.put(idToken.getId(), col);
        return col;
    }

    public String getFullAttributeOidClassId() {
        return getMib().getCodeNamingStrategy().getFullAttributeOidClassId(this);
    }

    public String getAttributeOidClassId() {
        return getMib().getCodeNamingStrategy().getAttributeOidClassId(this);
    }

    public void setMocMib(IdToken idToken) {
        setIdToken(idToken);
    }

    public void setMocRootOid(String oid) {
        // ignore
    }

    public SmiMultiRowClass createSmiMultiRowClass(IdToken idToken) {
        SmiMultiRowClass cl = new SmiMultiRowClass(this, idToken);
        m_classMap.put(idToken.getId(), cl); // TODO unique check
        return cl;
    }

    public SmiScalarsClass createSnmpGroupObject(IdToken idToken) {
        SmiScalarsClass cl = new SmiScalarsClass(idToken, this);
        m_classMap.put(idToken.getId(), cl); // TODO unique check
        return cl;
    }


    public List<SmiImports> getImports() {
        return m_imports;
    }

    public void fillTables() {
        for (SmiSymbol symbol : m_symbols) {
//            if (symbol instanceof SmiTable) {
//                m_tableMap.put(symbol.getId(), (SmiTable) symbol);
//            }
            put(m_tableMap, SmiTable.class, symbol);
            put(m_attributeMap, SmiAttribute.class, symbol);
            put(m_typeMap, SmiType.class, symbol);
            put(m_scalarMap, SmiScalar.class, symbol);
            put(m_columnMap, SmiColumn.class, symbol);
            put(m_rowMap, SmiRow.class, symbol);

        }
    }

    private <T extends SmiSymbol> void put(Map<String, T> map, Class<T> clazz, SmiSymbol symbol) {
        if (clazz.isInstance(symbol)) {
            map.put(symbol.getId(), (T) symbol);
        }
    }

}
