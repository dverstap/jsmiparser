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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SmiMib extends SmiSymbolContainer {

	private Map<String, SmiModule> moduleMap_ = new HashMap<String, SmiModule>();
	
	private SmiCodeNamingStrategy codeNamingStrategy_;
	
	public SmiMib(SmiCodeNamingStrategy codeNamingStrategy) {
		super(null);
		assert(codeNamingStrategy != null);
		codeNamingStrategy_ = codeNamingStrategy;
	}
	
	public SmiModule findModule(String id) {
		return moduleMap_.get(id);
	}
	
	public Collection<SmiModule> getModules() {
		return moduleMap_.values();
		
	}
	
	public SmiCodeNamingStrategy getCodeNamingStrategy() {
		return codeNamingStrategy_;
	}

	public void setCodeNamingStrategy(SmiCodeNamingStrategy codeNamingStrategy) {
		codeNamingStrategy_ = codeNamingStrategy;
	}

	public SmiModule createModule(String id) {
		return new SmiModule(this, id);
	}
	
	public SmiModule createModule() {
		// used to create class definition module from XML using the ReflectContentHandler.
		return new SmiModule(this, null);
	}
	
	public void determineInheritanceRelations() {
		for (SmiRow row : rowMap_.values()) {
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
		moduleMap_.put(id, module);		
	}

}
