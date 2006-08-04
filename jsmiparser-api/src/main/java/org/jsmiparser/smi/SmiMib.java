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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SmiMib extends SmiSymbolContainer {

	private Map<String, SmiModule> m_moduleMap = new HashMap<String, SmiModule>();
	
	private SmiCodeNamingStrategy m_codeNamingStrategy;
	
	public SmiMib(SmiCodeNamingStrategy codeNamingStrategy) {
		super(null);
		assert(codeNamingStrategy != null);
		m_codeNamingStrategy = codeNamingStrategy;
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
        }
    }
}
