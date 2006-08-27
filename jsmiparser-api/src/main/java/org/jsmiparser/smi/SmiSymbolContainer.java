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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SmiSymbolContainer {
	private static final Logger log = Logger.getLogger(SmiSymbolContainer.class);

	private SmiSymbolContainer m_parent;

	Map<String, SmiType> m_typeMap = new LinkedHashMap<String, SmiType>();
	Map<String, SmiSymbol> m_symbolMap = new LinkedHashMap<String, SmiSymbol>();
	Map<String, SmiClass> m_classMap = new LinkedHashMap<String, SmiClass>();
	Map<String, SmiAttribute> m_attributeMap = new LinkedHashMap<String, SmiAttribute>();
	Map<String, SmiScalar> m_scalarMap = new LinkedHashMap<String, SmiScalar>();
	Map<String, SmiTable> m_tableMap = new LinkedHashMap<String, SmiTable>();
	Map<String, SmiRow> m_rowMap = new LinkedHashMap<String, SmiRow>();
	Map<String, SmiColumn> m_columnMap = new LinkedHashMap<String, SmiColumn>();
	
	
	
	public SmiSymbolContainer(SmiSymbolContainer parent) {
		super();
		log.debug("SmiSymbolContainer constructor");
		m_parent = parent;
	}
	
	public SmiType findType(String id) {
		return m_typeMap.get(id);
	}
	
	public Collection<SmiType> getTypes() {
		return m_typeMap.values();
	}

	public Collection<SmiSymbol> getSymbols() {
		return m_symbolMap.values();
	}
	
	public SmiSymbol findSymbol(String id) {
		return m_symbolMap.get(id);
	}
	
	public SmiClass findClass(String id) {
		return m_classMap.get(id);
	}
	
	public Collection<SmiClass> getClasses() {
		return m_classMap.values();
	}
	
	public SmiAttribute findAttribute(String id) {
		return m_attributeMap.get(id);
	}

	public Collection<SmiAttribute> getAttributes() {
		return m_attributeMap.values();
	}
	
	public SmiScalar findScalar(String id) {
		return m_scalarMap.get(id);
	}
	
	public Collection<SmiScalar> getScalars() {
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
	
	public SmiColumn findColumn(String id) {
		return m_columnMap.get(id);
	}

    public Collection<SmiColumn> getColumns() {
        return m_columnMap.values();
    }

}
