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

public class SmiModule extends SmiSymbolContainer {

	private static final Logger log = Logger.getLogger(SmiModule.class);
	
	private SmiMib mib_;
	private String id_;
	
	private SmiScalarsClass scalarsClass_;
	
	public SmiModule(SmiMib mib, String id) {
		super(mib);
		mib_ = mib;
		
		typeMap_ = new ContextMap<String, SmiType>(mib.typeMap_);
		symbolMap_ = new ContextMap<String, SmiSymbol>(mib.symbolMap_);
		classMap_ = new ContextMap<String, SmiClass>(mib.classMap_);
		attributeMap_ = new ContextMap<String, SmiAttribute>(mib.attributeMap_);
		scalarMap_ = new ContextMap<String, SmiScalar>(mib.scalarMap_);
		tableMap_ = new ContextMap<String, SmiTable>(mib.tableMap_);
		rowMap_ = new ContextMap<String, SmiRow>(mib.rowMap_);
		columnMap_ = new ContextMap<String, SmiColumn>(mib.columnMap_);

		if (id != null) {
			setId(id);
			
			String classId = mib_.getCodeNamingStrategy().getModuleId(this) + "Scalars"; //TextUtil.makeTypeName(id);
			scalarsClass_ = new SmiScalarsClass(classId, this);
			classMap_.put(classId, scalarsClass_);
		}
	}

	public void setId(String id) {
		assert(id_ == null);
		id_ = id;
		mib_.addModule(id, this);
	}
	
	public String getId() {
		return id_;
	}
	
	public SmiMib getMib() {
		return mib_;
	}
	
	public SmiScalarsClass getScalarsClass() {
		return scalarsClass_;
	}
	
	public SmiType createType(String id) {
		SmiType type = new SmiType(id, this);
		typeMap_.put(id, type);
		return type;
	}
	
	public SmiTextualConvention createTextualConvention(String id) {
		SmiTextualConvention tc = new SmiTextualConvention(id, this);
		typeMap_.put(id, tc);
		return tc;
	}

	public SmiSingleAttributeEnum createSingleAttributeEnum(SmiAttribute attribute) {
		SmiSingleAttributeEnum result = new SmiSingleAttributeEnum(attribute);
		typeMap_.put(result.getId(), result);
		return result;
	}
	
	public SmiScalar createScalar(String id) {
		SmiScalar scalar = new SmiScalar(id, scalarsClass_);
		scalarMap_.put(id, scalar);
		attributeMap_.put(id, scalar);
		return scalar;
	}
	
	public String getCodeId() {
		return getMib().getCodeNamingStrategy().getModuleId(this);
	}
	
	public String getFullCodeId() {
		return getMib().getCodeNamingStrategy().getFullModuleId(this);
	}

	public SmiTable createTable(String id) {
		SmiTable table = new SmiTable(id, this);
		tableMap_.put(id, table);
		return table;
	}

	public SmiRow createRow(String id) {
		SmiRow row = new SmiRow(id, this);
		rowMap_.put(id, row);
		classMap_.put(id, row);
		return row;
	}

	public SmiColumn createColumn(String id) {
		SmiColumn col = new SmiColumn(id, this);
		columnMap_.put(id, col);
		attributeMap_.put(id, col);
		return col;
	}

	public String getFullAttributeOidClassId() {
		return getMib().getCodeNamingStrategy().getFullAttributeOidClassId(this);
	}

	public String getAttributeOidClassId() {
		return getMib().getCodeNamingStrategy().getAttributeOidClassId(this);
	}

	public void setMocMib(String id) {
		setId(id);
	}
	
	public void setMocRootOid(String oid) {
		// ignore
	}
	
	public SmiMultiRowClass createSmiMultiRowClass(String id) {
		SmiMultiRowClass cl = new SmiMultiRowClass(this, id);
		classMap_.put(id, cl); // TODO unique check
		return cl;
	}
	
	public SmiScalarsClass createSnmpGroupObject(String id) {
		SmiScalarsClass cl = new SmiScalarsClass(id, this);
		classMap_.put(id, cl); // TODO unique check
		return cl;
	}


}
