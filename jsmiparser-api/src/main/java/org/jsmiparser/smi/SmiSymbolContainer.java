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
import java.util.HashMap;
import java.util.Map;

public abstract class SmiSymbolContainer {
	private static final Logger log = Logger.getLogger(SmiSymbolContainer.class);
	
	private SmiSymbolContainer parent_;
	
	protected Map<String, SmiType> typeMap_ = new HashMap<String, SmiType>();
	protected Map<String, SmiSymbol> symbolMap_ = new HashMap<String, SmiSymbol>();
	protected Map<String, SmiClass> classMap_ = new HashMap<String, SmiClass>();
	protected Map<String, SmiAttribute> attributeMap_ = new HashMap<String, SmiAttribute>();
	protected Map<String, SmiScalar> scalarMap_ = new HashMap<String, SmiScalar>();
	protected Map<String, SmiTable> tableMap_ = new HashMap<String, SmiTable>();
	protected Map<String, SmiRow> rowMap_ = new HashMap<String, SmiRow>();
	protected Map<String, SmiColumn> columnMap_ = new HashMap<String, SmiColumn>();
	
	
	
	public SmiSymbolContainer(SmiSymbolContainer parent) {
		super();
		log.debug("SmiSymbolContainer constructor");
		parent_ = parent;
	}
	
	public SmiType findType(String id) {
		return typeMap_.get(id);
	}
	
	public Collection<SmiType> getTypes() {
		return typeMap_.values();
	}

	public Collection<SmiSymbol> getSymbols() {
		return symbolMap_.values();
	}
	
	public SmiSymbol findSymbol(String id) {
		return symbolMap_.get(id);
	}
	
	public SmiClass findClass(String id) {
		return classMap_.get(id);
	}
	
	public Collection<SmiClass> getClasses() {
		return classMap_.values();
	}
	
	public SmiAttribute findAttribute(String id) {
		return attributeMap_.get(id);
	}

	public Collection<SmiAttribute> getAttributes() {
		return attributeMap_.values();
	}
	
	public SmiScalar findScalar(String id) {
		return scalarMap_.get(id);
	}
	
	public Collection<SmiScalar> getScalars() {
		return scalarMap_.values();
	}
	
	public SmiTable findTable(String id) {
		return tableMap_.get(id);
	}
	
	public Collection<SmiTable> getTables() {
		return tableMap_.values();
	}
	
	public SmiRow findRow(String id) {
		return rowMap_.get(id);
	}
	
	public Collection<SmiRow> getRows() {
		return rowMap_.values();
	}
	
	public SmiColumn findColumn(String id) {
		return columnMap_.get(id);
	}
	public Collection<SmiColumn> getColumns()
	{
		return columnMap_.values();
	}

}
