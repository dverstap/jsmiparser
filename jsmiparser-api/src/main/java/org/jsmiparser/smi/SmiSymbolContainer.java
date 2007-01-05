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

public interface SmiSymbolContainer {

	public SmiType findType(String id);
	
	public Collection<SmiType> getTypes();

	public Collection<SmiSymbol> getSymbols();
	
	public SmiSymbol findSymbol(String id);

	public SmiVariable findVariable(String id);

	public Collection<SmiVariable> getVariables();
	
	public SmiVariable findScalar(String id);
	
	public Collection<SmiVariable> getScalars();

	public SmiTable findTable(String id);
	
	public Collection<SmiTable> getTables();
	
	public SmiRow findRow(String id);
	
	public Collection<SmiRow> getRows();

    public SmiVariable findColumn(String id);

    public Collection<SmiVariable> getColumns();

}
